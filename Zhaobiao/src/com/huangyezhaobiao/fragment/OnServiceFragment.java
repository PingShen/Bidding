package com.huangyezhaobiao.fragment;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.adapter.OrderLVAdapter;
import com.huangyezhaobiao.bean.push.PushToPassBean;
import com.huangyezhaobiao.inter.Constans;
import com.huangyezhaobiao.lib.QDBaseBean;

/**
 * 服务中列表
 * @author shenzhx
 *
 */
public class OnServiceFragment extends QiangDanBaseFragment {
	private View root_fragment_all;
	private PullToRefreshListView lv_all_fragment;
	private ListView lv;
	private View layout_no_internet,layout_no_internet_click;
	private OrderLVAdapter adapter;
	private int fromX;
	
	public void setFromX(int x){
		if(x!=1){
			fromX = x;
			Log.e("ashen", "fromx:"+fromX);
		}
	}
	@Override
	protected void loadDatas() {
		lvm.refresh();
		Log.e("ashen", "readyService..load");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(root_fragment_all==null){
			root_fragment_all = inflater.inflate(R.layout.layout_all_fragment, null);
			lv_all_fragment = (PullToRefreshListView) root_fragment_all.findViewById(R.id.lv_all_fragment);
			lv = lv_all_fragment.getRefreshableView();
			layout_no_internet = root_fragment_all.findViewById(R.id.layout_no_internet);
			layout_no_internet_click = root_fragment_all.findViewById(R.id.layout_no_internet_click_refresh);
			adapter = new OrderLVAdapter(getActivity(),this,1);
			configRefreshableListView(lv_all_fragment);//配置pullLv
			lv.setAdapter(adapter);
			lv.setDividerHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
			setOnItemClickListener(lv);
			goQDActivity(layout_no_internet_click);
			
		}else{
			((FrameLayout)root_fragment_all.getParent()).removeView(root_fragment_all);
		}
	
		
		return root_fragment_all;
	}

	@Override
	public void onRefreshSuccess(Object t) {
		List<QDBaseBean>  beans= (List<QDBaseBean>) t;
		//Log.e("askfhsjs", "refresh size:"+adapter.beans);
		adapter.refreshSuccess(beans);
		lv_all_fragment.onRefreshComplete();
		judgeHasOrNotOrder(lv,layout_no_internet,beans.size());
		
	}

	@Override
	public void onLoadingMoreSuccess(Object res) {
		List<QDBaseBean>  beans= (List<QDBaseBean>) res;
		adapter.loadMoreSuccess(beans);
	//	int size = adapter.beans.size();
		//Log.e("askfhsjs", "lode more size:"+size);
		lv_all_fragment.onRefreshComplete();
	}

	@Override
	public void onLoadingStart() {
		startLoading();
	}

	@Override
	public void onLoadingSuccess(Object t) {
		stopLoading();
	}

	@Override
	public void onLoadingError(String msg) {
		stopLoading();
		if( lv_all_fragment!=null && lv_all_fragment.isRefreshing())
			lv_all_fragment.onRefreshComplete();
	}

	@Override
	public void onLoadingCancel() {
		stopLoading();
		if(lv_all_fragment!=null && lv_all_fragment.isRefreshing())
			lv_all_fragment.onRefreshComplete();
	}

	@Override
	protected void loadMore() {
		lvm.loadMore();
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
	
		if(root_fragment_all!=null){
			if(isVisibleToUser){
				QiangDanBaseFragment.orderState = Constans.ON_SERVICE;
				root_fragment_all.setVisibility(View.VISIBLE);
			}else{
				root_fragment_all.setVisibility(View.GONE);
			}
		}
		if(isVisibleToUser && lv!=null){
			configListView(lv, getAnimationController((1-fromX)*400,0));
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	/*	String orderId = adapter.getSources().get(position).getOrderId();
		callBackActivity(orderId);*/
	}
	@Override
	public void onNoInterNetError() {
		stopLoading();
		if(lv_all_fragment!=null && lv_all_fragment.isRefreshing())
			lv_all_fragment.onRefreshComplete();
	}
	@Override
	public void onAdapterRefreshSuccess() {
		lv.setAdapter(adapter);		
	}
	@Override
	public void onAdapterLoadMoreSuccess() {
		adapter.notifyDataSetChanged();
	}
	@Override
	public void onAdapterViewClick(int id, PushToPassBean bean) {
		
	}


	
}
