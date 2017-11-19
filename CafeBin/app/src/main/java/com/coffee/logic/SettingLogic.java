package com.coffee.logic;

import java.util.List;

import com.coffee.data.PrefConstant;
import com.coffee.util.SharedPreferencesUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class SettingLogic {

	private static SettingLogic instance = null;

	private static final String PACKAGE_NAME = "badge_count_package_name";
	private static final String CLASS_NAME = "badge_count_class_name";
	private static final String BADGE_COUNT = "badge_count";
	private static final String BADGE_COUNT_UPDATE = "android.intent.action.BADGE_COUNT_UPDATE";

	public static SettingLogic getInstance() {
		if (instance == null) {
			instance = new SettingLogic();
		}

		return instance;
	}

	private SettingLogic() {
	}

	public static void setPushStatus(Context context, boolean status) {
		SharedPreferencesUtil.putSharedPreference(context, PrefConstant.NAME_MOBILE, PrefConstant.KEY_PUSH_STATUS,
				status);
	}

	public static boolean getPushStatus(Context context) {
		return SharedPreferencesUtil.getBooleanSharedPreference(context, PrefConstant.NAME_MOBILE,
				PrefConstant.KEY_PUSH_STATUS, false);
	}

	public static void setImageStaus(Context context, boolean status) {
		SharedPreferencesUtil.putSharedPreference(context, PrefConstant.NAME_IMAGE_STATUS, PrefConstant.KEY_IMAGE_KEY,
				status);
	}

	public static boolean getImageStatus(Context context) {
		return SharedPreferencesUtil.getBooleanSharedPreference(context, PrefConstant.NAME_IMAGE_STATUS,
				PrefConstant.KEY_IMAGE_KEY, false);
	}

	public static void updateIconBadgeCount(int badgeCount, Context context) {
		Intent intent = new Intent(BADGE_COUNT_UPDATE);

		intent.putExtra(PACKAGE_NAME, context.getPackageName());
		intent.putExtra(CLASS_NAME, getLauncherClassName(context));

		intent.putExtra(BADGE_COUNT, badgeCount);
		context.sendBroadcast(intent);
	}

	private static String getLauncherClassName(Context context) {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setPackage(context.getPackageName());
		List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, 0);
		if (resolveInfoList != null && resolveInfoList.size() > 0) {
			return resolveInfoList.get(0).activityInfo.name;
		}
		return resolveInfoList.get(0).activityInfo.name;
	}
}