package com.coffee.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.coffee.coffeebean.R;
import com.coffee.data.PrefConstant;
import com.coffee.util.Logger;
import com.coffee.util.SharedPreferencesUtil;

import java.util.ArrayList;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityFavorite extends Activity {

    private static final String TAG = "ActivityFavorite";

    private String title;

    private ListView mLvList;
    private ArrayAdapter<String> favoriteListItems;
    private ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // 리스트뷰 참조, Adapter 달기
        mLvList = (ListView) findViewById(R.id.list);
        try{

            mData = SharedPreferencesUtil.getSharedPreferenceArray(getApplicationContext(), PrefConstant.NAME_CAFE, PrefConstant.KEY_CAFE);
            Intent intent = getIntent();
            title = intent.getStringExtra("title");
            if(TextUtils.isEmpty(title)){
                Logger.e(TAG, "1111");
                favoriteListItems = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData);
                mLvList.setAdapter(favoriteListItems);
            }else{
                Logger.e(TAG, "2222");

                mData.add(title);
                SharedPreferencesUtil.pushSharedPreferenceArray(getApplicationContext(), PrefConstant.NAME_CAFE, PrefConstant.KEY_CAFE, mData);

                favoriteListItems = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData);
                mLvList.setAdapter(favoriteListItems);
            }
        }catch (Exception e ){

        }
        Button btnDelet = (Button) findViewById(R.id.btn_delete);

        btnDelet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.clear();
                Toast.makeText(getApplicationContext(), "전체삭제 되었습니다. 재 접속후 확인 해주세요.", Toast.LENGTH_SHORT).show();
                SharedPreferencesUtil.pushSharedPreferenceArray(getApplicationContext(), PrefConstant.NAME_CAFE, PrefConstant.KEY_CAFE, mData);
            }
        });
        Button btnBack = (Button) findViewById(R.id.btn_prev);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 아이템 추가후 지속적으로 유지 하기 위해서 사용함
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

}