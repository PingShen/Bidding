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
 * 
 * @author 商标注册详情国际注册的bean
 *
 */
public class IACInternationalDetailBean extends QDDetailBaseBean{
	private TextView tv_type_content;
	private TextView tv_size_content;
	private TextView tv_budget_content;
	private TextView tv_location_content;
	private TextView tv_lf_time_content;
	private TextView tv_zx_time_content;
	private TextView tv_needs_content;
	private TextView tv_ch_tel_content;
	private ImageView iv_tels;
	private String name;
	private String registerType;
	private String proxyTally;
	private String isProxyLocation;
	private String registLocation;
	private String registerTime;
	private String industry;
	private String special;
	private String clientPhone;
	private Context mContext;
	private ZhaoBiaoDialog dialog;
	@Override
	public View initView(Context context) {
		mContext = context;
		initDialog(context);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_qiangdan_middle_reg_international, null);
		tv_budget_content = (TextView) view.findViewById(R.id.tv_budget_content);
		tv_ch_tel_content = (TextView) view.findViewById(R.id.tv_ch_tel_content);
		tv_lf_time_content = (TextView) view.findViewById(R.id.tv_lf_time_content);
		tv_location_content = (TextView) view.findViewById(R.id.tv_location_content);
		tv_needs_content = (TextView) view.findViewById(R.id.tv_needs_content);
		tv_size_content = (TextView) view.findViewById(R.id.tv_size_content);
		tv_type_content = (TextView) view.findViewById(R.id.tv_type_content);
		tv_zx_time_content = (TextView) view.findViewById(R.id.tv_zx_time_content);
		iv_tels = (ImageView) view.findViewById(R.id.iv_tels);
		iv_tels.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initDialog(mContext);
				dialog.show();
			}
		});
		fillDatas();
		return view;
	}
	private void fillDatas() {
		tv_budget_content.setText(proxyTally);
		
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
	public TextView getTv_location_content() {
		return tv_location_content;
	}
	public void setTv_location_content(TextView tv_location_content) {
		this.tv_location_content = tv_location_content;
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
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public String getProxyTally() {
		return proxyTally;
	}
	public void setProxyTally(String proxyTally) {
		this.proxyTally = proxyTally;
	}
	public String getIsProxyLocation() {
		return isProxyLocation;
	}
	public void setIsProxyLocation(String isProxyLocation) {
		this.isProxyLocation = isProxyLocation;
	}
	public String getRegistLocation() {
		return registLocation;
	}
	public void setRegistLocation(String registLocation) {
		this.registLocation = registLocation;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
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
					ActivityUtils.goToDialActivity(mContext, clientPhone);
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
