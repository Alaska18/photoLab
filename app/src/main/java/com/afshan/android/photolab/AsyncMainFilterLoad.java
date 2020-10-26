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

/**
 * Class that will load the filter from the menu to the image on canvas in background.
 */

public class AsyncMainFilterLoad extends AsyncTask<Void, Void, Bitmap> {

    private Filter filter;  // Normal Filter
    private Context context; // Context.
    private Bitmap bitmap;   // Image on which the filter has to be loaded.
    private ProgressBar progressBar;  // The progress bar that will appear on the screen.
    private PhotoView photoView; // The view on which the bitmap will be placed.
    private GPUImageFilter gpuImageFilter;  // Gpu image filter.


    /**
     * This constructor is for normal filter
     * @param filter  The normal filter.
     * @param bitmap bitmap on which the filter has to be loaded.
     * @param context the context.
     * @param progressBar to be used for indicating progress.
     * @param photoView to be used for placing the bitmap.
     */

    AsyncMainFilterLoad(Filter filter, Bitmap bitmap, Context context, ProgressBar progressBar, PhotoView photoView) {
        this.filter = filter;
        this.context = context;
        this.bitmap = bitmap;
        this.progressBar = progressBar;
        this.photoView = photoView;
    }

    /**
     * This constructor is for GPUI mage filter.
     * @param filter the GPU image filter.
     * @param bitmap the bitmap on which the filter has to be loaded.
     * @param context the context.
     * @param progressBar to be used for indicating progress.
     * @param photoView to be used for placing the bitmap.
     */

    AsyncMainFilterLoad(GPUImageFilter filter, Bitmap bitmap, Context context, ProgressBar progressBar, PhotoView photoView) {
        this.gpuImageFilter = filter;
        this.context = context;
        this.bitmap = bitmap;
        this.progressBar = progressBar;
        this.photoView = photoView;
    }

    /**
     * Method that will execute background instructions.
     * @param voids Nothing passed.
     * @return Bitmap with applied filter.
     */

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

    /**
     * This method will place the returned bitmap on the photo view.
     * @param bitmap the returned bitmap with filter applied from the do in background method.
     */

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        photoView.setImageBitmap(bitmap);
        progressBar.setVisibility(View.GONE);
        PhotoFragment.buffer = bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }
}
