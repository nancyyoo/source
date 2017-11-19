package com.coffee.util;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class CommonUtil {

	private static final String TAG = "CommonUtil";

	public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (context.getPackageName().equals(service.service.getPackageName())
					&& serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}

		return false;
	}

	@SuppressLint("NewApi")
	public static int getColorDrawableColor(ColorDrawable cd) {
		final int version = Build.VERSION.SDK_INT;
		if (version >= 11) {
			return cd.getColor();
		} else {
			Bitmap bitmap = Bitmap.createBitmap(1, 1, Config.ARGB_4444);
			Canvas canvas = new Canvas(bitmap);
			cd.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			cd.draw(canvas);

			int color = bitmap.getPixel(0, 0);
			bitmap.recycle();

			return color;
		}
	}

	@SuppressLint("NewApi")
	public static Drawable getDrawable(Context context, int id) {
		final int version = Build.VERSION.SDK_INT;
		if (version >= 21) {
			return context.getResources().getDrawable(id, context.getTheme());
		} else {
			return context.getResources().getDrawable(id);
		}
	}

	@SuppressLint("NewApi")
	public static void setBackground(View view, Drawable drawable) {
		final int version = Build.VERSION.SDK_INT;
		if (version >= 16) {
			view.setBackground(drawable);
		} else {
			view.setBackgroundDrawable(drawable);
		}
	}

	public static int getNumOfCores() {
		int coreCnt = 1;
		if (Build.VERSION.SDK_INT >= 17) {
			coreCnt = Runtime.getRuntime().availableProcessors();
		} else {
			// Use saurabh64's answer
			coreCnt = getNumCoresOldPhones();
		}

		return coreCnt;
	}

	/**
	 * Gets the number of cores available in this device, across all processors.
	 * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
	 * 
	 * @return The number of cores, or 1 if failed to get result
	 */
	private static int getNumCoresOldPhones() {
		// Private Class to display only CPU devices in the directory listing
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				// Check if filename is "cpu", followed by a single digit number
				if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
					return true;
				}
				return false;
			}
		}

		try {
			// Get directory containing CPU info
			File dir = new File("/sys/devices/system/cpu/");
			// Filter to only list the devices we care about
			File[] files = dir.listFiles(new CpuFilter());
			// Return the number of cores (virtual CPU devices)
			return files.length;
		} catch (Exception e) {
			// Default to return 1 core
			return 1;
		}
	}

	public static boolean chkAppInstalled(Context context, String packageName) {
		boolean appInstalled = false;
		try {
			context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			appInstalled = true;
		} catch (NameNotFoundException e) {
			appInstalled = false;
		}
		return appInstalled;
	}

	public static boolean isActivityRunning(Activity activity) {
		if (activity == null || activity.isFinishing()) {
			return false;
		}
		return true;
	}

	public static void dismissDialog(DialogInterface dialog) {
		if (dialog == null)
			return;

		try {
			if (dialog instanceof AlertDialog) {
				if (((AlertDialog) dialog).isShowing()) {
					((AlertDialog) dialog).dismiss();
				}
				return;
			}

			if (dialog instanceof ProgressDialog) {
				if (((ProgressDialog) dialog).isShowing()) {
					((ProgressDialog) dialog).dismiss();
				}
				return;
			}

			if (dialog instanceof Dialog) {
				if (((Dialog) dialog).isShowing()) {
					((Dialog) dialog).dismiss();
				}
				return;
			}
		} catch (Exception e) {
			// ignore
			Logger.e(TAG, "dismissDialog err.", e);
		}
	}

	public static void showDialog(DialogInterface dialog) {
		if (dialog == null)
			return;

		try {
			if (dialog instanceof AlertDialog) {
				if (!((AlertDialog) dialog).isShowing()) {
					((AlertDialog) dialog).show();
				}
				return;
			}

			if (dialog instanceof ProgressDialog) {
				if (!((ProgressDialog) dialog).isShowing()) {
					((ProgressDialog) dialog).show();
				}
				return;
			}

			if (dialog instanceof Dialog) {
				if (!((Dialog) dialog).isShowing()) {
					((Dialog) dialog).show();
				}
				return;
			}
		} catch (Exception e) {
			// ignore
			Logger.e(TAG, "showDialog err.", e);
		}

	}

	public static String getAppVersion(Context context) {
		String version = "";
		try {
			PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			version = i.versionName;
		} catch (Exception e) {
			Logger.e(TAG, "getAppVersion failed. ", e);
		}
		return version;
	}

	public static int getAppVersionCode(Context context) {
		try {
			PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pkgInfo.versionCode;
		} catch (NameNotFoundException e) {
			return -1;
		}
	}
}