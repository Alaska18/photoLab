package com.afshan.android.photolab;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zomato.photofilters.imageprocessors.Filter;

import java.util.ArrayList;

import jp.co.cyberagent.android.gpuimage.GPUImage;

public class LoadFilter extends AsyncTask<Void, Void, Bitmap> {

    private String filterName;
    private Filter filter = null;
    private ImageView photoView;
    private ArrayList<MenuFilter> filters;
    private ProgressBar progressBar;
    private Context context;
    private int pos = 0;

    LoadFilter(Filter filter, String FilterName, ArrayList<MenuFilter> filters, ImageView photoView, ProgressBar progressBar) {
        this.filter = filter;
        this.filterName = FilterName;
        this.filters = filters;
        this.photoView = photoView;
        this.progressBar = progressBar;
    }

    LoadFilter(ArrayList<MenuFilter> filters, ImageView photoView, ProgressBar progressBar, Context context, int pos) {
        this.filters = filters;
        this.photoView = photoView;
        this.progressBar = progressBar;
        this.context = context;
        this.pos = pos;
    }


    @Override
    protected Bitmap doInBackground(Void... voids) {
        if (filter != null) {
            int nh = (int) (PhotoLab.bitmaps.get(0).getHeight() * (512.0 / PhotoLab.bitmaps.get(0).getWidth()));
            Bitmap mBitmap = Bitmap.createScaledBitmap(PhotoLab.bitmaps.get(0), 512, nh, true);
            return filter.processFilter(mBitmap.copy(Bitmap.Config.ARGB_8888, true));
        } else if (filters.get(pos).getGpuImageFilter() != null) {
            int nh = (int) (PhotoLab.bitmaps.get(0).getHeight() * (512.0 / PhotoLab.bitmaps.get(0).getWidth()));
            Bitmap mBitmap = Bitmap.createScaledBitmap(PhotoLab.bitmaps.get(0), 512, nh, true);
            GPUImage gpuImage = new GPUImage(context);
            gpuImage.setImage(mBitmap.copy(Bitmap.Config.ARGB_8888, true));
            gpuImage.setFilter(filters.get(pos).getGpuImageFilter());
            return gpuImage.getBitmapWithFilterApplied();
        } else {
            int nh = (int) (PhotoLab.bitmaps.get(0).getHeight() * (512.0 / PhotoLab.bitmaps.get(0).getWidth()));
            Bitmap mBitmap = Bitmap.createScaledBitmap(PhotoLab.bitmaps.get(0), 512, nh, true);
            GPUImage gpuImage = new GPUImage(context);
            gpuImage.setImage(mBitmap.copy(Bitmap.Config.ARGB_8888, true));
            gpuImage.setFilter(filters.get(pos).getGpuImageFilterGroup());
            return gpuImage.getBitmapWithFilterApplied();
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        photoView.setImageBitmap(bitmap);
        progressBar.setVisibility(View.GONE);


    }
}
