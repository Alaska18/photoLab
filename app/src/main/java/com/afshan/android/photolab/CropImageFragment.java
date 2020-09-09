package com.afshan.android.photolab;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImageView;


public class CropImageFragment extends Fragment {
    static private Bitmap image;
    private CropImageView cropImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crop_image, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cropImageView = view.findViewById(R.id.CropImageView);
    }

    static void setBitmap(Bitmap bitmap) {
        image = bitmap;
    }

    @Override
    public void onResume() {
        super.onResume();
        cropImageView.setImageBitmap(image);
    }

    void setAspectRatio(int A, int B) {
        if (A == 0 && B == 0) {
            cropImageView.clearAspectRatio();
        } else if (A == -1 && B == -1) {
            cropImageView.setCropShape(CropImageView.CropShape.OVAL);
        } else cropImageView.setAspectRatio(A, B);
    }

    Bitmap getCroppedImage() {
        return cropImageView.getCroppedImage();
    }

    void setRotation(float degrees) {
        cropImageView.rotateImage((int) degrees);
    }

}
