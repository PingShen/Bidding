package com.huangyezhaobiao.application;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.format.Time;
import android.util.Log;

import com.huangyezhaobiao.bean.AppBean;
import com.huangyezhaobiao.inter.INotificationListener;
import com.huangyezhaobiao.log.LogHandler;
import com.huangyezhaobiao.push.BiddingMessageReceiver.PushHandler;
import com.huangyezhaobiao.receiver.NetworkChangedReceiver;
import com.huangyezhaobiao.utils.FileUtils;
import com.huangyezhaobiao.voice.VoiceManager;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;
import com.tencent.bugly.crashreport.CrashReport;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * 1、为了打开客户端的日志，便于在开发过程中调试，需要自定义一个Application。
 * 并将自定义的application注册在AndroidManifest.xml文件中
 * 2、为了提高push的注册率，您可以在Application的onCreate中初始化push。你也可以根据需要，在其他地方初始化push。
 * 
 * @author linyueyang
 * 
 */
public class BiddingApplication extends Application {
	private INotificationListener listener;// 透传消息的引用
	private NetworkChangedReceiver receiver;
	// user your appid the key.
	//public static final String APP_ID = "2882303761517351320";
	public static final String APP_ID = "2882303761517362207";
	// user your appid the key.
	//public static final String APP_KEY = "5471735123320";
	public static final String APP_KEY = "5321736232207";

	// 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
	// com.xiaomi.mipushdemo
	public static final String TAG = "com.huangyezhaobiao";

	private static PushHandler handler = null;

	private static LogHandler loghandler = null;
	private Timer mTimer;
	private VoiceManager manager;
	public void setCurrentNotificationListener(INotificationListener listener) {
		this.listener = listener;
	}

	public void removeINotificationListener() {
		listener = null;
	}

	public INotificationListener getCurrentNotificationListener() {
		return listener;
	}

	/**
	 * 停止文件操作
	 */
	public void stopTimer(){
		if(mTimer!=null){
			mTimer.cancel();
		}
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		AppBean.getAppBean().setApp(this);
		manager = VoiceManager.getVoiceManagerInstance(this);
		manager.initVoiceManager(this);//初始化科大讯飞
		// 注册push服务，注册成功后会向DemoMessageReceiver发送广播
		// 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
		// 因为推送服务XMPushService在AndroidManifest.xml中设置为运行在另外一个进程，这导致本Application会被实例化两次，所以我们需要让应用的主进程初始化。
		if (shouldInit()) {
			MiPushClient.registerPush(this, APP_ID, APP_KEY);
			
		//	XingeRegister();
			// 上传日志定时任务
			mTimer = new Timer(true);
			TimerTask mTimerTask;
			mTimerTask = new TimerTask() {
				@Override
				public void run() {
					FileUtils.read(getApplicationContext());
				}
			};
			mTimer.schedule(mTimerTask, 5000, 1000*10);//10分钟传一次
			
			// 写日志线程
			new Thread(new Runnable() {
				@Override
				public void run() {

					if (loghandler == null) {
						Looper.prepare();
						loghandler = new LogHandler(getApplicationContext());
						Looper.loop();
					}
				}
			}).start();
		}

		Log.e("Thread", Thread.currentThread().getName());

		// 接入腾讯Bugly
		Context appContext = this.getApplicationContext();
		String appId = "900004313"; // 上Bugly(bugly.qq.com)注册产品获取的AppId
		boolean isDebug = true; // true代表App处于调试阶段，false代表App发布阶段
		CrashReport.initCrashReport(appContext, appId, isDebug); // 初始化SDK
		// CrashReport.testJavaCrash ();
		LoggerInterface newLogger = new LoggerInterface() {
			@Override
			public void setTag(String tag) {
				// ignore
			}
			@Override
			public void log(String content, Throwable t) {
				Log.d(TAG, content, t);
			}

			@Override
			public void log(String content) {
				Log.d(TAG, content);
			}
		};
		Logger.setLogger(this, newLogger);
		if (handler == null)
			handler = new PushHandler(getApplicationContext());

	}

	/**
	 * 注册网络变化的receiver
	 */
	public void registerNetStateListener() {
		receiver = new NetworkChangedReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(receiver, filter);

	}

	/**
	 * 解绑网络变化的receiver
	 */
	public void unRegisterNetStateListener() {
		if (receiver != null) {
			unregisterReceiver(receiver);
			receiver = null;
		}
	}

	/*private void XingeRegister() {
		// 信鸽注册
		// 为保证弹出通知前一定调用本方法，需要在application的onCreate注册
		// 收到通知时，会调用本回调函数。
		// 相当于这个回调会拦截在信鸽的弹出通知之前被截取
		// 一般上针对需要获取通知内容、标题，设置通知点击的跳转逻辑等等
		XGPushManager.setNotifactionCallback(new XGPushNotifactionCallback() {
			@Override
			public void handleNotify(XGNotifaction xGNotifaction) {
				Log.i("test", "处理信鸽通知：" + xGNotifaction);
				// 获取标签、内容、自定义内容
				String title = xGNotifaction.getTitle();
				String content = xGNotifaction.getContent();
				String customContent = xGNotifaction.getCustomContent();
				// 其它的处理
				// 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，否则，本通知将不会弹出在通知栏中。
				xGNotifaction.doNotify();
			}
		});
	}*/

	// 判断application是否已经被实例化
	private boolean shouldInit() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = Process.myPid();
		for (RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}

	
	//判断应用程序是否在前台
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					Log.i("后台", appProcess.processName);
					return true;
				} else {
					Log.i("前台", appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}

	public static PushHandler getHandler() {
		return handler;
	}

	public static LogHandler getLogHandler() {
		return loghandler;
	}

	private class MyDemoHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// baseActivity.showDialog();
		}

	}

}