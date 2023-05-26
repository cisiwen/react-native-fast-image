package com.dylanvann.fastimage.mediastore;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;

import androidx.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.IOException;

public class MediaStoreDataFetcher implements DataFetcher<Bitmap> {

    private Context context;
    private  String source;
    public  MediaStoreDataFetcher(Context context,String  source){
        this.context = context;
        this.source = source;
    }
    private static class  LoadLocalMediaStoreDataTask extends AsyncTask<Void,Void,Bitmap> {
        private Context context;
        private Uri uri;
        private Long imageId;

        private DataCallback<? super Bitmap> callback;
        private  LoadLocalMediaStoreDataTask(String uri,@NonNull DataCallback<? super Bitmap> callback,Context context){
              this.context = context;
              this.uri= Uri.parse(uri);
              String[] parts = uri.split("/");
              this.imageId= Long.getLong(parts[parts.length-1]);
              this.callback = callback;
        }
        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap thumbBitmap;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                 try {
                    thumbBitmap = this.context.getContentResolver().loadThumbnail(this.uri, new Size(200,200), null);
                } catch (IOException e) {
                    Log.e("LoadLocalMediaStoreDataTask",this.uri.toString(),e);
                }
                catch (Exception e){
                    Log.e("LoadLocalMediaStoreDataTask",this.uri.toString(),e);
                }
            } else {
                thumbBitmap = MediaStore.Images.Thumbnails.getThumbnail(this.context.getContentResolver(),
                        this.imageId, MediaStore.Images.Thumbnails.MINI_KIND, null);
            }
            return thumbBitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            callback.onDataReady(result);
        }
    }

    @Override
    public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super Bitmap> callback) {
        new LoadLocalMediaStoreDataTask(source,callback,context).execute();
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void cancel() {

    }

    @NonNull
    @Override
    public Class<Bitmap> getDataClass() {
        return Bitmap.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }
}
