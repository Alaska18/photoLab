package com.afshan.android.photolab;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.zomato.photofilters.imageprocessors.Filter;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

public class PhotoFragment extends Fragment {
    static Bitmap buffer;
    static private Bitmap image;
    private PhotoView imageView;
    private ProgressBar progressBar;
    private int pointer = 1;
    int position = 1;

    static void setBitmap(Bitmap bitmap) {

        image = bitmap.copy(Bitmap.Config.ARGB_8888, true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.photo);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.blue_bright), PorterDuff.Mode.SRC_IN);
        Bitmap b = PhotoLab.bitmaps.get(0).copy(Bitmap.Config.ARGB_8888, true);
        buffer = b.copy(Bitmap.Config.ARGB_8888, true);
        imageView.setImageBitmap(image);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onSwipeLeft() {
        if (1 + pointer < FilterFragment.filters.size()) {
            position = ++pointer;
            if (FilterFragment.filters.get(position).getGpuImageFilter() == null && FilterFragment.filters.get(position).getGpuImageFilterGroup() == null) {
                setFilter(FilterFragment.filters.get(position).getFilter());
            } else if (FilterFragment.filters.get(position).getGpuImageFilterGroup() != null) {
                setGPUImageFilterGroup(FilterFragment.filters.get(position).getGpuImageFilterGroup());
            } else {
                setGPUImageFilter(FilterFragment.filters.get(position).getGpuImageFilter());
            }
            FilterFragment.recyclerView.smoothScrollToPosition(position);
            FilterFragment.itemSelected(position);
        }

    }

    public void onSwipeRight() {
        if (pointer - 1 >= 0) {
            position = --pointer;
            if (FilterFragment.filters.get(position).getGpuImageFilter() == null && FilterFragment.filters.get(position).getGpuImageFilterGroup() == null) {
                setFilter(FilterFragment.filters.get(position).getFilter());
            } else if (FilterFragment.filters.get(position).getGpuImageFilterGroup() != null) {
                setGPUImageFilterGroup(FilterFragment.filters.get(position).getGpuImageFilterGroup());
            } else {
                setGPUImageFilter(FilterFragment.filters.get(position).getGpuImageFilter());
            }
            FilterFragment.recyclerView.smoothScrollToPosition(position);
            FilterFragment.itemSelected(position);
        }
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

    public void removeOnFlingListener() {
        imageView.setOnSingleFlingListener(null);
    }

    void addOnFlingListener() {
        imageView.setOnSingleFlingListener(new OnSingleFlingListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                final int SWIPE_THRESHOLD = 100;
                final int SWIPE_VELOCITY_THRESHOLD = 100;
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        });
    }

    void setImage(Bitmap b) {
        buffer = b.copy(Bitmap.Config.ARGB_8888, true);
        imageView.setImageBitmap(buffer);
    }
    void setPointer(int pointerPosition)
    {
        this.pointer = pointerPosition;
    }


}
