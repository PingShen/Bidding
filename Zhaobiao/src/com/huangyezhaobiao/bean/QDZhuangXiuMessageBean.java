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
import com.huangyezhaobiao.holder.QDZhuangXiuMessageHolder;
import com.huangyezhaobiao.inter.Constans;
import com.huangyezhaobiao.inter.MDConstans;
import com.huangyezhaobiao.lib.QDBaseBean;
import com.huangyezhaobiao.lib.ZBBaseAdapter;
import com.huangyezhaobiao.utils.ActivityUtils;
import com.huangyezhaobiao.utils.MDUtils;
import com.huangyezhaobiao.view.ZhaoBiaoDialog;
import com.huangyezhaobiao.view.ZhaoBiaoDialog.onDialogClickListener;

/**
 * 抢单信息的模型类----装修
 * @author 58
 *				"cateId":"4063",					//区分标的的业务类型 装修和工商注册
				"displayType":"1"					//分为装修，工商注册内资/外资，工商注册个人/海外（【可取到】）
				"orderId":"12312321",
				"time":"2015-05-15 18:49",			//抢单时间
				"title":"住宅装修-二手房",
				"space":"56平米",
				"budget":"预算3-5万",
				"endTime":"2015年6月",				//标的的服务时间
				"location":"朝阳区北苑",
				"type":"半包" 						//【可取到】
				"phone":"13564782223",				//发标用户手机
				"orderState":"0"	
 */
public class QDZhuangXiuMessageBean extends QDBaseBean{
	private ZhaoBiaoDialog dialog;
	  private int displayType;
	  private String orderId;
	  private String time;
	  private String title;
	  private String space;
	  private String budget;
	  private String endTime;
	  private String location;
	  private String type;
	  private String phone;
	  private String orderState;
	  private QDZhuangXiuMessageHolder holder;
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public void setCateId(String cateId){
		this.cateId = cateId;
	}
	
	public String getCateId(){
		return cateId;
	}
	@Override
	public String toString() {
		return "QiangDanBean [budget=" + budget + ", orderState=" + orderState
				+ ", phone=" + phone + ", title=" + title + ", time=" + time
				+ ", location=" + location + ", type=" + type + ", endTime="
				+ endTime + ", space=" + space + ", orderId=" + orderId + "]";
	}
	@Override
	public int getLayoutId() {
		return R.layout.center_mine_decorate;
	}
	
	@Override
	public void fillDatas() {
		holder.tv_time_content.setText(this.getTime());
		holder.tv_cost.setText(this.getBudget());
		holder.tv_location.setText(this.getLocation());
		holder.tv_mode.setText(this.getType());
		holder.tv_project.setText(this.getTitle());
		holder.tv_telephone.setText(this.getPhone());
		holder.tv_time.setText(this.getEndTime());
		holder.tv_size.setText(this.getSpace());
		if(TextUtils.equals(QiangDanBaseFragment.orderState,Constans.DONE_FRAGMENT)){
			holder.rl_maybe_not.setVisibility(View.VISIBLE);
			Log.e("ashenasas", "lalala visible");
			if(TextUtils.equals(orderState,Constans.DONE_FRAGMENT_FINISH)){
				holder.tv_message.setText("已结束:您已成功完成此单");
			}else if(TextUtils.equals(orderState, Constans.DONE_FRAGMENT_CANCEL)){
				holder.tv_message.setText("已结束:请下次努力！");
			}
		}else{
			holder.rl_maybe_not.setVisibility(View.GONE);
			Log.e("ashenasas", "lalala gone");
		}
	}
	@Override
	public View initView(View convertView, LayoutInflater inflater,
			ViewGroup parent,Context context,ZBBaseAdapter<QDBaseBean> adapter) {
		if(this.context == null) this.context = context;
		if(this.adapter==null) this.adapter = adapter;
		initDialog(context);
		convertView = getLayoutView(inflater, parent);
		holder = new QDZhuangXiuMessageHolder();
		holder.btn_alreadry_contact = (ImageView) convertView.findViewById(R.id.btn_alreadry_contact);
		holder.rl_maybe_not         = (RelativeLayout) convertView.findViewById(R.id.rl_maybe_not);
		holder.tv_time_content      = (TextView) convertView.findViewById(R.id.tv_time_content);
		holder.tv_cost              = (TextView) convertView.findViewById(R.id.tv_cost);
		holder.tv_location          = (TextView) convertView.findViewById(R.id.tv_location);
		holder.tv_mode              = (TextView) convertView.findViewById(R.id.tv_mode);
		holder.tv_project           = (TextView) convertView.findViewById(R.id.tv_project);
		holder.tv_size              = (TextView) convertView.findViewById(R.id.tv_size);
		holder.tv_telephone         = (TextView) convertView.findViewById(R.id.tv_telephone);
		holder.tv_time              = (TextView) convertView.findViewById(R.id.tv_time);
		holder.tv_message           = (TextView) convertView.findViewById(R.id.tv_message);
		FetchDetailsActivity.orderState = orderState;
		convertView.setTag(holder);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Map<String, String> map = new HashMap<String, String>();
				Log.e("ashenFetch", "orderid:"+orderId);
				map.put(Constans.ORDER_ID, orderId);
				ActivityUtils.goToActivityWithString(QDZhuangXiuMessageBean.this.context,FetchDetailsActivity.class,map);
				MDUtils.OrderListPageMD(QiangDanBaseFragment.orderState, cateId, orderId, MDConstans.ACTION_DETAILS);
			}
		});
		
		holder.btn_alreadry_contact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initDialog(QDZhuangXiuMessageBean.this.context);
				dialog.show();
			}
		});
		return convertView;
		
	}
	private void initDialog(Context context) {
		if(dialog == null){
			dialog = new ZhaoBiaoDialog(context, "提示", "确定要拨打电话?");
			dialog.setOnDialogClickListener(new onDialogClickListener() {
				
				@Override
				public void onDialogOkClick() {
					ActivityUtils.goToDialActivity(QDZhuangXiuMessageBean.this.context, phone);
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
	@Override
	public void converseView(View convertView,Context context) {
		holder = (QDZhuangXiuMessageHolder) convertView.getTag();
		if(this.context == null) this.context = context;
	}
	@Override
	public int getDisplayType() {
		return displayType;
	}
	
	public void setDisplayType(int type){
		this.displayType = type;
	}
	 
}
