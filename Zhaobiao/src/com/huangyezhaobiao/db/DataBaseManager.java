package com.huangyezhaobiao.db;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.huangyezhaobiao.bean.FirstBean;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner.Result;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
/**
 * 数据库管理类
 * 
 * 最好把数据模型封装成javaBean，这样操作在调用时就会好很多
 * 	分页加载
 * 	异步
 * @author shenzhixin
 */
public class DataBaseManager {
	/**
	 * 余额表的一些属性值
	 * @author shenzhixin
	 *
	 */
	public interface TABLE_YUE{
		String TABLE_NAME = "yue";
		String KEY_ID = "id";
		String KEY_TIME = "time";
		String KEY_TITLE = "title";
		String KEY_CONTENT = "content";
	}
	
	/**
	 * 其他三个表的一些属性
	 * @author shenzhixin
	 *
	 */
	public interface TABLE_OTHER{
		String TABLE_NAME = "other";
		String KEY_ID = "id";
		String KEY_TYPE = "type";
		String KEY_TIME = "time";
		String KEY_CONTENT = "content";
		String KOUFEI_TYPE = "1";//type为余额
		String DAOJISHI_TYPE = "2";//type为倒计时
		String SYSNOTIF_TYPE = "3";//type位系统通知
		
		
		int KOUFEI = 101;
		int DAOJISHI = 102;
		int SYSNOTIF = 103;
	}
}
