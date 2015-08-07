package com.example.testactivity.model;

import com.example.testactivity.Student;
import com.huangye.commonlib.model.ListStorageBaseModel;
import com.huangye.commonlib.model.callback.StorageModelCallBack;

import android.content.Context;

public class MyNewStoreModel extends ListStorageBaseModel<Student>{
	
	public MyNewStoreModel(Context context, StorageModelCallBack callback) {
		super(context, callback);
	}
	
	

}
