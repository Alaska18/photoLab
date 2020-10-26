package com.afshan.android.photolab;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xw.repo.BubbleSeekBar;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter;

public class EffectsFragment extends Fragment {
    private View saturation;
    private View effects_seekBar;
    private View contrast;
    private View brightness;
    private View sharpness;
    private View shadows;
    private View highlights;
    private View exposure;
    private View horizontalMenu;
    private Effects effects;
    private View okay;
    private View compare;
    private View mView;
    private ImageView close, compareFinal;
    private View utility;
    private GPUImageFilterGroup gpuImageFilterGroup = new GPUImageFilterGroup();
    private GPUImageSaturationFilter gpuImageSaturationFilter;
    private GPUImageContrastFilter gpuImageContrastFilter;
    private GPUImageSharpenFilter gpuImageSharpenFilter;
    private GPUImageHighlightShadowFilter gpuImageHighlightShadowFilter;
    private GPUImageExposureFilter gpuImageExposureFilter;
    private GPUImageBrightnessFilter gpuImageBrightnessFilter;
    private GPUImage gpuImage;
    private BubbleSeekBar sat, bright, cont, shadow, sharp, highlight, expose;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_effects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saturation = view.findViewById(R.id.saturation);
        contrast = view.findViewById(R.id.contrast);
        brightness = view.findViewById(R.id.brightness);
        horizontalMenu = view.findViewById(R.id.horizontal_effects_menu);
        effects_seekBar = view.findViewById(R.id.seekbar_of_effects);
        sat = view.findViewById(R.id.seekBarSaturation);
        cont = view.findViewById(R.id.seekBarContrast);
        bright = view.findViewById(R.id.seekBarBrightness);
        shadow = view.findViewById(R.id.seekBarShadows);
        sharp = view.findViewById(R.id.seekBarSharpness);
        highlight = view.findViewById(R.id.seekBarHighlights);
        expose = view.findViewById(R.id.seekBarExposure);
        sharpness = view.findViewById(R.id.sharpness);
        shadows = view.findViewById(R.id.shadows);
        highlights = view.findViewById(R.id.highlight);
        exposure = view.findViewById(R.id.exposure);
        utility = view.findViewById(R.id.utility_effects);
        close = view.findViewById(R.id.close_effects);
        compareFinal = view.findViewById(R.id.compare_effects_final);
        compare = view.findViewById(R.id.compare_effects);
        okay = view.findViewById(R.id.okay);
        gpuImage = new GPUImage(getContext());
        gpuImageSaturationFilter = new GPUImageSaturationFilter(1.0f);
        gpuImageContrastFilter = new GPUImageContrastFilter(1.0f);
        gpuImageBrightnessFilter = new GPUImageBrightnessFilter(0.0f);
        gpuImageSharpenFilter = new GPUImageSharpenFilter(0.0f);
        gpuImageExposureFilter = new GPUImageExposureFilter(0.0f);
        gpuImageHighlightShadowFilter = new GPUImageHighlightShadowFilter(0.0f, 1.0f);
        gpuImageFilterGroup.addFilter(gpuImageSaturationFilter);
        gpuImageFilterGroup.addFilter(gpuImageBrightnessFilter);
        gpuImageFilterGroup.addFilter(gpuImageContrastFilter);
        gpuImageFilterGroup.addFilter(gpuImageSharpenFilter);
        gpuImageFilterGroup.addFilter(gpuImageExposureFilter);
        gpuImageFilterGroup.addFilter(gpuImageHighlightShadowFilter);
        gpuImageFilterGroup.addFilter(gpuImageHighlightShadowFilter);
        mView = view;

    }

    @Override
    public void onResume() {
        super.onResume();
        class  SeekBarListener implements BubbleSeekBar.OnProgressChangedListener {
            private View v;

            public void setView(View v) {
                this.v = v;
            }

            @Override
            public void onProgressChanged(BubbleSeekBar seekBar, int a, float i, boolean b) {
                if (v.getId() == R.id.saturation) {
                    EffectsUtility.satVal = i;
                    gpuImageSaturationFilter.setSaturation(i / 10.0f);
                    setFilter(0, gpuImageSaturationFilter);
                }
                if (v.getId() == R.id.brightness) {
                    EffectsUtility.brightVal = i;
                    gpuImageBrightnessFilter.setBrightness((i - 10.0f) / 10.0f);
                    setFilter(1, gpuImageBrightnessFilter);

                }
                if (v.getId() == R.id.contrast) {
                    EffectsUtility.contrastVal = i;
                    gpuImageContrastFilter.setContrast(i / 300.0f);
                    setFilter(2, gpuImageContrastFilter);
                }
                if (v.getId() == R.id.sharpness) {
                    EffectsUtility.sharpVal = i;
                    gpuImageSharpenFilter.setSharpness((i - 30.0f) / 10.0f);
                    setFilter(3, gpuImageSharpenFilter);
                }
                if (v.getId() == R.id.shadows) {
                    EffectsUtility.ShadowVal = i;
                    gpuImageHighlightShadowFilter.setShadows(i / 100.0f);
                    setFilter(6, gpuImageHighlightShadowFilter);

                }
                if (v.getId() == R.id.highlight) {
                    EffectsUtility.highlightVal = i;
                    gpuImageHighlightShadowFilter.setHighlights((i) / 10.0f);
                    setFilter(5, gpuImageHighlightShadowFilter);

                }
                if (v.getId() == R.id.exposure) {
                    EffectsUtility.exposureVal = i;
                    gpuImageExposureFilter.setExposure((i - 1000.0f) / 100.0f);
                    setFilter(4, gpuImageExposureFilter);

                }
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }


        }
        final SeekBarListener seekBarListener = new SeekBarListener();
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
              effects_seekBar.setVisibility(View.GONE);
              horizontalMenu.setVisibility(View.VISIBLE);
              utility.setVisibility(View.VISIBLE);
            }
        });
        class EffectsOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                effects_seekBar.setVisibility(View.VISIBLE);
                horizontalMenu.setVisibility(View.GONE);

                seekBarListener.setView(view);

                // setting seek bar position and progress
                setSeekBarPosition(view);

                utility.setVisibility(View.GONE);

                setSeekBar(view);

            }
        };
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view)
            {
                /**
                 * Setting alert dialogue for comparing Images
                 */
                Rect rectangle = new Rect();
                Window window = null;
                try {

                    window = getActivity().getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
                } catch (NullPointerException n) {
                    // Nothing for now
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
        sat.setOnProgressChangedListener(seekBarListener);
        cont.setOnProgressChangedListener(seekBarListener);
        bright.setOnProgressChangedListener(seekBarListener);
        expose.setOnProgressChangedListener(seekBarListener);
        highlight.setOnProgressChangedListener(seekBarListener);
        shadow.setOnProgressChangedListener(seekBarListener);
        sharp.setOnProgressChangedListener(seekBarListener);
        EffectsOnClickListener effectsOnClickListener = new EffectsOnClickListener();
        saturation.setOnClickListener(effectsOnClickListener);
        contrast.setOnClickListener(effectsOnClickListener);
        brightness.setOnClickListener(effectsOnClickListener);
        sharpness.setOnClickListener(effectsOnClickListener);
        shadows.setOnClickListener(effectsOnClickListener);
        highlights.setOnClickListener(effectsOnClickListener);
        exposure.setOnClickListener(effectsOnClickListener);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        effects = (Effects) context;
    }

    private void setSeekBarPosition(View view) {
        if (view.getId() == R.id.saturation) {
            sat.setProgress(EffectsUtility.satVal);
            TextView heading = effects_seekBar.findViewById(R.id.effects_heading);
            heading.setText("Saturation");
        }
        if (view.getId() == R.id.brightness) {
            bright.setProgress(EffectsUtility.brightVal);
            TextView heading = effects_seekBar.findViewById(R.id.effects_heading);
            heading.setText("Brightness");
        }
        if (view.getId() == R.id.contrast) {
            cont.setProgress(EffectsUtility.contrastVal);
            TextView heading = effects_seekBar.findViewById(R.id.effects_heading);
            heading.setText("Contrast");
        }
        if (view.getId() == R.id.sharpness) {
            TextView heading = effects_seekBar.findViewById(R.id.effects_heading);
            heading.setText("Sharpness");
            sharp.setProgress(EffectsUtility.sharpVal);
        }
        if (view.getId() == R.id.shadows) {
            TextView heading = effects_seekBar.findViewById(R.id.effects_heading);
            heading.setText("Shadows");
            shadow.setProgress(EffectsUtility.ShadowVal);
        }
        if (view.getId() == R.id.highlight) {
            TextView heading = effects_seekBar.findViewById(R.id.effects_heading);
            heading.setText("Highlights");
            highlight.setProgress(EffectsUtility.highlightVal);
        }
        if (view.getId() == R.id.exposure) {
            TextView heading = effects_seekBar.findViewById(R.id.effects_heading);
            heading.setText("Exposure");
            expose.setProgress(EffectsUtility.exposureVal);
        }
    }

    private void setSeekBar(View v) {
        if (v.getId() == R.id.saturation) {
            sat.setVisibility(View.VISIBLE);
            setVisibilityForViews(cont, bright, highlight, shadow, sharp, expose);
        } else if (v.getId() == R.id.contrast) {
            cont.setVisibility(View.VISIBLE);
            setVisibilityForViews(sat, bright, highlight, shadow, sharp, expose);
        } else if (v.getId() == R.id.brightness) {
            bright.setVisibility(View.VISIBLE);
            setVisibilityForViews(cont, sat, highlight, shadow, sharp, expose);
        } else if (v.getId() == R.id.highlight) {
            highlight.setVisibility(View.VISIBLE);
            setVisibilityForViews(cont, bright, sat, shadow, sharp, expose);
        } else if (v.getId() == R.id.exposure) {
            expose.setVisibility(View.VISIBLE);
            setVisibilityForViews(cont, bright, highlight, shadow, sharp, sat);
        } else if (v.getId() == R.id.shadows) {

            shadow.setVisibility(View.VISIBLE);
            setVisibilityForViews(cont, bright, highlight, sat, sharp, expose);
        } else if (v.getId() == R.id.sharpness) {
            sharp.setVisibility(View.VISIBLE);
            setVisibilityForViews(cont, bright, highlight, shadow, sat, expose);
        }
    }

    private void setVisibilityForViews(BubbleSeekBar v1, BubbleSeekBar v2, BubbleSeekBar v3, BubbleSeekBar v4, BubbleSeekBar v5, BubbleSeekBar v6) {
        v1.setVisibility(View.GONE);
        v2.setVisibility(View.GONE);
        v3.setVisibility(View.GONE);
        v4.setVisibility(View.GONE);
        v5.setVisibility(View.GONE);
        v6.setVisibility(View.GONE);
    }

    private void setFilter(int index, GPUImageFilter gpuImageFilter) {
        gpuImage.deleteImage();
        gpuImage.setImage(PhotoLab.bitmaps.get(0));
        gpuImageFilterGroup.getFilters().remove(index);
        gpuImageFilterGroup.getFilters().add(index, gpuImageFilter);
        gpuImageFilterGroup.updateMergedFilters();
        gpuImage.setFilter(gpuImageFilterGroup);
        effects.setImage(gpuImage.getBitmapWithFilterApplied());
    }


    interface Effects {

        public void setImage(Bitmap b);

    }
}
