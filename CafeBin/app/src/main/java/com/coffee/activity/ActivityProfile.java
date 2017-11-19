package com.coffee.activity;

import com.coffee.coffeebean.R;
import com.coffee.data.PrefConstant;
import com.coffee.util.SharedPreferencesUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityProfile extends Activity {

	private static final String TESTNAME = "name_test";
	private static final String EDIT = "EDIT";
	private static final String TAG = "ActivityProfile";

	private static ImageView ivUserImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		setLayout();

	}

	private void setLayout() {
		// TODO Auto-generated method stub
		ivUserImage = (ImageView) findViewById(R.id.iv_userimage);
		TextView tvUserId = (TextView) findViewById(R.id.tv_userid);
		TextView tvUserAddress = (TextView) findViewById(R.id.tv_address);
		TextView tvCafeName = (TextView) findViewById(R.id.tv_cafename);
		TextView tvCafePhoneNumber = (TextView) findViewById(R.id.tv_phonenumber);
		TextView tvUserProfile = (TextView) findViewById(R.id.tv_userprofile);

		Button btEdit = (Button) findViewById(R.id.bt_edit);
		Button btBack = (Button) findViewById(R.id.bt_back);

		String userId = SharedPreferencesUtil.getStringSharedPreference(getApplicationContext(),
				PrefConstant.NAME_MEMBER, PrefConstant.KEY_USER_ID);
		String userAddress = SharedPreferencesUtil.getStringSharedPreference(getApplicationContext(),
				PrefConstant.NAME_MEMBER, PrefConstant.KEY_USER_ADDRESS);
		String userCafeName = SharedPreferencesUtil.getStringSharedPreference(getApplicationContext(),
				PrefConstant.NAME_MEMBER, PrefConstant.KEY_USER_CAFENAME);
		String userPhoneNumber = SharedPreferencesUtil.getStringSharedPreference(getApplicationContext(),
				PrefConstant.NAME_MEMBER, PrefConstant.KEY_USER_PHONE);
		String userProfile = SharedPreferencesUtil.getStringSharedPreference(getApplicationContext(),
				PrefConstant.NAME_MEMBER, PrefConstant.KEY_USER_PROFILE);

		tvUserId.setText(userId);
		tvUserAddress.setText(userAddress);
		tvCafeName.setText(userCafeName);
		tvCafePhoneNumber.setText(userPhoneNumber);
		tvUserProfile.setText(userProfile);

		btEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_LOGINOK,
						PrefConstant.KEY_PROFILE, TESTNAME);
				Intent intent = new Intent(getApplicationContext(), ActivityMember.class);
				intent.putExtra(EDIT, true);
				startActivity(intent);

			}
		});

		btBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public static void setProfileImage(Bitmap photo) {
		ivUserImage.setImageBitmap(photo);
	}

}
