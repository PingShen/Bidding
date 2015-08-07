package com.huangyezhaobiao.activity;

import com.huangyezhaobiao.R;
import com.huangyezhaobiao.utils.VersionUtils;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutActivity extends QBBaseActivity implements OnClickListener {
	private LinearLayout back_layout;
	private TextView     txt_head,tv_version;
	private String name;
	private RelativeLayout rl_gongneng,rl_check_version;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
		initListener();
	}
	@Override
	public void initView() {
		back_layout 	 = getView(R.id.back_layout);
		txt_head    	 = getView(R.id.txt_head);
		tv_version  	 = getView(R.id.tv_version);
		rl_gongneng      = getView(R.id.rl_gongneng);
		rl_check_version = getView(R.id.rl_check_version);
		tbl              = getView(R.id.tbl);
	}
	@Override
	public void initListener() {
		back_layout.setOnClickListener(this);
		rl_check_version.setOnClickListener(this);
		rl_gongneng.setOnClickListener(this);
		txt_head.setText("关于");
		try {
			name = "版本号:"+VersionUtils.getVersionName(this);
		} catch (NameNotFoundException e) {
			name = "获取版本号错误";
			e.printStackTrace();
		}
		tv_version.setText(name);
	
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back_layout:
			onBackPressed();
			break;
		case R.id.rl_check_version://跳转到检查版本更新的界面
			VersionUtils.isUpdate(this);
			break;
		case R.id.rl_gongneng://跳转到功能界面 都是h5;
			break;
		}
	}

	
}
