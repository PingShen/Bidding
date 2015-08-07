package com.example.testactivity.viewmodel;

import java.util.List;

import com.example.testactivity.model.MyNewStoreModel;
import com.huangye.commonlib.vm.ListStorageViewModel;
import com.huangye.commonlib.vm.callback.StorageVMCallBack;

import android.content.Context;

public class MyViewModel extends ListStorageViewModel{

	public MyViewModel(Context context, StorageVMCallBack callback) {
		super(context, callback);
	}

//	@Override
//	public Object getDate() {
//		return model.getData("", "");
//	}
//	
	public Object getDateList() {
		return listModel.getData("", "");
	}

	public void insert(List<Object> list) {
		listModel.putData(list);
	}
	@Override
	public void initModel(Context context) {
		listModel = new MyNewStoreModel(context, this);
	}

	@Override
	public Object getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	

}
