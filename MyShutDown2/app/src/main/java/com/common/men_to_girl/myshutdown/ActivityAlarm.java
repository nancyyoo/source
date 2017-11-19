package com.common.men_to_girl.myshutdown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Data.ReserveDataManager;
import Data.ReserveDatas;
import Utils.DialogUtil;
import Utils.Logger;

/**
 * Created by yooheeyoung on 2017. 10. 15..
 */


public class ActivityAlarm extends Activity {

    private String appName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Intent intent = getIntent();
        appName = intent.getExtras().getString("appname");

        setLayout();
    }

    private void setLayout(){
        TextView tvCancel = (TextView)findViewById(R.id.tv_cancel);
        TextView tvSave = (TextView)findViewById(R.id.tv_save);
        LinearLayout llName = (LinearLayout)findViewById(R.id.ll_name);

        llName.setOnClickListener(tvClickListener);
        tvCancel.setOnClickListener(tvClickListener);
        tvSave.setOnClickListener(tvClickListener);
    }

    private View.OnClickListener tvClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            Intent intent;

            switch (id){
                case R.id.tv_cancel:
                    finish();
                    break;
                case R.id.tv_save:
                    ArrayList<ReserveDatas> reserveDatases = new ArrayList<>();
                    try{


                    Logger.d("hy", "appname :"+appName);
                    ReserveDatas reserveDatas = null;
                    reserveDatas.setName(appName);
                    reserveDatases.add(reserveDatas);
                    }catch (Exception err){

                    }
                    ReserveDataManager.getmInstance().setReserveDatases(reserveDatases);

                    finish();

                    break;
                case R.id.ll_name:
                    DialogUtil.isfinishDialog(ActivityAlarm.this);
                    break;
                default:

            }
        }
    };
}
