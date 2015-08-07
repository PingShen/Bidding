package com.huangyezhaobiao.vm;

import java.util.Date;

import com.huangye.commonlib.model.NetWorkModel;
import com.huangye.commonlib.model.NetWorkModel.TAG;
import com.huangye.commonlib.utils.NetBean;
import com.huangye.commonlib.vm.SourceViewModel;
import com.huangye.commonlib.vm.callback.NetWorkVMCallBack;
import com.huangyezhaobiao.model.ValidateModel;
import com.huangyezhaobiao.url.URLConstans;

import android.content.Context;
import android.text.TextUtils;

public class ValidateViewModel extends SourceViewModel {

	public ValidateViewModel(NetWorkVMCallBack callBack, Context context) {
		super(callBack, context);
	}

	public void getCode(String userId, String mobile) {
		// //t.setRequestMethodPost();
		// HashMap<String, String> params_map = new HashMap<String, String>();
		// //请求API: http://serverdomain/api/sendCode?mobile=&token=&userId=
		// params_map.put("mobile",mobile);
		// params_map.put("userId", userId);
		// params_map.put("token",new Date().getTime()+"");
		// t.configParams(params_map);
		t.type = TAG.REFRESH;

		t.setRequestURL(URLConstans.BASE_URL+"api/sendCode?mobile=" + mobile + "&userId=" + userId + "&token="
				+ new Date().getTime());
		t.getDatas();

	}

	public void validate(String userId, String mobile, String code) {
		// t.setRequestMethodPost();
		// HashMap<String, String> params_map = new HashMap<String, String>();
		// //请求API:
		// //http://serverdomain/api/validate?userId=&mobile=&code=&token=
		// params_map.put("mobile",mobile);
		// params_map.put("userId", userId);
		// params_map.put("code",code);
		// params_map.put("token",new Date().getTime()+"");
		// t.configParams(params_map);
		t.type = TAG.LOGIN;
		t.setRequestURL(URLConstans.BASE_URL+"api/validate?mobile=" + mobile + "&userId=" + userId + "&code=" + code
				+ "&token=" + new Date().getTime());
		t.getDatas();
	}

	@Override
	protected NetWorkModel initListNetworkModel(Context context) {

		return new ValidateModel(this, context);
	}

	@Override
	public void onLoadingSuccess(NetBean bean, NetWorkModel model) {

		int status = bean.getStatus();
		if (status == 0) {

			if (model.type == TAG.REFRESH) {
				callBack.onLoadingSuccess(jsonTransferToMap(bean).get("status"));
			} else {
				callBack.onLoadingSuccess(jsonTransferToMap(bean));
			}

		} else {
			String msg = bean.getMsg();
			if (!TextUtils.isEmpty(msg)) {
				callBack.onLoadingError(bean.getMsg());
			} else {
				callBack.onLoadingError("连接失败!");
			}
		}
	}

}
