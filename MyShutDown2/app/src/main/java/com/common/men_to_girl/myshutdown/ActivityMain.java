package com.common.men_to_girl.myshutdown;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.InputStream;

import Adapter.ViewPagerAdapter;
import Data.WordDataManager;
import Utils.Logger;
import Utils.Util;

public class ActivityMain extends AppCompatActivity {

    // 애니메이션은 설정창을 위해 만든것
    private Animation translateLeftAnim;
    private Animation translateRightAnim;
    private RelativeLayout slidingPage;
    private ImageView imgClick;

    private boolean isPageOn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Logger클래스 사용
        Logger.setAllLogEnable(getApplicationContext(), getResources().getBoolean(R.bool.log_enable));

        InputStream inputStream = getResources().openRawResource(R.raw.words);
        WordDataManager.getmInstance().setWordDatases(Util.setJson(getApplicationContext(), inputStream));

        setLayout();

    }
    private void setLayout(){
        // tablayout 쓰려면 라이브러리 추가
        // 클릭했을 때 색상이 조금 변화주는 등의 기능
        // build.gradle 에 라이브러리 추가함
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // tablayout에 이름 추가
        tabLayout.addTab(tabLayout.newTab().setText("설치된 앱 리스트"));
        tabLayout.addTab(tabLayout.newTab().setText("알람 설정 리스트"));
        tabLayout.addTab(tabLayout.newTab().setText("명언 리스트"));

        slidingPage = (RelativeLayout) findViewById(R.id.slidingPage01);
        imgClick = (ImageView) findViewById(R.id.img_setting);

        imgClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPageOn){
                    slidingPage.startAnimation(translateLeftAnim);
                    slidingPage.setVisibility(View.VISIBLE);
                }else{
                    slidingPage.startAnimation(translateRightAnim);
                    slidingPage.setVisibility(View.GONE);
                }
            }
        });

        // 레이아웃이 어떤 식으로 나올지 정렬 (수평방향으로)
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        translateLeftAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right);

        SlidingPageAnumtionListener anumtionListener = new SlidingPageAnumtionListener();
        translateLeftAnim.setAnimationListener(anumtionListener);
        translateRightAnim.setAnimationListener(anumtionListener);

        // 뷰페이저가 밑부분을 양옆으로 슬라이드 할 수 있는 기능
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        // 뷰페이저의 changelistener 를 선언
        // 선언한 이유가 페이지를 선택했을 때 해당하는 페이지를 보여줄 때
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    /**
     * 해당 스크립탭을 사용하기 위해서 사용하는 클래스
     */
    private class SlidingPageAnumtionListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (isPageOn) {
                slidingPage.setVisibility(View.GONE);
                isPageOn = false;
            } else {
                slidingPage.setVisibility(View.VISIBLE);
                isPageOn = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}