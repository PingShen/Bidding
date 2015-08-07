package com.huangyezhaobiao.bean;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huangyezhaobiao.R;
import com.huangyezhaobiao.activity.FetchDetailsActivity;
import com.huangyezhaobiao.fragment.QiangDanBaseFragment;
import com.huangyezhaobiao.holder.MessCenIACInnerCashHolder;
import com.huangyezhaobiao.inter.Constans;
import com.huangyezhaobiao.inter.MDConstans;
import com.huangyezhaobiao.lib.QDBaseBean;
import com.huangyezhaobiao.lib.ZBBaseAdapter;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.MDUtils;
import com.huangyezhaobiao.view.ZhaoBiaoDialog;
import com.huangyezhaobiao.view.ZhaoBiaoDialog.onDialogClickListener;
/**
 * 内资外资的工商注册bean
 * @author 58
 *
 *			   "agencyLocation": "代理注册区域",
                "phone": null,
                "orderState": 1,
                "time": null,
                "title": "住宅装修-二手房",
                "location": "朝阳区北苑",
                "displayType": "3",
                "endTime": "2015年6月",
                "agencyTime": "3个月代理记账",
                "orderId": 3088047295737365000,
                "cateId": 4065
                
                	
                	"cateId":"4065",
				"orderId":"12312321",	
				"displayType":"3"					//【可取到】
				"title:"工商注册-内资",
				"time":"2015-05-15 18:49",
				"agencyTime":"3个月代理记账",       //【可取到】
				"agencyLocation":"代理注册区域",    //【可取到】
				"endTime":"2015年6月",
				"location":"朝阳区北苑",
				"orderState":"0"					//同上
				"phone":"13564782223",
 */
public class MessCenIACInnerCashBean extends QDBaseBean{
	private MessCenIACInnerCashHolder holder;
	private String orderId;
	private int displayType;
	private String title;
	private String time;
	private String agencyTime;
	private String agencyLocation;
	private String endTime;
	private String location;
	private String orderState;
	private String phone;
	private ZhaoBiaoDialog dialog;
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
		return R.layout.center_mine_inner_cash;
	}

	@Override
	public void fillDatas() {
		holder.tv_size.setText(agencyTime);
		holder.tv_project.setText(title);
		holder.tv_location.setText(location);
		holder.tv_adapter_time.setText(time);
		holder.tv_telephone.setText(phone);
		holder.tv_time.setText(endTime);
		holder.tv_cost.setText(agencyLocation);
		if(TextUtils.equals(QiangDanBaseFragment.orderState,Constans.DONE_FRAGMENT)){
			holder.rl_maybe_not.setVisibility(View.VISIBLE);
			if(TextUtils.equals(orderState,Constans.DONE_FRAGMENT_FINISH)){
				holder.tv_message.setText("已结束,已服务");
			}else if(TextUtils.equals(orderState, Constans.DONE_FRAGMENT_CANCEL)){
				holder.tv_message.setText("已结束,未服务");
			}
		}else{
			holder.rl_maybe_not.setVisibility(View.GONE);
		}
	}

	@Override
	public View initView(View convertView, LayoutInflater inflater,
			ViewGroup parent, Context context,ZBBaseAdapter<QDBaseBean> adapter) {
		super.context = context;
		initDialog(context);
		convertView = inflater.inflate(getLayoutId(), parent, false);
		holder                      = new MessCenIACInnerCashHolder();
		holder.btn_alreadry_contact = (ImageView) convertView.findViewById(R.id.btn_alreadry_contact);
		holder.tv_adapter_time      = (TextView) convertView.findViewById(R.id.tv_adapter_time);
		holder.tv_cost              = (TextView) convertView.findViewById(R.id.tv_cost);
		holder.tv_location          = (TextView) convertView.findViewById(R.id.tv_location);
		holder.tv_project           = (TextView) convertView.findViewById(R.id.tv_project);
		holder.tv_size              = (TextView) convertView.findViewById(R.id.tv_size);
		holder.tv_telephone         = (TextView) convertView.findViewById(R.id.tv_telephone);
		holder.tv_time              = (TextView) convertView.findViewById(R.id.tv_time);
		holder.tv_message           = (TextView) convertView.findViewById(R.id.tv_message);
		holder.rl_maybe_not         = (RelativeLayout) convertView.findViewById(R.id.rl_maybe_not);
		FetchDetailsActivity.orderState = orderState;
		convertView.setTag(holder);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Map<String, String> map = new HashMap<String, String>();
				Log.e("ashenFetch", "orderid:"+orderId);
				map.put(Constans.ORDER_ID, orderId);
				ActivityUtils.goToActivityWithString(MessCenIACInnerCashBean.this.context,FetchDetailsActivity.class,map);
				MDUtils.OrderListPageMD(QiangDanBaseFragment.orderState, cateId, orderId, MDConstans.ACTION_DETAILS);
			}
		});
		
		holder.btn_alreadry_contact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initDialog(MessCenIACInnerCashBean.this.context);
				dialog.show();
			}
		});
		return convertView;
	}

	@Override
	public void converseView(View convertView,Context context) {
		holder = (MessCenIACInnerCashHolder) convertView.getTag();
		if(this.context==null) this.context = context;
	}

	@Override
	public int getDisplayType() {
		return displayType;
	}

	
	
	public MessCenIACInnerCashHolder getHolder() {
		return holder;
	}

	public void setHolder(MessCenIACInnerCashHolder holder) {
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

	public String getAgencyTime() {
		return agencyTime;
	}

	public void setAgencyTime(String agencyTime) {
		this.agencyTime = agencyTime;
	}

	public String getAgencyLocation() {
		return agencyLocation;
	}

	public void setAgencyLocation(String agencyLocation) {
		this.agencyLocation = agencyLocation;
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

	public ZhaoBiaoDialog getDialog() {
		return dialog;
	}

	public void setDialog(ZhaoBiaoDialog dialog) {
		this.dialog = dialog;
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
					ActivityUtils.goToDialActivity(MessCenIACInnerCashBean.this.context, phone);
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
