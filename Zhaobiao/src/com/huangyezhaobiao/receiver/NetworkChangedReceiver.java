package com.huangyezhaobiao.receiver;

import com.huangyezhaobiao.netmodel.NetStateManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 网络变化的接受器 网络开始时和网络结束时都有
 * 
 * @author 58
 * 
 */
public class NetworkChangedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("ashenNet", "net changed...");
		String action = intent.getAction();
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			if (networkInfo.isConnected()) {
				Log.e("ashenUU", "net connected");
				NetStateManager.getNetStateManagerInstance().setNetState(NetStateManager.NET_CONNTTED);
			}
		} else {
			Log.e("ashenUU", "net not connected");
			NetStateManager.getNetStateManagerInstance().setNetState(NetStateManager.NET_DISCONNECTED);
		}
	}

}
