package com.afshan.android.photolab;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.PhotoView;
import com.zomato.photofilters.imageprocessors.Filter;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter;

public class PhotoFragment extends Fragment {
    static private Bitmap image;
    static Bitmap buffer;
    private View view;
    private ColorMatrix colorMatrix;
    private ColorMatrixColorFilter colorMatrixColorFilter;
    private Paint paint;
    private Canvas canvas;
    private Bitmap canvasBitmap;
    private PhotoView imageView;
    private ProgressBar progressBar;
    private Bitmap saturation, contrast, brightness, sharpness, exposure, highlight, shadows;
    private TextView level;

    static void setBitmap(Bitmap bitmap) {

        image = bitmap.copy(Bitmap.Config.ARGB_8888, true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        imageView = view.findViewById(R.id.photo);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.blue_bright), PorterDuff.Mode.SRC_IN);
        Bitmap b = PhotoLab.bitmaps.get(0).copy(Bitmap.Config.ARGB_8888, true);
        buffer = b.copy(Bitmap.Config.ARGB_8888, true);
        saturation = b.copy(Bitmap.Config.ARGB_8888, true);
        contrast = b.copy(Bitmap.Config.ARGB_8888, true);
        brightness = b.copy(Bitmap.Config.ARGB_8888, true);
        exposure = b.copy(Bitmap.Config.ARGB_8888, true);
        shadows = b.copy(Bitmap.Config.ARGB_8888, true);
        highlight = b.copy(Bitmap.Config.ARGB_8888, true);
        sharpness = b.copy(Bitmap.Config.ARGB_8888, true);
        imageView.setImageBitmap(image);
        colorMatrix = new ColorMatrix();
        colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint = new Paint();
        paint.setColorFilter(colorMatrixColorFilter);
        canvasBitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBitmap);


    }

    void setFilter(Filter filter) {
        Bitmap b = image.copy(Bitmap.Config.ARGB_8888, true);
        progressBar.setVisibility(View.VISIBLE);
        AsyncMainFilterLoad asyncMainFilterLoad = new AsyncMainFilterLoad(filter, b, getContext(), progressBar, imageView);
        asyncMainFilterLoad.execute();
    }

    void setGPUImageFilter(GPUImageFilter gpuImageFilter) {
        Bitmap b = image.copy(Bitmap.Config.ARGB_8888, true);
        progressBar.setVisibility(View.VISIBLE);
        AsyncMainFilterLoad asyncMainFilterLoad = new AsyncMainFilterLoad(gpuImageFilter, b, getContext(), progressBar, imageView);
        asyncMainFilterLoad.execute();
    }

    void setGPUImageFilterGroup(GPUImageFilter gpuImageFilterGroup) {
        Bitmap b = image.copy(Bitmap.Config.ARGB_8888, true);
        progressBar.setVisibility(View.VISIBLE);
        AsyncMainFilterLoad asyncMainFilterLoad = new AsyncMainFilterLoad(gpuImageFilterGroup, b, getContext(), progressBar, imageView);
        asyncMainFilterLoad.execute();
    }

    void setRemoveFilter() {
        imageView.setImageBitmap(PhotoLab.bitmaps.get(0));
    }


    void setContrast(float progress) {
        Bitmap current = doImageFilterWork(new GPUImageContrastFilter(progress), contrast);

        saturation = current.copy(Bitmap.Config.ARGB_8888, true);
        brightness = current.copy(Bitmap.Config.ARGB_8888, true);
        sharpness = current.copy(Bitmap.Config.ARGB_8888, true);
        highlight = current.copy(Bitmap.Config.ARGB_8888, true);
        shadows = current.copy(Bitmap.Config.ARGB_8888, true);
        exposure = current.copy(Bitmap.Config.ARGB_8888, true);
    }

    void setSaturation(float progress) {
        Bitmap current = doImageFilterWork(new GPUImageSaturationFilter(progress), saturation);

        contrast = current.copy(Bitmap.Config.ARGB_8888, true);
        brightness = current.copy(Bitmap.Config.ARGB_8888, true);
        sharpness = current.copy(Bitmap.Config.ARGB_8888, true);
        highlight = current.copy(Bitmap.Config.ARGB_8888, true);
        shadows = current.copy(Bitmap.Config.ARGB_8888, true);
        exposure = current.copy(Bitmap.Config.ARGB_8888, true);
    }

    void setBrightness(float progress) {
        Bitmap current = doImageFilterWork(new GPUImageBrightnessFilter(progress), brightness);

        contrast = current.copy(Bitmap.Config.ARGB_8888, true);
        saturation = current.copy(Bitmap.Config.ARGB_8888, true);
        sharpness = current.copy(Bitmap.Config.ARGB_8888, true);
        highlight = current.copy(Bitmap.Config.ARGB_8888, true);
        shadows = current.copy(Bitmap.Config.ARGB_8888, true);
        exposure = current.copy(Bitmap.Config.ARGB_8888, true);
    }

    void setShadows(float progress) {
        Bitmap current = doImageFilterWork(new GPUImageHighlightShadowFilter(progress, 1.0f), shadows);

        contrast = current.copy(Bitmap.Config.ARGB_8888, true);
        brightness = current.copy(Bitmap.Config.ARGB_8888, true);
        sharpness = current.copy(Bitmap.Config.ARGB_8888, true);
        highlight = current.copy(Bitmap.Config.ARGB_8888, true);
        saturation = current.copy(Bitmap.Config.ARGB_8888, true);
        exposure = current.copy(Bitmap.Config.ARGB_8888, true);
    }

    void setSharpness(float progress) {
        Bitmap current = doImageFilterWork(new GPUImageSharpenFilter(progress), sharpness);

        contrast = current.copy(Bitmap.Config.ARGB_8888, true);
        brightness = current.copy(Bitmap.Config.ARGB_8888, true);
        saturation = current.copy(Bitmap.Config.ARGB_8888, true);
        highlight = current.copy(Bitmap.Config.ARGB_8888, true);
        shadows = current.copy(Bitmap.Config.ARGB_8888, true);
        exposure = current.copy(Bitmap.Config.ARGB_8888, true);
    }

    void setHighlight(float progress) {
        Bitmap current = doImageFilterWork(new GPUImageHighlightShadowFilter(0.0f, progress), highlight);

        contrast = current.copy(Bitmap.Config.ARGB_8888, true);
        brightness = current.copy(Bitmap.Config.ARGB_8888, true);
        sharpness = current.copy(Bitmap.Config.ARGB_8888, true);
        saturation = current.copy(Bitmap.Config.ARGB_8888, true);
        shadows = current.copy(Bitmap.Config.ARGB_8888, true);
        exposure = current.copy(Bitmap.Config.ARGB_8888, true);
    }

    void setExposure(float progress) {
        Bitmap current = doImageFilterWork(new GPUImageExposureFilter(progress), exposure);

        contrast = current.copy(Bitmap.Config.ARGB_8888, true);
        brightness = current.copy(Bitmap.Config.ARGB_8888, true);
        sharpness = current.copy(Bitmap.Config.ARGB_8888, true);
        highlight = current.copy(Bitmap.Config.ARGB_8888, true);
        shadows = current.copy(Bitmap.Config.ARGB_8888, true);
        saturation = current.copy(Bitmap.Config.ARGB_8888, true);
    }

    void okayClicked() {
        image = buffer.copy(Bitmap.Config.ARGB_8888, true);
        imageView.setImageBitmap(buffer);
    }

    Bitmap doImageFilterWork(GPUImageFilter gpuImageFilter, Bitmap image) {
        GPUImage gpuImage = new GPUImage(getContext());

        int nh = (int) (image.getHeight() * (512.0 / image.getWidth()));
        Bitmap mBitmap = Bitmap.createScaledBitmap(image, 512, nh, true);

        gpuImage.setImage(mBitmap);

        gpuImage.setFilter(gpuImageFilter);

        imageView.setImageBitmap(gpuImage.getBitmapWithFilterApplied());

        buffer = gpuImage.getBitmapWithFilterApplied().copy(Bitmap.Config.ARGB_8888, true);

        return gpuImage.getBitmapWithFilterApplied();
    }


    /* void setImageSaturation(int progress)
     {
         AsyncSaturation saturation = new AsyncSaturation(progress, canvasBitmap, colorMatrix, colorMatrixColorFilter, paint, canvas, imageView, buffer, getContext());
         saturation.execute();
     }*/
    void setImages() {
        Bitmap b = PhotoLab.bitmaps.get(0).copy(Bitmap.Config.ARGB_8888, true);
        buffer = b.copy(Bitmap.Config.ARGB_8888, true);
        saturation = b.copy(Bitmap.Config.ARGB_8888, true);
        contrast = b.copy(Bitmap.Config.ARGB_8888, true);
        brightness = b.copy(Bitmap.Config.ARGB_8888, true);
        exposure = b.copy(Bitmap.Config.ARGB_8888, true);
        shadows = b.copy(Bitmap.Config.ARGB_8888, true);
        highlight = b.copy(Bitmap.Config.ARGB_8888, true);
        sharpness = b.copy(Bitmap.Config.ARGB_8888, true);
        EffectsUtility.satVal = 10;
        EffectsUtility.brightVal = 10;
        EffectsUtility.contrastVal = 300;
        EffectsUtility.sharpVal = 30;
        EffectsUtility.ShadowVal = 0;
        EffectsUtility.highlightVal = 0;
        EffectsUtility.exposureVal = 1000;

    }


}
