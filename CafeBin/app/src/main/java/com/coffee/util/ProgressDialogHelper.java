package com.coffee.util;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PixelFormat;

import com.coffee.coffeebean.R;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ProgressDialogHelper  {
	private static final String TAG = "ProgressDialogHelper";
	private static ProgressDialog progressDialog;
	private static ProgressDialog horizontalDialog;

	public static void showProgressPopup(Activity act, boolean createNew, int resId) {
		showProgressPopup(act, createNew, act.getString(resId));
	}
	
	public static void showProgressPopup(Activity act, boolean createNew, String msg) {
		if (progressDialog == null || createNew) {
			progressDialog = new ProgressDialog(act);
			progressDialog.setMessage(msg);
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminateDrawable(CommonUtil.getDrawable(act, R.drawable.progress_circular));
			progressDialog.getWindow().setFormat(PixelFormat.TRANSPARENT);
		}
		
		if (progressDialog != null && !progressDialog.isShowing()) {
			Logger.d(TAG, "progressDialog.show()");
			try {
				progressDialog.show();
			} catch (Exception e) {
				Logger.e(TAG, "showProgressPopup err.", e);
			}
		}
	}
	
	public static void closeProgressPopup() {
		if (progressDialog != null && progressDialog.isShowing()) {
			try {
				progressDialog.dismiss();
			}
			catch (Exception e) {
				Logger.e(TAG, "closeProgressPopup error. ", e);  // ignore
			}
		}
	}
	
	public static boolean isShowingProgress() {
		return progressDialog != null ? progressDialog.isShowing() : false;
	}
	
	
	public static void showHorizontalProgress(Activity act, boolean createNew, int totalCount, String msg) {
		if (horizontalDialog == null || createNew) {
			horizontalDialog = new ProgressDialog(act);
			horizontalDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			horizontalDialog.setMessage(msg);
			horizontalDialog.setCancelable(false);
		}
		horizontalDialog.setMax(totalCount);
		if (horizontalDialog != null && !horizontalDialog.isShowing()) {
			Logger.d(TAG, "horizontalDialog show()");
			horizontalDialog.show();
		}
	}
	
	public static void updateHorizontalProgress(int currentCount) {
		if (horizontalDialog != null) {
			horizontalDialog.setProgress(currentCount);
		}
	}
	
	public static void closeHorizontalProgress() {
		if (horizontalDialog != null && horizontalDialog.isShowing()) {
			horizontalDialog.dismiss();
		}
	}

}