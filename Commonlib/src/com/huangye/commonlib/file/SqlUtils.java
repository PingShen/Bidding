package com.huangye.commonlib.file;

import java.util.List;

import com.huangye.commonlib.delegate.StorageCallBack;
import com.huangye.commonlib.utils.ConditionBean;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import android.content.Context;

public class SqlUtils {

	public static SqlUtils sqlUtils;
	public static DbUtils dbUtils;

	public static SqlUtils getInstance(Context context) {
		if (sqlUtils == null) {
			sqlUtils = new SqlUtils();
			dbUtils = DbUtils.create(context, "huangye");
		}

		return sqlUtils;
	}

	public void save(Object o, StorageCallBack callback) {
		try {
			dbUtils.save(o);
			callback.insertDataSuccess();
		} catch (DbException e) {
			callback.insertDataFailure();
			e.printStackTrace();
		}
	}

	public void saveList(List<Object> list, StorageCallBack callback) {
		try {
			dbUtils.saveAll(list);
			callback.insertDataSuccess();
		} catch (DbException e) {
			callback.insertDataFailure();
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public <T> T get(Class<T> clazz, String key, String value, StorageCallBack callback) {

		T bean = null;
		try {
			if (null != key && "" != key) {

				Selector select = Selector.from(clazz).where(key, "=", value);

				bean = (T) dbUtils.findFirst(select);

			} else {
				bean = (T) dbUtils.findFirst(clazz);
			}
			callback.getDataSuccess(bean);
		} catch (DbException e) {
			callback.getDataFailure();
			e.printStackTrace();
		}

		return bean;

	}

	public <T> List<T> getList(Class<T> clazz, String key, String value, StorageCallBack callback) {
		List<T> list = null;
		try {
			if (null != key && "" != key) {
				Selector select = Selector.from(clazz).where(key, "=", value);

				list = dbUtils.findAll(select);

			} else {
				list = dbUtils.findAll(clazz);
			}
			callback.getDataSuccess(list);
		} catch (DbException e) {
			callback.getDataFailure();
			e.printStackTrace();
		}

		return list;

	}

	public <T> List<T> getListByCondition(Class<T> clazz, List<ConditionBean> conditionList, StorageCallBack callback) {
		List<T> list = null;

		try {
			Selector select = Selector.from(clazz);

			for (int i = 0; i < conditionList.size(); i++) {
				ConditionBean condition = conditionList.get(i);
				WhereBuilder where = WhereBuilder.b(condition.getColumnName(), condition.getOp(), condition.getValue());

				if (i == 0) {
					select.where(where);
				} else {
					select.and(where);
				}

			}
			list = dbUtils.findAll(select);
			callback.getDataSuccess(list);
		} catch (DbException e) {
			callback.getDataFailure();
			e.printStackTrace();
		}

		return list;

	}

	// public <T> void selectBySQL(Class<T> clazz, String condition, String
	// orderby, int pageSize,
	// int pageNum) {
	//
	//
	//
	// try {
	// dbUtils.execNonQuery(sql);
	// } catch (DbException e) {
	// e.printStackTrace();
	// }
	// }

	public <T> List<T> getListPage(Class<T> clazz, String orderBy, int pageSize, int pageNum,
			StorageCallBack callback) {

		List<T> list = null;
		Selector select = Selector.from(clazz).orderBy(orderBy).limit(pageSize).offset(pageSize * pageNum);
		try {
			list = dbUtils.findAll(select);
			callback.getDataSuccess(list);
		} catch (DbException e) {
			callback.getDataFailure();
			e.printStackTrace();
		}

		return list;

	}

	public <T> void delete(Class<T> clazz, String key, String value, StorageCallBack callback) {
		try {
			dbUtils.delete(clazz, WhereBuilder.b(key, "==", value));
			callback.deleteDataSuccess();
		} catch (DbException e) {
			callback.deleteDataFailure();
			e.printStackTrace();
		}
	}
	
	public <T> void deleteByCondition(Class<T> clazz, List<ConditionBean> conditionList, StorageCallBack callback) {

		try {
			WhereBuilder where = null;
			
			for (int i = 0; i < conditionList.size(); i++) {
				ConditionBean condition = conditionList.get(i);
				
				if (i == 0) {
					where = WhereBuilder.b(condition.getColumnName(), condition.getOp(), condition.getValue());
				} else {
					where.and(condition.getColumnName(), condition.getOp(), condition.getValue());
				}

			}

			dbUtils.delete(clazz, where);
			callback.deleteDataSuccess();
		} catch (DbException e) {
			callback.deleteDataFailure();
			e.printStackTrace();
		}


	}
	
	

}
