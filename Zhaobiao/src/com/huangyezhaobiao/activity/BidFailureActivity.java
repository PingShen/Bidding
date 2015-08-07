package com.huangyezhaobiao.activity;

import com.huangyezhaobiao.R;
import com.huangyezhaobiao.bean.push.PushToPassBean;
import com.huangyezhaobiao.inter.MDConstans;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.MDUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 抢单失败界面
 * @author linyueyang
 *
 */
public class BidFailureActivity extends QBBaseActivity{

	private TextView txt_head;
	private Button toBidList;
	private LinearLayout back_layout;
	private PushToPassBean receivePassBean;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bid_failure);
		Intent intent = this.getIntent(); 
		receivePassBean=(PushToPassBean)intent.getSerializableExtra("passBean");
		initView();
		initListener();
	}
	
	@Override
	public void initView() {
		txt_head = (TextView) findViewById(R.id.txt_head);
		txt_head.setText("抢单失败");
		toBidList = (Button) findViewById(R.id.failure_bidlist);
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
		toBidList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityUtils.goToActivity(BidFailureActivity.this, MainActivity.class);
				MDUtils.QDResult(MDConstans.BID_FAILURED, receivePassBean.getCateId()+"", receivePassBean.getBidId()+"", MDConstans.ACTION_CONTINUE_QD);
			}
		});
	}


	@Override
	public void finish() {
		super.finish();
		MDUtils.QDResult(MDConstans.BID_FAILURED, receivePassBean.getCateId()+"", receivePassBean.getBidId()+"", MDConstans.ACTION_CLOSE_RESULT);
	}
}
