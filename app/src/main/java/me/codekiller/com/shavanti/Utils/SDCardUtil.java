package me.codekiller.com.shavanti.Utils;

import android.os.Environment;

import java.io.File;

public class SDCardUtil {

    //文件根目录
    public static final String ROOT_DIR = "/Shavanti";
    //One图片存放目录
    public static final String ONE_PIC = "/OnePic";
    //缓存目录
    public static final String CACHE = "/cache";

    /**
     * 初始化文件目录
     */
    public static void initDirectories(){
        if (checkSdCard()) {
            //创建根目录
            createFileDir(ROOT_DIR);
            //创建One图片目录
            createFileDir(ROOT_DIR + ONE_PIC);
            //创建缓存目录
            createFileDir(ROOT_DIR + CACHE);
        }
    }

    /**
     * 检查SD卡是否可用
     */
    public static boolean checkSdCard(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡的路径
     */
    public static String getSdPath(){
        return Environment.getExternalStorageDirectory()+"/";
    }

    /**
     * 创建一个文件夹
     */
    public static void createFileDir(String fileDir){
        String path = getSdPath() + fileDir;
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 获取缓存路径
     */
    public static String getCachePath(){
        if (checkSdCard()){
            return getSdPath()+ROOT_DIR+CACHE;
        }else {
            return null;
        }
    }

    /**
     * 获取One图片保存路径
     */
    public static String getOnePicPath(){
        if (checkSdCard()){
            return getSdPath()+ROOT_DIR+ONE_PIC;
        }else {
            return null;
        }
    }
}
