package Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import Fragment.AppInfoFragment;
import Fragment.HealthFragment;
import Fragment.ReserveFragment;

/**
 * Created by JeaHun on 2017-10-15.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    // 사용할 fragment 전역변수로 정의
    private AppInfoFragment appInfoFragment;
    private HealthFragment healthFragment;
    private ReserveFragment reserveFragment;
    private int tabCount;

    // 생성자 만들고
    public ViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                appInfoFragment = new AppInfoFragment();
                return appInfoFragment;
            case 1:
                reserveFragment = new ReserveFragment();
                return reserveFragment;
            case 2:
                healthFragment = new HealthFragment();
                return healthFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
