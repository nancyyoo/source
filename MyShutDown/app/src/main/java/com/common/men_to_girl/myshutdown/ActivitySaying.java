package com.common.men_to_girl.myshutdown;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yooheeyoung on 2017. 10. 28..
 */

public class ActivitySaying extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saying);

        setLayout();
    }

    private void setLayout(){
        ImageView ivBack = (ImageView)findViewById(R.id.iv_back);
        TextView tvSaying = (TextView)findViewById(R.id.tv_saying);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  finish();
            }
        });
    }


}
