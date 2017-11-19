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
        TextView tvMeaning = (TextView)findViewById(R.id.tv_meaning);

        ArrayList<WordDatas> wordList = WordDataManager.getmInstance().getWordDate();
        String word = null;
        String meaning = null;

        // Collections -> '리스트'를 랜덤으로 돌리는 얘
        Collections.shuffle(wordList);
        for(WordDatas wordDatas : wordList){
            // 타입이 null이 아닐때
            if(!wordDatas.getmType().isEmpty()) {
                word = wordDatas.getmWord();
                // 랜덤 돌리기
                meaning = wordDatas.getmMeaning();
            }
        }
        tvWords.setText(word);
        tvMeaning.setText(meaning);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
