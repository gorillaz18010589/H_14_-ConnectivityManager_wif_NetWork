package tw.org.iii.appps.h_14_networkinfo;
//1.按下按鈕查詢是否連線network
//2.查詢wifi是否連線中
//3.廣播接收並不是透過邏輯去處理BroadcastReceiver,附掛在誰身上,來監聽
//*因為是內部類別所以不用再檔案總管裡面,誰要接收什麼廣播,你要收什麼樣的廣播,系統的廣播會發出來,例如開機了嗎
//*因為每個app彼此之間是獨立的溝通可以透過BroadcastReceiver,廣播是全部都會收到,但針對特定的來收
//內部類別廣播接受器,用飛航測試網路是否開,來決定要做什麼事情

//getSystemService(@RecentlyNonNull String name)//從系統伺服器取得(Context.連線伺服器狀態),回傳到(ConnectivityManager)裡
//cmgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);//從系統伺服器取得(Context.連線伺服器狀態),回傳到(ConnectivityManager)裡
//getActiveNetworkInfo();//取得連線狀態資訊(回傳到NetworkInfo)
//      NetworkInfo networkInfo =  cmgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);//取得連線狀態(ConnectivityManager裡面的.要詢問的種類type,wifi等需要 )

//registerReceiver(BroadcastReceiver receiver, IntentFilter filter):(回傳直Intent)
//registerReceiver(myReciver,intentFilter);//廣播註冊(1.自己定義的廣播 2.要inteniteFiter的東西 );

//IntentFilter(String action)://建立一個intentFilter(連線狀態經理人裡面的.開始連線)
//IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);//建立一個intentFilter(連線狀態經理人裡面的.開始連線)
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ConnectivityManager cmgr;//連線狀態管理員
    private MyReciver myReciver; //廣播接收訖
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSystemService(@RecentlyNonNull String name)//從系統伺服器取得(Context.連線伺服器狀態),回傳到(ConnectivityManager)裡
        cmgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);//從系統伺服器取得(Context.連線伺服器狀態),回傳到(ConnectivityManager)裡
        MyReciver myReciver = new MyReciver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);//建立一個intentFilter(連線狀態經理人裡面的.開始連線)
        //registerReceiver(BroadcastReceiver receiver, IntentFilter filter):(回傳直Intent)
        registerReceiver(myReciver,intentFilter);//廣播註冊(1.自己定義的廣播 2.要inteniteFiter的東西 );
    }

    //1.network是否連線方法
    private boolean isConnectNetword(){
        NetworkInfo networkInfo = cmgr.getActiveNetworkInfo();//取得連線狀態資訊(回傳到NetworkInfo)
        //當連線狀態不等於空,而且有包含連線時回傳true
        return networkInfo != null && networkInfo.isConnectedOrConnecting();

    }

    //2.wifi是否連線方法
    private  boolean isWifiConnected(){
      NetworkInfo networkInfo =  cmgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);//取得連線狀態(ConnectivityManager裡面的.要詢問的種類type,wifi等需要 )
      return  networkInfo.isConnected();//網路是否連線(回傳布林直)
    }

    //1.按下按鈕查詢是否連線network
    public void tset1(View view) {
        isConnectNetword();
        Log.v("brad","連線狀態為NetWordk:" + isConnectNetword());
    }

    //2.查詢wifi是否連線中
    public void tset2(View view) {
        isWifiConnected();
        Log.v("brad","wifi連線狀態為:" + isWifiConnected());
    }
    //3.內部類別廣播接受器,用飛航測試網路是否開,來決定要做什麼事情
    private class MyReciver extends BroadcastReceiver{
        //實作當收到廣播之後要做的事情
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("brad","onReceice收到:" + isConnectNetword() +"netWork連線中");
        }
    }

}
