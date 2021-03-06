package com.huangyezhaobiao.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huangye.commonlib.activity.BaseActivity;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.bean.push.PushBean;
import com.huangyezhaobiao.enums.TitleBarType;
import com.huangyezhaobiao.utils.ToastUtils;


/**
 * 用户反馈界面，进行用户意见的提交的
 * @author shenzhixin
 *
 */
public class UserFeedBackActivity extends QBBaseActivity {
	private LinearLayout back_layout;
	private TextView  txt_head;
	private EditText  et_message,et_contact;
	private Button    btn_submit;
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back_layout:
				onBackPressed();
				break;
			case R.id.btn_submit:
				submitSchedule();
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_feedback);
		initView();
		initListener();
	}

	
	/**
	 * 提交的流程
	 */
	protected void submitSchedule() {
		String message = et_message.getText().toString();
		if(TextUtils.isEmpty(message)){
			ToastUtils.makeImgAndTextToast(this, "请输入内容",R.drawable.back, 0).show();
			return;
		}
		//提交
	}

	public void initListener() {
		back_layout.setOnClickListener(listener);
		btn_submit.setOnClickListener(listener);
	}
	public void initView() {
		back_layout = getView(R.id.back_layout);
		txt_head    = getView(R.id.txt_head);
		et_contact  = getView(R.id.et_contact);
		et_message  = getView(R.id.et_message);
		btn_submit  = getView(R.id.btn_submit);
		tbl         = getView(R.id.tbl);
	}





}
