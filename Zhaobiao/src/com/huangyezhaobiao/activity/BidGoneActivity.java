package com.huangyezhaobiao.activity;

import com.huangyezhaobiao.R;
import com.huangyezhaobiao.bean.push.PushToPassBean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 标的被抢界面
 * @author linyueyang
 *
 */
public class BidGoneActivity extends QBBaseActivity{

	private TextView txt_head;
	private LinearLayout back_layout;
	private PushToPassBean receivePassBean;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bid_gone);
		Intent intent = this.getIntent(); 
		receivePassBean=(PushToPassBean)intent.getSerializableExtra("passBean");
		initView();
		initListener();
		
	}
	
	@Override
	public void initView() {
		txt_head = (TextView) findViewById(R.id.txt_head);
		txt_head.setText("订单详情");
		back_layout = (LinearLayout) this.findViewById(R.id.back_layout);
		
	}

	@Override
	public void initListener() {
		back_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
	}
	
	

}
