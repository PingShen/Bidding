package com.huangyezhaobiao.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huangye.commonlib.activity.BaseActivity;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.bean.push.PushBean;
import com.huangyezhaobiao.enums.TitleBarType;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.view.TitleMessageBarLayout;
import com.huangyezhaobiao.view.ZhaoBiaoDialog;
import com.huangyezhaobiao.view.ZhaoBiaoDialog.onDialogClickListener;

public class HelpActivity extends QBBaseActivity implements onDialogClickListener, OnClickListener{
	private WebView webView;
	private LinearLayout back_layout;
	private TextView  txt_head;
	private BaseWebClient client;
	private ZhaoBiaoDialog dialog;
	private View rl_help_tel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		initView();
		initListener();
		dialog = new ZhaoBiaoDialog(this, "", "4009-321-112");
		dialog.setOnDialogClickListener(this);
	
	}

	@Override
	public void initView() {
		rl_help_tel = getView(R.id.rl_help_tel);
		webView 	= getView(R.id.webView_help);
		back_layout = getView(R.id.back_layout);
		txt_head    = getView(R.id.txt_head);
		tbl         = getView(R.id.tbl);
		client      = new BaseWebClient();
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setDefaultTextEncodingName("UTF-8"); // 设置默认的显示编码
		webView.setWebViewClient(client);
		txt_head.setText("帮助");
		webView.loadUrl("http://www.baidu.com");
	}

	@Override
	public void initListener() {
		back_layout.setOnClickListener(this);
		rl_help_tel.setOnClickListener(this);
	}
	
	private class BaseWebClient extends WebViewClient{
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			stopLoading();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			startLoading();
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}
		
	}


	

	@Override
	public void onDialogOkClick() {
		dialog.dismiss();
		ActivityUtils.goToDialActivity(this, "111");
	}

	@Override
	public void onDialogCancelClick() {
		dialog.dismiss();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_layout:
			onBackPressed();
			break;
		case R.id.rl_help_tel:
				//dialog.show();
			ActivityUtils.goToDialActivity(this, "111");
			break;

		
		}
	}

}
