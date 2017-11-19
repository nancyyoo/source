package com.coffee.activity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coffee.coffeebean.R;
import com.coffee.data.PrefConstant;
import com.coffee.logic.SettingLogic;
import com.coffee.util.CommonUtil;
import com.coffee.util.Logger;
import com.coffee.util.SharedPreferencesUtil;
import com.coffee.util.ToastUtil;

/**
 * Created by 유희영 on 2017-06-11.
 */
public class AcitivitySetting extends Activity {

	private final static String TAG = "AcitivitySetting";
	private static final String LOGIN_OK = "LoginOk";

	private boolean mIsMobileOn;

	private ImageView mIvMobile;

	String userLoginOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setLayout();
	}

	// 레이아웃 정의
	private void setLayout() {
		Logger.d(TAG, "setLayout()");
		String versionCode = CommonUtil.getAppVersion(getApplicationContext());

		RelativeLayout llEnroll = (RelativeLayout) findViewById(R.id.ll_member_db);
		RelativeLayout rlCall = (RelativeLayout) findViewById(R.id.rl_call);
		RelativeLayout rlLogOut = (RelativeLayout) findViewById(R.id.rl_logout);
		RelativeLayout rlAlarm = (RelativeLayout) findViewById(R.id.rl_alarm);
		RelativeLayout rlset = (RelativeLayout) findViewById(R.id.rl_seat);

		final LinearLayout llprofile = (LinearLayout) findViewById(R.id.ll_profile);
		LinearLayout llPush = (LinearLayout) findViewById(R.id.ll_push_status);

		TextView tvVersion = (TextView) findViewById(R.id.tv_version);
		TextView tvLoginOut = (TextView) findViewById(R.id.tv_login_out);

		Button btnBack = (Button) findViewById(R.id.btn_prev);

		mIvMobile = (ImageView) findViewById(R.id.iv_mobile);
		mIsMobileOn = SettingLogic.getPushStatus(getApplicationContext());

		llPush.setOnClickListener(btnListener);
		setImageStatus(mIvMobile, mIsMobileOn);

		tvVersion.setText(versionCode);
		// TODO 여기 조건 제거
		try {
			userLoginOk = SharedPreferencesUtil.getStringSharedPreference(getApplicationContext(),
					PrefConstant.NAME_LOGINOK, PrefConstant.KEY_LOGINOK);
			if (userLoginOk.equals(LOGIN_OK)) {
				tvLoginOut.setText(R.string.setting_login_out);
				llprofile.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {

		}

		rlLogOut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 여기 조건 추가
				if (userLoginOk != null) {
					SharedPreferencesUtil.removeSharedPreference(getApplicationContext(), PrefConstant.NAME_LOGINOK,
							PrefConstant.KEY_LOGINOK);
					ToastUtil.showMakeText(getApplicationContext(), R.string.setting_logout, Toast.LENGTH_LONG);

					Intent intent = new Intent(getApplicationContext(), AcitivityMain.class);
					startActivity(intent);
				} else {
					// if (userLoginOk != null) {
					// llprofile.setVisibility(View.GONE);
					// } else {
					// llprofile.setVisibility(View.VISIBLE);
					// }
					Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
					startActivity(intent);
				}
			}
		});

		rlCall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent sendIntent = new Intent(Intent.ACTION_CALL);
				sendIntent.setData(Uri.parse("tel:01023513450"));
				startActivity(sendIntent);

			}
		});

		rlAlarm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AcitivitySetting.this, ActivityAlarm.class);
				startActivity(intent);

			}
		});
		
		llEnroll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ActivityMember.class);
				startActivity(intent);

			}
		});

		llprofile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View ar) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ActivityProfile.class);

				startActivity(intent);

			}
		});

		btnBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		rlset.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ActivityPersent.class);
				startActivity(intent);
			}
		});
	}

	private OnClickListener btnListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.ll_push_status || id == R.id.iv_mobile) {
				mIsMobileOn = !mIsMobileOn;
				setImageStatus(mIvMobile, mIsMobileOn);

				SettingLogic.setPushStatus(getApplicationContext(), mIsMobileOn);

			}
		}
	};

	/**
	 * push 상태 이미지 체크 메소드
	 * 
	 * @param ivImg
	 * @param status
	 */
	private void setImageStatus(ImageView ivImg, boolean status) {
		if (status) {
			ivImg.setImageResource(R.drawable.ic_check_on);
		} else {
			ivImg.setImageResource(R.drawable.ic_check_off);
		}
	}

}