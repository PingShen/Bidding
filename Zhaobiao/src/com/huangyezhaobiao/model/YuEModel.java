package com.huangyezhaobiao.model;

import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.util.Log;

import com.huangye.commonlib.model.NetWorkModel;
import com.huangye.commonlib.model.callback.NetworkModelCallBack;
import com.huangye.commonlib.network.HttpRequest;
import com.huangyezhaobiao.bean.YuEBean;
import com.huangyezhaobiao.url.URLConstans;
import com.huangyezhaobiao.url.UrlSuffix;
/**
 * 58余额的获取model
 * @author 58
 *
 */
public class YuEModel extends NetWorkModel{
	public YuEModel(NetworkModelCallBack baseSourceModelCallBack,
			Context context) {
		super(baseSourceModelCallBack, context);
	}


	String yueUrl;
	

	@Override
	protected HttpRequest<String> createHttpRequest() {
		String aa = URLConstans.GET_BALANCE_API + UrlSuffix.getApiBalance();
		Log.e("ashenjjjj", "uu:"+aa);
		yueUrl = URLConstans.URL_YUE_GET+UrlSuffix.getYuESuffix();
		URL url = null;;
		try {
			
			url = new URL(yueUrl);
			String path = url.toString();
			Log.e("ashen", "path:"+path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpRequest<String> request = new HttpRequest<String>(HttpRequest.METHOD_GET, aa, this);
		return request;
	}
	
	
	

}
