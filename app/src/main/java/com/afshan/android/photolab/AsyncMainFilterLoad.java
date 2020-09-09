package com.afshan.android.photolab;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.github.chrisbanes.photoview.PhotoView;
import com.zomato.photofilters.imageprocessors.Filter;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

public class AsyncMainFilterLoad extends AsyncTask<Void, Void, Bitmap> {

    private Filter filter;
    private Context context;
    private Bitmap bitmap;
    private ProgressBar progressBar;
    private PhotoView photoView;
    private GPUImageFilter gpuImageFilter;

    AsyncMainFilterLoad(Filter filter, Bitmap bitmap, Context context, ProgressBar progressBar, PhotoView photoView) {
        this.filter = filter;
        this.context = context;
        this.bitmap = bitmap;
        this.progressBar = progressBar;
        this.photoView = photoView;
    }

    AsyncMainFilterLoad(GPUImageFilter filter, Bitmap bitmap, Context context, ProgressBar progressBar, PhotoView photoView) {
        this.gpuImageFilter = filter;
        this.context = context;
        this.bitmap = bitmap;
        this.progressBar = progressBar;
        this.photoView = photoView;
    }


    @Override
    protected Bitmap doInBackground(Void... voids) {
        if (filter != null)
            return filter.processFilter(bitmap);
        else {
            GPUImage gpuImage = new GPUImage(context);
            gpuImage.setImage(bitmap);
            gpuImage.setFilter(gpuImageFilter);
            return gpuImage.getBitmapWithFilterApplied();
        }

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        photoView.setImageBitmap(bitmap);
        progressBar.setVisibility(View.GONE);
        PhotoFragment.buffer = bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }
}
