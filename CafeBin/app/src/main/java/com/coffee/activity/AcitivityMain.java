package com.coffee.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.coffee.coffeebean.R;
import com.coffee.logic.SettingLogic;
import com.coffee.util.Logger;
import com.coffee.util.PermissionUtil;
import com.coffee.util.ToastUtil;

import java.util.List;

/**
 * Created by 유희영 on 2017-06-11.
 */
public class AcitivityMain extends Activity {

	private static final String TAG = "ActivityMain";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 댓글 달기 기능
		// Intent intent = new Intent(getApplicationContext(),
		// ActivityListView.class);
		// startActivity(intent);

//		Intent intent = new Intent(getApplicationContext(), ActivityLocation.class);
//		startActivity(intent);
		Logger.setAllLogEnable(getApplicationContext(), getResources().getBoolean(R.bool.log_enable));

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			Logger.d(TAG, "permission check..");
//			serPiermission();
		}
		setLayout();
	}

	private void setLayout() {
		Logger.d(TAG, "setLayout");
		ImageButton btnMyCafeLocation = (ImageButton) findViewById(R.id.btn_mycafe);
		ImageButton btnMyFavorite = (ImageButton) findViewById(R.id.btn_myfavorite);
		ImageButton btnNotice = (ImageButton) findViewById(R.id.btn_notice);
		ImageButton btnSetting = (ImageButton) findViewById(R.id.btn_setting);

		LinearLayout llHome = (LinearLayout) findViewById(R.id.linear_home);
		LinearLayout llBack = (LinearLayout) findViewById(R.id.linear_back);
		LinearLayout llClose = (LinearLayout) findViewById(R.id.linear_close);
		LinearLayout llSetting = (LinearLayout) findViewById(R.id.linear_setting);

		btnMyCafeLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), ActivityCafeList.class);
				startActivity(intent);
			}
		});

		btnMyFavorite.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), ActivityFavorite.class); // ~~.this
				startActivity(intent);

			}
		});

		btnNotice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), ActivityNotice.class);
				startActivity(intent);
			}
		});

		btnSetting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), AcitivitySetting.class);
				startActivity(intent);
			}
		});

		llHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		llBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		llClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				setAlertDialog();

			}
		});

		llSetting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), AcitivitySetting.class);
				startActivity(intent);

			}
		});
	}

	private void setAlertDialog() {

		AlertDialog.Builder alert_confirm = new AlertDialog.Builder(AcitivityMain.this);
		alert_confirm.setMessage("카빈 앱을 종료하시겠습니까?").setCancelable(false)
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				}).setNegativeButton("취소", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						return;
					}
				});
		AlertDialog alert = alert_confirm.create();
		alert.show();
	}

	private void serPiermission() {
		Logger.d(TAG, "serPiermission");

		if (!PermissionUtil.isUpperMOS()) {
			return;
		}

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		List<String> perList;
		perList = PermissionUtil.getDefaultPermissionList(getApplicationContext());
		if (perList.contains(ActivityPermission.WINDOW_PERMISSION)) {
			perList.remove(ActivityPermission.WINDOW_PERMISSION);
			intent.putExtra(ActivityPermission.WINDOW_PERMISSION, true);
		}

		intent.putExtra(ActivityPermission.ALL_PERMISSION, perList.toArray(new String[perList.size()]));
		intent.setClass(getApplicationContext(), ActivityPermission.class);

		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SettingLogic.updateIconBadgeCount(0, getApplicationContext());
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		ToastUtil.showMakeText(getApplicationContext(), getString(R.string.activity_finish), Toast.LENGTH_LONG);
		finish();
	}
}
