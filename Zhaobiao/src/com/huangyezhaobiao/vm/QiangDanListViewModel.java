package com.huangyezhaobiao.vm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.huangye.commonlib.model.NetWorkModel;
import com.huangye.commonlib.vm.callback.ListNetWorkVMCallBack;
import com.huangyezhaobiao.bean.MessCenIACIndividualBean;
import com.huangyezhaobiao.bean.MessCenIACInnerCashBean;
import com.huangyezhaobiao.bean.QDZhuangXiuMessageBean;
import com.huangyezhaobiao.holder.MessCenIACIndividualHolder;
import com.huangyezhaobiao.lib.QDBaseBean;
import com.huangyezhaobiao.lib.ZBBaseListViewModel;
import com.huangyezhaobiao.model.CenterQiangDanListModel;
import com.huangyezhaobiao.model.QiangDanListModel;
/**
 * 抢单信息展示的lvm
 * @author 58
 *
 */
public class QiangDanListViewModel extends ZBBaseListViewModel<QDBaseBean>{

	public QiangDanListViewModel(ListNetWorkVMCallBack callBack, Context context) {
		super(callBack, context);
	}

	@Override
	protected void initKey() {
		key = "displayType";
	}

	@Override
	protected void registerSourceDirs() {
		sourcesDir.put("1", QDZhuangXiuMessageBean.class);
		sourcesDir.put("2", MessCenIACIndividualBean.class);
		sourcesDir.put("3", MessCenIACInnerCashBean.class);
	}

	@Override
	protected NetWorkModel initListNetworkModel(Context context) {
		return new CenterQiangDanListModel(this, context);
	}

//	@Override
//	protected List<QDBaseBean> transferListMapToListBean(
//			List<Map<String, String>> t) {
//		//加一个工商注册个体Bean和一个工商注册国际的bean
//		Map<String,String> map = new HashMap<String, String>();
//		map.put("cateId","1044");
//		map.put("orderId", "123123123");
//		map.put("title", "工商注册-个体");
//		map.put("time" , "2015-04-09");
//		map.put("endTime" , "2015-05-09");
//		map.put("location", "朝阳区北苑");
//		map.put("orderState", "0");
//		map.put("phone", "13993884185");
//		t.add(map);
//		Map<String,String> maps = new HashMap<String, String>();
//		maps.put("cateId","1045");
//		maps.put("orderId", "123123123");
//		maps.put("title", "工商注册-内资");
//		maps.put("time" , "2015-04-09");
//		maps.put("agencyTime" , "3个月代理记账");
//		maps.put("agencyLocation", "代理注册区域");
//		map.put("endTime" , "2015-05-09");
//		map.put("location", "朝阳区北苑");
//		maps.put("orderState", "0");
//		maps.put("phone", "13993884185");
//		t.add(maps);
//		return super.transferListMapToListBean(t);
//	}


	
	
}
