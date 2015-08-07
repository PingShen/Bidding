package com.huangye.commonlib.vm.callback;


public interface NetWorkVMCallBack {
	 void onLoadingStart();
	 void onLoadingSuccess(Object t);
	 void onLoadingError(String msg);
	 void onLoadingCancel();
	 void onNoInterNetError();
}