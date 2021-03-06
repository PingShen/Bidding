package com.huangyezhaobiao.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Currency;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.huangye.commonlib.network.HTTPTools;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.activity.MainActivity;
import com.huangyezhaobiao.view.ZhaoBiaoDialog;
import com.huangyezhaobiao.view.ZhaoBiaoDialog.onDialogClickListener;

/**
 * 更新的管理器
 * @author shenzhixin
 *						1.判断是否需要更新的方法
 *						2.弹出是否下载对话框的方法，以及点击事件的回调函数
 *						3.下载功能，以及下载的dialog，以及下载界面的回调，下载中的取消
 *						4.下载成功，下载失败
 *						
 *
 * 1.确认对话框及其控件
 * 2.下载对话框及其控件
 * 
 */		
public class UpdateManager {
	public static final int DOWNLOADING = 1;
	public static final int DOWNLOAD_FINISH = 2;
	private int progress = 0;//下载的百分比
	private int max;
	private Context context;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DOWNLOADING:
				pb_downloading.setProgress(progress);
				Log.e("ashenashenUpdate", "progress:"+progress+",total:"+pb_downloading.getMax());
				break;
			case DOWNLOAD_FINISH:
				//去安装
				dismissDownloadingDialog(context);
				installApk(context);
				break;
			}
			
		};
	};
	private static UpdateManager manager = new UpdateManager();
	private UpdateManager(){};
	
	/**
	 * 安装apk
	 */
	private void installApk(Context context) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(ApkFile),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 弹出是否下载的对话框
	 */
	private ZhaoBiaoDialog confirmUpdateDialog;
	/**
	 * 下载中的对话框
	 */
	private AlertDialog downloadDialog;
	
	private ProgressBar    pb_downloading;
	private RelativeLayout rl_cancel;
	private volatile boolean interceptFlag = false;
	private File ApkFile;
	public static boolean needUpdate = false;//是否要强制更新的状态
	public static UpdateManager getUpdateManager(){
		return manager;
	}
	
	
	
	
	/**
	 * 显示是否下载的对话框
	 * @param context
	 * @param url
	 */
	public void showConfirmDownloadDialog(final Context context,final String url){
		this.context = context;
		if(confirmUpdateDialog==null){
			confirmUpdateDialog = new ZhaoBiaoDialog(context, "app更新", "是否进行新版本的更新");
			confirmUpdateDialog.setCancelable(false);
			confirmUpdateDialog.setOnDialogClickListener(new onDialogClickListener() {
				@Override
				public void onDialogOkClick() {
					dismissConfirmDownloadDialog();
					startDownloading(context,url);
				}
				
				@Override
				public void onDialogCancelClick() {
					dismissConfirmDownloadDialog();
					((MainActivity)context).finish();//退出应用
				}
			});
		}
		confirmUpdateDialog.show();
	}
	
	/**
	 * 开始下载
	 * 			1.展示dialog
	 * 			2.后台下载
	 * 			3.回调ui
	 * 			4.取消时
	 */
	protected void startDownloading(final Context context,String url) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(context, "未安装sd卡", 0).show();
			return;
		}
		if (!NetUtils.isNetworkConnected(context)) {
			Toast.makeText(context, "网络未连接", 0).show();
			return;
		}
		if(downloadDialog==null){
			AlertDialog.Builder builder = new Builder(context);
			final View view = LayoutInflater.from(context).inflate(R.layout.dialog_downloading, null);
			pb_downloading = (ProgressBar) view.findViewById(R.id.pb_downloading);
			rl_cancel      = (RelativeLayout) view.findViewById(R.id.rl_cancel);
			rl_cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					cancelDownloading(context);
				}
			});
			downloadDialog = builder.create();
			downloadDialog.setCancelable(false);
			downloadDialog.show();
			downloadDialog.getWindow().setContentView(view);
		}
		Log.e("ashendownload", "line...164");
		//开启下载任务
		DownloadThread thread = new DownloadThread(url,"update.apk");
		thread.start();
	}




	/**
	 * 取消下载
	 * @param context
	 */
	protected void cancelDownloading(Context context) {
		//取消任务
		//对话框取消
		dismissDownloadingDialog(context);
		interceptFlag = true;
		
	}




	/**
	 * 取消下载对话框
	 * @param context
	 */
	private void dismissDownloadingDialog(Context context) {
		if(downloadDialog!=null && downloadDialog.isShowing()){
			downloadDialog.dismiss();
			downloadDialog = null;
			((MainActivity)context).finish();//退出
		}
	}





	/**
	 * 取消确认对话框
	 */
	private void dismissConfirmDownloadDialog(){
		if(confirmUpdateDialog!=null && confirmUpdateDialog.isShowing()){
			confirmUpdateDialog.dismiss();
			confirmUpdateDialog = null;
		}
	}
	
	
	/**
	 * 检查是否需要更新
	 * @return
	 * @throws NameNotFoundException 
	 */
	public void isUpdateNow(Context context,String version,String url) throws NameNotFoundException
	{
		String currentVersion = VersionUtils.getVersionName(context);
		currentVersion = currentVersion.replace(".", "");
		version        = version.replace(".", "");
		if(CommonUtils.compareTwoNumbers(version, currentVersion)){
			showConfirmDownloadDialog(context, url);
			needUpdate  = true;
		}else{
			needUpdate = false;
		}
	}
	
	
	
	/**
	 * 下载的任务
	 * @author shenzhixin
	 *
	 */
	class DownloadThread extends Thread{
		private String apkUrl;
		private String saveFileName;
		public DownloadThread(String apkUrl,String saveFileName) {
			this.apkUrl       = apkUrl;
			this.saveFileName = saveFileName;
			Log.e("ashendownload", "line...246");
		}
		@Override
		public void run() {
			URL url;
			try {
				Log.e("ashendownload", "line...252");
			  Log.e("ashengjgj", "url:"+apkUrl);
			  url = new URL(apkUrl);
			  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			  conn.connect();
			  Log.e("ashengjgj", "after connection");
			  int length = conn.getContentLength();
			  max = length;
			  handler.post(new Runnable() {
				@Override
				public void run() {
					pb_downloading.setMax(100);
				}
			});
			  InputStream ins = conn.getInputStream();
			  ApkFile  = new File(Environment.getExternalStorageDirectory(),
					  saveFileName);
			 
			  FileOutputStream outStream = new FileOutputStream(ApkFile);
			  int count = 0;
			  byte buf[] = new byte[1024];
			  do {
			    int numread = ins.read(buf);
			    count += numread;
			    Log.e("ashendownload", "count..."+count+",length:"+length);
			    progress = (int) (((float) count / length) * 100);
			    Log.e("ashendownload", "progress..."+progress);
			    // 下载进度
			    handler.sendEmptyMessage(DOWNLOADING);
			    if (numread <= 0) {
			     // 下载完成通知安装
			     handler.sendEmptyMessage(DOWNLOAD_FINISH);
			     break;
			   }
			     outStream.write(buf, 0, numread);
			     Log.e("ashendownload", "interceptFlag:"+interceptFlag);
			    } while (!interceptFlag);// 点击取消停止下载
				  outStream.close();
				  ins.close();
			   } catch (Exception e) {
			    e.printStackTrace();
			    ((MainActivity)context).finish();
	         }
	  }
   };
		
	
}
