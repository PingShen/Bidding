package com.huangye.commonlib.vm;

import java.util.ArrayList;
import java.util.List;

import com.huangye.commonlib.model.ListNetWorkModel;
import com.huangye.commonlib.model.NetWorkModel;
import com.huangye.commonlib.model.NetWorkModel.TAG;
import com.huangye.commonlib.model.callback.NetworkModelCallBack;
import com.huangye.commonlib.utils.NetBean;
import com.huangye.commonlib.vm.callback.ListNetWorkVMCallBack;
import com.huangye.commonlib.vm.callback.NetWorkVMCallBack;

import android.content.Context;
import android.util.Log;

public abstract class ListSourceViewModel<T> extends SourceViewModel implements NetworkModelCallBack{
	protected ListNetWorkModel model;
	protected ListNetWorkVMCallBack callBack;
	protected List<T> allDatas = new ArrayList<T>();
	
	public ListSourceViewModel(ListNetWorkVMCallBack callBack,Context context){
		super((NetWorkVMCallBack)callBack,context);
		
		model = (ListNetWorkModel) initListNetworkModel(context);
		this.callBack = callBack;
	}
	

	
	public void loadMore(){
		model.type = TAG.LOADMORE;
		Log.e("sourceModel", "ViewModel......loadMore");
		model.loadMore();
		
	}
	
	public void refresh() {
		model.type = TAG.REFRESH;
		model.refresh();
	}
	
	public void canLoadMore(){
		model.canLoadMore();
	}

	@Override
	public void onLoadingCancell() {
		callBack.onLoadingCancel();
	}

	@Override
	public void onLoadingStart() {
		callBack.onLoadingStart();
	}

	@Override
	public void onLoadingFailure(String err) {
		callBack.onLoadingError(err);
	}

	@Override
	public void onLoadingSuccess(NetBean bean, NetWorkModel model) {
		Log.e("asasas", "ss:"+bean.getData());
		//List<Map<String,String>> t = jsonTransferToListMap(bean);
		//List<T> ts = JsonUtils.jsonToObjectList(bean.getData(), clazz);
		
		List<T> ts = transferToListBean(bean.getData());
		
		Log.e("asdfggg", "list size:"+ts.size());
		callBack.onLoadingSuccess(ts);
		if(model.type==TAG.REFRESH){
			allDatas = ts;
			callBack.onRefreshSuccess(allDatas);
		}else if(model.type == TAG.LOADMORE){
			allDatas.addAll(ts);
			callBack.onLoadingMoreSuccess(allDatas);
		}
	}

	protected abstract List<T> transferToListBean(String t);
	
	//protected abstract List<T> transferListMapToListBean(List<Map<String, String>> t);



	



	
	

	
	

	
	
	
	
}
