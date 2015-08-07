package com.huangyezhaobiao.model;

import android.content.Context;
import android.util.Log;

import com.huangye.commonlib.model.ListNetWorkModel;
import com.huangye.commonlib.model.callback.NetworkModelCallBack;
import com.huangye.commonlib.network.HttpRequest;
import com.huangyezhaobiao.fragment.QiangDanBaseFragment;
import com.huangyezhaobiao.url.URLConstans;
import com.huangyezhaobiao.url.UrlSuffix;
/**
 * 信息中心的抢单信息的model
 * @author shenzhixin
 *
 */
public class CenterQiangDanListModel extends ListNetWorkModel{
	public CenterQiangDanListModel(NetworkModelCallBack baseSourceModelCallBack,
			Context context) {
		super(baseSourceModelCallBack, context);
	}

	private String orderState;
	
	@Override
	public void loadMore() {
		current_load_page++;
		Log.e("ashen", "loadmore..page"+current_load_page);
		String url = URLConstans.MESSAGE_CENTER_URL + UrlSuffix.getAppCenterSuffix(""+current_load_page);
		setRequestURL(url);
		Log.e("ashenTest", "loadmore url:"+"http://192.168.118.41/app/order/orderlist?userid=24454277549826&orderstate="+QiangDanBaseFragment.orderState+"&pageNum=1&token=1");
		getDatas();
	}

	@Override
	public void refresh() {
		//根据m
		current_load_page = 1;
		Log.e("ashen", "refresh..page"+current_load_page);
		String url = URLConstans.MESSAGE_CENTER_URL + UrlSuffix.getAppCenterSuffix("1");
		Log.e("shenzhixintest", "url:"+url);
		//::http://192.168.118.41/app/order/orderlist?userid/27353503259910&orderstate/1&&pageNum=1&token=1
		//"http://192.168.118.41/app/order/orderlist?userid=24454277549826&orderstate="+QiangDanBaseFragment.orderState+"&pageNum=1&token=1"
		setRequestURL(url);
		Log.e("ashenTest", "refresh url:"+"http://192.168.118.41/app/order/orderlist?userid=24454277549826&orderstate="+QiangDanBaseFragment.orderState+"&pageNum=1&token=1");
		getDatas();
	}

	@Override
	public void canLoadMore() {
		
	}

	@Override
	public void loadCache() {
		
	}

	@Override
	protected HttpRequest<String> createHttpRequest() {
		return new HttpRequest<String>(HttpRequest.METHOD_GET,"",this);
	}

	/**
	 * 设置订单的状态，进行不同的url获取
	 * @param orderState
	 */
	public void setOrderState(String orderState){
		this.orderState = orderState;
	}
}
