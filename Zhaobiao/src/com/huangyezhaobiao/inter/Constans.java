package com.huangyezhaobiao.inter;

public interface Constans {

	/**
	 * 已完成
	 */
	String DONE_FRAGMENT = "3";

	/**
	 * 待服务
	 */
	String READY_SERVICE = "1";
	
	/**
	 * 服务中
	 */
	String ON_SERVICE = "2";
	
	/**
	 * 已完成---已服务
	 */
	String DONE_FRAGMENT_FINISH="31";
	
	/**
	 * 已完成--已取消
	 */
	String DONE_FRAGMENT_CANCEL = "32";

	/**
	 * orderId的key----bean-->fetchDetailsActivity的传递key
	 */
	String ORDER_ID = "orderId";

	/**
	 * 抢单结果
	 */
	int QD_RESULT = 101;
	
	/**
	 * 抢单倒计时提醒
	 */
	int QD_DAOJISHI = 102;
	
	/**
	 * 系统通知
	 */
	int SYS_NOTI = 103;

	/**
	 * sharedPreference的名字
	 */
	String APP_SP = "app";

	/**
	 * 版本号
	 */
	String VERSION_NAME = "versionName";
	
	/**
	 * 数据库查找等于
	 */
	String DB_OPERATION_EQUAL = "=";
}
