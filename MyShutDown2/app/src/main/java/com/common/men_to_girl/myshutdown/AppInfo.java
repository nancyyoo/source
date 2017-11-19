package com.common.men_to_girl.myshutdown;

import java.text.Collator;
import java.util.Comparator;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

// info 는 액티비티가 아닌데 액티비틸 상속받아서 화면을 구성하게끔 도와주는 역할
public class AppInfo {
    public static interface AppFilter {
        public void init();
        public boolean filterApp(ApplicationInfo info);
    }

	public Drawable mIcon = null;

	public String mAppNaem = null;

    public String mAppPackge = null;
	

    public static final AppFilter THIRD_PARTY_FILTER = new AppFilter() {
        public void init() {
        }
        
        @Override
        public boolean filterApp(ApplicationInfo info) {
            if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                return true;
            } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                return true;
            }
            return false;
        }
    };

    public static final Comparator<AppInfo> ALPHA_COMPARATOR = new Comparator<AppInfo>() {
        private final Collator sCollator = Collator.getInstance();

        // 이름 기준으로 정렬
        @Override
        public int compare(AppInfo object1, AppInfo object2) {
            return sCollator.compare(object1.mAppNaem, object2.mAppNaem);
        }
    };
}