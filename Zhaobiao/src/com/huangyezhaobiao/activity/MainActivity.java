package com.huangyezhaobiao.activity;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huangye.commonlib.vm.callback.ListNetWorkVMCallBack;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.adapter.PopAdapter;
import com.huangyezhaobiao.application.BiddingApplication;
import com.huangyezhaobiao.bean.push.PushBean;
import com.huangyezhaobiao.bean.push.PushToPassBean;
import com.huangyezhaobiao.enums.TitleBarType;
import com.huangyezhaobiao.inter.ActivityInterface;
import com.huangyezhaobiao.inter.INotificationListener;
import com.huangyezhaobiao.inter.MDConstans;
import com.huangyezhaobiao.lib.QDBaseBean;
import com.huangyezhaobiao.lib.ZBBaseAdapter.AdapterListener;
import com.huangyezhaobiao.netmodel.INetStateChangedListener;
import com.huangyezhaobiao.netmodel.NetStateManager;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.AnimationController;
import com.huangyezhaobiao.utils.MDUtils;
import com.huangyezhaobiao.utils.NetUtils;
import com.huangyezhaobiao.utils.StateUtils;
import com.huangyezhaobiao.utils.UnreadUtils;
import com.huangyezhaobiao.utils.UpdateManager;
import com.huangyezhaobiao.utils.UserUtils;
import com.huangyezhaobiao.view.LoadingProgress;
import com.huangyezhaobiao.view.MyCustomDialog;
import com.huangyezhaobiao.view.SegmentControl;
import com.huangyezhaobiao.view.TitleMessageBarLayout;
import com.huangyezhaobiao.view.TitleMessageBarLayout.OnTitleBarClickListener;
import com.huangyezhaobiao.view.ZhaoBiaoDialog;
import com.huangyezhaobiao.view.ZhaoBiaoDialog.onDialogClickListener;
import com.huangyezhaobiao.vm.GrabListViewModel;
import com.huangyezhaobiao.vm.KnockViewModel;
import com.huangyezhaobiao.vm.UpdateViewModel;
import com.huangyezhaobiao.vm.YuEViewModel;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
/**
 * 主页面
 * @author 58
 * 
 */
