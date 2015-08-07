package com.huangyezhaobiao.activity;

import com.huangyezhaobiao.R;
import com.huangyezhaobiao.bean.push.PushToPassBean;
import com.huangyezhaobiao.utils.ActivityUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 抢单成功界面
 * 
 * @author linyueyang
 *
 */
public class BidSuccessActivity extends QBBaseActivity {

	private TextView txt_head;
	private Button toOrderList;

	private Button toBidList;
	private LinearLayout back_layout;
	private PushToPassBean receivePassBean;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bid_success);
		Intent intent = this.getIntent(); 
		receivePassBean=(PushToPassBean)intent.getSerializableExtra("passBean");
		initView();
		initListener();
	}

	@Override
	public void initView() {
		txt_head = (TextView) findViewById(R.id.txt_head);
		txt_head.setText("抢单成功");
		toOrderList = (Button) findViewById(R.id.success_orderlist);
		toBidList = (Button) findViewById(R.id.success_bidlist);
		back_layout = (LinearLayout) this.findViewById(R.id.back_layout);
	}

	@Override
	public void initListener() {
		toOrderList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ActivityUtils.goToActivity(BidSuccessActivity.this, OrderListActivity.class);
			}
		});
		toBidList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ActivityUtils.goToActivity(BidSuccessActivity.this, MainActivity.class);
			}
		});
		back_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}

}
