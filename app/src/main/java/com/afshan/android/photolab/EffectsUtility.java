package com.afshan.android.photolab;

import android.graphics.Bitmap;

public class EffectsUtility {
    static float satVal = 10;
    static float brightVal = 10;
    static float contrastVal = 300;
    static float sharpVal = 30;
    static float ShadowVal = 0;
    static float highlightVal = 0;
    static float exposureVal = 1000;
    static int hueVal = 0;
    static Bitmap finalBitmap = PhotoLab.bitmaps.get(0).copy(Bitmap.Config.ARGB_8888, true);
}
