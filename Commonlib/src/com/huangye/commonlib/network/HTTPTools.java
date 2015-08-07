package com.huangye.commonlib.network;

import com.huangye.commonlib.delegate.HttpRequestCallBack;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 网络的封装类
 * 
 * @author shenzhixin 还需要上传文件等东西
 */
public class HTTPTools {
	public static HTTPTools httpTools;
	public static HttpUtils httpUtils;
	private boolean config = true;

	private HTTPTools() {
	}
	
	/**
	 * 设置超时时间
	 * @param timeout
	 */
	public void setRequestTimeOut(int timeout){
		httpUtils.configSoTimeout(timeout);
	}
	
	
	public static HTTPTools newHttpUtilsInstance() {
		if (httpTools == null) {
			synchronized (HTTPTools.class) {
				if (httpTools == null) {
					httpTools = new HTTPTools();
					httpUtils = new HttpUtils();
					httpUtils.configRequestThreadPoolSize(5);
					httpUtils.configCurrentHttpCacheExpiry(1000 * 10);// 设置超时时间
				}
			}
		}

		return httpTools;
	}

	public HttpHandler<String> doGet(String url, HttpRequestCallBack callBack) {// 外部接口函数
		return doGetJson(url, callBack);
	}

	public HttpHandler<String> doPost(String url, RequestParams params,
			HttpRequestCallBack iOAuthCallBack) {
		return doPostJson(url, params, iOAuthCallBack);
	}

	private HttpHandler<String> doPostJson(String url, RequestParams params,
			final HttpRequestCallBack iOAuthCallBack) {
		
		if (config) {
			httpUtils.configCurrentHttpCacheExpiry(1000 * 10);
			config = false;
		}
		return httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						iOAuthCallBack.onLoadingFailure(arg1);
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						iOAuthCallBack.onLoadingSuccess(arg0.result);
					}

					@Override
					public void onCancelled() {
						iOAuthCallBack.onLoadingCancelled();
						super.onCancelled();
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);
						
					}

					@Override
					public void onStart() {
						super.onStart();
						iOAuthCallBack.onLoadingStart();
					}

				});

	}

	private HttpHandler<String> doGetJson(String url,
			final HttpRequestCallBack callBack) {
		return httpUtils.send(HttpMethod.GET, url,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException exception, String err) {
						callBack.onLoadingFailure(err);
					}

					@Override
					public void onSuccess(ResponseInfo<String> result) {
						callBack.onLoadingSuccess(result.result);
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						callBack.onLoadingStart();
					}

					@Override
					public void onCancelled() {
						// TODO Auto-
						super.onCancelled();
						callBack.onLoadingCancelled();
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// TODO Auto-generated method stub
						super.onLoading(total, current, isUploading);
						callBack.onLoading(total,current);
					}
				});

	}

	/**
	 * 取消请求
	 * 
	 * @param handler
	 */
	public <T> void cancelRequest(HttpHandler<T> handler) {
		handler.cancel(true);
	}

}
