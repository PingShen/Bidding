package com.huangyezhaobiao.bean.popdetail;

import com.huangyezhaobiao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class CommonDecorateInfoBean extends QDDetailBaseBean {

	private String name;// :"基本信息",
	private String houstType;// ":"住宅装修-二手房",
	private String space;// ":"56平米",
	private String budget;// ":"预算3-5万",
	private String type;// ":"半包",
	private String measureTime;// ":"2015年6月1日",
	private String decorateTime;// ":"2015年6月",
	private String location;// ":"朝阳区北苑"

	public CommonDecorateInfoBean() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHoustType() {
		return houstType;
	}

	public void setHoustType(String houstType) {
		this.houstType = houstType;
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

	@Override
	public View initView(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.detail_item_common_decorate, null);
		((TextView) view.findViewById(R.id.detail_item_common_decorate_text1)).setText(houstType);
		((TextView) view.findViewById(R.id.detail_item_common_decorate_text2)).setText(space);
		((TextView) view.findViewById(R.id.detail_item_common_decorate_text3)).setText(budget);
		((TextView) view.findViewById(R.id.detail_item_common_decorate_text4)).setText(type);
		((TextView) view.findViewById(R.id.detail_item_common_decorate_text5)).setText(measureTime);
		((TextView) view.findViewById(R.id.detail_item_common_decorate_text6)).setText(decorateTime);
		((TextView) view.findViewById(R.id.detail_item_common_decorate_text7)).setText(location);

		return view;
	}

}
