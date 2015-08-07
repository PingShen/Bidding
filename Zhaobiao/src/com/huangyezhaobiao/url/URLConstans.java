package com.huangyezhaobiao.url;

/**
 * 接口的链接
 * 
 * @author shenzhixin 把后缀加入到方法里去写
 * 
 */
public interface URLConstans {
	/**
	 * 斜线
	 */
	String LINE = "/";

	/**
	 * 与符号
	 */
	String AND = "&";

	/**
	 * 问号
	 */
	String CHA_ASK = "?";

	/**
	 * 等于号
	 */
	String APP_CHA_EQUALS = "=";

	/**
	 * 最基类的链接，所有链接的基础 http://192.168.118.41/api/
	 */
	String BASE_URL = "http://192.168.118.41/";

	/**
	 * api的字段
	 */
	String API = "api/";

	/**
	 * app的字段
	 */
	String APP = "app/";

	/**
	 * order的字段
	 */
	String APP_ORDER = "order";

	/**
	 * app前缀的orderlist的字段
	 */
	String APP_ORDER_LIST = "orderlist";

	/**
	 * app前缀的orderdetails的字段
	 */
	String APP_ORDER_DETAIL = "orderdetail";

	/**
	 * app前缀的orderid的字段
	 */
	String APP_ORDER_ID = "orderid";

	/**
	 * app前缀的userid
	 */
	String APP_USER_ID = "userid";

	/**
	 * api前缀的userId
	 */
	String API_USER_ID = "userId";

	/**
	 * app前缀的orderstate
	 */
	String APP_ORDER_STATE = "orderstate";

	/**
	 * app前缀的token
	 */
	String APP_TOKEN = "token";

	/**
	 * app前缀的pageNum
	 */
	String APP_PAGE_NUM = "pageNum";

	/**
	 * api前缀的getBalance
	 */
	String APP_GET_BALANCE = "balance";

	
	
	
	/**
	 * http://192.168.118.41/app/
	 */
	String BASE_URL_APP = BASE_URL + APP;

	/**
	 * http://192.168.118.41/api/
	 */
	String BASE_URL_API = BASE_URL + API;

	/**
	 * 查询余额的url "http://192.168.118.41/app/order/balance?userid=24454277549825&token=1"
	 */
	String GET_BALANCE_API = BASE_URL_APP + APP_ORDER + LINE + APP_GET_BALANCE + CHA_ASK;
	

	/**
	 * 新的抢单中心的url "http://192.168.118.41/app/order/orderlist?
	 * userid=24454277549826&orderstate=
	 * "+QiangDanBaseFragment.orderState+"&pageNum=1&token=1"
	 */
	String MESSAGE_CENTER_URL = BASE_URL_APP + APP_ORDER + LINE
			+ APP_ORDER_LIST + CHA_ASK;

	/**
	 * 新的抢单详情的url
	 * "http://192.168.118.41/app/order/orderdetail?orderid="+orderId+"&token=1"
	 */
	String MESSAGE_CENTER_DETEAILS_URL = BASE_URL_APP + APP_ORDER + LINE
			+ APP_ORDER_DETAIL + CHA_ASK;

	
	
	/**
	 * 版本更新的链接
	 * http://192.168.118.41/app/getversion/apk?appId=1"
	 */
	String UPDATE_APP_URL = BASE_URL_APP+"getversion/apk?appId=1";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 版本字段
	 */
	String PLATFORM = "platform=android";

	/**
	 * 时间相关字段
	 */
	String TOKEN = "&token=";

	/**
	 * userId
	 */
	String USER_ID = "userId=";

	/**
	 * order_state
	 */
	String ORDER_STATE = "orderState=";

	/**
	 * 加了一个标识符,暂时由这个来处理
	 */
	String BASE_URL_DE = BASE_URL + "/api";

	/**
	 * 登录的链接Url
	 */
	String URL_LOGIN_POST = BASE_URL_DE + "/login";

	/**
	 * App更新的链接Url
	 * http://192.168.118.41/API/getVersion?platform=android&token=1112
	 */
	String URL_APP_UPDATE_GET = BASE_URL_DE + "/getVersion?" + PLATFORM + TOKEN;

	/**
	 * 58余额的链接url http://192.168.118.41
	 */
	String URL_YUE_GET = BASE_URL_DE + "/getBalance?";

	/**
	 * 订单中心的url
	 */
	String URL_ORDER_CENTER_GET = BASE_URL_DE + "/getOrders?";

	/**
	 * 消息中心的url http://192.168.118.41/API/getMessage?userId= &token=
	 */
	String URL_MESSAGE_CENTER_GET = BASE_URL_DE + "/getMessage?";
}
