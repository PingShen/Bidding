package com.huangyezhaobiao.bean.popdetail;

import com.huangyezhaobiao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class CommonPersonalRegisterInfoBean extends QDDetailBaseBean {

	private String title;// :"基本信息",
	private String registerType;// :"个体工商注册",
	private String registerTime;// :"2015年8月1日",
	private String registerLocation;// ":"朝阳区北苑",

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

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getRegisterLocation() {
		return registerLocation;
	}

	public void setRegisterLocation(String registerLocation) {
		this.registerLocation = registerLocation;
	}

	public CommonPersonalRegisterInfoBean() {

	}

	@Override
	public View initView(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.detail_item_common_personal, null);
		
		((TextView)view.findViewById(R.id.detail_item_common_personal_text1)).setText(registerType);
		((TextView)view.findViewById(R.id.detail_item_common_personal_text2)).setText(registerTime);
		((TextView)view.findViewById(R.id.detail_item_common_personal_text3)).setText(registerLocation);

		return view;
	}

}
