package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.common.men_to_girl.myshutdown.ActivitySaying;
import com.common.men_to_girl.myshutdown.ActivityWords;
import com.common.men_to_girl.myshutdown.R;

/**
 * Created by yooheeyoung on 2017. 10. 14..
 */

public class HealthFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View)getActivity().getLayoutInflater().inflate(R.layout.fragment_health,null);

        ImageView ivSaying = (ImageView) view.findViewById(R.id.iv_saying);
        ImageView ivWords = (ImageView) view.findViewById(R.id.iv_words);
        ImageView ivExercise = (ImageView) view.findViewById(R.id.iv_exercise);

        ivSaying.setOnClickListener(clickAction);
        ivWords.setOnClickListener(clickAction);
        ivExercise.setOnClickListener(clickAction);

        return view;
    }

    private View.OnClickListener clickAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            Intent intent;

            switch (id){
                case R.id.iv_saying:
                    intent = new Intent(getActivity(), ActivitySaying.class);
                    startActivity(intent);
                    break;
                case R.id.iv_words:
                    intent = new Intent(getActivity(), ActivityWords.class);
                    startActivity(intent);
                    break;
                case R.id.iv_exercise:
                    break;
                default:
            }
        }
    };

}
