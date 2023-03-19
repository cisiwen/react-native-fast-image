package com.dylanvann.fastimage.mediastore;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

public class MediaStoreLoaderFactory implements ModelLoaderFactory<String, Bitmap> {


    private  Context context;
    public  MediaStoreLoaderFactory(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ModelLoader<String, Bitmap> build(@NonNull MultiModelLoaderFactory multiFactory) {
        return new MediaStoreLoader(context);
    }

    @Override
    public void teardown() {

    }
}
