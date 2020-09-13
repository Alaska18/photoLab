package com.afshan.android.photolab;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zomato.photofilters.SampleFilters;

import java.util.ArrayList;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageRGBDilationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSepiaToneFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageWhiteBalanceFilter;

public class FilterFragment extends Fragment {
    static RecyclerView recyclerView;
    static RecyclerView.LayoutManager layoutManager;
    static ArrayList<MenuFilter> filters;
    public View view;
    private TextView textView;
    private TextView pro;
    private TextView BandW;
    private TextView special;
    private TextView special2;
    private TextView premium;
    private Image mImage;
    private View mView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filters, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addData();
        mImage.addOnFlingListener();
        {
            /**
             * Views Initialisation
             */
            mView = view;
            recyclerView = view.findViewById(R.id.recyclerView);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            MyAdapter adapter = new MyAdapter(getActivity(), filters);
            textView = view.findViewById(R.id.type);
            textView.setTextColor(getResources().getColor(R.color.blue_bright));
            TextPaint paint = textView.getPaint();
            float width = paint.measureText(textView.getText().toString());
            Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(), new int[]{Color.GRAY, Color.GRAY}, null, Shader.TileMode.CLAMP);
            textView.getPaint().setShader(textShader);
            pro = view.findViewById(R.id.pro);
            BandW = view.findViewById(R.id.black_and_white);
            special = view.findViewById(R.id.special);
            special2 = view.findViewById(R.id.vignette);
            premium = view.findViewById(R.id.monochrome);
            ImageView compare = view.findViewById(R.id.compare);
            ImageView removeFilter = view.findViewById(R.id.remove_filter);


            /**
             * On click listener to compare two images
             */

