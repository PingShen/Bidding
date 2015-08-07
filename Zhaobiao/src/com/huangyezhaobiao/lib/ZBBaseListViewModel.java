package com.huangyezhaobiao.lib;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huangye.commonlib.model.NetWorkModel;
import com.huangye.commonlib.utils.JsonUtils;
import com.huangye.commonlib.utils.NetBean;
import com.huangye.commonlib.vm.ListSourceViewModel;
import com.huangye.commonlib.vm.callback.ListNetWorkVMCallBack;

import android.content.Context;
import android.util.Log;
/**
 * 所有的列表页的list都是继承自这个
 * @author 58
 *
 * @param <T>
 */
public abstract class ZBBaseListViewModel<T> extends ListSourceViewModel<T>{
	protected String key;
	/**
	 * 注册的type与类型的对应 左侧是cateId,右侧是这个cateId对应的业务bean的类
	 */
	protected Map<String, Class<? extends T>> sourcesDir = new HashMap<String, Class<? extends T>>();
		/**
	 * 要转变成的listBean
	 */
	private List<T> beans = new ArrayList<T>();
	
	public ZBBaseListViewModel(ListNetWorkVMCallBack callBack, Context context) {
		super(callBack, context);
		
		registerSourceDirs();
		initKey();
		Set<Entry<String, Class<? extends T>>> entrySet = sourcesDir.entrySet();
		Iterator<Entry<String, Class<? extends T>>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Entry<String, Class<? extends T>> next = iterator.next();
			String key = next.getKey();
			Class<? extends T> clazz = next.getValue();
			Log.e("deoedododo", "key:"+key+",value:"+clazz);
			
		}
	}

	/**
	 * 得到可以决定的key的值
	 */
	protected abstract void initKey();

	/**
	 * 注册所有的业务资源
	 */
	protected abstract void registerSourceDirs();
		
	
	
	@Override
	protected List<T> transferToListBean(String t) {
		// TODO Auto-generated method stub
		List<T> list = new ArrayList<T>();
		JSONArray jsonArray = JSON.parseArray(t);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Log.e("asjaksjaksjk", "key:"+jsonObject.get(key));
			Class<? extends T> classz = sourcesDir.get(jsonObject.get(key)+"");
			Log.e("asjaksjaksjk", "key:"+key+",className:"+classz+",content:"+jsonObject.toString());
			T object = JsonUtils.jsonToObject(jsonObject.toString(), classz);
			list.add(object);
		}
		return list;
	}
	
	

}
