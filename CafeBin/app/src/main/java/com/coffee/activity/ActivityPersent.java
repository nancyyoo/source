package com.coffee.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.coffee.coffeebean.R;
import com.coffee.data.PrefConstant;
import com.coffee.util.Logger;
import com.coffee.util.SharedPreferencesUtil;

/**
 * Created by 유희영 on 2017-06-11.
 */

public class ActivityPersent extends Activity {

    private static final String TAG = "ActivityPersent";
    EditText etTest;
    EditText etName;

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
        setContentView(R.layout.activity_persent_set);

        etTest = (EditText) findViewById(R.id.et_test);
        etName = (EditText) findViewById(R.id.et_name);

        Button btnBack = (Button) findViewById(R.id.btn_prev);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnTest = (Button) findViewById(R.id.btn_test);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int percent = Integer.parseInt(etTest.getText().toString());
                String titleName = etName.getText().toString();

                Logger.e(TAG, "titleName :" + titleName);
                Logger.e(TAG, "persent :" + percent);

                if (titleName.equalsIgnoreCase(TOMNTOMS)) {
                    SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_TOMNTOMS, percent);
                } else if (titleName.equalsIgnoreCase(EDIYA)) {
                    Logger.e(TAG, "titleName : " + titleName);
                    SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_EDIYA, percent);
                } else if (titleName.equalsIgnoreCase(HAPUM)) {
                    SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_HAPUM, percent);
                } else if (titleName.equalsIgnoreCase(HCAFE)) {
                    SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_HCAFE, percent);
                } else if (titleName.equalsIgnoreCase(LAVIDA)) {
                    SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_LAVIDA, percent);
                } else if (titleName.equalsIgnoreCase(GAEUN)) {
                    SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_GAEUN, percent);
                } else if (titleName.equalsIgnoreCase(PANDOROTHY)) {
                    SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_PANDOROTHY, percent);
                } else if (titleName.equalsIgnoreCase(ESLOW)) {
                    SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_ESLOW, percent);
                } else if (titleName.equalsIgnoreCase(STARBUCKS)) {
                    SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_STARBUCKS, percent);
                } else if (titleName.equalsIgnoreCase(YOGERPRESSO)) {
                    SharedPreferencesUtil.putSharedPreference(getApplicationContext(), PrefConstant.NAME_FAVORE_STATUS, PrefConstant.KEY_YOGERPRESSO, percent);
                }

                Intent intent = new Intent(getApplicationContext(), ActivityCafeList.class);
                startActivity(intent);
            }
        });
    }
}
