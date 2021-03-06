package com.huangye.commonlib.vm;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.huangye.commonlib.model.NetWorkModel;
import com.huangye.commonlib.model.callback.NetworkModelCallBack;
import com.huangye.commonlib.utils.JsonUtils;
import com.huangye.commonlib.utils.NetBean;
import com.huangye.commonlib.vm.callback.NetWorkVMCallBack;

public abstract class SourceViewModel implements NetworkModelCallBack{
	protected NetWorkVMCallBack callBack;
	protected NetWorkModel t;

	public SourceViewModel(NetWorkVMCallBack callBack,Context context){
		
		this.callBack = callBack;
		t = initListNetworkModel(context);
		
	}
	protected abstract NetWorkModel initListNetworkModel(Context context);

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
		int status = bean.getStatus();
		if(status==0){
			callBack.onLoadingSuccess(jsonTransferToMap(bean));
		}else{
			String msg = bean.getMsg();
			if(!TextUtils.isEmpty(msg)){
				callBack.onLoadingError(bean.getMsg());
			}else{
				callBack.onLoadingError("连接失败!");
			}
		}
	}
	
	@Override
	public void noInternetConnect() {
		callBack.onNoInterNetError();
	}
	

	
	/**
	 * 把json转化成map
	 * @param bean
	 * @return
	 */
	protected Map<String,String> jsonTransferToMap(NetBean bean){
		Log.e("adsss", "balance:"+bean.getData());
		
		return JsonUtils.jsonToMap(bean.getData());
	}
	
	/**
	 * 把json转化成list<Map>
	 * @param bean
	 * @return
	 */
	protected List<Map<String,String>> jsonTransferToListMap(NetBean bean){
		return JsonUtils.jsonToListMap(bean.getData());
	}
	
	/**
	 * 详情页的转化，把Json转化成list<map>
	 * @param bean
	 * @return
	 */
	protected List<Map<String,String>> jsonTransferToDetailsListMap(NetBean bean){
		return JsonUtils.jsonToNewListMap(bean.getData());
	}
}
