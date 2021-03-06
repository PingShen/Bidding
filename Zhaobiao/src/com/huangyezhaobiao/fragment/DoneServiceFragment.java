package com.huangyezhaobiao.fragment;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
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
 * 已结束列表
 * @author shenzhx
 *
 */
public class DoneServiceFragment extends QiangDanBaseFragment {
	private View root_fragment_all;
	private PullToRefreshListView lv_all_fragment;
	private ListView lv;
	private View layout_no_internet,layout_no_internet_click;
	private OrderLVAdapter adapter;
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
			adapter = new OrderLVAdapter(getActivity(),this,2);
			configRefreshableListView(lv_all_fragment);//配置pullLv
			lv.setAdapter(adapter);
			lv.setDividerHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
			setOnItemClickListener(lv);
			goQDActivity(layout_no_internet_click);
		}else{
			((FrameLayout)root_fragment_all.getParent()).removeView(root_fragment_all);
		}
		Log.e("ashenService", "onCreateView...doneService");
	
		return root_fragment_all;
	}

	@Override
	public void onRefreshSuccess(Object t) {
		List<QDBaseBean> beans = (List<QDBaseBean>) t;
		adapter.refreshSuccess(beans);
		lv_all_fragment.onRefreshComplete();
		judgeHasOrNotOrder(lv,layout_no_internet,beans.size());
	}

	@Override
	public void onLoadingMoreSuccess(Object res) {
		List<QDBaseBean> beans = (List<QDBaseBean>) res;
		adapter.loadMoreSuccess(beans);
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
		if(lv_all_fragment!=null &&lv_all_fragment.isRefreshing())
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
	public void onDestroy() {
		super.onDestroy();
		stopLoading();
		if(lv_all_fragment!=null && lv_all_fragment.isRefreshing())
			lv_all_fragment.onRefreshComplete();
		
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		
		if(root_fragment_all!=null){
			if(isVisibleToUser){
				orderState = Constans.DONE_FRAGMENT;
				root_fragment_all.setVisibility(View.VISIBLE);
			}else{
				root_fragment_all.setVisibility(View.GONE);
			}
		}
		if(isVisibleToUser && lv!=null){
			configListView(lv, getAnimationController(400,0));
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	/*	String orderId = adapter.getSources().get(position).getOrderId();
		callBackActivity(orderId);*/
	}

	@Override
	public void onNoInterNetError() {
		if(lv_all_fragment!=null && lv_all_fragment.isRefreshing())
			lv_all_fragment.onRefreshComplete();
		stopLoading();
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
