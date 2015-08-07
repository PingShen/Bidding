package com.huangyezhaobiao.model;

import com.huangye.commonlib.model.ListNetWorkModel;
import com.huangye.commonlib.model.callback.NetworkModelCallBack;
import com.huangye.commonlib.network.HttpRequest;
import com.huangyezhaobiao.url.URLConstans;

import android.content.Context;

public class GrabListModel extends ListNetWorkModel{

	public GrabListModel(NetworkModelCallBack baseSourceModelCallBack,
			Context context) {
		super(baseSourceModelCallBack, context);
	}

	@Override
	public void loadMore() {
		setRequestMethodGet();
		type = TAG.LOADMORE;
		setRequestURL(URLConstans.BASE_URL+"api/getOrders?userId=11&orderState=123&token=111");
		getDatas();
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		setRequestMethodGet();
		type = TAG.REFRESH;
		setRequestURL(URLConstans.BASE_URL+"api/getOrders?userId=11&orderState=123&token=111");
		getDatas();
	}

	@Override
	public void canLoadMore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected HttpRequest<String> createHttpRequest() {
		return new HttpRequest<String>(HttpRequest.METHOD_GET, "", this);
	}




}
