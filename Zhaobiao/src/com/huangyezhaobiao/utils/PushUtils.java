package com.huangyezhaobiao.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huangye.commonlib.file.SqlUtils;
import com.huangye.commonlib.utils.JsonUtils;
import com.huangyezhaobiao.activity.FetchDetailsActivity;
import com.huangyezhaobiao.activity.MessageCenterActivity;
import com.huangyezhaobiao.activity.OrderDetailActivity;
import com.huangyezhaobiao.activity.OtherDetailActivity;
import com.huangyezhaobiao.bean.push.PushBean;
import com.huangyezhaobiao.bean.push.PushToStorageBean;
import com.huangyezhaobiao.bean.push.bar.CountdownPushBean;
import com.huangyezhaobiao.bean.push.bar.SystemPushBean;
import com.huangyezhaobiao.bean.push.pop.DomesticRegisterPopBean;
import com.huangyezhaobiao.bean.push.pop.PersonalRegisterPopBean;
import com.huangyezhaobiao.bean.push.pop.PopBaseBean;
import com.huangyezhaobiao.bean.push.pop.RenovationPopBean;
import com.huangyezhaobiao.db.DataBaseManager.TABLE_OTHER;
import com.huangyezhaobiao.enums.PopTypeEnum;
import com.lidroid.xutils.exception.DbException;
import com.xiaomi.mipush.sdk.MiPushMessage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

//Push json信息转化成 Bean 的工具类
public class PushUtils {

	public static List<PopBaseBean> pushList = new ArrayList<PopBaseBean>();

	@SuppressWarnings("rawtypes")
	public static Map<String, Class<? extends PopBaseBean>> popMap = new HashMap<String,  Class<? extends PopBaseBean>>();

	static {
		popMap.put("1", RenovationPopBean.class);
		popMap.put("2", PersonalRegisterPopBean.class);
		popMap.put("3", DomesticRegisterPopBean.class);
	}

