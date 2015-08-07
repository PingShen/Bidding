package com.huangyezhaobiao.bean;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huangyezhaobiao.R;
import com.huangyezhaobiao.activity.FetchDetailsActivity;
import com.huangyezhaobiao.fragment.QiangDanBaseFragment;
import com.huangyezhaobiao.holder.MessCenIACIndividualHolder;
import com.huangyezhaobiao.inter.Constans;
import com.huangyezhaobiao.inter.MDConstans;
import com.huangyezhaobiao.lib.QDBaseBean;
import com.huangyezhaobiao.lib.ZBBaseAdapter;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.MDUtils;
import com.huangyezhaobiao.view.ZhaoBiaoDialog;
import com.huangyezhaobiao.view.ZhaoBiaoDialog.onDialogClickListener;
/**
 * 我的订单中心页面的工商注册--个体的bean
 * @author shenzhixin
 *
 *
 *				"cateId":"4065",	
				"displayType":"2"					//【可取到】
				"orderId":"12312321",
				"title":"工商注册-个体",
				"time":"2015-05-15 18:49",
				"endTime":"2015年6月",				//标的的服务时间
				"location":"朝阳区北苑",
				"orderState":"0"					//同上
				"phone":"13564782223",
 */
public class MessCenIACIndividualBean extends QDBaseBean{
	private MessCenIACIndividualHolder holder;
	private int displayType;
	private String orderId;
	private String title;
	private String time;
	private String endTime;
	private String location;
	private String orderState;
	private String phone;
	private ZhaoBiaoDialog dialog;
	
	public MessCenIACIndividualHolder getHolder() {
		return holder;
	}

	public void setHolder(MessCenIACIndividualHolder holder) {
		this.holder = holder;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String getCateId() {
		return cateId;
	}

	@Override
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	@Override
	public int getLayoutId() {
		return R.layout.center_mine_indiviual;
	}

	@Override
	public void fillDatas() {
		holder.tv_adapter_time.setText(endTime);
		holder.tv_location.setText(location);
		holder.tv_project.setText(title);
		holder.tv_telephone.setText(phone);
		holder.tv_time.setText(time);
	
		if(TextUtils.equals(QiangDanBaseFragment.orderState,Constans.DONE_FRAGMENT)){
			holder.rl_maybe_not.setVisibility(View.VISIBLE);
			if(TextUtils.equals(orderState, Constans.DONE_FRAGMENT_FINISH)){
				holder.tv_message.setText("已结束:已服务");
			}else if(TextUtils.equals(orderState, Constans.DONE_FRAGMENT_CANCEL)){
				holder.tv_message.setText("已结束:未服务");
			}
		}else{
			holder.rl_maybe_not.setVisibility(View.GONE);
		}
	}

	@Override
	public View initView(View convertView, LayoutInflater inflater,
			ViewGroup parent, Context context,ZBBaseAdapter<QDBaseBean> adapter) {
		super.context = context;
		convertView = inflater.inflate(getLayoutId(), parent, false);
		initDialog(context);
		holder  = new MessCenIACIndividualHolder();
		holder.btn_alreadry_contact = (ImageView) convertView.findViewById(R.id.btn_alreadry_contact);
		holder.rl_maybe_not = (RelativeLayout) convertView.findViewById(R.id.rl_maybe_not);
		holder.tv_adapter_time = (TextView) convertView.findViewById(R.id.tv_adapter_time);
		holder.tv_location = (TextView) convertView.findViewById(R.id.tv_location);
		holder.tv_project = (TextView) convertView.findViewById(R.id.tv_project);
		holder.tv_telephone = (TextView) convertView.findViewById(R.id.tv_telephone);
		holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
		holder.tv_message = (TextView) convertView.findViewById(R.id.tv_message);
		FetchDetailsActivity.orderState = orderState;
		convertView.setTag(holder);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Map<String, String> map = new HashMap<String, String>();
				Log.e("ashenFetch", "orderid:"+orderId);
				map.put(Constans.ORDER_ID, orderId);
				ActivityUtils.goToActivityWithString(MessCenIACIndividualBean.this.context,FetchDetailsActivity.class,map);
				MDUtils.OrderListPageMD(QiangDanBaseFragment.orderState, cateId, orderId, MDConstans.ACTION_DETAILS);
			}
		});
		
		holder.btn_alreadry_contact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initDialog(MessCenIACIndividualBean.this.context);
				dialog.show();
			}
		});
		return convertView;
	}

	@Override
	public void converseView(View convertView,Context context) {
		holder = (MessCenIACIndividualHolder) convertView.getTag();
		if(this.context == null){
			this.context = context;
		}
	}

	@Override
	public int getDisplayType() {
		return displayType;
	}
	
	public void setDisplayType(int displayType) {
		this.displayType = displayType;
	}

	private void initDialog(Context context) {
		if(dialog == null){
			dialog = new ZhaoBiaoDialog(context, "提示", "确定要拨打电话?");
			dialog.setOnDialogClickListener(new onDialogClickListener() {
				
				@Override
				public void onDialogOkClick() {
					ActivityUtils.goToDialActivity(MessCenIACIndividualBean.this.context, phone);
					MDUtils.OrderListPageMD(QiangDanBaseFragment.orderState, cateId, orderId, MDConstans.ACTION_UP_TEL);
					dialog.dismiss();
				}
				@Override
				public void onDialogCancelClick() {
					dialog.dismiss();
				}
			});
		}
	}
}
