package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.men_to_girl.myshutdown.R;

/**
 * Created by yooheeyoung on 2017. 10. 14..
 */

public class ReserveFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) getActivity().getLayoutInflater().inflate(R.layout.fragment_reserve, null);

        return view;
    }
}
