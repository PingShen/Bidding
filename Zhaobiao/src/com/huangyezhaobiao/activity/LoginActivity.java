package com.huangyezhaobiao.activity;
import com.huangye.commonlib.activity.BaseActivity;
import com.huangye.commonlib.vm.callback.NetWorkVMCallBack;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.bean.LoginBean;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.AnimationController;
import com.huangyezhaobiao.utils.UserUtils;
import com.huangyezhaobiao.view.LoadingProgress;
import com.huangyezhaobiao.view.ZhaoBiaoDialog;
import com.huangyezhaobiao.view.ZhaoBiaoDialog.onDialogClickListener;
import com.huangyezhaobiao.vm.LoginViewModel;
import com.xiaomi.mipush.sdk.MiPushClient;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 登陆页面
 * @author linyueyang
 *
 */
public class LoginActivity extends BaseActivity implements NetWorkVMCallBack, onDialogClickListener {
	private EditText username;
	private EditText password;
	private ImageButton nCloseBtn;
	private ImageButton pCloseBtn;
	private Button loginbutton;
	private ImageView userIcon;
	private ImageView passwordIcon;
	private LoginViewModel loginViewModel;
	private ZhaoBiaoDialog dialog;
	private LoadingProgress loading; 
	private AnimationController animationController;
	
