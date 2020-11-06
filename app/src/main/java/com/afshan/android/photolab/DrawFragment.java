package com.afshan.android.photolab;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rtugeek.android.colorseekbar.ColorSeekBar;
import com.xw.repo.BubbleSeekBar;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

/**
 * This class is used to create the bottom drawing menu using fragments.
 */

public class DrawFragment extends Fragment {
    private PhotoEditor photoEditor;
    private LinearLayout eraserLayout;
    private BubbleSeekBar eraserSize;
    private BubbleSeekBar brushSize;
    private BubbleSeekBar opacity;
    private TextView eraser;
    private draw tool;
    private ImageView undo;
    private ImageView redo;
    private int opacityValue = 100, colorValue = Color.WHITE, eraserSizeValue = 50;
    private float brushSizeValue = 5;
    private LinearLayout brushLayout;
    private TextView brush;
    private ColorSeekBar colorSelectionSeekBar;
    private PhotoEditorView photoEditorView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.draw_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eraserLayout = view.findViewById(R.id.eraserLayout);
        eraserSize = view.findViewById(R.id.seekBarEraser);
        brushLayout = view.findViewById(R.id.brushLayout);
        eraser = view.findViewById(R.id.eraser);
        brush = view.findViewById(R.id.brush);
        undo = view.findViewById(R.id.undo);
        redo = view.findViewById(R.id.redo);
        brushSize = view.findViewById(R.id.seekBarBrushSize);
        opacity = view.findViewById(R.id.seekBarOpacity);
        colorSelectionSeekBar = view.findViewById(R.id.colorsSeekBar);
    }

    @Override
    public void onResume() {
        super.onResume();

        seekBarChangeListener size = new seekBarChangeListener();
        brushSize.setOnProgressChangedListener(size);
        opacity.setOnProgressChangedListener(size);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tool.undo();
            }
        });
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tool.redo();
            }
        });
        colorSelectionSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
                colorValue = color;
                tool.brushColor(color);
            }
        });
        eraserSize.setOnProgressChangedListener(size);
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tool.setEraserSize(eraserSizeValue);
                eraser.setTextColor(getResources().getColor(R.color.blue_bright));
                brush.setTextColor(getResources().getColor(R.color.white));
                brushLayout.setVisibility(View.GONE);
                eraserLayout.setVisibility(View.VISIBLE);
            }
        });
        brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tool.brushColor(colorValue);
                tool.brushSize(brushSizeValue);
                tool.brushOpacity(opacityValue);
                brush.setTextColor(getResources().getColor(R.color.blue_bright));
                eraser.setTextColor(getResources().getColor(R.color.white));
                eraserLayout.setVisibility(View.GONE);
                brushLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        tool = (draw) context;
    }

    interface draw {
        void brushSize(float size);

        void brushColor(int color);

        void brushOpacity(int opacity);

        void setEraserSize(int size);

        void undo();

        void redo();
    }

    private class seekBarChangeListener implements BubbleSeekBar.OnProgressChangedListener {

        @Override
        public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
            if (fromUser) {
                if (bubbleSeekBar.getId() == R.id.seekBarBrushSize)
                {
                    brushSizeValue = progressFloat * 5;
                    tool.brushSize(progressFloat * 5);
                } else if (bubbleSeekBar.getId() == R.id.seekBarOpacity)
                {
                    opacityValue = progress * 10;
                    tool.brushOpacity(progress * 10);
                }
                else if (bubbleSeekBar.getId() == R.id.seekBarEraser)
                {
                    eraserSizeValue = progress * 10;
                    tool.setEraserSize(progress * 10);
                }
            }
        }

        @Override
        public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

        }

        @Override
        public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

        }
    }
}