            compare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                    View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.compare_alert, viewGroup, false);

                    ImageView original = dialogView.findViewById(R.id.original);
                    original.setImageBitmap(PhotoLab.bitmaps.get(0));

                    ImageView edited = dialogView.findViewById(R.id.edited);
                    edited.setImageBitmap(PhotoFragment.buffer);

                    dialogView.setMinimumHeight((int) (rectangle.height() * 0.3f));
                    dialogView.setMinimumWidth((int) (rectangle.width() * 0.3f));

                    builder.setView(dialogView);

                    final AlertDialog alertDialog = builder.create();
                    ImageView back = dialogView.findViewById(R.id.backButton);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            });
            removeFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mImage.setOriginalImage();
                }
            });
            pro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerView.smoothScrollToPosition(0);
                    pro.setBackgroundColor(getResources().getColor(R.color.blue_bright));
                    BandW.setBackgroundColor(getResources().getColor(R.color.white));
                    special.setBackgroundColor(getResources().getColor(R.color.white));
                    special2.setBackgroundColor(getResources().getColor(R.color.white));
                    premium.setBackgroundColor(getResources().getColor(R.color.white));
                }
            });
            BandW.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerView.smoothScrollToPosition(19);
                    pro.setBackgroundColor(getResources().getColor(R.color.white));
                    BandW.setBackgroundColor(getResources().getColor(R.color.blue_bright));
                    special.setBackgroundColor(getResources().getColor(R.color.white));
                    special2.setBackgroundColor(getResources().getColor(R.color.white));
                    premium.setBackgroundColor(getResources().getColor(R.color.white));
                }
            });
            adapter.notifyDataSetChanged();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

        }
    }

    public void addData() {
        filters = new ArrayList<>();
        Filters mFilters = new Filters(getContext());
        filters.add(new MenuFilter(mFilters.Saturation, "Little"));
        filters.add(new MenuFilter(mFilters.Sunlight, "Sunlight"));
        filters.add(new MenuFilter(mFilters.Beach, "Beach"));
        filters.add(new MenuFilter(mFilters.LitRoom, "LitRoom"));
        filters.add(new MenuFilter(mFilters.DarkMoon, "DarkMoon"));
        filters.add(new MenuFilter(mFilters.Quiet, "Quiet"));
        filters.add(new MenuFilter(mFilters.Bright, "Bright"));
        filters.add(new MenuFilter(mFilters.Cool, "Cool"));
        filters.add(new MenuFilter(mFilters.BrightMoon, "BrightMoon"));
        filters.add(new MenuFilter(mFilters.Light, "Light"));
        filters.add(new MenuFilter(mFilters.Fresh, "Fresh"));
        filters.add(new MenuFilter(SampleFilters.getStarLitFilter(), "Starry"));
        filters.add(new MenuFilter(SampleFilters.getAweStruckVibeFilter(), "Vibe"));
        filters.add(new MenuFilter(SampleFilters.getNightWhisperFilter(), "Poetic"));
        filters.add(new MenuFilter(SampleFilters.getLimeStutterFilter(), "Lime"));
        filters.add(new MenuFilter(SampleFilters.getBlueMessFilter(), "Blue"));
        filters.add(new MenuFilter(mFilters.ButterGrey, "Butter Grey"));
        filters.add(new MenuFilter(mFilters.Charcoal, "Charcoal"));
        filters.add(new MenuFilter(mFilters.MidNight, "MidNight"));
        filters.add(new MenuFilter(mFilters.BlackFire, "Black Fire"));
        filters.add(new MenuFilter(mFilters.DarkMoon2, "Dark Moon 2"));
        filters.add(new MenuFilter(mFilters.White, "White"));
        filters.add(new MenuFilter(mFilters.DarkMoon3, "Dark Moon 3"));
        filters.add(new MenuFilter(mFilters.Shades50, "50 Shades"));
        filters.add(new MenuFilter(new GPUImageGrayscaleFilter(), "Grayscale"));
        filters.add(new MenuFilter(new GPUImageSketchFilter(), "Sketch"));
        filters.add(new MenuFilter(new GPUImageContrastFilter(1.8f), "Contrast"));
        filters.add(new MenuFilter(new GPUImageBrightnessFilter(0.3f), "Brightness"));
        filters.add(new MenuFilter(new GPUImageExposureFilter(), "Exposure"));
        filters.add(new MenuFilter(new GPUImageRGBFilter(4, 3, 2), "RGB"));
        filters.add(new MenuFilter(new GPUImageRGBDilationFilter(25), "RGB+"));
        filters.add(new MenuFilter(new GPUImageHueFilter(), "Hue"));
        filters.add(new MenuFilter(new GPUImageHueFilter(180.0f), "Hue+"));
        filters.add(new MenuFilter(new GPUImageWhiteBalanceFilter(6000.0f, 45), "White Balance"));
        filters.add(new MenuFilter(new GPUImageSharpenFilter(3.0f), "Sharpen"));
        filters.add(new MenuFilter(new GPUImageTransformFilter(), "Transform"));
        filters.add(new MenuFilter(new GPUImageGammaFilter(0.5f), "Gamma"));
        filters.add(new MenuFilter(new GPUImageHighlightShadowFilter(0.8f, 0.2f), "Shadow"));
        filters.add(new MenuFilter(new GPUImageHazeFilter(), "Haze"));
        filters.add(new MenuFilter(new GPUImageSepiaToneFilter(), "Sepia"));
        filters.add(new MenuFilter(new GPUImageMonochromeFilter(), "Cookie"));
        filters.add(new MenuFilter(new GPUImageFalseColorFilter(0.0f, 0.8f, 0.0f, 0.5f, 0, 0), "Premium 1"));
        filters.add(new MenuFilter(new GPUImageFalseColorFilter(0.8f, 0.0f, 0.0f, 0.0f, 0, 0.5f), "Premium 2"));
        filters.add(new MenuFilter(new GPUImageFalseColorFilter(0.8f, 0.0f, 0.0f, 0.0f, 0.1f, 0.0f), "Premium 3"));
        filters.add(new MenuFilter(new GPUImageFalseColorFilter(0.0f, 0.8f, 0.0f, 0.0f, 0, 0.5f), "Premium 4"));
        filters.add(new MenuFilter(new GPUImageFalseColorFilter(0.0f, 0.0f, 1f, 0.0f, 0, 0.0f), "Premium 5"));
        filters.add(new MenuFilter(new GPUImageFalseColorFilter(0.5f, 0.0f, 0.0f, 1f, 0, 0.0f), "Premium 6"));
        filters.add(new MenuFilter(new GPUImageFalseColorFilter(0.5f, 0.0f, 0.0f, 0.0f, 1f, 0.0f), "Premium 7"));
        filters.add(new MenuFilter(new GPUImageFalseColorFilter(0.0f, 0.0f, 0.0f, 0.0f, 0, 1f), "Premium 8"));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mImage = (Image) context;
    }

    public void setName(String name) {
        textView.setText(name);
        // seekBar.setProgress(50);
    }

    public void setTextColor(int pos) {
        if (pos <= 12) {
            pro.setBackground(getResources().getDrawable(R.drawable.fragment_background));
            BandW.setBackgroundColor(getResources().getColor(R.color.black));
            special.setBackgroundColor(getResources().getColor(R.color.black));
            special2.setBackgroundColor(getResources().getColor(R.color.black));
            premium.setBackgroundColor(getResources().getColor(R.color.black));
        } else if (pos >= 18 && pos <= 19) {
            pro.setBackgroundColor(getResources().getColor(R.color.black));
            BandW.setBackground(getResources().getDrawable(R.drawable.fragment_background));
            special.setBackgroundColor(getResources().getColor(R.color.black));
            special2.setBackgroundColor(getResources().getColor(R.color.black));
            premium.setBackgroundColor(getResources().getColor(R.color.black));
        } else if (pos >= 27 && pos <= 28) {
            pro.setBackgroundColor(getResources().getColor(R.color.black));
            BandW.setBackgroundColor(getResources().getColor(R.color.black));
            special.setBackground(getResources().getDrawable(R.drawable.fragment_background));
            special2.setBackgroundColor(getResources().getColor(R.color.black));
            premium.setBackgroundColor(getResources().getColor(R.color.black));
        } else if (pos >= 29 && pos <= 31) {
            pro.setBackgroundColor(getResources().getColor(R.color.black));
            BandW.setBackgroundColor(getResources().getColor(R.color.black));
            special.setBackgroundColor(getResources().getColor(R.color.black));
            special2.setBackground(getResources().getDrawable(R.drawable.fragment_background));
            premium.setBackgroundColor(getResources().getColor(R.color.black));
        } else if (pos >= 42 && pos <= 43) {
            pro.setBackgroundColor(getResources().getColor(R.color.black));
            BandW.setBackgroundColor(getResources().getColor(R.color.black));
            special.setBackgroundColor(getResources().getColor(R.color.black));
            special2.setBackgroundColor(getResources().getColor(R.color.black));
            premium.setBackground(getResources().getDrawable(R.drawable.fragment_background));
        }
    }

    interface Image {
        void setOriginalImage();

        public void addOnFlingListener();
    }
}