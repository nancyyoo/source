package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.common.men_to_girl.myshutdown.ActivityAlarm;
import com.common.men_to_girl.myshutdown.R;

import java.util.ArrayList;

/**
 * Created by yooheeyoung on 2017. 10. 15..
 */

public class AlarmAdapter extends BaseAdapter {

    private Context mContext = null;
    private ArrayList<ActivityAlarm> mAlarmListData = new ArrayList<ActivityAlarm>();

    public AlarmAdapter(Context context){
        super();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mAlarmListData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_alarm_item, null);

            holder.mName = (TextView) convertView.findViewById(R.id.app_name);
            holder.mPackage = (TextView) convertView.findViewById(R.id.app_package);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


         return null;
    }

    private class ViewHolder {
        // Alarm name
        public TextView mName;

        // Alarm setting name
        public TextView mPackage;
    }
}
