package com.coffee.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ToastUtil {
	private static Toast sToast = null;
	private static final String TAG = "ToastUtil";

	public static void showMakeText(Context context, CharSequence text, int duration) {
		showMakeText(context, text, duration, Gravity.CENTER);
	}

	public static void showMakeTextBottom(Context context, CharSequence text, int duration) {
		showMakeText(context, text, duration, Gravity.BOTTOM, 0, 200);
	}

	public static void showMakeText(Context context, int resId, int duration) {
		showMakeText(context, context.getResources().getString(resId), duration);
	}

	public static void showMakeText(Context context, CharSequence text, int duration, int gravity) {
		showMakeText(context, text, duration, gravity, 0, 0);
	}

	public static void showMakeText(Context context, CharSequence text, int duration, int gravity, int xOffset,
			int yOffset) {
		try {
			if (sToast != null) {
				sToast.setText(text);
				sToast.setDuration(duration);
			} else {
				sToast = Toast.makeText(context.getApplicationContext(), text, duration);
			}
			sToast.setGravity(gravity, xOffset, yOffset);
			sToast.show();
		} catch (Exception e) {
			Logger.e(TAG, "showMakeText err.", e);
		}
	}

}