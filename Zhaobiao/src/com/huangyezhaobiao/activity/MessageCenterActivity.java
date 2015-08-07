package com.huangyezhaobiao.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huangye.commonlib.vm.callback.StorageVMCallBack;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.adapter.MessageSeriesAdapter;
import com.huangyezhaobiao.bean.push.PushBean;
import com.huangyezhaobiao.bean.push.PushToStorageBean;
import com.huangyezhaobiao.db.DataBaseManager.TABLE_OTHER;
import com.huangyezhaobiao.enums.PopTypeEnum;
import com.huangyezhaobiao.enums.TitleBarType;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.StateUtils;
import com.huangyezhaobiao.utils.UnreadUtils;
import com.huangyezhaobiao.view.MyCustomDialog;
import com.huangyezhaobiao.vm.CenterMessageStorageViewModel;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 消息中心的界面
 * 
 * @author shenzhixin 需要一个消息中心的viewModel和model 然后再进行存储的viewModel和model 在其他页面进行读取
 *         type值就一个
 */
public class MessageCenterActivity extends QBBaseActivity implements
		OnClickListener, StorageVMCallBack {
	private RelativeLayout rl_back;
	private Button btn_clean;
	private ListView lv_message_center;
	private MessageSeriesAdapter adapter;
	private CenterMessageStorageViewModel vm;
	private List<PushToStorageBean> messageBeans;
	private List<PushToStorageBean> qiangDan = new ArrayList<PushToStorageBean>();
	private List<PushToStorageBean> daoJiShi = new ArrayList<PushToStorageBean>();
	private List<PushToStorageBean> sysNoti = new ArrayList<PushToStorageBean>();
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_center_message);
		adapter = new MessageSeriesAdapter(this);
		initView();
		bindListener();
		// centerMessageViewModel = new CenterMessageViewModel(this,this);
		vm = new CenterMessageStorageViewModel(this, this);
		messageBeans = new ArrayList<PushToStorageBean>();
		

	}

	@Override
	protected void onResume() {
		loadDatas();
		super.onResume();
	}
	
	private void loadDatas() {
		vm.getDate();
		Log.e("pushshen", "line...74");
	}

	private void bindListener() {
		rl_back.setOnClickListener(this);
		btn_clean.setOnClickListener(this);
	}

	private Map<String, Integer> maps = new HashMap<String, Integer>();

	public void initView() {
		tbl = getView(R.id.tbl);
		rl_back = getView(R.id.rl_back);
		btn_clean = getView(R.id.btn_clean);
		btn_clean.setVisibility(View.GONE);
		// layout_hint = findView(R.id.layout_hint);
		lv_message_center = getView(R.id.lv_message_center);
		// lv_message_center.setAdapter(adapter);
		lv_message_center.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				int[] count = new int[] {/* YuE.size(), */qiangDan.size(),
						daoJiShi.size(), sysNoti.size() };
				switch (position + 1) {
				/*
				 * case 0: adapter.setEmpty(0);
				 * ActivityUtils.goToActivity(MessageCenterActivity.this,
				 * YuEDetailActivity.class); break;
				 */
				case 1:
					UnreadUtils.clearQDResult(MessageCenterActivity.this);
					maps.put("type", TABLE_OTHER.KOUFEI);
					ActivityUtils.goToActivityWithInteger(
							MessageCenterActivity.this,
							OtherDetailActivity.class, maps);
					break;
				case 2:
					UnreadUtils.clearDaoJiShiResult(MessageCenterActivity.this);
					maps.put("type", TABLE_OTHER.DAOJISHI);
					ActivityUtils.goToActivityWithInteger(
							MessageCenterActivity.this,
							OtherDetailActivity.class, maps);
					break;
				case 3:
					UnreadUtils.clearSysNotiNum(MessageCenterActivity.this);
					maps.put("type", TABLE_OTHER.SYSNOTIF);
					ActivityUtils.goToActivityWithInteger(
							MessageCenterActivity.this,
							OtherDetailActivity.class, maps);
					break;
				}

			}
		});
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		
	}

	private void notifyDatasWithoutUnread() {
		messageBeans.clear();
		// operateList(YuE);
		operateList(qiangDan);
		operateList(daoJiShi);
		operateList(sysNoti);
		// int[] counts = new int[]{/*YuE.size(),*/0,0,sysNoti.size()};
		adapter.setDataSources(messageBeans);
		// adapter.setUnreadCounts(counts);
		lv_message_center.setAdapter(adapter);
	}

	private void operateList(List<PushToStorageBean> bean) {
		if (bean.size() > 0) {
			PushToStorageBean single = bean.get(0);
			messageBeans.add(single);
		} else {
			PushToStorageBean bean2 = new PushToStorageBean("暂无消息", "");
			messageBeans.add(bean2);
		}
	}

	/**
	 * 根据type,加入不同的list,主要为了显示未读数字
	 * 
	 * @param type
	 */
	private void pickWithBeanType(PushToStorageBean type) {
		Log.e("ashenPush", "tag:" + type.getTag() + "msg:" + type.getStr());
		int aType = Integer.valueOf(type.getTag());
		switch (PopTypeEnum.getPopType(aType)) {
		/*
		 * case 0://余额提醒 YuE.add(type); break;
		 */
		case ORDERRESULT:// 抢单结果提醒
			Log.e("ashenjssi", "line:" + 173);
			qiangDan.add(type);
			break;
		case COUNTDOWN:// 倒计时提醒
			Log.e("ashenjssi", "line:" + 176);
			daoJiShi.add(type);
			break;
		case SYSTEMMESSAGE:// 系统提醒
			Log.e("ashenjssi", "line:" + 179);
			sysNoti.add(type);
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			onBackPressed();
			break;

		default:
			break;
		}
	}

	@Override
	public void getDataSuccess(Object o) {
		qiangDan.clear();
		daoJiShi.clear();
		sysNoti.clear();
		ArrayList<PushToStorageBean> bean = (ArrayList<PushToStorageBean>) o;
		if(bean==null){
			Log.e("shenzsswwhx", "null:...");
		}else{
			int size = bean.size();
			Log.e("shenzhx", "size:" + size);
			for (PushToStorageBean bean2 : bean) {
				pickWithBeanType(bean2);
			}
		}
		Log.e("pushshen", "size:" + qiangDan.size());
		notifyDatasWithoutUnread();

	}

	@Override
	public void getDataFailure() {
		Toast.makeText(this, "存取数据异常,建议重启app", 0).show();
	}

	@Override
	public void insertDataSuccess() {

	}

	@Override
	public void insertDataFailure() {

	}

	@Override
	public void deleteDataSuccess() {

	}

	@Override
	public void deleteDataFailure() {

	}

	@Override
	public void initListener() {

	}

	@Override
	public void onNotificationCome(PushBean pushBean) {
		Log.e("pushshen", "notification come....");
		if (pushBean != null) {
			int type = pushBean.getTag();
			if (type == 100) {
				if (StateUtils.getState(MessageCenterActivity.this) == 1) {
					MyCustomDialog dialog = MyCustomDialog.getInstance(this,this);
					dialog.show();
				}else{
					tbl.setPushBean(pushBean);
					tbl.setVisibility(View.VISIBLE);
				}
			} else {
				loadDatas();
			}
		}
	}

	@Override
	public void onTitleBarClicked(TitleBarType type) {

	}

	@Override
	public void NetConnected() {
		tbl.setVisibility(View.GONE);
	}

	@Override
	public void NetDisConnected() {
		tbl.setType(TitleBarType.NETWORK_ERROR);
		tbl.setTitleMessage("网络连接异常,请去设置网络");
		tbl.setVisibility(View.VISIBLE);
	}

	

	

}
