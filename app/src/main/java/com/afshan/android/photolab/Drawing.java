package com.afshan.android.photolab;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class Drawing extends Fragment
{
    private Bitmap image;
    private PhotoEditor photoEditor;
    private PhotoEditorView photoEditorView;
    private transition mTransition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.text_and_drawing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photoEditorView = view.findViewById(R.id.text_image_view);
        photoEditor = new PhotoEditor.Builder(getContext(), photoEditorView).build();
        photoEditor.setBrushSize(10.0f);
        photoEditor.setBrushColor(getContext().getResources().getColor(R.color.white));
    }
    public void setBitmap(Bitmap bitmap) {
        image = bitmap;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mTransition = (transition) context;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        photoEditorView.getSource().setImageBitmap(image.copy(Bitmap.Config.ARGB_8888, true));
    }
    void brushSize(float size)
    {
        photoEditor.setBrushSize(size);
        photoEditor.setBrushDrawingMode(true);
    }
    void brushOpacity(int opacity)
    {
        photoEditor.setOpacity(opacity);
        photoEditor.setBrushDrawingMode(true);
    }
    void brushColor(int color)
    {
        photoEditor.setBrushColor(color);
        photoEditor.setBrushDrawingMode(true);
    }
    void setEraserSize(int size)
    {
        photoEditor.setBrushEraserSize(size);
        photoEditor.brushEraser();
    }
    void undo()
    {
        photoEditor.undo();
    }
    void redo()
    {
        photoEditor.redo();
    }

    void getBitmap()
    {
        photoEditor.saveAsBitmap(new OnSaveBitmap() {
            @Override
            public void onBitmapReady(Bitmap saveBitmap)
            {
                PhotoLab.bitmaps.add(0, saveBitmap.copy(Bitmap.Config.ARGB_8888, true));
                mTransition.setTransition();

            }

            @Override
            public void onFailure(Exception e)
            {

            }
        });
    }
    interface transition
    {
        void setTransition();
    }
}
