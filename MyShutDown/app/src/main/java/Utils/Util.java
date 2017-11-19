package Utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import Data.WordDatas;

/**
 * Created by yooheeyoung on 2017. 10. 28..
 */

public class Util {


    public static ArrayList<WordDatas> setJson(Context context, InputStream inputStream){
      StringBuffer sb = new StringBuffer();
        ArrayList<WordDatas> arrayList = new ArrayList<WordDatas>();
        // 문자를 string 할 떄는 많은 예외처리를 해야함 예외처리를 안하면 앱이 죽어버림
        try{
            JSONArray jsonArray = new JSONArray(readTxt(context, inputStream));

            for(int i=0; i<jsonArray.length(); i++){
                // i 에 대한 걸 한 줄 한 줄씩 가져옴
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String word = jsonObject.getString("word");
                String meaning = jsonObject.getString("meaning");

                WordDatas wordDatas = new WordDatas();
                wordDatas.setmWord(word);
                wordDatas.setmMeaning(meaning);
                arrayList.add(wordDatas);
            }

            return arrayList;
        }catch (JSONException err){
            err.printStackTrace();
        }
        return arrayList;
    }


    /**
     * 텍스트 파일 읽기 위한 메소드
     * @param context
     * @param inputStream
     * @return
     */
    private static String readTxt(Context context, InputStream inputStream) {
        String data = null;
        BufferedReader reader = null;

        StringBuffer buf = new StringBuffer();
        String str = null;
        InputStreamReader inc = null;

        try {
            inc = new InputStreamReader(inputStream, "UTF-8");
            reader = new BufferedReader(inc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            while ((str = reader.readLine()) != null) {
                buf.append(str + "\n");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return buf.toString();
    }
}
