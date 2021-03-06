package com.huangyezhaobiao.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 我的抢单信息的baseBean
 * @author shenzhixin
 *
 */
public abstract class QDBaseBean {
	protected Context context;
	protected String cateId;//类别，每一个bean都有的
	protected int layout_id;
	protected ZBBaseAdapter<QDBaseBean> adapter;
	/**
	 * 这个方法和setCateId是必须要实现的，否则在反射解析时会报错的
	 * @return
	 */
	public  abstract String getCateId();
	
	public  abstract int getDisplayType();

	public abstract  void setCateId(String cateId);

	@Override
	public String toString() {
		return "BaseBean [cateId=" + cateId + "]";
	}
	/**
	 * 每个bean内部都有一个id
	 * @return
	 */
	public  abstract int getLayoutId();
	
	public QDBaseBean() {
		layout_id = getLayoutId();
	}
	
	
	
	/**
	 * 得到view
	 * @return
	 */
	public  View getLayoutView(LayoutInflater inflater,ViewGroup parent){
		return inflater.inflate(getLayoutId(), parent, false);
	}
	
	/**
	 * 填充数据
	 */
	public  abstract void fillDatas();

	/**
	 * 初始化这个view
	 * @param convertView
	 */
	public abstract View initView(View convertView,LayoutInflater inflater,ViewGroup parent,Context context,ZBBaseAdapter<QDBaseBean> adapter);
	
	/**
	 * 复用这个view
	 */
	public abstract void converseView(View convertView,Context context);
	
}
