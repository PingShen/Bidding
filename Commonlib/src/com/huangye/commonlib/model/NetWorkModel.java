package com.huangye.commonlib.model;
import java.util.HashMap;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huangye.commonlib.delegate.HttpRequestCallBack;
import com.huangye.commonlib.model.callback.NetworkModelCallBack;
import com.huangye.commonlib.network.HttpRequest;
import com.huangye.commonlib.utils.JsonUtils;
import com.huangye.commonlib.utils.NetBean;
import com.huangye.commonlib.utils.NetworkTools;

/**
 * 网络层的model模型
 * @author shenzhixin
 *
 */
public abstract class NetWorkModel extends HYBaseModel implements HttpRequestCallBack{
	protected NetworkModelCallBack baseSourceModelCallBack;
	private HttpRequest<String> request;
	private Context context;
	//@SuppressWarnings("unchecked")
	//Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
	public TAG type;

	public void setBaseSourceModelCallBack(
			NetworkModelCallBack baseSourceModelCallBack) {
		this.baseSourceModelCallBack = baseSourceModelCallBack;
	}
	
	
	
	public NetWorkModel(NetworkModelCallBack baseSourceModelCallBack,Context context) {
		super();
		this.context = context;
		/*Type modelType = getClass().getGenericSuperclass();
		if ((modelType instanceof ParameterizedType)) {
			Type[] modelTypes = ((ParameterizedType) modelType).getActualTypeArguments();
			clazz = ((Class) modelTypes[0]);
		}*/
		this.baseSourceModelCallBack = baseSourceModelCallBack;
		request = createHttpRequest();
	}
	
	public void setRequestURL(String url){
		request.setUrl(url);
	}
	
	public void setRequestMethodGet(){
		request.setMethod(HttpRequest.METHOD_GET);
	}
	
	public void setRequestMethodPost(){
		request.setMethod(HttpRequest.METHOD_POST);
	}
	
	/**
	 * 创建model对应的httpRequest
	 */
	protected abstract HttpRequest<String> createHttpRequest();
	
	public void configParams(HashMap<String, String> params_map){
		request.configParams(params_map);
	}
	
	public void getDatas(){
		if(!NetworkTools.isNetworkConnected(context)){
			baseSourceModelCallBack.noInternetConnect();
			return ;
		}
		if(request != null)
			request.getDatas();
	}
	
	@Override
	public void onLoadingFailure(String err) {
		baseSourceModelCallBack.onLoadingFailure(err);
	}
	@Override
	public void onLoadingSuccess(String result) {
		Log.e("httpRequestResult", "result:"+result);
		JSONObject jsonObject = JSON.parseObject(result);
		if(TextUtils.isEmpty(jsonObject.getString("result"))){
			baseSourceModelCallBack.onLoadingFailure(jsonObject.getString("msg"));
		}else{
			baseSourceModelCallBack.onLoadingSuccess(transformJsonToNetBean(result),this);
		}
	}
	/**
	 * 先把json转成netBean
	 * @param result
	 * @return
	 */
	public NetBean transformJsonToNetBean(String result){
		NetBean bean = JsonUtils.jsonToNetBean(result);
		return bean;
	}
	
	@Override
	public void onLoadingStart() {
		baseSourceModelCallBack.onLoadingStart();
	}
	@Override
	public void onLoadingCancelled() {
		baseSourceModelCallBack.onLoadingCancell();
	}

	@Override
	public void onLoading(long total, long current) {
		
	}
	
/*	public T jsonTransferToBean(String json){
		NetBean bean = JsonUtils.jsonToNetBean(json);
		Log.e("ashen", "name:"+clazz.getSimpleName());
		return JsonUtils.jsonToObject(bean.getData(),clazz);
	}*/

	
	public enum TAG{
		LOGIN,LOADMORE,REFRESH,CHECKVERSION
	};
	
	
	
}
