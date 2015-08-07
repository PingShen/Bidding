package com.huangyezhaobiao.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huangye.commonlib.vm.callback.NetWorkVMCallBack;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.inter.Constans;
import com.huangyezhaobiao.view.TitleMessageBarLayout;
import com.huangyezhaobiao.vm.FetchDetailsVM;

/**
 * 抢单详情
 * 
 * @author shenzhixin
 * 
 */
public class FetchDetailsActivity extends QBBaseActivity implements
		OnClickListener, NetWorkVMCallBack {
	public static String orderState;
	private FetchDetailsVM vm;
	private LinearLayout ll;
	private LinearLayout back_layout;
	private TextView txt_head;
	private String orderId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fetch_details);
		orderId = getIntent().getStringExtra(Constans.ORDER_ID);
		Log.e("ashenFetch", "fetchDetails.....orderid:"+orderId);
		initView();
		initListener();
		if(TextUtils.isEmpty(orderId)){//orderId如果为空
			vm = new FetchDetailsVM(this, this, 0l);
		}else{
			vm = new FetchDetailsVM(this, this, Long.parseLong(orderId));
		}
		vm.fetchDetailDatas();

	}

	public void initListener() {
		back_layout.setOnClickListener(this);
	}

	public void initView() {
		ll = (LinearLayout) findViewById(R.id.ll);
		tbl = (TitleMessageBarLayout) findViewById(R.id.tbl);
		back_layout = getView(R.id.back_layout);
		txt_head = getView(R.id.txt_head);
		txt_head.setText("订单详情");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_layout:
			onBackPressed();
			break;

		}
	}

	@Override
	public void onLoadingStart() {
		startLoading();
	}

	@Override
	public void onLoadingSuccess(Object t) {
		stopLoading();
		ArrayList<View> views = (ArrayList<View>) t;
		if(views!=null){
			for (int i = 0; i < views.size(); i++) {
				View view = views.get(i);
				if (view != null) {
					if (i == 0) {
						ll.addView(view);
					} else {
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.FILL_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT);
						lp.topMargin = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 10, this
										.getResources().getDisplayMetrics());
						view.setLayoutParams(lp);
						ll.addView(view);
					}
				}
			}
		}
	}

	@Override
	public void onLoadingError(String msg) {
		Toast.makeText(this, msg, 0).show();
		stopLoading();
		
	}

	@Override
	public void onLoadingCancel() {
		stopLoading();
	}

	@Override
	public void onNoInterNetError() {
		Toast.makeText(this, "网络错误", 0).show();
	}


}
