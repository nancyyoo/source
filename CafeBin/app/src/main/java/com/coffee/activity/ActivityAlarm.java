package com.coffee.activity;

import com.coffee.coffeebean.R;
import com.coffee.logic.SettingLogic;
import com.coffee.util.Logger;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityAlarm extends Activity {

	private static final String TAG = "ActivityAlarm";

	NotificationManager Notifi_M;
	Notification Notifi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		setLayout();
	}

	private void setLayout() {

		final EditText etTitleText = (EditText) findViewById(R.id.et_titletext);
		final EditText etTitle = (EditText) findViewById(R.id.et_name);
		final EditText etMessage = (EditText) findViewById(R.id.et_message);

		Button btnPrev = (Button) findViewById(R.id.btn_prev);

		btnPrev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		Button btOk = (Button) findViewById(R.id.bt_ok);

		btOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// string 으로 형변환하기!
				final String titleText = etTitleText.getText().toString();
				final String title = etTitle.getText().toString();
				final String message = etMessage.getText().toString();

				Logger.d(TAG, "titleText1 :" + titleText);
				Logger.d(TAG, "title1 :" + title);
				Logger.d(TAG, "message1 :" + message);

				setNotification(title, message, titleText);
			}
		});
	}

	private void setNotification(String title, String message, String pushAlarm) {

		int badgeCount = 0;

		Notifi_M = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Intent intent = new Intent(getApplicationContext(), AcitivityMain.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT); // 인텐트(메시지)를 갖고만 있다가, 인텐트를 보낼 수 있게끔 담아놓은 객체같은것

		Notifi = new Notification.Builder(getApplicationContext()).setContentTitle(title).setContentText(message)
				.setSmallIcon(R.drawable.cafeebin).setTicker(pushAlarm).setContentIntent(pendingIntent).build();

		// 소리추가
		Notifi.defaults = Notification.DEFAULT_SOUND;

		// 알림 소리를 한번만 내도록
		Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;

		// 확인하면 자동으로 알림이 제거 되도록
		Notifi.flags = Notification.FLAG_AUTO_CANCEL;

		SettingLogic.updateIconBadgeCount(++badgeCount, getApplicationContext());
		Notifi_M.notify(777, Notifi);
	}
}
