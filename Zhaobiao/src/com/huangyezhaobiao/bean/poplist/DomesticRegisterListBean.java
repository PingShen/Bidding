package com.huangyezhaobiao.bean.poplist;

import com.huangyezhaobiao.R;
import com.huangyezhaobiao.activity.OrderDetailActivity;
import com.huangyezhaobiao.bean.push.PushToPassBean;
import com.huangyezhaobiao.holder.DomesticRegisterViewHolder;
import com.huangyezhaobiao.inter.MDConstans;
import com.huangyezhaobiao.lib.QDBaseBean;
import com.huangyezhaobiao.lib.ZBBaseAdapter;
import com.huangyezhaobiao.utils.MDUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 抢单信息的模型类----装修
 * 
 * @author linyueyang
 *
 */
public class DomesticRegisterListBean extends QDBaseBean {

	private long pushId;
	private int pushTurn;
	private int displayType;
	private int bidState;
	private long bidId;
	private String title;// 工商注册-个体
	private String time;
	private String endTime;
	private String location;
	private String fee;// 系统扣费
	private String agencyTime;// 3个月代理记账
	private String agencyLocation;// 代理注册区域

	private DomesticRegisterViewHolder holder;

	public int getDisplayType() {
		return displayType;
	}

	public void setDisplayType(int displayType) {
		this.displayType = displayType;
	}

	public long getPushId() {
		return pushId;
	}

	public void setPushId(long pushId) {
		this.pushId = pushId;
	}

	public int getPushTurn() {
		return pushTurn;
	}

	public void setPushTurn(int pushTurn) {
		this.pushTurn = pushTurn;
	}

	public DomesticRegisterViewHolder getHolder() {
		return holder;
	}

	public void setHolder(DomesticRegisterViewHolder holder) {
		this.holder = holder;
	}

	public int getBidState() {
		return bidState;
	}

	public void setBidState(int bidState) {
		this.bidState = bidState;
	}

	public long getBidId() {
		return bidId;
	}

	public void setBidId(long bidId) {
		this.bidId = bidId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getAgencyTime() {
		return agencyTime;
	}

	public void setAgencyTime(String agencyTime) {
		this.agencyTime = agencyTime;
	}

	public String getAgencyLocation() {
		return agencyLocation;
	}

	public void setAgencyLocation(String agencyLocation) {
		this.agencyLocation = agencyLocation;
	}

	@Override
	public String getCateId() {
		return cateId;
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fillDatas() {
		holder.title.setText(this.getTitle());
		holder.time.setText(this.getTime());
		holder.text1.setText(this.getAgencyTime());
		holder.text2.setText(this.getAgencyLocation());
		holder.text3.setText(this.getTime());
		holder.text4.setText(this.getLocation());

		holder.item.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				intent.setClass(context, OrderDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("passBean", toPopPassBean());
				intent.putExtras(bundle);
				context.startActivity(intent);
				
				MDUtils.servicePageMD(context, cateId, String.valueOf(bidId), MDConstans.ACTION_DETAILS);
			}
		});

	}

	@SuppressLint("NewApi")
	@Override
	public View initView(View convertView, LayoutInflater inflater, ViewGroup parent, Context context,
			ZBBaseAdapter<QDBaseBean> adapter) {
		if (this.context == null)
			this.context = context;
		// convertView = getLayoutView(inflater, parent);
		holder = new DomesticRegisterViewHolder();
		this.adapter = adapter;
		convertView = inflater.inflate(R.layout.order_item_domesticregister, null);
		holder.item = (LinearLayout) convertView.findViewById(R.id.domesticregister_item);
		holder.title = (TextView) convertView.findViewById(R.id.grab_domesticregister_title);
		holder.time = (TextView) convertView.findViewById(R.id.grab_domesticregister_time);
		holder.text1 = (TextView) convertView.findViewById(R.id.grab_domesticregister_text1);
		holder.text2 = (TextView) convertView.findViewById(R.id.grab_domesticregister_text2);
		holder.text3 = (TextView) convertView.findViewById(R.id.grab_domesticregister_text3);
		holder.text4 = (TextView) convertView.findViewById(R.id.grab_domesticregister_text4);
		holder.knock = (ImageView) convertView.findViewById(R.id.grab_domesticregister_knock);
		convertView.setTag(holder);
		if (bidState == 1)
			holder.knock.setImageResource(R.drawable.t_bid_gone);
		else {
			holder.knock.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					DomesticRegisterListBean.this.adapter.itemClicked(holder.knock.getId(), toPopPassBean());
					MDUtils.servicePageMD(DomesticRegisterListBean.this.context, cateId, String.valueOf(bidId),
							MDConstans.ACTION_QIANG_DAN);
				}
			});
		}
		return convertView;
	}

	@Override
	public void converseView(View convertView, Context context) {
		holder = (DomesticRegisterViewHolder) convertView.getTag();
		if (this.context == null)
			this.context = context;

	}

	public PushToPassBean toPopPassBean() {
		PushToPassBean bean = new PushToPassBean();
		bean.setBidId(bidId);
		bean.setPushId(pushId);
		bean.setPushTurn(pushTurn);
		bean.setCateId(Integer.parseInt(cateId));
		return bean;
	}

	@Override
	public void setCateId(String cateId) {

		this.cateId = cateId;
	}

}
