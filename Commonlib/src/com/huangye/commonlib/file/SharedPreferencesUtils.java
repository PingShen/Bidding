package com.huangye.commonlib.file;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {

	private static final String NAME = "push_sp";


	public static String ReadDefault(Context context, String key) {

		SharedPreferences preferences = context.getSharedPreferences("default", Context.MODE_PRIVATE);
		String value = preferences.getString(key, "0");
		return value;

	}

	public static void WriteDefault(Context context, String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences("default", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String Read(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		String value = preferences.getString(key, "0");
		return value;

	}
	
	public static int ReadInt(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		int value = preferences.getInt(key, 0);
		return value;

	}

	public static void Write(Context context, String name, String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static Map<String,?> ReadAll(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Map<String, ?> map = preferences.getAll();
		return map;

	}

	public static void WriteAll(Context context, String name, Map<String, String> map) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();

		Collection<String> c = map.values();
		Iterator<String> it = c.iterator();
		for (; it.hasNext();) {
			editor.putString((String) it.next(), map.get(it.next()));
		}
		editor.commit();
	}

	
	
}