	private static JSONObject obj; 
	private static JSONObject extMap;
	private static JSONObject detail;
	private static JSONObject info;
	
	
	public static void initJson(MiPushMessage message){
		obj = JSON.parseObject(message.getContent());
		extMap = JSON.parseObject(obj.getString("extMap"));
		detail = JSON.parseObject(extMap.getString("detail"));
		info = JSON.parseObject(detail.getString("info"));
	}
	
	
	/**
	 * 根据现在的协议传递把MiPushMessage 根据类别 转换成我们要的数据
	 * 
	 * @param message
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PushBean dealPushMessage(Context context, MiPushMessage message) {
		initJson(message);
		PushBean bean = null;

		// 这里拿到Push的基本信息 类型和推送时间
		int type = 0;
		
		type = extMap.getInteger("type");
		String pushTime = extMap.getString("pushTime");
		long pushId = extMap.getLong("id");
		int pushTurn = 0;

		// 100:新标的 101:抢单结果,成功or失败在detail中体现 102:倒计时提醒 103:系统消息
		switch (PopTypeEnum.getPopType(type)) {

		case NEW_ORDER:
			try {
				pushTurn = detail.getInteger("pushTurn");

			} catch (Exception e) {
			}
			String voice = detail.getString("voice");
			info.put("voice", voice);

			Class clazz0 = popMap.get(info.getString("displayType"));
			PopBaseBean popBean0 = (PopBaseBean)JsonUtils.jsonToObject(info.toString(), clazz0);
			pushList.add(popBean0);

			bean = popBean0;
			break;  
		case ORDERRESULT:

			String status = detail.getString("status");
			info.put("status", status);
			Class clazz1 = popMap.get(info.getString("displayType"));
			PopBaseBean popBean1 = (PopBaseBean)JsonUtils.jsonToObject(info.toString(), clazz1);
			// pushList.add(popBean1);

			bean = popBean1;
			// bean.setStatus(status);
			UnreadUtils.saveQDResult(context);
			break;

		case COUNTDOWN:
			String deadLine = detail.getString("deadLine");
			info.put("deadLine", deadLine);

			CountdownPushBean countdownPushBean = JsonUtils.jsonToObject(info.toString(), CountdownPushBean.class);
			bean = countdownPushBean;
			UnreadUtils.saveDaoJiShi(context);
			break;
		case SYSTEMMESSAGE:
			SystemPushBean systemPushBean = JsonUtils.jsonToObject(info.toString(), SystemPushBean.class);
			bean = systemPushBean;
			UnreadUtils.saveSysNoti(context);
			break;
		default:
			break;
		}
		// Log.e("ashenPushaaa", "type:"+type);
		// 写入extMap级信息
		bean.setTag(type);
		bean.setPushTime(pushTime);
		bean.setPushId(pushId);
		bean.setPushTurn(pushTurn);

		PushToStorageBean pushToStorageBean = bean.toPushStorageBean();
		pushToStorageBean.setTag(type);

		int tag = pushToStorageBean.getTag();
		Log.e("ashenPushaaa", "tag:" + tag);
		try {
			SqlUtils.getInstance(context);
			SqlUtils.dbUtils.save(pushToStorageBean);
			List<PushToStorageBean> lists = SqlUtils.dbUtils.findAll(PushToStorageBean.class);
			Log.e("pushshen", " save size:" + lists.size());
		} catch (DbException e) {
			e.printStackTrace();
			Log.e("lists", "error:" + e.getMessage());

		}
		int statuss = bean.toPushStorageBean().getStatus();
		Log.e("ashenashen", "status:" + statuss);
		return bean;

	}

	public static void notify(Context context, MiPushMessage message) {

		
		
		
		int type = Integer.parseInt(extMap.getString("type"));
		switch (PopTypeEnum.getPopType(type)) {

		case NEW_ORDER:
			Intent intent = new Intent(context, OrderDetailActivity.class);
			// 程序在后台运行的时候必须要加的标志，新建activity队列
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			intent.putExtra("bidId", info.getString("bidId"));
			context.startActivity(intent);
			break;
		case ORDERRESULT:
			
			UnreadUtils.clearQDResult(context);
			Intent intent1 = new Intent(context, OtherDetailActivity.class);
			// 程序在后台运行的时候必须要加的标志，新建activity队列
			intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			
			intent1.putExtra("type",  TABLE_OTHER.KOUFEI);
			//intent1.putExtra("bidId", info.getString("bidId"));
			context.startActivity(intent1);
			break;
		case COUNTDOWN:
			

			
			UnreadUtils.clearDaoJiShiResult(context);
			Intent intent2 = new Intent(context, OtherDetailActivity.class);
			// 程序在后台运行的时候必须要加的标志，新建activity队列
			intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			intent2.putExtra("type",  TABLE_OTHER.DAOJISHI);
			context.startActivity(intent2);
			break;
		case SYSTEMMESSAGE:
			UnreadUtils.clearSysNotiNum(context);
			Intent intent3 = new Intent(context, OtherDetailActivity.class);
			// 程序在后台运行的时候必须要加的标志，新建activity队列
			intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			intent3.putExtra("type",  TABLE_OTHER.SYSNOTIF);
			context.startActivity(intent3);
			break;
		default:
			break;
		}

	}

//	public static JSONObject transferMessage(MiPushMessage message) {
//
//		JSONObject obj = JSON.parseObject(message.getContent());
//		JSONObject extMap = JSON.parseObject(obj.getString("extMap"));
//
//		return extMap;
//	}
//
//	public static long getBididfromMessage(MiPushMessage message) {
//		JSONObject obj = JSON.parseObject(message.getContent());
//		JSONObject extMap = JSON.parseObject(obj.getString("extMap"));
//		JSONObject detail = JSON.parseObject(extMap.getString("detail"));
//		JSONObject info = JSON.parseObject(detail.getString("info"));
//
//		return info.getLong("bidId");
//	}

}
