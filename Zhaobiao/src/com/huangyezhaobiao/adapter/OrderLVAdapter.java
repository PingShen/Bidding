package com.huangyezhaobiao.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangyezhaobiao.bean.QDZhuangXiuMessageBean;
import com.huangyezhaobiao.lib.QDBaseBean;
import com.huangyezhaobiao.lib.ZBBaseAdapter;
/**
 * 四个fragment中lv共用的adapter
 * @author shenzhx
 *
 */
public class OrderLVAdapter extends ZBBaseAdapter<QDBaseBean>{
	public OrderLVAdapter(Context context,
			com.huangyezhaobiao.lib.ZBBaseAdapter.AdapterListener listener,int type) {
		super(context, listener);
	
	}

	@Override
	public void transferBeanTypeToAdapterType() {
		typeMap.put("1", 0);
		typeMap.put("2", 1);
		typeMap.put("3", 2);
	}

	@Override
	public int getTotalTypeCount() {
		return typeMap.size();
	}

	
	
	@Override
	public View initView(QDBaseBean t, View view, ViewGroup parent,
			LayoutInflater inflater,Context context) {
		return t.initView(view, inflater, parent,context,this);
	}

	@Override
	public void converseView(QDBaseBean t, View view,Context context) {
		t.converseView(view,context);
	}

	@Override
	public void fillDatas(QDBaseBean t) {
		t.fillDatas();
	}

	@Override
	public QDBaseBean initJavaBean(int position) {
		return beans.get(position);
	}

	@Override
	protected int getAdapterItemType(int position) {
	//	Log.e("ashenasasa", "typeMap:"+(typeMap==null)+",bean:"+(beans.get(position)==null)+",type:"+	beans.get(position).getDisplayType());
	
		return typeMap.get(""+beans.get(position).getDisplayType());
	}
	
}
