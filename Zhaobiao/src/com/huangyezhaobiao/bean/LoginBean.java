package com.huangyezhaobiao.bean;

public class LoginBean {

	private long userId;//":"7348394343",
	private String companyName;//":"北京崛起装饰公司"
	private int hasValidated;//":1,//是否验证过手机，1：验证过，0：没验证
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getHasValidated() {
		return hasValidated;
	}
	public void setHasValidated(int hasValidated) {
		this.hasValidated = hasValidated;
	}
	
//	private static final String KEY_NAME = "name";
//	private static final String KEY_PWD = "password";
//	private static LoginBean user;
//	private SharedPreferences sp;
//	private LoginBean(){};
//	public static LoginBean getLogin(){
//		if(user == null){
//			synchronized (LoginBean.class) {
//				if(user == null){
//					user = new LoginBean();
//				}
//			}
//		}
//		return user;
//	} 
//	
//	
//	public void saveName(Context context,String name){
//		getSpModel(context);
//		sp.edit().putString(KEY_NAME, name).commit();
//	}
//	
//	
//	public void savePwd(Context context,String password){
//		//进行一个md5的加密
//		getSpModel(context);
//		sp.edit().putString(KEY_PWD, password).commit();
//	}
//	
//	/**
//	 * 是否登录
//	 * @return
//	 */
//	public boolean isLogin(Context context){
//		getSpModel(context);
//		return !TextUtils.isEmpty(sp.getString(KEY_PWD, ""));
//	}
//	
//	private void getSpModel(Context context) {
//		if(sp == null)
//			sp = context.getSharedPreferences("loginfo", 0);
//	}
//	
//	
//	/**
//	 * 取消登录
//	 */
//	public void clearLoginInfo(Context context){
//		getSpModel(context);
//		sp.edit().clear().commit();
//	}
}
