package com.afshan.android.photolab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xw.repo.BubbleSeekBar;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class DrawFragment extends Fragment
{
    private PhotoEditor photoEditor;
    private LinearLayout eraserLayout;
    private BubbleSeekBar eraserSize;
    private TextView eraser;
    private LinearLayout brushLayout;
    private TextView brush;
    private PhotoEditorView photoEditorView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.draw_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        eraserLayout = view.findViewById(R.id.eraserLayout);
        eraserSize = view.findViewById(R.id.seekBarEraser);
        brushLayout = view.findViewById(R.id.brushLayout);
        eraser = view.findViewById(R.id.eraser);
        brush = view.findViewById(R.id.brush);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                eraser.setTextColor(getResources().getColor(R.color.blue_bright));
                brush.setTextColor(getResources().getColor(R.color.white));
               brushLayout.setVisibility(View.GONE);
               eraserLayout.setVisibility(View.VISIBLE);
            }
        });
        brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                brush.setTextColor(getResources().getColor(R.color.blue_bright));
                eraser.setTextColor(getResources().getColor(R.color.white));
                eraserLayout.setVisibility(View.GONE);
                brushLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
