package com.huangyezhaobiao.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huangye.commonlib.vm.callback.StorageVMCallBack;
import com.huangyezhaobiao.R;
import com.huangyezhaobiao.adapter.OtherAdapter;
import com.huangyezhaobiao.bean.push.PushBean;
import com.huangyezhaobiao.bean.push.PushToStorageBean;
import com.huangyezhaobiao.db.DataBaseManager.TABLE_OTHER;
import com.huangyezhaobiao.inter.Constans;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.UnreadUtils;
import com.huangyezhaobiao.view.ZhaoBiaoDialog;
import com.huangyezhaobiao.view.ZhaoBiaoDialog.onDialogClickListener;
import com.huangyezhaobiao.vm.DetailMessageListStorageVM;

/**
 * 其他三个读取数据库展示的列表页
 * 余额提醒，抢单提醒，倒计时提醒，系统通知的列表页
 * @author shenzhx
 *
 */
public class OtherDetailActivity extends QBBaseActivity implements OnClickListener,StorageVMCallBack, onDialogClickListener{
	private ListView lv_message_center;
	private OtherAdapter adapter;
	private Button btn_clean;
	public List<PushToStorageBean> beans = new ArrayList<PushToStorageBean>();
	private int type;
	private DetailMessageListStorageVM svm;
	private TextView tv_no_unread,tv_top_center;
	private ZhaoBiaoDialog dialog;
	private RelativeLayout rl_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_message);
		initView();
		dialog = new ZhaoBiaoDialog(this, "提示", "确定要清空全部消息吗?");
		dialog.setOnDialogClickListener(this);
		tbl.setVisibility(View.GONE);
		svm = new DetailMessageListStorageVM(this, this);
		type = getIntent().getIntExtra("type", -1);
		configTypeTitle(type);
		svm.setType(String.valueOf(type));
		svm.getDate();
	}

	private void configTypeTitle(int type) {
		String title = "";
		switch (type) {
		case TABLE_OTHER.KOUFEI:
			title = "抢单结果";
			break;
		case TABLE_OTHER.DAOJISHI:
			title = "倒计时";
			break;
		case  TABLE_OTHER.SYSNOTIF:
			title = "系统通知";
			break;

		default:
			break;
		}
		tv_top_center.setText(title);
	}

	public  void initView() {
		tbl          = getView(R.id.tbl);
		rl_back      = getView(R.id.rl_back);
		tv_top_center = getView(R.id.tv_top_center);
		tv_no_unread = getView(R.id.tv_no_unread);
		lv_message_center = getView(R.id.lv_message_center);
		adapter = new OtherAdapter(this);
		lv_message_center.setAdapter(adapter);
		btn_clean = getView(R.id.btn_clean);
		btn_clean.setOnClickListener(this);
		rl_back.setOnClickListener(this);
		lv_message_center.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				PushToStorageBean pushToStorageBean = beans.get(position);
				int state = pushToStorageBean.getStatus();
				String orderId = String.valueOf(pushToStorageBean.getOrderid());
				HashMap<String, String> map = new HashMap<String, String>();
				//跳转到不同的页面
				switch (type) {
				case TABLE_OTHER.DAOJISHI:
					map.put(Constans.ORDER_ID, "3088047297886421281");
					ActivityUtils.goToActivityWithString(OtherDetailActivity.this, FetchDetailsActivity.class, map);
					break;
				case TABLE_OTHER.KOUFEI:
					if(state == 1){
						map.put(Constans.ORDER_ID,"3088047297886421281");
						ActivityUtils.goToActivityWithString(OtherDetailActivity.this, FetchDetailsActivity.class, map);
					}
					break;
				case TABLE_OTHER.SYSNOTIF:
					break;

				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_clean:
			dialog.show();
			break;
		case R.id.rl_back:
			onBackPressed();
			break;
		}
	}


	@Override
	public void getDataSuccess(Object o) {
		beans = (List<PushToStorageBean>) o;
		Log.e("szxMessage", "beans:"+(beans==null));
		if(beans!=null){
			Collections.reverse(beans);
			Log.e("szxMessage", "size:"+beans.size());
			adapter.setDataSources(beans);
			if(beans.size()==0){
				lv_message_center.setVisibility(View.GONE);
				tv_no_unread.setVisibility(View.VISIBLE);
			}else{
				lv_message_center.setVisibility(View.VISIBLE);
				tv_no_unread.setVisibility(View.GONE);
			}
		}  else{
			lv_message_center.setVisibility(View.GONE);
			tv_no_unread.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void getDataFailure() {
		Log.e("szxMessage", "failure");
	}

	@Override
	public void insertDataSuccess() {
	}

	@Override
	public void insertDataFailure() {
		Toast.makeText(this, "插入数据出错,请稍后重试", 0).show();
	}

	@Override
	public void deleteDataSuccess() {
		Log.e("ashen", "deleteSuccess");
		svm.getDate();
	}
	
	@Override
	public void deleteDataFailure() {
		Toast.makeText(this, "清空数据出错,请稍后重试", 0).show();
	}

	@Override
	public void initListener() {
		
	}

	@Override
	public void onNotificationCome(PushBean pushBean) {
		if(null != pushBean){
			int push_type = pushBean.getTag();
			if(type == push_type){
				svm.getDate();
				switch (type) {
				case TABLE_OTHER.DAOJISHI:
					UnreadUtils.clearDaoJiShiResult(this);
					break;
				case TABLE_OTHER.KOUFEI:
					UnreadUtils.clearQDResult(this);
					break;
				case TABLE_OTHER.SYSNOTIF:
					UnreadUtils.clearSysNotiNum(this);
					break;
				default:
					break;
				}
			}else{
				super.onNotificationCome(pushBean);
			}
		}
	
	}

	@Override
	public void onDialogOkClick() {
		dialog.dismiss();
		svm.cleanAllDatas();
	}

	@Override
	public void onDialogCancelClick() {
		dialog.dismiss();
	}

	

}
