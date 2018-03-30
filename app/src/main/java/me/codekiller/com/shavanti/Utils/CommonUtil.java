package me.codekiller.com.shavanti.Utils;

/**
 * 杂项工具类
 */
public class CommonUtil {

    /**
     * 通过图片Url截取图片名称
     */
    public static String getOnePicName(String url){
        String[] strs = url.split("/");
        return strs[strs.length-1];
    }
}
