package com.afshan.android.photolab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.zomato.photofilters.imageprocessors.Filter;

public class LoadFilter extends AsyncTask<Void, Void, Bitmap> {

    private Filter filter;
    private ImageView imageView;
    private Context context;

    LoadFilter(Filter filter, ImageView imageView, Context context) {
        this.filter = filter;
        this.imageView = imageView;
        this.context = context;
    }


    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo_reference);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, 400, 400, false);
        return filter.processFilter(mBitmap);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
    }
}
