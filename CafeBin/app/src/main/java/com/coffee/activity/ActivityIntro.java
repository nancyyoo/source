package com.coffee.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.coffee.coffeebean.R;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityIntro extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(ActivityIntro.this, AcitivityMain.class);
				startActivity(intent);

				finish();
			}
		}, 1500);
	}
}
