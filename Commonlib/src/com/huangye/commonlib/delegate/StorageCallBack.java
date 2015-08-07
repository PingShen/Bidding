package com.huangye.commonlib.delegate;

public interface StorageCallBack {
	/**
	 * 读取数据成功
	 * @param list 
	 * @param exception
	 * @param err
	 */
	public  void getDataSuccess(Object o);
	
	/**
	 * 读取数据失败
	 * @param jsonResult
	 */
	public void getDataFailure();
	
	public void insertDataSuccess();
	public void insertDataFailure();
	public void deleteDataSuccess();
	public void deleteDataFailure();
	

}
