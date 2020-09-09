package com.afshan.android.photolab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.AsyncTask;

import com.github.chrisbanes.photoview.PhotoView;

public class AsyncSaturation extends AsyncTask<Void, Void, Bitmap> {
    PhotoView photoView;
    Context context;
    Bitmap bitmap;
    ColorMatrix colorMatrix;
    ColorMatrixColorFilter colorMatrixColorFilter;
    Paint paint;
    Canvas canvas;
    Bitmap canvasBitmap;
    int progress;

    AsyncSaturation(int progress, Bitmap canvasBitmap, ColorMatrix colorMatrix, ColorMatrixColorFilter colorMatrixColorFilter, Paint paint, Canvas canvas, PhotoView photoView, Bitmap bitmap, Context context) {
        this.photoView = photoView;
        this.context = context;
        this.bitmap = bitmap;
        this.colorMatrix = colorMatrix;
        this.colorMatrixColorFilter = colorMatrixColorFilter;
        this.paint = paint;
        this.canvas = canvas;
        this.canvasBitmap = canvasBitmap;
        this.progress = progress;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        colorMatrix.setSaturation(progress);
        colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixColorFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return canvasBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        photoView.setImageBitmap(bitmap);
    }
}
