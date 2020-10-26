package com.afshan.android.photolab;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.rtugeek.android.colorseekbar.ColorSeekBar;

public class TextFragment extends Fragment
{
    private EditText editText;
    private TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14;
    private ColorSeekBar colorSeekBar;
    private Typeface typeface;
    private int color;
    private View mView;
    ImageView okay;
    private ImageView close, done;
    private TextInterface textInterface;
    private AlertDialog alertDialog = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_text, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView addText = view.findViewById(R.id.add_text);
        typeface = ResourcesCompat.getFont(getActivity(), R.font.assistant_extralight);
        editText = view.findViewById(R.id.edit_text);
        mView = view;
        okay = view.findViewById(R.id.done_trial);
        close = view.findViewById(R.id.close_edit_text);
        done = view.findViewById(R.id.done_edit_text);
        t1 = view.findViewById(R.id.t1);
        t2 = view.findViewById(R.id.t2);
        t3 = view.findViewById(R.id.t3);
        t4 = view.findViewById(R.id.t4);
        t5 = view.findViewById(R.id.t5);
        t6 = view.findViewById(R.id.t6);
        t7 = view.findViewById(R.id.t7);
        t8 = view.findViewById(R.id.t8);
        t9 = view.findViewById(R.id.t9);
        t10 = view.findViewById(R.id.t10);
        t11 = view.findViewById(R.id.t11);
        t12 = view.findViewById(R.id.t12);
        t13 = view.findViewById(R.id.t13);
        t14 = view.findViewById(R.id.t14);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textInterface.okay();
            }
        });

        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog(false);
            }
        });

    }
    public void setAlertDialog(final boolean isRequested)
    {
        Rect rectangle = new Rect();
        Window window = null;

        try {

            window = getActivity().getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        } catch (NullPointerException n) {
            // Handle Exception
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.compareAlertDialogue);


        ViewGroup viewGroup = mView.findViewById(android.R.id.content);
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.edit_text_layout, viewGroup, false);
        editText = view1.findViewById(R.id.edit_text);
        close = view1.findViewById(R.id.close_edit_text);
        done = view1.findViewById(R.id.done_edit_text);
        t1 = view1.findViewById(R.id.t1);
        t2 = view1.findViewById(R.id.t2);
        t3 = view1.findViewById(R.id.t3);
        t4 = view1.findViewById(R.id.t4);
        t5 = view1.findViewById(R.id.t5);
        t6 = view1.findViewById(R.id.t6);
        t7 = view1.findViewById(R.id.t7);
        t8 = view1.findViewById(R.id.t8);
        t9 = view1.findViewById(R.id.t9);
        t10 = view1.findViewById(R.id.t10);
        t11 = view1.findViewById(R.id.t11);
        t12 = view1.findViewById(R.id.t12);
        t13 = view1.findViewById(R.id.t13);
        t14 = view1.findViewById(R.id.t14);
        colorSeekBar = view1.findViewById(R.id.color_bar);
        //color = view1.findViewById(R.id.add_color);
        FontOnClickListener fontOnClickListener = new FontOnClickListener();
        t1.setOnClickListener(fontOnClickListener);
        t2.setOnClickListener(fontOnClickListener);
        t3.setOnClickListener(fontOnClickListener);
        t4.setOnClickListener(fontOnClickListener);
        t5.setOnClickListener(fontOnClickListener);
        t6.setOnClickListener(fontOnClickListener);
        t7.setOnClickListener(fontOnClickListener);
        t8.setOnClickListener(fontOnClickListener);
        t9.setOnClickListener(fontOnClickListener);
        t10.setOnClickListener(fontOnClickListener);
        t11.setOnClickListener(fontOnClickListener);
        t12.setOnClickListener(fontOnClickListener);
        t13.setOnClickListener(fontOnClickListener);
        t14.setOnClickListener(fontOnClickListener);
        colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int mColor)
            {
                color = mColor;
                editText.setTextColor(color);
            }
        });


        view1.setMinimumHeight((int) (rectangle.height() * 0.3f));
        view1.setMinimumWidth((int) (rectangle.width() * 0.3f));

        builder.setView(view1);

        alertDialog = builder.create();
        alertDialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                textInterface.addText(color, editText.getText().toString(), typeface, isRequested);
                alertDialog.dismiss();

            }
        });
    }
    class FontOnClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View view)
        {
          if (view.getId() == R.id.t1)
          {
              typeface = ResourcesCompat.getFont(getActivity(), R.font.amatica_sc);
              editText.setTypeface(typeface);
          }
            if (view.getId() == R.id.t2)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.alice);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t3)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.calligraffitti);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t4)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.allan);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t5)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.basic);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t6)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.bangers);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t7)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.anonymous_pro);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t8)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.alex_brush);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t9)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.arima_madurai_thin);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t10)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.belleza);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t11)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.electrolize);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t12)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.trochut);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t13)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.bungee_shade);
                editText.setTypeface(typeface);
            }
            if (view.getId() == R.id.t14)
            {
                typeface = ResourcesCompat.getFont(getActivity(), R.font.limelight);
                editText.setTypeface(typeface);
            }

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        textInterface = (TextInterface) context;
    }
    interface  TextInterface
    {
        public void addText(int color, String Text, Typeface typeface, boolean isRequested);
        public void okay();
    }
    public void requestNewText()
    {
        setAlertDialog(true);
    }


}
