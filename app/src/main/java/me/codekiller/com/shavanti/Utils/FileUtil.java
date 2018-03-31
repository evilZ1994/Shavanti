package me.codekiller.com.shavanti.Utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     * 保存bitmap到指定文件
     */
    public static void saveBitmap(Bitmap bitmap, String path){
        File file = new File(path);
        if (!file.exists() && bitmap != null){
            try {
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件夹下的文件列表
     * @param folderPath 文件夹路径
     * @return 文件名称列表
     */
    public static List<String> getFileNames(String folderPath){
        File folder = new File(folderPath);
        List<String> fileNames = new ArrayList<>();
        if (folder.exists() && folder.isDirectory()){
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isFile()){
                    fileNames.add(file.getName());
                }
            }
        }

        return fileNames;
    }
}
