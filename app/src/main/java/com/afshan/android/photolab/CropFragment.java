package com.afshan.android.photolab;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CropFragment extends Fragment
{
    ItemClicked itemClicked;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crop, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageViewSquare = view.findViewById(R.id.square);
        final TextView squareText = view.findViewById(R.id.squareT);
        final TextView cropFreeText = view.findViewById(R.id.freeCropT);
        final TextView fbPostText = view.findViewById(R.id.fbT);
        final TextView InstaPostText = view.findViewById(R.id.instaT);
        final TextView tinderText = view.findViewById(R.id.tinderT);
        final TextView landscapeText = view.findViewById(R.id.landscapeT);
        final TextView circle = view.findViewById(R.id.circleT);
        final ImageView rotateLeft = view.findViewById(R.id.rotate_left);
        final ImageView rotateRight = view.findViewById(R.id.rotate_right);
        imageViewSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.itemClicked(1, 1);
                squareText.setTextColor(getResources().getColor(R.color.blue_bright));
                setColor(cropFreeText, fbPostText, tinderText, InstaPostText, landscapeText, circle);
            }

        });
        ImageView imageViewCropFree = view.findViewById(R.id.freeCrop);
        imageViewCropFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.itemClicked(0, 0);
                cropFreeText.setTextColor(getResources().getColor(R.color.blue_bright));
                setColor(squareText, fbPostText, tinderText, InstaPostText, landscapeText, circle);

            }

        });
        ImageView imageViewFbPost = view.findViewById(R.id.fb);
        imageViewFbPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.itemClicked(3, 2);
                fbPostText.setTextColor(getResources().getColor(R.color.blue_bright));
                setColor(cropFreeText, squareText, tinderText, InstaPostText, landscapeText, circle);
            }

        });
        ImageView imageViewInstaPost = view.findViewById(R.id.insta);
        imageViewInstaPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.itemClicked(4, 5);
                InstaPostText.setTextColor(getResources().getColor(R.color.blue_bright));
                setColor(cropFreeText, fbPostText, tinderText, squareText, landscapeText, circle);
            }

        });
        ImageView imageViewTinder = view.findViewById(R.id.tinder);
        imageViewTinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.itemClicked(1, 1);
                tinderText.setTextColor(getResources().getColor(R.color.blue_bright));
                setColor(cropFreeText, fbPostText, squareText, InstaPostText, landscapeText, circle);
            }

        });
        ImageView imageViewLandscape = view.findViewById(R.id.lanscape);
        imageViewLandscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.itemClicked(16, 9);
                landscapeText.setTextColor(getResources().getColor(R.color.blue_bright));
                setColor(cropFreeText, fbPostText, tinderText, InstaPostText, squareText, circle);
            }

        });
        ImageView imageViewCircle = view.findViewById(R.id.circle);
        imageViewCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.itemClicked(-1, -1);
                circle.setTextColor(getResources().getColor(R.color.blue_bright));
                setColor(cropFreeText, fbPostText, tinderText, InstaPostText, squareText, landscapeText);
            }
        });
        rotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.rotateClicked(-90);

            }
        });
        rotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.rotateClicked(90);
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            itemClicked = (ItemClicked) context;
        }
        catch (ClassCastException c)
        {
            //
        }

    }
    private void setColor(TextView a, TextView b, TextView c, TextView d, TextView e, TextView f)
    {
        a.setTextColor(getResources().getColor(R.color.black));
        b.setTextColor(getResources().getColor(R.color.black));
        c.setTextColor(getResources().getColor(R.color.black));
        e.setTextColor(getResources().getColor(R.color.black));
        d.setTextColor(getResources().getColor(R.color.black));
        f.setTextColor(getResources().getColor(R.color.black));
    }

    interface ItemClicked
    {
        public void itemClicked(int AspectRatioA, int AspectRatioB);

        public void rotateClicked(float degrees);
    }
}