	private String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initListener();
		loginViewModel = new LoginViewModel(this, this);
		dialog = new ZhaoBiaoDialog(this, "提示", "登录失败，您输入的账户名和密码不符!");
		dialog.setCancelButtonGone();
		dialog.setOnDialogClickListener(this);
		
	}


	@Override
	public void initView() {
		username = (EditText) findViewById(R.id.username);
		if(!TextUtils.isEmpty(UserUtils.getUserName(LoginActivity.this)))
		{
			username.setText(UserUtils.getUserName(LoginActivity.this));
		}
		password = (EditText) findViewById(R.id.password);
		username.clearFocus();
		nCloseBtn = (ImageButton) findViewById(R.id.nCloseBtn);
		pCloseBtn = (ImageButton) findViewById(R.id.pCloseBtn);
		loginbutton = (Button) findViewById(R.id.loginbutton);
		userIcon = (ImageView) findViewById(R.id.userIcon);
		passwordIcon = (ImageView) findViewById(R.id.passwordIcon);
		animationController = new AnimationController();
	}
	@Override
	public void initListener() {
		loginbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String name = username.getText().toString();
				userName = name;
				String passwords = password.getText().toString();
				//需要加密
				
				if(TextUtils.isEmpty(name)){
					dialog.setMessage("请输入用户名！");
					dialog.show();
					return;
				}
				if(TextUtils.isEmpty(passwords)){
					dialog.setMessage("请输入密码!");
					dialog.show();
					return;
				}
				startLoading();
		    	loginViewModel.login(name, passwords);
				
			}
		});
		//用户名密码的动画控制 start
		username.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if(userIcon.getVisibility()!=View.VISIBLE)
						animationController.slideIn(userIcon, 500, 0);
					if(!TextUtils.isEmpty(username.getText().toString())){
						if(nCloseBtn.getVisibility()!=View.VISIBLE)
							animationController.fadeIn(nCloseBtn, 1000, 0);
					}
				} else {
					if(TextUtils.isEmpty(username.getText().toString()))
						animationController.slideOut(userIcon, 500, 0);
					// 此处为失去焦点时的处理内容
				}
			}
		});
		username.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable arg0) {

				if(!TextUtils.isEmpty(username.getText().toString())){
					if(nCloseBtn.getVisibility()!=View.VISIBLE)
						animationController.fadeIn(nCloseBtn, 1000, 0);
				}
				else{
					animationController.fadeOut(nCloseBtn, 1000, 0);
				}
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {}
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}
			
		});
		nCloseBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				username.setText("");
			}
		});
		password.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if(passwordIcon.getVisibility()!=View.VISIBLE)
						animationController.slideIn(passwordIcon, 500, 0);
				} else {
					if(TextUtils.isEmpty(password.getText().toString()))
						animationController.slideOut(passwordIcon, 500, 0);
					// 此处为失去焦点时的处理内容
				}
			}
		});
		password.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable arg0) {

				if(!TextUtils.isEmpty(password.getText().toString())){
					if(pCloseBtn.getVisibility()!=View.VISIBLE)
						animationController.fadeIn(pCloseBtn, 1000, 0);
				}
				else{
					animationController.fadeOut(pCloseBtn, 1000, 0);
				}
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {}
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}
			
		});
		pCloseBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				password.setText("");
			}
		});
		//用户名密码的动画控制 end
	}

	//网络层回调

	@Override
	public void onLoadingStart() {
		
	}

	@Override
	public void onLoadingSuccess(Object t) {
		stopLoading();
//		if (t instanceof Map<?, ?>) {
//			Map<String, String> map = (Map<String, String>) t;
//			String userId = map.get("userId");
//			String companyName = map.get("companyName");
//			//String 
//			
//			
//			Log.e("asdas", "userId:" + userId + ",companyName:" + companyName);
//			UserUtils.saveUser(this, userId, companyName);
//
//			// 用于测试，写死数据"24454277549825",实际用UserUtils.getUserId(LoginActivity.this)
//			MiPushClient.setAlias(LoginActivity.this, "24454277549825", null);
//			// UserUtils.getUserId(LoginActivity.this)
//			Log.e("成功注册,注册token：", UserUtils.getUserId(LoginActivity.this) + "");
//			ActivityUtils.goToActivity(LoginActivity.this, MobileValidateActivity.class);
//			finish();
//
//		}
		
		if (t instanceof LoginBean) {
			LoginBean loginBean = (LoginBean)t;
			long userId = loginBean.getUserId();
			String companyName = loginBean.getCompanyName();
			int hasValidated =  loginBean.getHasValidated();
			
			Log.e("asdas", "userId:" + userId + ",companyName:" + companyName);
			UserUtils.saveUser(this, userId+"", companyName,userName);

			// 用于测试，写死数据"24454277549825",实际用UserUtils.getUserId(LoginActivity.this)
			MiPushClient.setAlias(LoginActivity.this, "24454277549825", null);
			// UserUtils.getUserId(LoginActivity.this)
			Log.e("成功注册,注册token：", UserUtils.getUserId(LoginActivity.this) + "");
			
			//判断是否验证过手机
			if(hasValidated==0)
				ActivityUtils.goToActivity(LoginActivity.this, MobileValidateActivity.class);
			else
				ActivityUtils.goToActivity(LoginActivity.this, MainActivity.class);
			finish();

		}
		
		

	}

	@SuppressLint("ShowToast")
	@Override
	public void onLoadingError(String msg) {
		stopLoading();
		//Toast.makeText(this, msg, 0).show();
		//TODO:判断一下是不是在当前界面
		
		Log.e("asajiss", "isRoot:"+isTaskRoot());
		try {
			if(dialog!=null && isTaskRoot()){
				dialog.setMessage(msg);
				dialog.show();
			}
		} catch (Exception e) {
			Toast.makeText(this, msg, 0).show();
		}
		
		
	}

	@Override
	public void onLoadingCancel() {
		stopLoading();
	}

	@SuppressLint("ShowToast")
	@Override
	public void onNoInterNetError() {
		Toast.makeText(this, "当前没有网络", 0).show();
		stopLoading();
	}


	@Override
	public void onDialogOkClick() {
		dialog.dismiss();
	}


	@Override
	public void onDialogCancelClick() {
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(dialog!=null && dialog.isShowing()){
			dialog.dismiss();
			dialog = null;
		}
	}


	/**
	 * 加载效果
	 */
	public void startLoading() {
		if (loading == null) {
			loading = new LoadingProgress(LoginActivity.this,
					R.style.loading);
		}
		loading.show();
	}

	/**
	 * 对话框消失
	 */
	public void stopLoading() {
		if (loading != null && loading.isShowing()) {
			loading.dismiss();
			loading = null;
		}
	}

}
