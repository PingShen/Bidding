package com.huangye.commonlib.model;

import android.content.Context;

import com.huangye.commonlib.listop.ListNetModelOp;
import com.huangye.commonlib.model.callback.NetworkModelCallBack;

public abstract class  ListNetWorkModel extends NetWorkModel implements ListNetModelOp{
	/**
	 * 当前加载到第几页
	 * 初始页为1
	 */
	protected int current_load_page = 1;
	public ListNetWorkModel(NetworkModelCallBack baseSourceModelCallBack,Context context) {
		super(baseSourceModelCallBack,context);
		
	}


	
	
}
