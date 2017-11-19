package com.coffee.activity;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.coffee.coffeebean.R;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityCameraGallery extends Activity {

	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	
	private ImageView imgview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_camera_gallery);

		imgview = (ImageView) findViewById(R.id.imageView1);
		Button buttonCamera = (Button) findViewById(R.id.btn_take_camera);
		Button buttonGallery = (Button) findViewById(R.id.btn_select_gallery);

		buttonCamera.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// 카메라 호출
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());

				// 이미지 잘라내기 위한 크기
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 0);
				intent.putExtra("aspectY", 0);
				intent.putExtra("outputX", 200);
				intent.putExtra("outputY", 150);

				try {
					intent.putExtra("return-data", true);
					startActivityForResult(intent, PICK_FROM_CAMERA);
				} catch (ActivityNotFoundException e) {
					// Do nothing for now
				}
			}
		});

		buttonGallery.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent();
				// Gallery 호출
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				// 잘라내기 셋팅
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 0);
				intent.putExtra("aspectY", 0);
				intent.putExtra("outputX", 200);
				intent.putExtra("outputY", 150);
				try {
					intent.putExtra("return-data", true);
					startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
				} catch (ActivityNotFoundException e) {
					// Do nothing for now
				}
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		try{
			
		if (requestCode == PICK_FROM_CAMERA) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				imgview.setImageBitmap(photo);
				ActivityMember.setImageView(photo);
			    ActivityProfile.setProfileImage(photo);

			}
		}
		if (requestCode == PICK_FROM_GALLERY) {
			Bundle extras2 = data.getExtras();
			if (extras2 != null) {
				Bitmap photo = extras2.getParcelable("data");
				imgview.setImageBitmap(photo);
			    ActivityMember.setImageView(photo);
			    ActivityProfile.setProfileImage(photo);
			}
		}
		}catch(NullPointerException e){
			 finish();
		}
		 finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		finish();
	}
	

}
