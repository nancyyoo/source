package Data;

import java.util.ArrayList;

/**
 * Created by yooheeyoung on 2017. 10. 28..
 */

public class WordDataManager {

    //singleton 으로 만든 데이터형식
    // 생성자, 클래스를 객체로 만들어서 쓸거임
    private static ArrayList<WordDatas> wordDatases = new ArrayList<>();

    public void setWordDatases(ArrayList<WordDatas> arrayList){
        wordDatases = arrayList;
    }

    public ArrayList<WordDatas> getWordDate(){
        return wordDatases;
    }

    private static WordDataManager mInstance = null;

    public static WordDataManager getmInstance(){
        if(mInstance == null){
           // 가지고 있는 클래스를 객체로 사용할 수 있게끔 추상메소드 같은 개념
            synchronized (WordDataManager.class){
                if(mInstance == null){
                    mInstance = new WordDataManager();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }
}
