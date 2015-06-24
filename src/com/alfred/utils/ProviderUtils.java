/**
 * 
 */
package com.alfred.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alfred.R;

/**
 * 描述:
 * 
 * @author LGF
 * @version Ver1.0 2015年6月4日
 * @Update History Copyright (c) 中国石油化工股份有限公司
 */
public class ProviderUtils {

	public static SharedPreferences getPreferences(Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.game_file_name), Context.MODE_PRIVATE);
		return sharedPref;
	}

	public static int getInt(Context context, int id) {
		SharedPreferences sharedPref = getPreferences(context);
		int result = 0;
		result = sharedPref.getInt(context.getString(id), 0);
		return result;
	}

	public static String getString(Context context, int id) {
		SharedPreferences sharedPref = getPreferences(context);
		String result = "";
		result = sharedPref.getString(context.getString(id), "");
		return result;
	}

	public static boolean getBoolean(Context context, int id) {
		SharedPreferences sharedPref = getPreferences(context);
		boolean result = false;
		result = sharedPref.getBoolean(context.getString(id), true);
		return result;
	}

	public static void setInt(Context context, int id, int value) {
		SharedPreferences sharedPref = getPreferences(context);
		sharedPref.edit().putInt(context.getString(id), value).commit();
	}

	public static void setString(Context context, int id, String value) {
		SharedPreferences sharedPref = getPreferences(context);
		sharedPref.edit().putString(context.getString(id), value).commit();
	}

	public static void setBooelan(Context context, int id, boolean value) {
		SharedPreferences sharedPref = getPreferences(context);
		sharedPref.edit().putBoolean(context.getString(id), value).commit();
	}
}
