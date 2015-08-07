package com.huangyezhaobiao.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huangyezhaobiao.R;
import com.huangyezhaobiao.bean.popdetail.QDDetailBaseBean;
/**
 * 订单详情的价格区间的bean
 * @author shenzhixin
 *
 */
public class PriceAreaBean extends QDDetailBaseBean{
	private String name;
	private String orderNum;
	private String orderFee;
	private TextView tv_last_cost_content;
	private TextView tv_last_number_content;
	@Override
	public View initView(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.layout_qiangdan_last, null);
		tv_last_cost_content = (TextView) view.findViewById(R.id.tv_last_cost_content);
		tv_last_number_content = (TextView) view.findViewById(R.id.tv_last_number_content);
		fillDatas();
		return view;
	}
	
	private void fillDatas()
	{
		tv_last_cost_content.setText(orderFee);
		tv_last_number_content.setText(orderNum);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderFee() {
		return orderFee;
	}
	public void setOrderFee(String orderFee) {
		this.orderFee = orderFee;
	}
	public TextView getTv_last_cost_content() {
		return tv_last_cost_content;
	}
	public void setTv_last_cost_content(TextView tv_last_cost_content) {
		this.tv_last_cost_content = tv_last_cost_content;
	}
	public TextView getTv_last_number_content() {
		return tv_last_number_content;
	}
	public void setTv_last_number_content(TextView tv_last_number_content) {
		this.tv_last_number_content = tv_last_number_content;
	}

	
	public void setNewType(String newType){
		super.newtype = newType;
	}
}
