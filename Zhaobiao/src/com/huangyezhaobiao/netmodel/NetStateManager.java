package com.huangyezhaobiao.netmodel;

import android.R.integer;
import android.util.Log;

/**
 * 网络状态的管理者，用他来进行网络状态的管理
 * @author shenzhixin
 *
 */
public class NetStateManager {
	private INetStateChangedListener listener;
	private static NetStateManager manager = new NetStateManager();
	private int net_state;
	public static final int NET_CONNTTED = 1;
	public static final int NET_DISCONNECTED = 2;
	private NetStateManager(){};
	
	public static NetStateManager getNetStateManagerInstance(){
		return manager;
	}
	
	public void setNetState(int state){
		net_state = state;
		if(listener!=null){
			if(net_state==NET_CONNTTED){
				listener.NetConnected();
			}else{
				Log.e("ashenUU", "not connected");
				listener.NetDisConnected();
			}
		}
		
	}
	public void setINetStateChangedListener(INetStateChangedListener listener){
		this.listener = listener;
	}
}
