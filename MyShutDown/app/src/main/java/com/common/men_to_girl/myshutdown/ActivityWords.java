package com.common.men_to_girl.myshutdown;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import Data.WordDataManager;
import Data.WordDatas;

/**
 * Created by yooheeyoung on 2017. 10. 28..
 */

public class ActivityWords extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        setLayout();
    }

    private void setLayout(){
        ImageView ivBack = (ImageView)findViewById(R.id.iv_back);
        TextView tvWords = (TextView)findViewById(R.id.tv_words);
        ArrayList<WordDatas> wordList = WordDataManager.getmInstance().getWordDate();
        String word = null;
        String meaning = null;

        Collections.shuffle(wordList);
        for(WordDatas wordDatas : wordList){
            word = wordDatas.getmWord();
            meaning = wordDatas.getmMeaning();
        }
        tvWords.setText(word);
        
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
