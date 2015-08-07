package com.huangyezhaobiao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huangye.commonlib.vm.callback.ListNetWorkVMCallBack;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.activity.MainActivity;
import com.huangyezhaobiao.activity.OrderListActivity;
import com.huangyezhaobiao.activity.QBBaseActivity;
import com.huangyezhaobiao.inter.MDConstans;
import com.huangyezhaobiao.lib.ZBBaseAdapter.AdapterListener;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.MDUtils;
import com.huangyezhaobiao.view.LoadingProgress;
import com.huangyezhaobiao.vm.QiangDanListViewModel;
/**
 * 抢单列表的基类fragment
 * 抽一些共通的方法
 * @author shenzhixin
 * 第一次去取数据，然后后面就是手动的取刷新
 */
public abstract class QiangDanBaseFragment extends Fragment implements ListNetWorkVMCallBack, OnItemClickListener,AdapterListener{
	protected QiangDanListViewModel lvm;
	protected boolean isLoadFirst;
	private View emptyView;
	private LoadingProgress dialog;
	public static String orderState;
	public static final String WAITING_SERVICE = "1";
	public static final String ON_SERVICE = "2";
	public static final String DONE_SERVICE = "3";
	public static final String DONE_SERVICE_FINISH = "31";
	public static final String DONE_SERVICE_CANCEL = "32";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		lvm = new QiangDanListViewModel(this,getActivity());
		
	}

	/**
	 * 加载网络数据
	 */
	protected abstract void loadDatas();

	/**
	 * 给ListView配备动画效果
	 * @param lv
	 * @param controller
	 */
	protected void configListView(ListView lv,LayoutAnimationController controller){
		lv.setLayoutAnimation(controller);
	}
	
	/**
	 * 配置下拉刷新，上拉刷新的lv
	 * @param mPullToRefreshListView
	 */
	public void configRefreshableListView(
			PullToRefreshListView mPullToRefreshListView) {
		/** 初始化抢单列表 */
		mPullToRefreshListView.setMode(Mode.BOTH);
		// 设置上拉下拉事件
		mPullToRefreshListView.getLoadingLayoutProxy(true, false)
				.setLastUpdatedLabel("");
		mPullToRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel(
				"下拉刷新");
		mPullToRefreshListView.getLoadingLayoutProxy(true, false)
				.setRefreshingLabel("正在刷新");
		mPullToRefreshListView.getLoadingLayoutProxy(true, false)
				.setReleaseLabel("放开以刷新");
		// 上拉加载更多时的提示文本设置
		mPullToRefreshListView.getLoadingLayoutProxy(false, true)
				.setLastUpdatedLabel("");
		mPullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(
				"上拉加载");
		mPullToRefreshListView.getLoadingLayoutProxy(false, true)
				.setRefreshingLabel("正在加载...");
		mPullToRefreshListView.getLoadingLayoutProxy(false, true)
				.setReleaseLabel("放开以加载");
		configListViewRefresh(mPullToRefreshListView);
	}
	
	/**
	 * 配置listView的上拉下拉事件
	 */
	private void configListViewRefresh(PullToRefreshListView lv){
		lv.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					// 下拉刷新
					String label = DateUtils.formatDateTime(getActivity()
							.getApplicationContext(), System
							.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
					// Update the LastUpdatedLabel
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
							label);
					//加载数据
					loadDatas();
					MDUtils.OrderListPageMD(orderState, "0", "0", MDConstans.ACTION_PULL_TO_REFRESH);
				} else {
					// 上滑加载---page++
					//加载数据
					loadMore();
					MDUtils.OrderListPageMD(orderState, "0", "0", MDConstans.ACTION_LOAD_MORE_REFRESH);
				}
			}
		});
	}
	
	/**
	 * 下拉加载
	 */
	protected abstract void loadMore();

	/**
	 * 配置layout的动画
	 * @return
	 */
	protected LayoutAnimationController getAnimationController(float fromX,float toX){
		AnimationSet set = new AnimationSet(true);
		AlphaAnimation   alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(600);
		TranslateAnimation animation = new TranslateAnimation(fromX, toX, 0, 0);
		animation.setDuration(600);
		set.addAnimation(alphaAnimation);
		set.addAnimation(animation);
		set.setInterpolator(new OvershootInterpolator(1.3f));
		LayoutAnimationController lac = new LayoutAnimationController(set, 0.3f);
		return lac;
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser && !isLoadFirst){
			//设置orderState
			loadDatas();
			isLoadFirst = true;
			Log.e("ashenaassaasassaass", "visible");
		}else{
			Log.e("ashen", "not visible");
		}
	}
	
	/**
	 * 判断有没有数据，没数据就显示啥都没
	 * @param lv
	 * @param rl
	 * @param size
	 */
	protected void judgeHasOrNotOrder(ListView lv,View rl,int size){
		if(size>0){
			lv.setVisibility(View.VISIBLE);
			rl.setVisibility(View.GONE);
		}else{
			lv.setVisibility(View.GONE);
			rl.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 给ListView设置点击事件
	 */
	protected void setOnItemClickListener(ListView listView){
		if(listView!=null){
			listView.setOnItemClickListener(this);
		}
	}

	protected void goQDActivity(View view){
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityUtils.goToActivity(getActivity(), MainActivity.class);
			}
		});
	}
	
	
	public void callBackActivity(String orderId){
		((OrderListActivity)getActivity()).onItemClick(orderId);
	}
	
	
	
	/**
	 * 加载效果
	 */
	public void startLoading() {
		if (dialog == null) {
			dialog = new LoadingProgress(getActivity(),
					R.style.loading);
		}
		dialog.show();
	}

	/**
	 * 对话框消失
	 */
	public void stopLoading() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
		}
	}
}
