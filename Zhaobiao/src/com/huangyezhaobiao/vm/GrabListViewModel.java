package com.huangyezhaobiao.vm;

import com.huangye.commonlib.model.NetWorkModel;
import com.huangye.commonlib.vm.callback.ListNetWorkVMCallBack;
import com.huangyezhaobiao.bean.poplist.DomesticRegisterListBean;
import com.huangyezhaobiao.bean.poplist.PersonalRegisterListBean;
import com.huangyezhaobiao.bean.poplist.RenovationListBean;
import com.huangyezhaobiao.lib.QDBaseBean;
import com.huangyezhaobiao.lib.ZBBaseListViewModel;
import com.huangyezhaobiao.model.QiangDanListModel;

import android.content.Context;

public class GrabListViewModel extends ZBBaseListViewModel<QDBaseBean>{

	public GrabListViewModel(ListNetWorkVMCallBack callBack, Context context) {
		super(callBack, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initKey() {
		key = "displayType";
	}

	@Override
	protected void registerSourceDirs() {
		sourcesDir.put("1", RenovationListBean.class);
		sourcesDir.put("2", PersonalRegisterListBean.class);
		sourcesDir.put("3", DomesticRegisterListBean.class);
		//sourcesDir.put("1044", zhuche.class);
	}
	
	
	

	@Override
	protected NetWorkModel initListNetworkModel(Context context) {
		return new QiangDanListModel(this, context);
	}

	

}
