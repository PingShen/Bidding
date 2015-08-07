package com.huangyezhaobiao.vm;

import android.content.Context;
import android.util.Log;

import com.huangye.commonlib.model.ListStorageBaseModel;
import com.huangye.commonlib.vm.ListStorageViewModel;
import com.huangye.commonlib.vm.callback.StorageVMCallBack;
import com.huangyezhaobiao.bean.MessageBean;
import com.huangyezhaobiao.model.DetailMessageListStorageModel;

/**
 * 具体的各个消息中心的item的列表页vm
 * @author shenzhixin
 *
 */
public class DetailMessageListStorageVM extends ListStorageViewModel{
	private String type;
	public DetailMessageListStorageVM(Context context,
			StorageVMCallBack callback) {
		super(context, callback);
	}

	@Override
	public void initModel(Context context) {
		listModel = new DetailMessageListStorageModel(context, this);
	}

	public void cleanAllDatas(){
		Log.e("ashen", "clean datas");
		listModel.delete("tag",type);
	}
	
	@Override
	public Object getDate() {
		Log.e("storage", "type:"+type);
		return 	listModel.getAll("tag", type);
	}

	public void setType(String type){
		this.type = type;
	}


	
	
}
