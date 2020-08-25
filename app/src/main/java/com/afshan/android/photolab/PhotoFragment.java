package com.afshan.android.photolab;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zomato.photofilters.imageprocessors.Filter;

public class PhotoFragment extends Fragment
{
    static private Bitmap image;
    View view;
    private ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_image, container, false);

    }

    static void setBitmap(Bitmap bitmap) {

        image = bitmap;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        imageView = view.findViewById(R.id.photo);
        imageView.setImageBitmap(image);
    }

    void setFilter(Filter filter)
    {
        image = filter.processFilter(image);
        imageView.setImageBitmap(image);
    }
}
