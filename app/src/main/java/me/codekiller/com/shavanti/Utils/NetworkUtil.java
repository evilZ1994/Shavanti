package me.codekiller.com.shavanti.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

public class NetworkUtil {

    /**
     * 检查网络是否已连接
     */
    public static boolean isNetworkConnected(Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return checkState_23(context);
        }else {
            return checkState_23orNew(context);
        }
    }

    public static boolean checkState_23(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiOn = networkInfo.isConnected();
        networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileOn = networkInfo.isConnected();

        return isWifiOn || isMobileOn;
    }

    public static boolean checkState_23orNew(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks = manager.getAllNetworks();
        boolean isNetworkOn = false;
        for (Network network : networks){
            NetworkInfo networkInfo = manager.getNetworkInfo(network);
            isNetworkOn = isNetworkOn || networkInfo.isConnected();
        }

        return isNetworkOn;
    }
}
