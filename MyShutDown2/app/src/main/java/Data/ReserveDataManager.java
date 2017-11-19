package Data;

import java.util.ArrayList;

/**
 * Created by yooheeyoung on 2017. 10. 28..
 */

public class ReserveDataManager {

    //singleton 으로 만든 데이터형식
    // 생성자, 클래스를 객체로 만들어서 쓸거임
    // 디비나 sharedpreference(os에 저장) 하기 애매할 때 싱글톤형식으로 혀재 내 앱이 종료되지 않은 이상
    // 데이터를 계속 갖고 있을 수 있게끔
    private static ArrayList<ReserveDatas> reserveDatases = new ArrayList<>();

    public void setReserveDatases(ArrayList<ReserveDatas> arrayList){
        reserveDatases = arrayList;
    }

    public ArrayList<ReserveDatas> getReserveDatases(){
        return reserveDatases;
    }

    private static ReserveDataManager mInstance = null;

    public static ReserveDataManager getmInstance(){
        if(mInstance == null){
           // 가지고 있는 클래스를 객체로 사용할 수 있게끔 추상메소드 같은 개념
            synchronized (ReserveDataManager.class){
                if(mInstance == null){
                    mInstance = new ReserveDataManager();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }
}
