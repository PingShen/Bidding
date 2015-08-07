//package com.huangyezhaobiao.adapter;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.huangyezhaobiao.R;
//import com.huangyezhaobiao.activity.OrderDetailActivity;
//import com.huangyezhaobiao.holder.ViewHolder;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class MyAdapter extends BaseAdapter{
//	private LayoutInflater mInflater;
//	private List<Map<String, String>> mData;
//	
//	private List<Map<String,String>> list;
//	private Context myContext;
//	
////	public MyAdapter(Context context){
////		myContext = context;
////		this.mInflater = LayoutInflater.from(context);
////		//mData = getData();
////	}
//	
//	public MyAdapter(Context context, List<Map<String,String>> list) {
//		myContext = context;
//		this.mInflater = LayoutInflater.from(context);
//		this.list = list;
//	}
//
//	public void addList(List<Map<String,String>> list){
//		this.list.addAll(list);
//		notifyDataSetChanged();
//	}
//	
//	@Override
//	public int getCount() {
//		return list.size();
//	}
//
//	@Override
//	public Object getItem(int arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public long getItemId(int arg0) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		
//		ViewHolder holder = null;
//		if (convertView == null) {
//			
//			holder=new ViewHolder();  
//			
//			convertView = mInflater.inflate(R.layout.order_item_renovation, null);
//			holder.item = (LinearLayout)convertView.findViewById(R.id.item);
//			holder.title = (TextView)convertView.findViewById(R.id.grab_title);
//			holder.time = (TextView)convertView.findViewById(R.id.grab_time);
//			holder.text1 = (TextView)convertView.findViewById(R.id.grab_text1);
//			holder.text2 = (TextView)convertView.findViewById(R.id.grab_text2);
//			holder.text3 = (TextView)convertView.findViewById(R.id.grab_text3);
//			holder.text4 = (TextView)convertView.findViewById(R.id.grab_text4);
//			holder.text5 = (TextView)convertView.findViewById(R.id.grab_text5);
//			holder.knock = (ImageView)convertView.findViewById(R.id.grab_knock);
//			convertView.setTag(holder);
//
//		}else{
//			holder = (ViewHolder)convertView.getTag();
//		}
//
////		Log.i(""+position, "=============================");
////		Log.i(""+mData.get(position).get("img"), "=============================");
//		//holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
//		
//		holder.title.setText(list.get(position).get("title"));
//		holder.time.setText(list.get(position).get("time"));
//		holder.text1.setText(list.get(position).get("space"));
//		holder.text2.setText(list.get(position).get("budget"));
//		holder.text3.setText(list.get(position).get("type"));
//		holder.text4.setText(list.get(position).get("endTime"));
//		holder.text5.setText(list.get(position).get("location"));
//		
//		holder.item.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Map<String, Integer> maps = new HashMap<String, Integer>();
//				maps.put("mode", 2);
//				//ActivityUtils.goToActivityWithInteger(myContext, OrderDetailActivity.class,maps);
//				Intent intent=new Intent(myContext,OrderDetailActivity.class);  //方法1
//				intent.putExtra("mode", 2);
//				myContext.startActivity(intent);	
//			}
//		});
//		holder.knock.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
//		return convertView;
//	}
//	
//	
//	//【模拟电话事件】实际通过调去电话服务权限
//	public void showInfo(){
//		new AlertDialog.Builder(myContext)
//		.setTitle("我的listview")
//		.setMessage("介绍...")
//		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//			}
//		})
//		.show();
//		
//	}
//	
//	
//	//【模拟数据】自己测试的数据实际需要通过http请求获取数据
////	private List<Map<String, Object>> getData() {
////		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
////
////		Map<String, Object> map = new HashMap<String, Object>();
////		map.put("title", "刘德华");
////		map.put("info", "刘德华剪头很棒");
////		//map.put("img", R.drawable.i1);
////		list.add(map);
////
////		map = new HashMap<String, Object>();
////		map.put("title", "林青霞");
////		map.put("info", "林青霞剪头很棒");
////		//map.put("img", R.drawable.i2);
////		list.add(map);
////
////		map = new HashMap<String, Object>();
////		map.put("title", "张学友");
////		map.put("info", "学剪头很棒");
////		//map.put("img", R.drawable.i3);
////		list.add(map);
////		
////		return list;
////	}
//	
//	
//	
//	
//}