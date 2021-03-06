package com.huangyezhaobiao.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.huangye.commonlib.vm.callback.NetWorkVMCallBack;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.application.BiddingApplication;
import com.huangyezhaobiao.bean.push.PushToPassBean;
import com.huangyezhaobiao.bean.push.PushBean;
import com.huangyezhaobiao.enums.TitleBarType;
import com.huangyezhaobiao.fragment.DoneServiceFragment;
import com.huangyezhaobiao.fragment.OnServiceFragment;
import com.huangyezhaobiao.fragment.ReadyServiceFragment;
import com.huangyezhaobiao.inter.INotificationListener;
import com.huangyezhaobiao.netmodel.INetStateChangedListener;
import com.huangyezhaobiao.netmodel.NetStateManager;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.LogUtils;
import com.huangyezhaobiao.utils.MDUtils;
import com.huangyezhaobiao.utils.NetUtils;
import com.huangyezhaobiao.utils.StateUtils;
import com.huangyezhaobiao.utils.ViewPageHelper;
import com.huangyezhaobiao.view.CustomViewPager;
import com.huangyezhaobiao.view.MyCustomDialog;
import com.huangyezhaobiao.view.MyCustomDialog.OnCustomDialogListener;
import com.huangyezhaobiao.view.TitleMessageBarLayout;
import com.huangyezhaobiao.view.ZhaoBiaoDialog;
import com.huangyezhaobiao.view.TitleMessageBarLayout.OnTitleBarClickListener;
import com.huangyezhaobiao.view.ZhaoBiaoDialog.onDialogClickListener;
import com.huangyezhaobiao.vm.KnockViewModel;
import com.tencent.android.tpush.service.t;

/**
 * 我的订单页面，由3个fragment+viewpager组成
 * 
 * @author shenzhx
 * 
 *         注意: 1.viewpager+fragment组合常见问题(销毁，何时加载，手势冲突)
 *         2.每个界面都是从网络获取数据展示，网络层的东西--封装 3.listView+adapter结合 4.待定
 * 
 */
