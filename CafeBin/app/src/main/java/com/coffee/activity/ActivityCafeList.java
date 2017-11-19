package com.coffee.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.coffee.adapter.ListViewItem;
import com.coffee.adapter.CaffeListAdapter;
import com.coffee.coffeebean.R;
import com.coffee.util.Logger;

import java.io.ByteArrayOutputStream;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityCafeList extends Activity {

	private static final String TAG = "ActivityCafeList";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cafe_list);

		ListView listview;
		final CaffeListAdapter adapter;

		Button btPrev;

		btPrev = (Button) findViewById(R.id.btn_prev);

		btPrev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// Adapter 생성
		adapter = new CaffeListAdapter();

		// 리스트뷰 참조 및 Adapter달기
		listview = (ListView) findViewById(R.id.listview1);
		listview.setAdapter(adapter);

		// 첫 번째 아이템 추가.Drawable icon, String title, String desc, String phone,
		// String open, String address
		adapter.addItem(getApplicationContext().getResources().getDrawable(R.drawable.test_tomandtoms), "TOMNTOMS",
				"#24시간#연중할인#시험기간#핫플#프레즐맛집", "02 - 973 - 5000", "24hours", "서울시 노원구 노원로 3", 37.625538, 127.088678);

		adapter.addItem(getApplicationContext().getResources().getDrawable(R.drawable.test_ediya), "EDIYA",
				"#24시간#연중할인#태릉입구", "02 - 970 - 9979", "08:00 ~ 02:00", "서울시 노원구 화랑로",37.618635, 127.077870);

		adapter.addItem(getApplicationContext().getResources().getDrawable(R.drawable.test_hapum), "HAPUM",
				"#연중할인#초코빙수#서울여대#남문", "02 - 978 - 2988", "09:00 ~ 23:00", "서울시 노원구 화랑로51길", 37.624966, 127.089020);

		adapter.addItem(getApplicationContext().getResources().getDrawable(R.drawable.test_hcafe), "HCAFE",
				"#연중할인#자몽빙수#꽃집#서울여대#남문", "02 - 972 - 2819", "09:00 ~ 23:00", "서울시 노원구 화랑로51길", 37.624228, 127.089464);

		adapter.addItem(getApplicationContext().getResources().getDrawable(R.drawable.test_lavida), "LAVIDA",
				"#연중할인#딸기요거트#서울여대#남문", "02 - 972 - 2819", "09:00 ~ 23:00", "서울시 노원구 화랑로51길", 37.624433, 127.089338);

		adapter.addItem(getApplicationContext().getResources().getDrawable(R.drawable.test_gaeun), "GAEUN",
				"#서울여대#누리관#카페", "010 - 2351 - 3450", "09:00 ~ 23:00", "서울시 노원구 화랑로 621", 37.628681, 127.090387);

		adapter.addItem(getApplicationContext().getResources().getDrawable(R.drawable.test_pandorthy), "PANDOROTHY",
				"#신장개업#와플#서울여대#대강당#카페", "02 - 971 - 3456", "09:00 ~ 23:00", "서울시 노원구 화랑로 621", 37.627501, 127.090359);

		adapter.addItem(getApplicationContext().getResources().getDrawable(R.drawable.test_eslow), "ESLOW",
				"#24시간#빈티지#칵테일#화랑대#카페", "02 - 949 - 4542", "24hours", "서울시 노원구 노원로 3",37.622159, 127.086763);

		adapter.addItem(getApplicationContext().getResources().getDrawable(R.drawable.test_starbucks), "STARBUCKS",
				"#사이즈#업그레이드#서울여대#50주년기념관", "02 - 758 - 8647", "09:00 ~ 23:00", "서울시 노원구 화랑로", 37.626127, 127.093082);

		adapter.addItem(getApplicationContext().getResources().getDrawable(R.drawable.test_yogerpresso), "YOGERPRESSO",
				"#신장개업#요거트스무디#서울여대#남문", "02 - 972 - 5505", "09:00 ~ 23:00", "서울시 노원구 화랑로", 37.624722, 127.089377);

		// 위에서 생성한 listview에 클릭 이벤트 핸들러 정의.
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				Logger.d(TAG, "클릭!!!!");
				// get item
				ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);
				String titleStr = item.getTitle();
				String descStr = item.getDesc();
				String phoneStr = item.getPhoneNumber();
				String openStr = item.getOpenTime();
				String addressStr = item.getAddress();
				Drawable iconDrawable = item.getIcon();
				Double hardness = item.getHardness();
				Double latitude = item.getLatitude();

				Bitmap sendBitmap = drawableToBitmap(iconDrawable);

				Intent intent = new Intent(getApplication(), ActivityCafeInfo.class);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				sendBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();

				intent.putExtra("title", titleStr);
				intent.putExtra("tag", descStr);
				intent.putExtra("image", byteArray);
				intent.putExtra("phonenumber", phoneStr);
				intent.putExtra("opentime", openStr);
				intent.putExtra("address", addressStr);
				intent.putExtra("hardness", hardness);
				intent.putExtra("latitude", latitude);
				startActivity(intent);
			}
		});
	}

	// 이미지를 비트맵화 시키는 것
	public static Bitmap drawableToBitmap(Drawable drawable) {

		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
				Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}
}