public class MainActivity extends SlidingFragmentActivity implements
		ActivityInterface, OnClickListener, ListNetWorkVMCallBack,
		onDialogClickListener, INotificationListener, OnTitleBarClickListener,INetStateChangedListener, AdapterListener, OnGestureListener {
	
	private ZhaoBiaoDialog dialog;
	private TitleMessageBarLayout tbl;
	
	private SegmentControl mSegmentControl;// head中间服务与休息选择器
	private ImageView refreshbutton; // head右侧消息按钮
	private ImageView userbutton; // head左侧用户按钮
	
	private PullToRefreshListView mPullToRefreshListView; // 抢单列表
	private GrabListViewModel listViewModel; // 抢单列表的viewModel
	private KnockViewModel knockViewModel;//抢单的viewModel
	private PushToPassBean passBean;//抢单流程传递参数
	
	
	private LinearLayout no_bid;//服务模式下没有订单布局
	private Button to_service;//没有订单下的进服务模式按钮
	
	private PopAdapter adapter; // 列表适配器
	private SlidingMenu menu; // 用户模块
	private LinearLayout gary;//侧滑页面时使用的遮罩页
	private RelativeLayout myOrder; // 抢单弹窗
	private ImageView iv_refresh;// 刷新按钮
	private RelativeLayout help;
	private View about;
	private View rl_exit;
	private AnimationController animationController;// 动画控制类
	
	private TextView tv_yue;
	private YuEViewModel yuEViewModel;
	private BiddingApplication app;
	private UpdateViewModel updateViewModel;
	private UpdateManager updateManager;
	private TextView tv_unread;
	private TextView smallred;
	private TextView tv_userCompany;

	@SuppressLint("NewApi")
	private LoadingProgress loading;
	private GestureDetectorCompat detector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (BiddingApplication) getApplication();
		app.registerNetStateListener();
		setContentView(R.layout.activity_main);
		dialog = new ZhaoBiaoDialog(this, "提示", "确定要退出当前账号吗?");
		dialog.setOnDialogClickListener(this);
		initControl();
		initEvent();
		getSlidingMenu().setOnClosedListener(new OnClosedListener() {
			@Override
			public void onClosed() {
			}
		});
		listViewModel = new GrabListViewModel(this, this);
		listViewModel.refresh();
		yuEViewModel = new YuEViewModel(this, this);
		updateViewModel = new UpdateViewModel(this, this);
		//updateViewModel.checkVersion();
		adapter = new PopAdapter(this, this);
		ListView mListView = mPullToRefreshListView.getRefreshableView();
		mListView.setDividerHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 10, getResources()
						.getDisplayMetrics()));
		detector = new GestureDetectorCompat(this, this);
	}

	@Override
	protected void onDestroy() {
		app.unRegisterNetStateListener();//解除网络的变化Listener
		app.stopTimer();//停止文件的上传
		super.onDestroy();
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		readNumbers();
		app.setCurrentNotificationListener(this);
		NetStateManager.getNetStateManagerInstance().setINetStateChangedListener(this);
		tbl.setVisibility(View.GONE);	
		if(NetUtils.isNetworkConnected(this)){
			NetConnected();
		}else{
			NetDisConnected();
		}
		
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		listViewModel.refresh();
		
	}


	// 初始化组建
	public void initControl() {
		tbl = (TitleMessageBarLayout) findViewById(R.id.tbl);
		mSegmentControl = (SegmentControl) findViewById(R.id.segment_control);
		//初始化服务模式
		StateUtils.state = 1;
		tbl.setVisibility(View.GONE);
		tbl.setTitleBarListener(this);
		refreshbutton = (ImageView) this.findViewById(R.id.refreshbutton);
		userbutton = (ImageView) this.findViewById(R.id.userbutton);
//		serviceLinearLayout = (LinearLayout) this
//				.findViewById(R.id.service_loading);
		mPullToRefreshListView = (PullToRefreshListView) this
				.findViewById(R.id.mainlist);
		//relativeLayout = (RelativeLayout) this.findViewById(R.id.orderpop);
		no_bid = (LinearLayout) findViewById(R.id.no_bid);
		to_service = (Button) findViewById(R.id.to_service);
		// 初始化侧滑控件
		setBehindContentView(R.layout.slide_menu);
		menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		// menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		myOrder = (RelativeLayout) this.findViewById(R.id.myorder);
		help = (RelativeLayout) findViewById(R.id.help);
		about = findViewById(R.id.about);
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
		// shenzhx
		iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
		rl_exit = findViewById(R.id.rl_exit);
		//call_400_map = findViewById(R.id.call_400_map);
		animationController = new AnimationController();
		gary = (LinearLayout) findViewById(R.id.garyview);
		tv_yue = (TextView) findViewById(R.id.tv_yue);
		tv_unread = (TextView)findViewById(R.id.tv_unread);
		smallred = (TextView)findViewById(R.id.smallred);
		tv_userCompany = (TextView)findViewById(R.id.tv_userCompany);
		tv_userCompany.setText(UserUtils.getUserCompany(this));
		
		readNumbers();
		
	}

	// 初始化事件监听
	public void initEvent() {
		mSegmentControl
				.setmOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
					@Override
					public void onSegmentControlClick(int index) {
						onChangeView(index);
					}
				});
		refreshbutton.setOnClickListener(new OnClickListener() {// 创建监听对象
					public void onClick(View v) {
						//跳转到我的订单中心
						ActivityUtils.goToActivity(MainActivity.this, OrderListActivity.class);
					}
				});
		userbutton.setOnClickListener(new OnClickListener() {// 创建监听对象
					public void onClick(View v) {
						toggle();
					}
				});
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						if (refreshView.isHeaderShown()) {
							String label = DateUtils.formatDateTime(
									getApplicationContext(),
									System.currentTimeMillis(),
									DateUtils.FORMAT_SHOW_TIME
											| DateUtils.FORMAT_SHOW_DATE
											| DateUtils.FORMAT_ABBREV_ALL);
							refreshView.getLoadingLayoutProxy()
									.setLastUpdatedLabel(label);
							listViewModel.refresh();
							MDUtils.servicePageMD(MainActivity.this, "0", "0", MDConstans.ACTION_PULL_TO_REFRESH);
						} else {
							listViewModel.loadMore();
							MDUtils.servicePageMD(MainActivity.this, "0", "0", MDConstans.ACTION_LOAD_MORE_REFRESH);
						}
					}
				});
		
		to_service.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSegmentControl.getOnSegmentControlClicklistener().onSegmentControlClick(0);
				
				mSegmentControl.service(0);
			}
		});
		

		menu.setOnOpenListener(new OnOpenListener() {
			@Override
			public void onOpen() {
				yuEViewModel.getBalance();
				userbutton.setVisibility(View.GONE);
				animationController.fadeIn(gary, 1000, 0);
				readNumbers();
			
			}

			

		});
		menu.setOnCloseListener(new OnCloseListener() {
			@Override
			public void onClose() {
				userbutton.setVisibility(View.VISIBLE);
				animationController.fadeOut(gary, 1000, 0);
			}
		});

		myOrder.setOnClickListener(new OnClickListener() {// 创建监听对象
			public void onClick(View v) {
				// shenzhixin 2015-6-25 注释 写到自己的业务逻辑里面
				ActivityUtils.goToActivity(MainActivity.this,
						MessageCenterActivity.class);
			
			}
		});

		help.setOnClickListener(this);
		about.setOnClickListener(this);
		rl_exit.setOnClickListener(this);
		iv_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				yuEViewModel.getBalance();
		        tv_yue.setText("获取中..");
				// 要判断是否在刷新中，如果已经在刷新中就不要在刷新了，出一个提示就可以
				// 刷新，去取数据，取到后赋值。ui也要变化，iv消失，pb出现，等结束后再恢复。
				MDUtils.myUserCenterMD(MainActivity.this,MDConstans.ACTION_REFRESH_YUE);
			}
		});

	}
	//侧滑栏小红点，头像小红点
	private void readNumbers() {
		int num = UnreadUtils.getAllNum(MainActivity.this);
		Log.e("ashen", "num:"+num);
		if(num==0){
			tv_unread.setVisibility(View.GONE);
			smallred.setVisibility(View.GONE);
			
		}else{
			smallred.setVisibility(View.VISIBLE);
			smallred.setText(num+"");
			tv_unread.setVisibility(View.VISIBLE);
			tv_unread.setText(num+"");
		}
	}
	
	// 判断选择的是服务还是休息
	private void onChangeView(int index) {
		Log.e("ashjhj", "index:"+index);
		try {
			// 测试界面，实际开发中是从layout中读取的，下同。
			switch (index) {
			case 0:
				StateUtils.state = 1;
				break;
			case 1:
				StateUtils.state  = 2;
				break;
			default:
				break;
			}
			listViewModel.refresh();
			Log.e("ssssssssss", "state:"+StateUtils.getState(MainActivity.this));
		} catch (Exception e) {
			// TODO: handle exception
		}
		//Log.e("ashjhj", "status:"+StaticVariable.status);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.help:// 跳到帮助
			ActivityUtils.goToActivity(this, HelpActivity.class);
			break;
		case R.id.about:// 关于
			ActivityUtils.goToActivity(this, AboutActivity.class);
			break;
		case R.id.rl_exit:// 退出
			dialog.show();
			break;
		
		}
	}

	@Override
	public void onLoadingStart() {
		Log.e("ashqsqsqsqsen", "start");
		startLoading();
		
	}

	@Override
	public void onLoadingSuccess(Object t) {
		stopLoading();
		Log.e("asheasasasn", "loadingSuccess");
		if (t instanceof Map<?, ?>) {
			Log.e("asheasasasn", "is");
			Map<String, String> maps = (Map<String, String>) t;
			String balance = maps.get("balance");
			if(!TextUtils.isEmpty(balance)){
				tv_yue.setText(balance);
				Log.e("asheasasasn", "balance:"+balance);
			}else{
				updateManager = UpdateManager.getUpdateManager();
				String currentVersion = maps.get("currentVersion");
				String url = maps.get("url");
				Log.e("ashendownload", "version:"+currentVersion+",url:"+url);
				try {
					updateManager.isUpdateNow(this, currentVersion,url);
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			}
			Log.e("aaa", "nnn:" + balance);
		}
		
		if (t instanceof Integer){
			
			int status = (Integer) t;
			
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("passBean", passBean);
			intent.putExtras(bundle);
			
			if(status==3){
				intent.setClass(MainActivity.this, BidLoadingActivity.class);
				startActivity(intent);
			}
			else if(status==1){
				intent.setClass(MainActivity.this, BidGoneActivity.class);
				startActivity(intent);
			}
			else if(status==2) {
				
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
		stopLoading();
	}

	@Override
	public void onLoadingCancel() {
		stopLoading();
	}

	@Override
	public void onRefreshSuccess(Object t) {
		List<QDBaseBean> list = (List<QDBaseBean>)t;
		adapter.refreshSuccess(list);
		mPullToRefreshListView.onRefreshComplete();
		
		if(null==list||list.size()==0&&StateUtils.getState(this)==2){
			no_bid.setVisibility(View.VISIBLE);
		}else{
			no_bid.setVisibility(View.GONE);
		}
		
	}

	@Override
	public void onLoadingMoreSuccess(Object t) {
		List<QDBaseBean> list = (List<QDBaseBean>)t;
		adapter.loadMoreSuccess(list);
		mPullToRefreshListView.onRefreshComplete();
	}

	@Override
	public void onNoInterNetError() {
		Toast.makeText(this, "没有网络", 0).show();
		stopLoading();
	}

	@Override
	public void onDialogOkClick() {
		//发送请求，成功才结束
		Toast.makeText(this, "退出了当前的账号", 0).show();
		MDUtils.myUserCenterMD(this, MDConstans.ACTION_EXIT);
		UserUtils.clearUserInfo(this);
		dialog.dismiss();
		ActivityUtils.goToActivity(this, LoginActivity.class);
		finish();
		
	}

	@Override
	public void onDialogCancelClick() {
		dialog.dismiss();
	}

	@Override
	protected void onPause() {
		super.onPause();
		app.removeINotificationListener();
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
		if(tbl.getType()==TitleBarType.NETWORK_ERROR){
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
				tbl.showNetError();
				tbl.setVisibility(View.VISIBLE);
			}
		});
	
	}

	@Override
	public void onAdapterRefreshSuccess() {
		mPullToRefreshListView.setAdapter(adapter);
	}

	@Override
	public void onAdapterLoadMoreSuccess() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onAdapterViewClick(int id,PushToPassBean bean) {

		passBean = bean;
		knockViewModel = new KnockViewModel(MainActivity.this, MainActivity.this);
		knockViewModel.knock(bean);

	}

	@Override
	public void onNotificationCome(PushBean pushBean) {
		Log.e("flingTest", "step1:state:"+StateUtils.getState(MainActivity.this)+",ss:"+StateUtils.state);
		
		if (null != pushBean) {
			Log.e("onNotificationCome", "step2");
			int type = pushBean.getTag();
			if (type == 100 && StateUtils.getState(MainActivity.this) == 1) {
				Log.e("onNotificationCome", "step3");
				MyCustomDialog dialog = MyCustomDialog.getInstance(this, new MyCustomDialog.OnCustomDialogListener() {
					//点击抢单后的回调
					
					@Override
					public void back(PushToPassBean bean) {
						Log.e("onNotificationCome", "step5");
						
						passBean = bean;
						knockViewModel = new KnockViewModel(MainActivity.this, MainActivity.this);
						knockViewModel.knock(bean);
					}
				});
				Log.e("onNotificationCome", "step4");
				dialog.show();
			}
			else{
				tbl.setPushBean(pushBean);
				tbl.setVisibility(View.VISIBLE);
			}
			Log.e("onNotificationCome", "step6");
		}
		Log.e("onNotificationCome", "step7");
		readNumbers();
		Log.e("onNotificationCome", "step8");

	}
	
	/**
	 * 加载效果
	 */
	public void startLoading() {
		if (loading == null) {
			loading = new LoadingProgress(MainActivity.this,
					R.style.loading);
		}
		loading.show();
	}

	/**
	 * 对话框消失
	 */
	public void stopLoading() {
		if (loading != null && loading.isShowing()) {
			loading.dismiss();
			loading = null;
		}
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
			detector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.e("scrolllllllll", "distanceX:"+distanceX+",distancty:"+distanceY);
		if(Math.abs(distanceX)>Math.abs(distanceY) && Math.abs(distanceX)>40){
			
			if(distanceX>0){
				//服務模式
				mSegmentControl.getOnSegmentControlClicklistener().onSegmentControlClick(0);
				mSegmentControl.service(0);
			}else{
				mSegmentControl.getOnSegmentControlClicklistener().onSegmentControlClick(1);
				mSegmentControl.service(1);
			}
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		float distanceX = e1.getX() - e2.getX();
		float distanceY = e1.getY() - e2.getY();
		Log.e("detector", "fling....e1x:"+e1.getX()+",e2x:"+e2.getX()+"disX:"+Math.abs(e2.getX()-e1.getX())+",disY:"+Math.abs(e2.getY()-e1.getY()));
		if(Math.abs(distanceX)>Math.abs(distanceY)){
			if(distanceX>0){
				Log.e("flingTest", "service...");
				//服務模式
				mSegmentControl.getOnSegmentControlClicklistener().onSegmentControlClick(0);
				mSegmentControl.service(0);
			}else{
				//休息模式
				Log.e("flingTest", "rest...");
				mSegmentControl.getOnSegmentControlClicklistener().onSegmentControlClick(1);
				mSegmentControl.service(1);
			}
			return true;
		}
		return false;
		
	}
}
