package com.coffee.activity;

import com.coffee.coffeebean.R;
import com.coffee.data.PrefConstant;
import com.coffee.util.Logger;
import com.coffee.util.SharedPreferencesUtil;
import com.coffee.util.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityMember extends Activity {

	private static final String TAG = "ActivityMember";
	private static final String TESTNAME = "name_test";
	private static final String EDITSTATUS = "EDIT";
	
	private static ImageButton ibUserImage;
	boolean status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_db);
		TextView tvMemberName = (TextView) findViewById(R.id.tvmembername);

		try {
			Intent intent = getIntent();
			status = intent.getExtras().getBoolean(EDITSTATUS);
			if (status == true) {
				tvMemberName.setText(R.string.member_profile);
			}
		} catch (Exception e) {

		}
		ibUserImage = (ImageButton) findViewById(R.id.ib_callery);

		final EditText etUserId = (EditText) findViewById(R.id.et_id);
		final EditText etUserPassword = (EditText) findViewById(R.id.et_password);
		final EditText etUserPhone = (EditText) findViewById(R.id.et_phonenumber);
		final EditText etUserCafeName = (EditText) findViewById(R.id.et_cafename);
		final EditText etUserAddress = (EditText) findViewById(R.id.et_address);
		final EditText etUserIntroduce = (EditText) findViewById(R.id.et_introduce);

		Button btUserOk = (Button) findViewById(R.id.bt_ok);
		Button btUserCancel = (Button) findViewById(R.id.bt_cancel);

		ibUserImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ActivityCameraGallery.class);
				startActivity(intent);
			}
		});

		btUserOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String userId = etUserId.getText().toString();
				String userPassword = etUserPassword.getText().toString();
				String userPhone = etUserPhone.getText().toString();
				String userCafeName = etUserCafeName.getText().toString();
				String userAddress = etUserAddress.getText().toString();
				String userIntroduce = etUserIntroduce.getText().toString();
				compareMember(userId, userPassword, userPhone, userCafeName, userAddress, userIntroduce);

				SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_MEMBER,
						PrefConstant.KEY_USER_ID, userId);
				SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_MEMBER,
						PrefConstant.KEY_USER_PASSWORD, userPassword);
				SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_MEMBER,
						PrefConstant.KEY_USER_PHONE, userPhone);
				SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_MEMBER,
						PrefConstant.KEY_USER_CAFENAME, userCafeName);
				SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_MEMBER,
						PrefConstant.KEY_USER_ADDRESS, userAddress);
				SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_MEMBER,
						PrefConstant.KEY_USER_PROFILE, userIntroduce);
				if (compareMember(userId, userPassword, userPhone, userCafeName, userAddress, userIntroduce) == false) {
					return;
				}

				String profileEdit = SharedPreferencesUtil.getStringSharedPreference(getApplicationContext(),
						PrefConstant.NAME_LOGINOK, PrefConstant.KEY_PROFILE);
				try {

					if (profileEdit.equals(TESTNAME)) {
						Logger.d(TAG, "profileEdit.equals(testName) : " + profileEdit.equals(TESTNAME));
						ToastUtil.showMakeText(getApplicationContext(), R.string.member_profile_complete,
								Toast.LENGTH_SHORT);
					} else {
						ToastUtil.showMakeText(getApplicationContext(), R.string.member_complete, Toast.LENGTH_SHORT);
					}
				} catch (Exception e) {

				}
				Intent intent = new Intent(ActivityMember.this, AcitivitySetting.class);
				startActivity(intent);

			}
		});

		btUserCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (status == true) {
					finish();
					return;
				}
				Intent intent = new Intent(ActivityMember.this, AcitivitySetting.class);
				ToastUtil.showMakeText(getApplicationContext(), R.string.member_cancel, Toast.LENGTH_SHORT);
				startActivity(intent);

			}
		});

	}

	private boolean compareMember(String userId, String userPassword, String userPhone, String userCafeName,
			String userAddress, String userIntroduce) {
		if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(userPhone)
				|| TextUtils.isEmpty(userCafeName) || TextUtils.isEmpty(userAddress)
				|| TextUtils.isEmpty(userIntroduce)) {

			ToastUtil.showMakeText(getApplicationContext(), R.string.member_input_empty, Toast.LENGTH_SHORT);
			return false;
			// }else if (!userPassword.equals(userRePassword)) {
			// ToastUtil.showMakeText(getApplicationContext(), "비밀번호가 일치하지
			// 않습니다.", Toast.LENGTH_SHORT);
			// return false;
			// }else if(userPassword.equals(userRePassword)){
			// return true;
		}
		return true;
	}

	public static void setImageView(Bitmap photo) {
		ibUserImage.setImageBitmap(photo);
	}
}
