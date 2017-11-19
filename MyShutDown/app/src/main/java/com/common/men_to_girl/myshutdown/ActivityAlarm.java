package com.common.men_to_girl.myshutdown;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yooheeyoung on 2017. 10. 15..
 */

public class ActivityAlarm extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Log.d("hy", "onCreate: 1234");
    }

}
