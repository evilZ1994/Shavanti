package me.codekiller.com.shavanti.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class FrescoUtil {

    /**
     * 保存Fresco缓存的图片
     * @param path 保存的路径
     * @param imageUri 图片的uri
     */
    public static void savePic(final String path, Uri imageUri, Context context){
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setProgressiveRenderingEnabled(true)
                .build();
        DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline()
                .fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(new BaseDataSubscriber<CloseableReference<CloseableImage>>() {
            @Override
            protected void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                CloseableReference<CloseableImage> reference = dataSource.getResult();
                CloseableImage image = reference.get();
                if (image instanceof CloseableBitmap){
                    CloseableBitmap closeableBitmap = (CloseableBitmap)image;
                    Bitmap bitmap = closeableBitmap.getUnderlyingBitmap();

                    FileUtil.saveBitmap(bitmap, path);
                }
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

            }
        }, CallerThreadExecutor.getInstance());
    }
}
