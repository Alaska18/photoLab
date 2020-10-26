package com.afshan.android.photolab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.ViewType;

public class TextandDrawing extends Fragment {
    private static Bitmap image;
    private static PhotoEditorView imageView;
    private static PhotoEditor mPhotoEditor;
    private TextView textView;
    private static Bitmap bitmap;
    private View mView;
    private Context mContext;

    public void setBitmap(Bitmap bitmap) {
        image = bitmap;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.text_and_drawing, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.text_image_view);
        mPhotoEditor = new PhotoEditor.Builder(getContext(), imageView).setPinchTextScalable(true).build();
        mPhotoEditor.setBrushEraserSize(2.0f);
        mPhotoEditor.setBrushColor(getContext().getResources().getColor(R.color.white));

    }

    @Override
    public void onResume() {
        super.onResume();
        imageView.getSource().setImageBitmap(image.copy(Bitmap.Config.ARGB_8888, true));
        mPhotoEditor.setOnPhotoEditorListener(new OnPhotoEditorListener()
        {
            @Override
            public void onEditTextChangeListener(View rootView, String text, int colorCode)
            {
                     textView.requestNewText();
                     mView = rootView;
            }

            @Override
            public void onAddViewListener(ViewType viewType, int numberOfAddedViews)
            {

            }

            @Override
            public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

            }

            @Override
            public void onStartViewChangeListener(ViewType viewType)
            {

            }

            @Override
            public void onStopViewChangeListener(ViewType viewType)
            {

            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        textView = (TextView) context;
        mContext = context;

    }

    public void addText(int color, String text, Typeface typeface, boolean isRequested)
    {
        if (!isRequested)
        {
            mPhotoEditor.addText(typeface, text, color);
        }
        else
        {
            mPhotoEditor.editText(mView, typeface, text, color);
        }
    }
    public static void getBitmap()
    {
        mPhotoEditor.saveAsBitmap(new OnSaveBitmap() {
            @Override
            public void onBitmapReady(Bitmap saveBitmap)
            {
                 imageView.getSource().setImageBitmap(saveBitmap);
                 PhotoFragment.setBitmap(saveBitmap);
                 PhotoLab.bitmaps.add(0, saveBitmap);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
    public void setDrawingMode()
    {
        imageView.getSource().setImageBitmap(image.copy(Bitmap.Config.ARGB_8888, true));
        mPhotoEditor = new PhotoEditor.Builder(getContext(), imageView).setPinchTextScalable(true).build();
        mPhotoEditor.setBrushEraserSize(2.0f);
        mPhotoEditor.setBrushColor(getContext().getResources().getColor(R.color.white));
    }
    interface TextView
    {
        public void requestNewText();
    }
}
