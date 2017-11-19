package com.coffee.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.content.Context;
import android.util.Log;

import com.coffee.coffeebean.R;

/**
 * Created by 유희영 on 2017-06-11.
 */
public class Logger {
	
	public static final String PREF_LOG = "PREF_LOG";
	
	private static boolean isVerboseEnable = false;
	private static boolean isDebugEnable = false;
	private static boolean isInfoEnable = false;
	private static boolean isWarnEnable = false;
	private static boolean isErrorEnable = false;
	
	private static Context mContext;
	
	private Logger() {
	}
	
	public static void setVerboseEnabled(boolean isEnable) {
		isVerboseEnable = isEnable;
	}
	
	public static void setDebugEnable(boolean isEnable) {
		isDebugEnable = isEnable;
	}
	
	public static void setInfoEnable(boolean isEnable) {
		isInfoEnable = isEnable;
	}
	
	public static void setWarnEnable(boolean isEnable) {
		isWarnEnable = isEnable;
	}
	
	public static void setErrorEnable(boolean isEnable) {
		isErrorEnable = isEnable;
	}
	
	public static void setAllLogEnable(Context context, boolean isEnable) {
		setContext(context);
		isVerboseEnable = isEnable;
		isDebugEnable = isEnable;
		isInfoEnable = isEnable;
		isWarnEnable = isEnable;
		isErrorEnable = isEnable;
	}
	
	public static void setContext(Context context) {
		mContext = context;
	}
	
	private static void writeLog(String tag, String log, Throwable t) {
		String msg;
		String tString = "";
		if (t != null) {
			tString = getStacktraceToString(t);
			msg = String.format("TAG : %s, LOG : %s, ERR : %s", tag, log, tString);
		} else {
			msg = String.format("TAG : %s, LOG : %s", tag, log);
		}
		
		if (mContext.getResources().getBoolean(R.bool.crashlog_send)) {
			SharedPreferencesUtil.putSharedPreference(mContext, PREF_LOG, "" + System.currentTimeMillis(), msg);
		}
	}
	
	public static void v(String tag, String log) {
		if (isVerboseEnable) {
			Log.v(tag, log);
		}
		writeLog(tag, log, null);
	}
	
	public static void v(String tag, String log, Object... param) {
		if (isVerboseEnable) {
			Log.v(tag, String.format(log, param));
		}
		writeLog(tag, String.format(log, param), null);
	}
	
	public static void v(String tag, String log, Throwable t) {
		if (isVerboseEnable) {
			Log.v(tag, log, t);
		}
		writeLog(tag, log, t);
	}
	
	public static void d(String tag, String log) {
		if (isDebugEnable) {
			Log.d(tag, log);
		}
		writeLog(tag, log, null);
	}
	
	public static void d(String tag, String log, Object... param) {
		if (isDebugEnable) {
			Log.d(tag, String.format(log, param));
		}
		writeLog(tag, String.format(log, param), null);
	}
	
	public static void d(String tag, String log, Throwable t) {
		if (isDebugEnable) {
			Log.d(tag, log, t);
		}
		writeLog(tag, log, t);
	}
	
	public static void i(String tag, String log) {
		if (isInfoEnable) {
			Log.i(tag, log);
		}
		writeLog(tag, log, null);
	}
	
	public static void i(String tag, String log, Object... param) {
		if (isInfoEnable) {
			Log.i(tag, String.format(log, param));
		}
		writeLog(tag, String.format(log, param), null);
	}
	
	public static void i(String tag, String log, Throwable t) {
		if (isInfoEnable) {
			Log.i(tag, log, t);
		}
		writeLog(tag, log, t);
	}
	
	public static void w(String tag, String log) {
		if (isWarnEnable) {
			Log.w(tag, log);
		}
		writeLog(tag, log, null);
	}
	
	public static void w(String tag, String log, Object... param) {
		if (isWarnEnable) {
			Log.w(tag, String.format(log, param));
		}
		writeLog(tag, String.format(log, param), null);
	}
	
	public static void w(String tag, String log, Throwable t) {
		if (isWarnEnable) {
			Log.w(tag, log, t);
		}
		writeLog(tag, log, t);
	}
	
	public static void e(String tag, String log) {
		if (isErrorEnable) {
			Log.e(tag, log);
		}
		writeLog(tag, log, null);
	}
	
	public static void e(String tag, String log, Object... param) {
		if (isErrorEnable) {
			Log.e(tag, String.format(log, param));
		}
		writeLog(tag, String.format(log, param), null);
	}
	
	public static void e(String tag, String log, Throwable t) {
		if (isErrorEnable) {
			Log.e(tag, log, t);
		}
		writeLog(tag, log, t);
	}
	
	public static String getStacktraceToString(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
	
}
