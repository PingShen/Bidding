package com.example.testactivity;

import java.util.ArrayList;
import java.util.List;

import com.example.testactivity.viewmodel.MyViewModel;
import com.huangye.commonlib.activity.BaseActivity;
import com.huangye.commonlib.vm.ListStorageViewModel;
import com.huangye.commonlib.vm.callback.StorageVMCallBack;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements StorageVMCallBack {

	public ListStorageViewModel viewModel;
	
	
	public TextView name;
	public TextView age;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		
		Student s = new Student();
		s.setAge("15");
		s.setName("林乐洋");
		List list = new ArrayList();
		list.add(s);
		s.setId(1);
		list.add(s);
		viewModel = new MyViewModel(this,this);
		((MyViewModel) viewModel).insert(list);
	}

	@Override
	public void initView() {
		
		name = (TextView) this.findViewById(R.id.name);
		age = (TextView) this.findViewById(R.id.age);
		//name = getView(R.id.name);
		//age = getView(R.id.age);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getDataSuccess(Object o) {
		// TODO Auto-generated method stub
		Student student = (Student)o;
		
		name.setText(student.getName());
		age.setText(student.getAge());
//		if(HYBaseModel isinstance student){
//			
//		}
		
	}

	@Override
	public void getDataFailure() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertDataSuccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertDataFailure() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDataSuccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDataFailure() {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
