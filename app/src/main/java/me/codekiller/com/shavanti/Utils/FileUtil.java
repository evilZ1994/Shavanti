package me.codekiller.com.shavanti.Utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
}
