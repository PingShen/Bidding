package com.huangyezhaobiao.activity;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huangye.commonlib.vm.callback.NetWorkVMCallBack;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.bean.push.PushBean;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.ToastUtils;
import com.huangyezhaobiao.utils.UserUtils;
import com.huangyezhaobiao.vm.ValidateViewModel;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 手机验证页面
 * 
 * @author linyueyang
 *
 */
public class MobileValidateActivity extends QBBaseActivity implements NetWorkVMCallBack {

	private Button commit;
	private TextView txt_head;
	private EditText mobile;
	private EditText code;
	private Button getcode;
	private int countDown;
	
	private ValidateViewModel viewModel;
	private LinearLayout back_layout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_valid);
		initView();
		initListener();
		
		viewModel = new ValidateViewModel(this,this);
		
	}

	@Override
	public void initView() {
		txt_head = (TextView) findViewById(R.id.txt_head);
		txt_head.setText("手机验证");
		commit = (Button) findViewById(R.id.commit);
		mobile = (EditText) findViewById(R.id.validate_mobile);
		code = (EditText) findViewById(R.id.validate_code);
		getcode = (Button) findViewById(R.id.validate_getcode);
		back_layout = (LinearLayout) this.findViewById(R.id.back_layout);
	}

	@Override
	public void initListener() {
		getcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String mobiletext = mobile.getText().toString();

				if (isMobile(mobiletext)) {
					viewModel.getCode(UserUtils.userId, mobiletext);
				} else {
					ToastUtils.makeImgAndTextToast(MobileValidateActivity.this, "请输入正确的手机号", R.drawable.validate_error, 0).show();
				}
			}
		});
		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				String mobiletext = mobile.getText().toString();
//				String codetext = code.getText().toString();
//				
//				if (!isMobile(mobiletext)) {
//					ToastUtils.makeImgAndTextToast(MobileValidateActivity.this, "请输入正确的手机号", R.drawable.validate_error, 0).show();
//				}
//				else if(!isCode(codetext)){
//					ToastUtils.makeImgAndTextToast(MobileValidateActivity.this, "请输入正确的验证码", R.drawable.validate_error, 0).show();
//				}
//				else{
//					viewModel.validate(UserUtils.userId, mobiletext, codetext);
//				}
				
				ActivityUtils.goToActivity(MobileValidateActivity.this, MainActivity.class);
				finish();
				
			}

		});
		
		back_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}
	
	
	private void countdown(){
		countDown = 5;
        handler.postDelayed(runnable, 0);  
	}
	
	Handler handler = new Handler();  
    Runnable runnable = new Runnable() {  
		@Override  
        public void run() {  
        	countDown--;  
        	ColorStateList color;
            if(countDown>=0){
            	getcode.setClickable(false);
            	getcode.setText(countDown+"s后重新发送"); 
            	color = MobileValidateActivity.this.getResources().getColorStateList(R.color.whitedark);
            	getcode.setTextColor(color);
            	handler.postDelayed(this, 1000);  
            }
            else{
            	getcode.setClickable(true);
            	color = MobileValidateActivity.this.getResources().getColorStateList(R.color.red);
            	getcode.setTextColor(color);
             	getcode.setText("获取验证码"); 
            	handler.removeCallbacks(runnable);
            }
            
            
        }  
    };  
	

	
	
	protected boolean isCode(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[0-9]{6}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;

	 }
                                                                                                                                                                                                                                                                                 	/**                
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	@Override
	public void NetConnected() {

	}

	@Override
	public void NetDisConnected() {

	}

	@Override
	public void onNotificationCome(PushBean pushBean) {

	}

	@Override
	public void onLoadingStart() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onLoadingSuccess(Object t) {
		
		if (t instanceof String){
			String status = (String)t;
			if(status.equals("0")){
				ToastUtils.makeImgAndTextToast(MobileValidateActivity.this, "验证码已发送，请耐心等待", R.drawable.validate_done, 0).show();
				countdown();
			}
		}else if(t instanceof Map){
			Map<String, String> map = (Map<String, String>) t;
			String status = map.get("status");
			if(status.equals("0")){
				ActivityUtils.goToActivity(MobileValidateActivity.this, MainActivity.class);
				finish();
				UserUtils.hasValidate = 0;
			}
			else{
				ToastUtils.makeImgAndTextToast(MobileValidateActivity.this, map.get("msg"), R.drawable.validate_error, 0).show();

			}
			
		}
		
	}

	@Override
	public void onLoadingError(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadingCancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNoInterNetError() {
		// TODO Auto-generated method stub
		
	}

}
