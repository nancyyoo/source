package com.coffee.activity;

import com.coffee.coffeebean.R;
import com.coffee.data.PrefConstant;
import com.coffee.util.CommonUtil;
import com.coffee.util.SharedPreferencesUtil;
import com.coffee.util.ToastUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityLogin extends Activity {
	private static final String TAG = "ActivityLogin";

	private static final String LOGIN_OK = "LoginOk";

	EditText etUserId;
	EditText etUserPassword;

	String reUserId;
	String reUserPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Button btLogin = (Button) findViewById(R.id.bt_login);
		etUserId = (EditText) findViewById(R.id.userid);
		etUserPassword = (EditText) findViewById(R.id.userpassword);

		TextView tvSns = (TextView) findViewById(R.id.tv_sns);
		TextView tvSignUp = (TextView) findViewById(R.id.tv_sign_up);

		CheckBox cbCheck = (CheckBox) findViewById(R.id.cb_check);

		tvSns.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setDialog();
			}
		});

		cbCheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDialog();
			}
		});
		
		tvSignUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), ActivityMember.class);
				startActivity(intent);
			}
		});
		
		btLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				compareLogin();
				if (compareLogin() == true) {
					SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_LOGINOK,
							PrefConstant.KEY_LOGINOK, LOGIN_OK);
					ToastUtil.showMakeText(getApplicationContext(), R.string.login_user, Toast.LENGTH_SHORT);
					Intent intent = new Intent(getApplicationContext(), AcitivityMain.class);
					startActivity(intent);
				} else {
					ToastUtil.showMakeText(getApplicationContext(), R.string.login_fail, Toast.LENGTH_SHORT);
				}

			}
		});
	}

	private void setDialog() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		View view = getLayoutInflater().inflate(R.layout.dialog_service_prepare, null);
		dialog.setContentView(view);
		dialog.setCancelable(false);

		Button btAgree = (Button) view.findViewById(R.id.btn_agree);
		btAgree.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				CommonUtil.dismissDialog(dialog);
			}
		});
		CommonUtil.showDialog(dialog);
	}

	private boolean compareLogin() {
		String userId = etUserId.getText().toString();
		String userPassword = etUserPassword.getText().toString();

		// 저장된 아이디,비밀번호
		reUserId = SharedPreferencesUtil.getStringSharedPreference(getApplicationContext(), PrefConstant.NAME_MEMBER,
				PrefConstant.KEY_USER_ID);
		reUserPassword = SharedPreferencesUtil.getStringSharedPreference(getApplicationContext(),
				PrefConstant.NAME_MEMBER, PrefConstant.KEY_USER_PASSWORD);

		if (!userId.equals(reUserId) || !userPassword.equals(reUserPassword)) {
			return false;
		} else if (userId.equals(reUserId) && userPassword.equals(reUserPassword)) {
			return true;
		}

		return true;
	}
}
