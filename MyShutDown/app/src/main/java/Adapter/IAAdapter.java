package Adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.men_to_girl.myshutdown.AppInfo;
import com.common.men_to_girl.myshutdown.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yooheeyoung on 2017. 10. 14..
 */

public class IAAdapter extends BaseAdapter {
    private Context mContext = null;
    private PackageManager pm;
    private List<ApplicationInfo> mAppList = null;
    private ArrayList<AppInfo> mListData = new ArrayList<AppInfo>();

    private final int MENU_DOWNLOAD = 0;
    private final int MENU_ALL = 1;
    private int MENU_MODE = MENU_DOWNLOAD;

    public IAAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public int getCount() {
        return mListData.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_layout, null);

            holder.mIcon = (ImageView) convertView.findViewById(R.id.app_icon);
            holder.mName = (TextView) convertView.findViewById(R.id.app_name);
            holder.mPacakge = (TextView) convertView.findViewById(R.id.app_package);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AppInfo data = mListData.get(position);

        if (data.mIcon != null) {
            holder.mIcon.setImageDrawable(data.mIcon);
        }

        holder.mName.setText(data.mAppNaem);
        holder.mPacakge.setText(data.mAppPackge);

        return convertView;
    }

    public void rebuild() {
        if (mAppList == null) {
            Log.d("hy", "Is Empty Application List");

            pm = mContext.getPackageManager();

            mAppList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        }

        AppInfo.AppFilter filter;
        switch (MENU_MODE) {
            case MENU_DOWNLOAD:
                filter = AppInfo.THIRD_PARTY_FILTER;
                break;
            default:
                filter = null;
                break;
        }

        if (filter != null) {
            filter.init();
        }

        mListData.clear();

        AppInfo addInfo = null;
        ApplicationInfo info = null;
        for (ApplicationInfo app : mAppList) {
            info = app;

            if (filter == null || filter.filterApp(info)) {

                addInfo = new AppInfo();

                addInfo.mIcon = app.loadIcon(pm);

                addInfo.mAppNaem = app.loadLabel(pm).toString();

                addInfo.mAppPackge = app.packageName;
                mListData.add(addInfo);
            }
        }
        Collections.sort(mListData, AppInfo.ALPHA_COMPARATOR);
    }

    //리스트 구성할 때 속도면에서 빠르다
    private class ViewHolder {
        // App Icon
        public ImageView mIcon;
        // App Name
        public TextView mName;
        // App Package Name
        public TextView mPacakge;
    }

}

