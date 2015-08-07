package com.huangyezhaobiao.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huangyezhaobiao.R;
import com.huangyezhaobiao.bean.push.PushToStorageBean;
import com.huangyezhaobiao.inter.Constans;

public class OtherAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater mInflater;
	private List<PushToStorageBean> beans = new ArrayList<PushToStorageBean>();
	public OtherAdapter(Context context){
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}
	public void setDataSources(List<PushToStorageBean> beans){
		this.beans = beans;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return beans.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.layout_other_item, parent,false);
			holder.tv_bg_title = (TextView) convertView.findViewById(R.id.tv_bg_title);
			holder.tv_time     = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_qd_result = (TextView) convertView.findViewById(R.id.tv_qd_result);
			holder.rl_more = (RelativeLayout) convertView.findViewById(R.id.rl_more);
			holder.tv_order_number = (TextView) convertView.findViewById(R.id.tv_order_number);
			holder.tv_order_numberss = (TextView) convertView.findViewById(R.id.tv_order_numberss);
			holder.tv_noouejd = (TextView) convertView.findViewById(R.id.tv_noouejd);
			holder.view   = convertView.findViewById(R.id.view);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		PushToStorageBean messageBean = beans.get(position);
		String fee = messageBean.getFee();
		Log.e("efefefefef", "fee:"+fee);
		int status = messageBean.getStatus();
		int tag = messageBean.getTag();
		Log.e("ashenhhhh", "tag:"+tag);
		switch (tag) {
		case Constans.QD_RESULT:
			if(status == 1){
				holder.tv_qd_result.setText("抢单成功");
				holder.view.setVisibility(View.VISIBLE);
				holder.rl_more.setVisibility(View.VISIBLE);
				holder.tv_noouejd.setVisibility(View.VISIBLE);
				holder.tv_noouejd.setText("订单编号"+messageBean.getOrderid());
				
			}else{
				holder.tv_qd_result.setText("抢单失败");
				holder.view.setVisibility(View.GONE);
				holder.tv_noouejd.setVisibility(View.GONE);
				holder.rl_more.setVisibility(View.GONE);
			}
			if(!TextUtils.isEmpty(fee)){
				holder.tv_order_numberss.setVisibility(View.VISIBLE);
				holder.tv_order_number.setVisibility(View.VISIBLE);
				holder.tv_order_number.setText("￥"+fee);
			}else{
				holder.tv_order_numberss.setVisibility(View.INVISIBLE);
				holder.tv_order_number.setVisibility(View.INVISIBLE);
			}
			Log.e("ashjhj","status:"+status);
			holder.tv_time.setText(messageBean.getTime());
			String mss = messageBean.getStr();
			if(mss.length()>31)
				holder.tv_bg_title.setText(mss.substring(0, 31));
			else {
				holder.tv_bg_title.setText(mss);
			}
			break;
		case Constans.QD_DAOJISHI://倒计时
			holder.tv_bg_title.setText("您有一笔抢单即将到期,请3小时内联系客户");
			holder.tv_noouejd.setText("订单编号"+messageBean.getOrderid());
			holder.view.setVisibility(View.VISIBLE);
			holder.rl_more.setVisibility(View.VISIBLE);
			holder.tv_noouejd.setVisibility(View.VISIBLE);
			holder.tv_order_numberss.setVisibility(View.GONE);
			holder.tv_order_number.setVisibility(View.GONE);
			holder.tv_qd_result.setVisibility(View.GONE);
			break;
		case Constans.SYS_NOTI:
			holder.tv_bg_title.setText(messageBean.getStr());
			holder.tv_noouejd.setText("订单编号"+messageBean.getOrderid());
			holder.view.setVisibility(View.GONE);
			holder.rl_more.setVisibility(View.GONE);
			holder.tv_noouejd.setVisibility(View.GONE);
			holder.tv_order_numberss.setVisibility(View.GONE);
			holder.tv_order_number.setVisibility(View.GONE);
			holder.tv_qd_result.setVisibility(View.GONE);
			break;

		default:
			break;
		}
		
		Log.e("asasasas","msg:"+messageBean.getStr());
		return convertView;
	}
	
	class ViewHolder{
		public TextView tv_time;
		public TextView tv_bg_title;
		public TextView tv_qd_result;
		public TextView tv_order_number;
		public TextView tv_order_numberss;
		public TextView tv_noouejd;
		public RelativeLayout rl_more;
		public View view;
		
	}
	
	

}
