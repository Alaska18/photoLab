package com.afshan.android.photolab;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xw.repo.BubbleSeekBar;

public class EffectsFragment extends Fragment {
    private View saturation;
    private View effects_seekBar;
    private View contrast;
    private View brightness;
    private View sharpness;
    private View shadows;
    private View highlights;
    private View hue;
    private View exposure;
    private View rgb;
    private View horizontalMenu;
    private Effects effects;
    private View okay;
    private View compare;
    private View mView;
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
        hue = view.findViewById(R.id.Hue);
        rgb = view.findViewById(R.id.rgb);
        compare = view.findViewById(R.id.compare_effects);
        okay = view.findViewById(R.id.okay);
        mView = view;

    }

    @Override
    public void onResume() {
        super.onResume();
        compare.setOnClickListener(new
        class SeekBarListener implements BubbleSeekBar.OnProgressChangedListener {
            public View v;

            public void setView(View v) {
                this.v = v;
            }

            @Override
            public void onProgressChanged(BubbleSeekBar seekBar, int a, float i, boolean b) {
                if (v.getId() == R.id.saturation) {
                    EffectsUtility.satVal = a;
                    effects.saturationChanged(i / 10.0f);
                }
                if (v.getId() == R.id.brightness) {
                    EffectsUtility.brightVal = a;
                    effects.brightnessChanged((i - 10.0f) / 10.0f);
                }
                if (v.getId() == R.id.contrast) {
                    EffectsUtility.contrastVal = a;
                    effects.contrastChanged(i / 300.0f);
                }
                if (v.getId() == R.id.sharpness) {
                    EffectsUtility.sharpVal = a;
                    effects.sharpnessChanged((i - 30.0f) / 10.0f);
                }
                if (v.getId() == R.id.shadows) {
                    EffectsUtility.ShadowVal = a;
                    effects.shadowsChanged((i) / 100.0f);
                }
                if (v.getId() == R.id.highlight) {
                    EffectsUtility.highlightVal = a;
                    effects.highlightsChanged((100.0f - i) / 100.0f);
                }
                if (v.getId() == R.id.exposure) {
                    EffectsUtility.exposureVal = a;
                    effects.exposureChanged((i - 1000) / 100.0f);
                }
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }


        });
        okay.setOnClickListener(new
        class EffectsOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                effects_seekBar.setVisibility(View.VISIBLE);
                horizontalMenu.setVisibility(View.GONE);

                seekBarListener.setView(view);

                // setting seek bar position and progress
                setSeekBarPosition(null, view);

                setSeekBar(view);

            }
        });
        View.OnClickListener() {
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
                    // Nothinf for now
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
        }
        final SeekBarListener seekBarListener = new SeekBarListener();
        sat.setOnProgressChangedListener(seekBarListener);
        cont.setOnProgressChangedListener(seekBarListener);
        bright.setOnProgressChangedListener(seekBarListener);
        expose.setOnProgressChangedListener(seekBarListener);
        highlight.setOnProgressChangedListener(seekBarListener);
        shadow.setOnProgressChangedListener(seekBarListener);
        sharp.setOnProgressChangedListener(seekBarListener);
        View.OnClickListener() {
            @Override
            public void onClick (View view){
                effects.okayClicked();
                horizontalMenu.setVisibility(View.VISIBLE);
                effects_seekBar.setVisibility(View.GONE);
            }
        }
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

    void setSeekBarPosition(SeekBar seekBar, View view) {
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

    BubbleSeekBar setSeekBar(View v) {
        if (v.getId() == R.id.saturation) {
            sat.setVisibility(View.VISIBLE);
            cont.setVisibility(View.GONE);
            bright.setVisibility(View.GONE);
            highlight.setVisibility(View.GONE);
            expose.setVisibility(View.GONE);
            shadow.setVisibility(View.GONE);
            sharp.setVisibility(View.GONE);
            return sat;
        } else if (v.getId() == R.id.contrast) {
            sat.setVisibility(View.GONE);
            cont.setVisibility(View.VISIBLE);
            bright.setVisibility(View.GONE);
            highlight.setVisibility(View.GONE);
            expose.setVisibility(View.GONE);
            shadow.setVisibility(View.GONE);
            sharp.setVisibility(View.GONE);
            return cont;
        } else if (v.getId() == R.id.brightness) {
            sat.setVisibility(View.GONE);
            cont.setVisibility(View.GONE);
            bright.setVisibility(View.VISIBLE);
            highlight.setVisibility(View.GONE);
            expose.setVisibility(View.GONE);
            shadow.setVisibility(View.GONE);
            sharp.setVisibility(View.GONE);
            return bright;
        } else if (v.getId() == R.id.highlight) {
            sat.setVisibility(View.GONE);
            cont.setVisibility(View.GONE);
            bright.setVisibility(View.GONE);
            highlight.setVisibility(View.VISIBLE);
            expose.setVisibility(View.GONE);
            shadow.setVisibility(View.GONE);
            sharp.setVisibility(View.GONE);
            return highlight;
        } else if (v.getId() == R.id.exposure) {
            sat.setVisibility(View.GONE);
            cont.setVisibility(View.GONE);
            bright.setVisibility(View.GONE);
            highlight.setVisibility(View.GONE);
            expose.setVisibility(View.VISIBLE);
            shadow.setVisibility(View.GONE);
            sharp.setVisibility(View.GONE);
            return expose;
        } else if (v.getId() == R.id.shadows) {
            sat.setVisibility(View.GONE);
            cont.setVisibility(View.GONE);
            bright.setVisibility(View.GONE);
            highlight.setVisibility(View.GONE);
            expose.setVisibility(View.GONE);
            shadow.setVisibility(View.VISIBLE);
            sharp.setVisibility(View.GONE);
            return shadow;
        } else {
            sat.setVisibility(View.GONE);
            cont.setVisibility(View.GONE);
            bright.setVisibility(View.GONE);
            highlight.setVisibility(View.GONE);
            expose.setVisibility(View.GONE);
            shadow.setVisibility(View.GONE);
            sharp.setVisibility(View.VISIBLE);
            return sharp;
        }
    }

    interface Effects {
        public void saturationChanged(float progress);

        public void contrastChanged(float progress);

        public void brightnessChanged(float progress);

        public void sharpnessChanged(float progress);

        public void shadowsChanged(float progress);

        public void highlightsChanged(float progress);

        public void exposureChanged(float progress);

        public void okayClicked();

    }
}