public class OrderListActivity extends FragmentActivity implements
		OnClickListener, INotificationListener, OnTitleBarClickListener,
		INetStateChangedListener, NetWorkVMCallBack {
	private static final int INVALID_WIDTH = -1;
	private static final int INVALID_INDEX = INVALID_WIDTH;
	private static final int FRAGMENT_COUNTS = 3;
	private static final int ALL_INDEX = INVALID_INDEX + 1;
	private static final int CONTACT_INDEX = ALL_INDEX + 1 - 1;
	private static final int ENSURE_INDEX = CONTACT_INDEX + 1;
	private static final int DONE_INDEX = ENSURE_INDEX + 1;
	private TitleMessageBarLayout tbl;
	private LinearLayout back_layout;
	private RelativeLayout /* rl_all, */rl_contact, rl_ensure, rl_done;
	private TextView /* tv_all, */tv_contact, tv_ensure, tv_done;
	private View line_fragment_title;
	private CustomViewPager viewpage_fragment;
	private int line_width = INVALID_WIDTH;
	private ViewPageAdapter adapter;
	private List<Fragment> fragments_sources = new ArrayList<Fragment>();
	private int mCurrentIndex = 0;
	private OnServiceFragment f;
	private BiddingApplication app;
	public static TextView message, txt_head;
	private KnockViewModel knockViewModel;
	private ZhaoBiaoDialog dialog;
	private PushToPassBean passBean;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		app = (BiddingApplication) getApplication();
		initEnvironment();
		setContentView(R.layout.activity_szx_myorder);
		initView();
		registerViewListener();
		initDatas();
		configViewPager();
		bindListener();
		configUIStyle(CONTACT_INDEX);

	}

	protected void onResume() {
		super.onResume();
		tbl.setVisibility(View.GONE);
		app.setCurrentNotificationListener(this);
		NetStateManager.getNetStateManagerInstance()
				.setINetStateChangedListener(this);
		if (NetUtils.isNetworkConnected(this)) {
			NetConnected();
		} else {
			NetDisConnected();
		}
	};

	/**
	 * 监听事件
	 */
	private void bindListener() {
		back_layout.setOnClickListener(this);
		// rl_all.setOnClickListener(this);
		rl_contact.setOnClickListener(this);
		rl_done.setOnClickListener(this);
		rl_ensure.setOnClickListener(this);
	}

	/**
	 * 对viewpager进行配置
	 */
	private void configViewPager() {
		adapter = new ViewPageAdapter(getSupportFragmentManager());
		viewpage_fragment.setAdapter(adapter);
		viewpage_fragment.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				LogUtils.LogE("demo", "onpageSelected");
				mCurrentIndex = position;
				configUIStyle(position);
				f.setFromX(position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				ViewPageHelper.goLine(line_fragment_title, position,
						positionOffset, line_width);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});

	}

	/**
	 * 把这四个fragment加入到集合中
	 */
	private void initDatas() {
		// addFragment(new AllFragment());
		f = new OnServiceFragment();
		addFragment(new ReadyServiceFragment());
		addFragment(f);
		addFragment(new DoneServiceFragment());
	}

	private void addFragment(Fragment fragment) {
		fragments_sources.add(fragment);
	}

	/**
	 * 给view来绑定listener
	 */
	private void registerViewListener() {
		rl_contact.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						rl_contact.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						line_width = rl_contact.getWidth();
						LayoutParams params = (LayoutParams) line_fragment_title
								.getLayoutParams();
						params.width = line_width;
						line_fragment_title.setLayoutParams(params);
					}
				});
	}

	/**
	 * 寻找view
	 */
	private void initView() {
		back_layout = findView(R.id.back_layout);
		// rl_all = findView(R.id.rl_all);
		rl_contact = findView(R.id.rl_contact);
		rl_done = findView(R.id.rl_done);
		rl_ensure = findView(R.id.rl_ensure);
		line_fragment_title = findView(R.id.line_fragment_title);
		viewpage_fragment = findView(R.id.viewpage_fragment);
		viewpage_fragment.setPagingEnabled(true);
		viewpage_fragment.setOffscreenPageLimit(2);
		// tv_all = findView(R.id.tv_all);
		tv_contact = findView(R.id.tv_contact);
		tv_done = findView(R.id.tv_done);
		tv_ensure = findView(R.id.tv_ensure);
		tbl = findView(R.id.tbl);

		// 初始化消息栏
		// layout_hint = (RelativeLayout) findViewById(R.id.layout_hint);
		message = (TextView) findViewById(R.id.message);
		txt_head = findView(R.id.txt_head);
		txt_head.setText("我的抢单");
		tbl.setTitleBarListener(this);

	}

	/**
	 * 进行一些界面环境的配置
	 */
	private void initEnvironment() {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	/**
	 * 根据资源Id找到对应的view
	 * 
	 * @param res_id
	 * @return
	 */
	public <T> T findView(int res_id) {
		return (T) findViewById(res_id);
	}

	private class ViewPageAdapter extends FragmentStatePagerAdapter {

		public ViewPageAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments_sources.get(arg0);
		}

		@Override
		public int getCount() {
			return FRAGMENT_COUNTS;
		}

	}

	@Override
	public void onClick(View v) {
		int index = INVALID_INDEX;
		switch (v.getId()) {
		case R.id.back_layout:
			onBackPressed();
			break;
		/*
		 * case R.id.rl_all: index = ALL_INDEX; break;
		 */
		case R.id.rl_contact:
			index = CONTACT_INDEX;
			break;
		case R.id.rl_done:
			index = DONE_INDEX;
			break;
		case R.id.rl_ensure:
			index = ENSURE_INDEX;
			break;
		}
		LogUtils.LogE("viewpage", "index:" + index + "mCurrent:"
				+ mCurrentIndex);
		if (index != INVALID_INDEX && index != mCurrentIndex
				&& index <= DONE_INDEX && index >= ALL_INDEX) {
			// 满足条件，viewpage就动
			viewpage_fragment.setCurrentItem(index);
		}
	}

	/**
	 * 改变ui的样式
	 * 
	 * @param position
	 */
	public void configUIStyle(int position) {
		switch (position) {
		/*
		 * case ALL_INDEX:
		 * //tv_all.setTextColor(getResources().getColor(R.color.red));
		 * tv_contact.setTextColor(getResources().getColor(R.color.gray));
		 * tv_ensure.setTextColor(getResources().getColor(R.color.gray));
		 * tv_done.setTextColor(getResources().getColor(R.color.gray)); break;
		 */
		case CONTACT_INDEX:
			tv_contact.setTextColor(getResources().getColor(R.color.white));
			tv_contact.setBackgroundResource(R.drawable.bg_qiangdan_clicked);
			// tv_all.setTextColor(getResources().getColor(R.color.gray));
			tv_ensure.setTextColor(getResources().getColor(R.color.black));
			tv_ensure.setBackgroundDrawable(null);
			tv_done.setTextColor(getResources().getColor(R.color.black));
			tv_done.setBackgroundDrawable(null);
			break;
		case ENSURE_INDEX:
			tv_ensure.setTextColor(getResources().getColor(R.color.white));
			tv_ensure.setBackgroundResource(R.drawable.bg_qiangdan_clicked);
			// tv_all.setTextColor(getResources().getColor(R.color.gray));
			tv_contact.setTextColor(getResources().getColor(R.color.black));
			tv_contact.setBackgroundDrawable(null);
			tv_done.setTextColor(getResources().getColor(R.color.black));
			tv_done.setBackgroundDrawable(null);
			break;
		case DONE_INDEX:
			tv_done.setBackgroundResource(R.drawable.bg_qiangdan_clicked);
			tv_done.setTextColor(getResources().getColor(R.color.gray));
			// tv_all.setTextColor(getResources().getColor(R.color.gray));
			tv_contact.setTextColor(getResources().getColor(R.color.black));
			tv_contact.setBackgroundDrawable(null);
			tv_ensure.setTextColor(getResources().getColor(R.color.black));
			tv_ensure.setBackgroundDrawable(null);
			break;
		}
	}

	

	public void onItemClick(String id) {
		Log.e("ashenashen", "id:" + id);
		ActivityUtils.goToActivity(this, FetchDetailsActivity.class);
	}

	@Override
	public void onNotificationCome(PushBean pushBean) {
		Log.e("szxNotification", "notification come.");
		if (null != pushBean) {
			int type = pushBean.getTag();
			Log.e("szxNotification", "notification type:"+type+",state:"+StateUtils.getState(OrderListActivity.this));
			if (type == 100 && StateUtils.getState(OrderListActivity.this) == 1) {
				MyCustomDialog dialog = MyCustomDialog.getInstance(this,
						new MyCustomDialog.OnCustomDialogListener() {
							// 点击抢单后的回调
							@Override
							public void back(PushToPassBean bean) {
								passBean = bean;
								knockViewModel = new KnockViewModel(
										OrderListActivity.this,
										OrderListActivity.this);
								knockViewModel.knock(bean);
							}
						});
				Log.e("szxNotification", "show");
				dialog.show();
			} else {
				tbl.setPushBean(pushBean);
				tbl.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onTitleBarClicked(TitleBarType type) {
	}

	@Override
	public void onTitleBarClosedClicked() {
		tbl.setVisibility(View.GONE);
	}

	@Override
	public void NetConnected() {
		if (tbl != null && tbl.getType() == TitleBarType.NETWORK_ERROR) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					tbl.setVisibility(View.GONE);
				}
			});
		}
	}

	@Override
	public void NetDisConnected() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (tbl != null) {
					tbl.showNetError();
					tbl.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@Override
	public void onLoadingStart() {

	}

	@Override
	public void onLoadingSuccess(Object t) {

		if (t instanceof Integer) {

			int status = (Integer) t;

			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("passBean", passBean);
			intent.putExtras(bundle);

			if (status == 3) {
				intent.setClass(OrderListActivity.this,
						BidLoadingActivity.class);
				startActivity(intent);
			} else if (status == 1) {
				intent.setClass(OrderListActivity.this, BidGoneActivity.class);
				startActivity(intent);
			} else if (status == 2) {

				dialog = new ZhaoBiaoDialog(this, "提示", "您的账户余额不足");
				dialog.setCancelButtonGone();
				dialog.setOnDialogClickListener(new onDialogClickListener() {

					@Override
					public void onDialogOkClick() {
						dialog.dismiss();

					}

					@Override
					public void onDialogCancelClick() {
						// TODO Auto-generated method stub

					}
				});
				dialog.show();
				MDUtils.YuENotEnough("", "");
			}

		}

	}

	@Override
	public void onLoadingError(String msg) {

	}

	@Override
	public void onLoadingCancel() {

	}

	@Override
	public void onNoInterNetError() {

	}

}
