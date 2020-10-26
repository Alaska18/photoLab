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

/**
 *  This class will load the image for cropping on the screen using a fragment.
 */

public class CropImageFragment extends Fragment {

    static private Bitmap image; // the image to be loaded.
    private CropImageView cropImageView; // the view on which image has to be loaded.

    /**
     * The method that will be called to create the view.
     * @param inflater inflater used to inflate the layout of the fragment.
     * @param container Container used to inflate the layout of the fragment.
     * @param savedInstanceState the bundle to check if any saved instance state is passed.
     * @return the view of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crop_image, container, false);

    }

    /**
     * The method that will be called after the view has been created.
     * @param view the created view.
     * @param savedInstanceState --
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cropImageView = view.findViewById(R.id.CropImageView);
    }

    /**
     * This method will set the bitmap to the image.
     * @param bitmap the bitmap.
     */
    static void setBitmap(Bitmap bitmap) {
        image = bitmap;
    }

    /**
     * This method will be called when the fragment is in resume state i.e it is running.
     * In this state the image is set on the view.
     */
    @Override
    public void onResume() {
        super.onResume();
        cropImageView.setImageBitmap(image);
    }

    /**
     * This method will set the aspect ratio of the image.
     * @param A the width.
     * @param B the height.
     */
    void setAspectRatio(int A, int B) {
        if (A == 0 && B == 0) {
            cropImageView.clearAspectRatio();
        } else if (A == -1 && B == -1) {
            cropImageView.setCropShape(CropImageView.CropShape.OVAL);
        } else cropImageView.setAspectRatio(A, B);
    }

    /**
     * This method will give the final cropped image.
     * @return the final cropped bitmap.
     */
    Bitmap getCroppedImage() {
        return cropImageView.getCroppedImage();
    }

    /**
     * This method will set the rotation of the image.
     * @param degrees the degrees by which to rotate the image.
     */
    void setRotation(float degrees) {
        cropImageView.rotateImage((int) degrees);
    }

}
