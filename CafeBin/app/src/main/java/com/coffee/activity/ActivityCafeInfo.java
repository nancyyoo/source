package com.coffee.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coffee.coffeebean.R;
import com.coffee.data.PrefConstant;
import com.coffee.util.CommonUtil;
import com.coffee.util.Logger;
import com.coffee.util.SharedPreferencesUtil;

import java.io.ByteArrayOutputStream;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityCafeInfo extends Activity {
	private static final String TAG = "ActivityCafeInfo";

	private Bitmap image;
	private String title;
	private String tag;
 	private TextView tvPercent;
	private double hardness;
	private double latitude;

	private String TOMNTOMS = "TOMNTOMS";
	private String EDIYA = "EDIYA";
	private String HAPUM = "HAPUM";
	private String HCAFE = "HCAFE";
	private String LAVIDA = "LAVIDA";
	private String GAEUN = "GAEUN";
	private String PANDOROTHY = "PANDOROTHY";
	private String ESLOW = "ESLOW";
	private String STARBUCKS = "STARBUCKS";
	private String YOGERPRESSO = "YOGERPRESSO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_cafe);

		Logger.d(TAG, "ActivityCafeInfo");
		LinearLayout llLike = (LinearLayout)findViewById(R.id.ll_like);
		
		Button btLocation = (Button) findViewById(R.id.bt_location);
		Button btBack = (Button) findViewById(R.id.btn_prev);
		Button btnReview = (Button) findViewById(R.id.btn_review);
		Button btnReserve = (Button) findViewById(R.id.btn_reserve);

		ImageView ivTest = (ImageView) findViewById(R.id.iv_cafe);

		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		TextView tvTag = (TextView) findViewById(R.id.tv_tag);
		TextView tvPhone = (TextView) findViewById(R.id.tv_phonenumber);
		TextView tvOpen = (TextView) findViewById(R.id.tv_opentime);
		TextView tvAddress = (TextView) findViewById(R.id.tv_address);
		tvPercent = (TextView)findViewById(R.id.tv_percent);

		try {
			// 이미지 가져오기
			Intent intent = getIntent();
			byte[] arr = intent.getByteArrayExtra("image");
			image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
			ivTest.setImageBitmap(image);

			title = intent.getStringExtra("title");
			Logger.e(TAG, "title : " + title);
				if(title.equalsIgnoreCase(TOMNTOMS)){
					int percent =	SharedPreferencesUtil.getIntSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_TOMNTOMS);
					tvPercent.setText(percent + "석");
				}else if(title.equalsIgnoreCase(EDIYA)){
					int percent = SharedPreferencesUtil.getIntSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_EDIYA);
					Logger.e(TAG, "percent : " + percent);
					tvPercent.setText(percent + "석");
				}else if(title.equalsIgnoreCase(HAPUM)){
					int percent = SharedPreferencesUtil.getIntSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_HAPUM);
					tvPercent.setText(percent+ "석");
				}else if(title.equalsIgnoreCase(HCAFE)){
					int percent = SharedPreferencesUtil.getIntSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_HCAFE);
					tvPercent.setText(percent+ "석");
				}else if(title.equalsIgnoreCase(LAVIDA)){
					int percent = SharedPreferencesUtil.getIntSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_LAVIDA);
					tvPercent.setText(percent+ "석");
				}else if(title.equalsIgnoreCase(GAEUN)){
					int percent = SharedPreferencesUtil.getIntSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_GAEUN);
					tvPercent.setText(percent+ "석");
				}else if(title.equalsIgnoreCase(PANDOROTHY)){
					int percent = SharedPreferencesUtil.getIntSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_PANDOROTHY);
					tvPercent.setText(percent+ "석");
				}else if(title.equalsIgnoreCase(ESLOW)){
					int percent = SharedPreferencesUtil.getIntSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_ESLOW);
					tvPercent.setText(percent+ "석");
				}else if(title.equalsIgnoreCase(STARBUCKS)){
					int percent = SharedPreferencesUtil.getIntSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_STARBUCKS);
					tvPercent.setText(percent+ "석");
				}else if(title.equalsIgnoreCase(YOGERPRESSO)){
					int percent = SharedPreferencesUtil.getIntSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_YOGERPRESSO);
					tvPercent.setText(percent+ "석");
			}

			tag = intent.getStringExtra("tag");
			String phone = intent.getStringExtra("phonenumber");
			String open = intent.getStringExtra("opentime");
			String address = intent.getStringExtra("address");

			latitude = intent.getExtras().getDouble("latitude");
			hardness = intent.getExtras().getDouble("hardness");
			Logger.d(TAG, "hardness : " + hardness);
			Logger.d(TAG, "lattitude : " + latitude);

			tvTitle.setText(title);
			tvTag.setText(tag);
			tvPhone.setText(phone);
			tvOpen.setText(open);
			tvAddress.setText(address);

		} catch (Exception e) {

		}

		llLike.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setLikeDialog();
				
			}
		});

		btnReview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), ActivityListView.class);
				startActivity(intent);
			}
		});

		btBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), ActivityLocation.class);
				intent.putExtra("title" , title);
				intent.putExtra("hardness", hardness);
				intent.putExtra("latitude", latitude );

				startActivity(intent);
			}
		});

		btnReserve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setReserveDialog();
			}
		});
	}

	private void setReserveDialog() {
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
	
	private void setLikeDialog() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		View view = getLayoutInflater().inflate(R.layout.dialog_like, null);
		dialog.setContentView(view);
		dialog.setCancelable(false);

		Button btAgree = (Button) view.findViewById(R.id.btn_agree);
		btAgree.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), ActivityFavorite.class);
				intent.putExtra("title", title);

				startActivity(intent);
				CommonUtil.dismissDialog(dialog);
			}
		});
		
		Button btCancel = (Button) view.findViewById(R.id.btn_cancel);
		btCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonUtil.dismissDialog(dialog);				
			}
		});
		CommonUtil.showDialog(dialog);
	}
}