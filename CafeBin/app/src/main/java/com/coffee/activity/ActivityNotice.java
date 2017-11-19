package com.coffee.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.coffee.coffeebean.R;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityNotice extends Activity {

	private static final String TAG = "ActivityNotice";

	private WebView mWebView;
	private WebSettings mWebSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_notice);
		// 웹뷰 세팅
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.setWebViewClient(new WebViewClient());
		mWebSettings = mWebView.getSettings();
		mWebSettings.setJavaScriptEnabled(true);

		mWebView.loadUrl("https://heeyommi.modoo.at");

		// Button btOk = (Button) findViewById(R.id.bt_ok);
		// final EditText etPush = (EditText) findViewById(R.id.et_pushalarm);
		// final EditText etTitle = (EditText) findViewById(R.id.et_title);
		// final EditText etMessage = (EditText) findViewById(R.id.et_message);
		//
		// btOk.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// final String pushAlarm = etPush.getText().toString();
		// final String title = etTitle.getText().toString();
		// final String message = etMessage.getText().toString();
		//
		// if (SettingLogic.getPushStatus(getApplicationContext())) {
		// setNotification(title, message, pushAlarm);
		// }
		//
		// }
		// });

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
