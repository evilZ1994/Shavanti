package me.codekiller.com.shavanti.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.codekiller.com.shavanti.R;

/**
 * 杂项工具类
 */
public class CommonUtil {

    /**
     * 通过图片Url截取图片名称
     */
    public static String getOnePicName(String url) {
        String[] strs = url.split("/");
        return strs[strs.length - 1];
    }

    public static String getB(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("day", Context.MODE_PRIVATE);
        return new DES(new DES(readP(context)).decrypt(preferences.getString("k", "50d760b44f0ds684f")+preferences.getString("d", "a36e49c5d8c4b22c"))).decrypt(readB(context));
    }

    public static String getP(Context context){
        return new DES(context.getResources().getString(R.string.n)).decrypt(readP(context));
    }

    public static String getN(Context context){
        return new DES(readB(context)).decrypt(context.getResources().getString(R.string.n));
    }

    public static String readB(Context context) {
        try {
            return new BufferedReader(new InputStreamReader(context.getResources().getAssets().open("b"), "UTF-8")).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "404";
    }

    public static String readP(Context context) {
        try {
            return new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.p), "UTF-8")).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "404";
    }
}
