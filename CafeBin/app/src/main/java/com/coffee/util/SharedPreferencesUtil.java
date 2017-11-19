package com.coffee.util;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class SharedPreferencesUtil {
	private static final String TAG = "SharedPreferencesUtil";
	
	public static SharedPreferences getSharedPreferences(Context context, String name) {
		return context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	public static SharedPreferences.Editor getSharedPreferencesEditor(Context context, String name) {
		return getSharedPreferences(context, name).edit(); // xml 파일이 생성됨
	}

	/**
	 * SharedPreferences 데이터를 리스트로 저장하기 위해서 사용하는 메소드
	 * @param context
	 * @param name 
	 * @param key
	 * @param values 문자열 데이터 배열
	 */
	public static void pushSharedPreferenceArray(Context context, String name, String key, ArrayList<String> values) {
		SharedPreferences.Editor editor = getSharedPreferencesEditor(context, name);
		
		JSONArray jsonDate = new JSONArray();

		for (int i = 0; i < values.size(); i++) {
			jsonDate.put(values.get(i));
			Logger.d(TAG, "valuse.get(i) : " + values.get(i));
		}
		editor.putString(key, jsonDate.toString());

		editor.apply();
	}
	
	/**
	 * SharedPreferences 데이터를 가져오기 위해서 사용하는 메소드
	 * @param context
	 * @param name
	 * @param key
	 * @return
	 */
	public static ArrayList<String> getSharedPreferenceArray(Context context, String name, String key){
		SharedPreferences prefes = getSharedPreferences(context, name);
		
		String pushData = prefes.getString(key, null);
		ArrayList<String> dataList = new ArrayList<String>();
		
		if(pushData != null){
			try{
				JSONArray jsonDate = new JSONArray(pushData);
				
				for(int i = 0; i< jsonDate.length(); i++){
					Logger.w(TAG, "jsonData.length : " + jsonDate.length());
					
					String dataVuse = jsonDate.optString(i);
					Logger.d(TAG, "dataVuse : " + dataVuse);
					dataList.add(dataVuse);
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return dataList;
	}

	public static void putSharedPreference(Context context, String name, String key, String value) {
		getSharedPreferencesEditor(context, name).putString(key, value).commit();
	}

	public static void putSharedPreference(Context context, String name, String key, boolean value) {
		getSharedPreferencesEditor(context, name).putBoolean(key, value).commit();
	}

	public static void putSharedPreference(Context context, String name, String key, float value) {
		getSharedPreferencesEditor(context, name).putFloat(key, value).commit();
	}

	public static void putSharedPreference(Context context, String name, String key, int value) {
		getSharedPreferencesEditor(context, name).putInt(key, value).commit();
	}

	public static void putSharedPreference(Context context, String name, String key, long value) {
		getSharedPreferencesEditor(context, name).putLong(key, value).commit();
	}

	public static void removeSharedPreference(Context context, String name, String key) {
		getSharedPreferencesEditor(context, name).remove(key).commit();
	}

	public static void removeAllSharedPreference(Context context, String name) {
		getSharedPreferencesEditor(context, name).clear().commit();
	}

	public static Map<String, ?> getAllSharedPrefrence(Context context, String name) {
		return getSharedPreferences(context, name).getAll();
	}

	public static String getStringSharedPreference(Context context, String name, String key) {
		return getSharedPreferences(context, name).getString(key, null);
	}

	public static boolean getBooleanSharedPreference(Context context, String name, String key, boolean defaultValue) {
		return getSharedPreferences(context, name).getBoolean(key, defaultValue);
	}

	public static float getFloatSharedPreference(Context context, String name, String key) {
		return getSharedPreferences(context, name).getFloat(key, 0);
	}

	public static float getFloatSharedPreference(Context context, String name, String key, float defValue) {
		return getSharedPreferences(context, name).getFloat(key, defValue);
	}

	public static int getIntSharedPreference(Context context, String name, String key, int defValue) {
		return getSharedPreferences(context, name).getInt(key, defValue);
	}

	public static int getIntSharedPreference(Context context, String name, String key) {
		return getSharedPreferences(context, name).getInt(key, 0);
	}

	public static long getLongSharedPreference(Context context, String name, String key) {
		return getSharedPreferences(context, name).getLong(key, 0);
	}


	public static boolean isContainsData(Context context, String name, String key) {
		return getSharedPreferences(context, name).contains(key);
	}

}
