package com.huangyezhaobiao.bean;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.huangyezhaobiao.R;
import com.huangyezhaobiao.bean.popdetail.QDDetailBaseBean;
import com.huangyezhaobiao.fragment.QiangDanBaseFragment;
import com.huangyezhaobiao.inter.MDConstans;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.DetailsLogBeanUtils;
import com.huangyezhaobiao.utils.MDUtils;
import com.huangyezhaobiao.view.ZhaoBiaoDialog;
import com.huangyezhaobiao.view.ZhaoBiaoDialog.onDialogClickListener;

/**
 * 装修的业务bean
 * @author shenzhixin
 *
 */
public class OrderDetailDecorateBean extends QDDetailBaseBean{
	private Context context;
	private TextView tv_type_content;
	private TextView tv_size_content;
	private TextView tv_budget_content;
	private TextView tv_zx_type_content;
	private TextView tv_lf_time_content;
	private TextView tv_zx_time_content;
	private TextView tv_location_content;
	private TextView tv_needs_content;
	private TextView tv_ch_tel_content;
	private ImageView iv_tels;
	private String name;
	private String houseType;
	private String space;
	private String budget;
	private String type;
	private String measureTime;
	private String decorateTime;
	private String location;
	private String special;
	private String clientPhone;
	private ZhaoBiaoDialog dialog;
	@Override
	public View initView(Context context) {
		this.context = context;
		initDialog(context);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_qiangdan_middle, null);
		tv_budget_content = (TextView) view.findViewById(R.id.tv_budget_content);
		tv_ch_tel_content = (TextView) view.findViewById(R.id.tv_ch_tel_content);
		tv_lf_time_content = (TextView) view.findViewById(R.id.tv_lf_time_content);
		tv_location_content = (TextView) view.findViewById(R.id.tv_location_content);
		tv_needs_content = (TextView) view.findViewById(R.id.tv_needs_content);
		tv_size_content = (TextView) view.findViewById(R.id.tv_size_content);
		tv_type_content = (TextView) view.findViewById(R.id.tv_type_content);
		tv_zx_time_content = (TextView) view.findViewById(R.id.tv_zx_time_content);
		tv_zx_type_content = (TextView) view.findViewById(R.id.tv_zx_type_content);
		iv_tels            = (ImageView) view.findViewById(R.id.iv_tels);
		iv_tels.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initDialog(OrderDetailDecorateBean.this.context);
				dialog.show();
				
			}
		});
		fillDatas();
		return view;
	}
	
	/**
	 * 填充数据
	 */
	private void fillDatas() {
		tv_budget_content.setText(budget);
		tv_ch_tel_content.setText(clientPhone);
		tv_lf_time_content.setText(decorateTime);
		tv_location_content.setText(location);
		tv_needs_content.setText(special);
		tv_size_content.setText(space);
		tv_type_content.setText(houseType);
		tv_zx_time_content.setText(measureTime);
		tv_zx_type_content.setText(type);
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public TextView getTv_type_content() {
		return tv_type_content;
	}
	public void setTv_type_content(TextView tv_type_content) {
		this.tv_type_content = tv_type_content;
	}
	public TextView getTv_size_content() {
		return tv_size_content;
	}
	public void setTv_size_content(TextView tv_size_content) {
		this.tv_size_content = tv_size_content;
	}
	public TextView getTv_budget_content() {
		return tv_budget_content;
	}
	public void setTv_budget_content(TextView tv_budget_content) {
		this.tv_budget_content = tv_budget_content;
	}
	public TextView getTv_zx_type_content() {
		return tv_zx_type_content;
	}
	public void setTv_zx_type_content(TextView tv_zx_type_content) {
		this.tv_zx_type_content = tv_zx_type_content;
	}
	public TextView getTv_lf_time_content() {
		return tv_lf_time_content;
	}
	public void setTv_lf_time_content(TextView tv_lf_time_content) {
		this.tv_lf_time_content = tv_lf_time_content;
	}
	public TextView getTv_zx_time_content() {
		return tv_zx_time_content;
	}
	public void setTv_zx_time_content(TextView tv_zx_time_content) {
		this.tv_zx_time_content = tv_zx_time_content;
	}
	public TextView getTv_location_content() {
		return tv_location_content;
	}
	public void setTv_location_content(TextView tv_location_content) {
		this.tv_location_content = tv_location_content;
	}
	public TextView getTv_needs_content() {
		return tv_needs_content;
	}
	public void setTv_needs_content(TextView tv_needs_content) {
		this.tv_needs_content = tv_needs_content;
	}
	public TextView getTv_ch_tel_content() {
		return tv_ch_tel_content;
	}
	public void setTv_ch_tel_content(TextView tv_ch_tel_content) {
		this.tv_ch_tel_content = tv_ch_tel_content;
	}
	public ImageView getIv_tels() {
		return iv_tels;
	}
	public void setIv_tels(ImageView iv_tels) {
		this.iv_tels = iv_tels;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMeasureTime() {
		return measureTime;
	}
	public void setMeasureTime(String measureTime) {
		this.measureTime = measureTime;
	}
	public String getDecorateTime() {
		return decorateTime;
	}
	public void setDecorateTime(String decorateTime) {
		this.decorateTime = decorateTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	public String getClientPhone() {
		return clientPhone;
	}
	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	public void setNewType(String newType){
		super.newtype = newType;
	}
	
	
	
	private void initDialog(Context context) {
		if(dialog == null){
			dialog = new ZhaoBiaoDialog(context, "提示", "确定要拨打电话?");
			dialog.setOnDialogClickListener(new onDialogClickListener() {
				
				@Override
				public void onDialogOkClick() {
					Log.e("assssshenaaa", "bidid:"+DetailsLogBeanUtils.bean.getBidID()+",cateid:"+DetailsLogBeanUtils.bean.getCateID());
					ActivityUtils.goToDialActivity(OrderDetailDecorateBean.this.context, clientPhone);
					MDUtils.OrderDetailsPageMD(QiangDanBaseFragment.orderState,DetailsLogBeanUtils.bean.getCateID()+"",orderId+"",MDConstans.ACTION_DOWN_TEL,clientPhone);
					dialog.dismiss();
					dialog = null;
				}
				
				@Override
				public void onDialogCancelClick() {
					dialog.dismiss();
					dialog = null;
				}
			});
		}
	}
}
