package com.huangyezhaobiao.activity;

import com.huangyezhaobiao.R;
import com.huangyezhaobiao.inter.Constans;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.CommonUtils;
import com.huangyezhaobiao.utils.UserUtils;
import com.huangyezhaobiao.utils.VersionUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

/**
 * 引导界面，每次都进入这个界面，闪屏3秒后会进入界面 ---第一次 guideActivity ---或者版本变化了 guideActivity else
 * ---没登录 LoginActivity else ---登录了 MainActivity
 * 
 * @author 58
 * 
 */
public class SplashActivity extends QBBaseActivity {
	private static final long DELAYED_TIMES = 3 * 1000;
	private SharedPreferences sp;
	private Handler handler = new Handler();
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		context = this;
		// 等三秒
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				sp = getSharedPreferences(Constans.APP_SP, 0);
				String currentVersionName = "1.0.0";
				try {
					currentVersionName = VersionUtils.getVersionName(context);
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
				String saveVersionName = sp.getString(Constans.VERSION_NAME,
						"1.0.0");
				String mCurrentVersionName = currentVersionName
						.replace(".", "");
				Log.e("ashenVersion", "cvN:" + currentVersionName);
				Log.e("ashenVersion",
						"isFirst:" + sp.getBoolean("isFirst", true));
				if(sp.getBoolean("isFirst", true) ||!CommonUtils.compareTwoNumbers(saveVersionName,
						mCurrentVersionName) ){//进入引导界面
					ActivityUtils.goToActivity(context, GuideActivity.class);
				}else if(TextUtils.isEmpty(UserUtils.getUserId(context))){//如果没有登录过
					ActivityUtils.goToActivity(context, LoginActivity.class);
				}else if(!UserUtils.isValidate(context)){//还未验证，走验证界面
					ActivityUtils.goToActivity(context, MobileValidateActivity.class);
				}else{//走主界面
					ActivityUtils.goToActivity(context, MainActivity.class);
				}
				finish();
				sp.edit()
						.putString(Constans.VERSION_NAME,
								currentVersionName).commit();
				return;
			}
		}, DELAYED_TIMES);

	}

	@Override
	public void initView() {

	}

	@Override
	public void initListener() {

	}

}
