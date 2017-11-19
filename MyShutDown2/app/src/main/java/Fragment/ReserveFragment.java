package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.men_to_girl.myshutdown.R;

import java.util.ArrayList;

import Data.ReserveDataManager;
import Data.ReserveDatas;

/**
 * Created by yooheeyoung on 2017. 10. 14..
 */

public class ReserveFragment extends Fragment {

    private ArrayList<ReserveDatas> reserveDatases;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) getActivity().getLayoutInflater().inflate(R.layout.fragment_reserve, null);

        TextView tvTest = (TextView) view.findViewById(R.id.tv_test);
        reserveDatases = new ArrayList<>();
        reserveDatases = ReserveDataManager.getmInstance().getReserveDatases();
        // 알람에서 저장한 거 가져오기
        for (ReserveDatas Data : reserveDatases) {
            Log.d("hy", "onCreateView :"+Data );
            tvTest.setText(Data.getName());
        }
        return view;
    }
}
