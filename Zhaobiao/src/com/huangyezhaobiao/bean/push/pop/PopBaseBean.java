package com.huangyezhaobiao.bean.push.pop;

import com.huangyezhaobiao.bean.push.PushBean;

import android.content.Context;
import android.view.View;

/**
 * 需要弹窗的类别基类，需要完成和对应view的绑定
 * @author linyueyang
 *
 */
public abstract class PopBaseBean extends PushBean{

	public abstract View initView(Context context);

	public abstract int getCateId();
	
	
	
	public abstract String getVoice();
	
	public abstract String getFee();
	
}
