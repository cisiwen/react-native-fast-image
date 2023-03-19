package com.dylanvann.fastimage.mediastore;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;

public final class MediaStoreLoader implements ModelLoader<String, Bitmap> {

    private  Context context;
    public  MediaStoreLoader(Context context){
        this.context = context;
    }
    public static final String ANDROID_CONTENT_SCHEME = "content";
    @Nullable
    @Override
    public LoadData<Bitmap> buildLoadData(@NonNull String source, int width, int height, @NonNull Options options) {
        return new LoadData<>(new ObjectKey(source), new MediaStoreDataFetcher(this.context,source));
    }

    @Override
    public boolean handles(@NonNull String source) {
        return  source.startsWith(ANDROID_CONTENT_SCHEME);
    }
}
