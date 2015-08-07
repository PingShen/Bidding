package com.huangyezhaobiao.bean.popdetail;

import com.huangyezhaobiao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class CommonInternationalRegisterInfoBean extends QDDetailBaseBean {
	private String title;// ":"基本信息",
	private String registerType;// :"外资工商注册",
	private String proxyTally;// :"3个月",
	private String isProxyLocation;// :"是",
	private String registerLocation;// :"朝阳区北苑"//如果isProxyLocation的值为“是”，该字段为空
	private String registerTime;// :"2015年6月",
	private String industry;// :"商贸类"

	public CommonInternationalRegisterInfoBean() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getRegisterLocation() {
		return registerLocation;
	}

	public void setRegisterLocation(String registerLocation) {
		this.registerLocation = registerLocation;
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

	@Override
	public View initView(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.detail_item_common_international, null);
		((TextView)view.findViewById(R.id.detail_item_common_international_text1)).setText(registerType);
		((TextView)view.findViewById(R.id.detail_item_common_international_text2)).setText(proxyTally);
		((TextView)view.findViewById(R.id.detail_item_common_international_text3)).setText(isProxyLocation);
		((TextView)view.findViewById(R.id.detail_item_common_international_text4)).setText(registerLocation);
		((TextView)view.findViewById(R.id.detail_item_common_international_text5)).setText(registerTime);
		((TextView)view.findViewById(R.id.detail_item_common_international_text6)).setText(industry);
		
		return view;
	}

}
