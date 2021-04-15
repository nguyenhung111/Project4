package com.example.mystore.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnection {
    public static boolean CheckNetwork(Context context){
        boolean haveConnectWifi = false;
        boolean haveConnectMobi = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netinfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netinfo){
            if(ni.getTypeName().equalsIgnoreCase("WIFI"))
                if(ni.isConnected())
                    haveConnectWifi= true;
            if(ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if(ni.isConnected())
                    haveConnectMobi= true;
        }
        return haveConnectWifi || haveConnectMobi ;
    }
    public static void ShowToast (Context context, String thongbao){
        Toast.makeText(context, thongbao,Toast.LENGTH_SHORT).show();
    }
}
