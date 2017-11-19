package Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.common.men_to_girl.myshutdown.ActivityAlarm;
import com.common.men_to_girl.myshutdown.R;

import Adapter.IAAdapter;

public class AppInfoFragment extends Fragment {

    private final int MENU_DOWNLOAD = 0;
    private final int MENU_ALL = 1;
    private int MENU_MODE = MENU_DOWNLOAD;

    private View mLoadingContainer;
    private ListView mListView = null;
    private IAAdapter mAdapter = null;

    // fragment 는 activity 의 자식개념으로 들어가는것 레이아웃 재정의 의미
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) getActivity().getLayoutInflater().inflate(R.layout.activity_app_info, null);

        mLoadingContainer = view.findViewById(R.id.loading_container);
        mListView = (ListView) view.findViewById(R.id.listView1);

        mAdapter = new IAAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                String app_name = ((TextView) view.findViewById(R.id.app_name)).getText().toString();
                String package_name = ((TextView) view.findViewById(R.id.app_package)).getText().toString();
                Intent intent = new Intent(getActivity(), ActivityAlarm.class);
                intent.putExtra("appname",app_name);
                startActivity(intent);
                //Toast.makeText(getActivity(), package_name, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startTask();
    }

    private void startTask() {
        new AppTask().execute();
    }


    private void setLoadingView(boolean isView) {
        if (isView) {
            mLoadingContainer.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            mLoadingContainer.setVisibility(View.GONE);
        }
    }

    /**
     * List Adapter
     *
     * @author nohhs
     */

    private class AppTask extends AsyncTask<Void, Void, Void> {

        // 강제로 취소해야할 시점에 얘를 호출해서 종료를시켜줌
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        // 다이얼로그 바를 출력해줄 때 사용하는 것 다운로드할 때 많이씀
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        // 로딩바
        // 첫 스타트 지점 ( 다이얼로그 띄워주기도 하고.. 등)
        @Override
        protected void onPreExecute() {
            setLoadingView(true);
        }

        //data
        // back 에서 실행되는 것 (읽어오는처리 back에서)
        @Override
        protected Void doInBackground(Void... params) {
            mAdapter.rebuild();

            return null;
        }

        // 읽어 온 데이터를 다시 refresh 해주거나 다이얼로그 를 종료시켜주거나..
        @Override
        protected void onPostExecute(Void result) {
            mAdapter.notifyDataSetChanged();

            setLoadingView(false);
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
         menu.add(0, MENU_DOWNLOAD, 1, R.string.menu_download);
         menu.add(0, MENU_ALL, 2, R.string.menu_all);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (MENU_MODE == MENU_DOWNLOAD) {
            menu.findItem(MENU_DOWNLOAD).setVisible(false);
            menu.findItem(MENU_ALL).setVisible(true);
        } else {
            menu.findItem(MENU_DOWNLOAD).setVisible(true);
            menu.findItem(MENU_ALL).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        if (menuId == MENU_DOWNLOAD) {
            MENU_MODE = MENU_DOWNLOAD;
        } else {
            MENU_MODE = MENU_ALL;
        }

        startTask();

        return true;
    }
}