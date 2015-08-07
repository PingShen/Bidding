package com.huangyezhaobiao.url;

import java.util.Date;

import com.huangyezhaobiao.fragment.QiangDanBaseFragment;
import com.huangyezhaobiao.utils.UserUtils;

import android.content.Context;

/**
 * 获得各个链接的后缀名
 * 
 * @author 58
 * 
 */
public class UrlSuffix {
	/**
	 * 返回app前缀的UserId userid/212111
	 * 
	 * @return
	 */
	public static String getAppUserId() {
		return URLConstans.APP_USER_ID + URLConstans.APP_CHA_EQUALS + "24454277549826";
	}
	
	/**
	 * 返回api前缀的userId userId
	 * @return
	 */
	public static String getApiUserId(){
		return URLConstans.API_USER_ID + URLConstans.APP_CHA_EQUALS + UserUtils.userId;
	}

	/**
	 * 返回app前缀的orderState
	 * 
	 * @return
	 */
	public static String getAppCenterOrderState() {
		return URLConstans.APP_ORDER_STATE + URLConstans.APP_CHA_EQUALS
				+ QiangDanBaseFragment.orderState;
	}

	/**
	 * 返回app前缀的pageNum
	 * 
	 * @param pageNum
	 * @return
	 */
	public static String getPageNum(String pageNum) {
		return  URLConstans.APP_PAGE_NUM
				+ URLConstans.APP_CHA_EQUALS + pageNum;
	}

	/**
	 * 返回token
	 * 
	 * @return
	 */
	public static String getToken() {
		return URLConstans.APP_TOKEN + URLConstans.APP_CHA_EQUALS +  new Date().getTime();
	}

	
	/**
	 * 得到
	 * @return
	 */
	public static String getAppOrderId(String orderId){
		return URLConstans.APP_ORDER_ID + URLConstans.APP_CHA_EQUALS + orderId;
	}
	
	
	public static String getAppCenterDetailsSuffix(String orderId){
		return getAppOrderId(orderId) + URLConstans.AND + getToken();
	}
	
	
	/**
	 * 得到订单中心的后缀
	 * 
	 * @return
	 */
	public static String getAppCenterSuffix(String pageNum) {
		return getAppUserId() + URLConstans.AND + getAppCenterOrderState()
				+ URLConstans.AND + getPageNum(pageNum) + URLConstans.AND
				+ getToken();
	}
	
	/**
	 * 得到余额的后缀
	 * @return
	 */
	public static String getApiBalance(){
		return getAppUserId() + URLConstans.AND + getToken();
	}

	
	
	
	/**
	 * 获取余额链接的后缀名
	 * 
	 * @return
	 */
	public static String getYuESuffix() {
		return URLConstans.USER_ID + "24454277549825" + URLConstans.TOKEN
				+ "111";
	}

	/**
	 * 获取消息中心的后缀名
	 */
	public static String getMessageCentrSuffix() {
		return getYuESuffix();
	}

	/**
	 * 获取订单中心的后缀名
	 * 
	 * @return
	 */
	public static String getOrderCenterSuffix(String state) {
		return URLConstans.USER_ID + "1234" + "&" + URLConstans.ORDER_STATE
				+ state + URLConstans.TOKEN + "111";
	}
}
