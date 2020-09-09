package com.afshan.android.photolab;

import android.content.Context;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.SubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

import java.util.ArrayList;

public class Filters {
    Filter Saturation = new Filter();
    Filter Sunlight = new Filter();
    Filter Beach = new Filter();
    Filter LitRoom = new Filter();
    Filter DarkMoon = new Filter();
    Filter Quiet = new Filter();
    Filter Bright = new Filter();
    Filter Cool = new Filter();
    Filter BrightMoon = new Filter();
    Filter Light = new Filter();
    Filter Fresh = new Filter();
    Filter ButterGrey = new Filter();
    Filter Charcoal = new Filter();
    Filter MidNight = new Filter();
    Filter BlackFire = new Filter();
    Filter DarkMoon2 = new Filter();
    Filter White = new Filter();
    Filter DarkMoon3 = new Filter();
    Filter Shades50 = new Filter();


    Filters(Context context) {
        // saturation filter
        Saturation.addSubFilter(new SaturationSubFilter(1.1f));

        // sunlight filter
        ArrayList<SubFilter> subFilterArrayList0 = new ArrayList<>();
        subFilterArrayList0.add(new ContrastSubFilter(1.3f));
        subFilterArrayList0.add(new BrightnessSubFilter(25));
        Sunlight.addSubFilters(subFilterArrayList0);

        // Beach filter
        ArrayList<SubFilter> subFilterArrayList1 = new ArrayList<>();
        subFilterArrayList1.add(new ContrastSubFilter(1.3f));
        subFilterArrayList1.add(new VignetteSubFilter(context, 18));
        subFilterArrayList1.add(new BrightnessSubFilter(16));
        Beach.addSubFilters(subFilterArrayList1);

        // lit Room Filter
        ArrayList<SubFilter> subFilterArrayList2 = new ArrayList<>();
        subFilterArrayList2.add(new ContrastSubFilter(1.1f));
        subFilterArrayList2.add(new BrightnessSubFilter(15));
        subFilterArrayList2.add(new ColorOverlaySubFilter(3, 3.4f, 3.5f, 0.5f));
        LitRoom.addSubFilters(subFilterArrayList2);

        // Dark Room Filter
        ArrayList<SubFilter> subFilterArrayList3 = new ArrayList<>();
        subFilterArrayList3.add(new ContrastSubFilter(1.5f));
        subFilterArrayList3.add(new BrightnessSubFilter(15));
        subFilterArrayList3.add(new VignetteSubFilter(context, 6));
        subFilterArrayList3.add(new ColorOverlaySubFilter(2, 1.1f, 1.1f, 1.1f));
        DarkMoon.addSubFilters(subFilterArrayList3);

        // Quiet Filter
        ArrayList<SubFilter> subFilterArrayList4 = new ArrayList<>();
        subFilterArrayList4.add(new ContrastSubFilter(1.1f));
        subFilterArrayList4.add(new BrightnessSubFilter(35));
        Quiet.addSubFilters(subFilterArrayList4);


        // Bright Filter
        ArrayList<SubFilter> subFilterArrayList5 = new ArrayList<>();
        subFilterArrayList5.add(new BrightnessSubFilter(25));
        subFilterArrayList5.add(new SaturationSubFilter(1.1f));
        subFilterArrayList5.add(new ColorOverlaySubFilter(2, 4, 1, 8f));
        Bright.addSubFilters(subFilterArrayList5);

        // Cool Filter
        ArrayList<SubFilter> subFilterArrayList6 = new ArrayList<>();
        subFilterArrayList6.add(new BrightnessSubFilter(35));
        subFilterArrayList6.add(new VignetteSubFilter(context, 12));
        subFilterArrayList6.add(new ColorOverlaySubFilter(2, 8, 8, 1f));
        Cool.addSubFilters(subFilterArrayList6);

        // BrightMoon Filter
        ArrayList<SubFilter> subFilterArrayList7 = new ArrayList<>();
        subFilterArrayList7.add(new BrightnessSubFilter(35));
        subFilterArrayList7.add(new ContrastSubFilter(1.1f));
        subFilterArrayList7.add(new VignetteSubFilter(context, 12));
        subFilterArrayList7.add(new ColorOverlaySubFilter(3, 3, 8, 8f));
        BrightMoon.addSubFilters(subFilterArrayList7);

        //Light Filter
        ArrayList<SubFilter> subFilterArrayList8 = new ArrayList<>();
        subFilterArrayList8.add(new BrightnessSubFilter(60));
        subFilterArrayList8.add(new VignetteSubFilter(context, 30));
        Light.addSubFilters(subFilterArrayList8);

        //Fresh Filters
        ArrayList<SubFilter> subFilterArrayList9 = new ArrayList<>();
        subFilterArrayList9.add(new BrightnessSubFilter(25));
        subFilterArrayList9.add(new ColorOverlaySubFilter(2, 4, 4, 12f));
        Fresh.addSubFilters(subFilterArrayList9);

        //ButterGrey Filter
        ArrayList<SubFilter> subFilterArrayList10 = new ArrayList<>();
        subFilterArrayList10.add(new ContrastSubFilter(1.3f));
        subFilterArrayList10.add(new BrightnessSubFilter(10));
        subFilterArrayList10.add(new SaturationSubFilter(0.3f));
        ButterGrey.addSubFilters(subFilterArrayList10);

        //Charcoal Filter
        ArrayList<SubFilter> subFilterArrayList11 = new ArrayList<>();
        subFilterArrayList11.add(new BrightnessSubFilter(15));
        subFilterArrayList11.add(new SaturationSubFilter(0.0f));
        Charcoal.addSubFilters(subFilterArrayList11);

        //Midnight Filter
        ArrayList<SubFilter> subFilterArrayList12 = new ArrayList<>();
        subFilterArrayList12.add(new BrightnessSubFilter(35));
        subFilterArrayList12.add(new ColorOverlaySubFilter(100, 0.1f, 0.1f, 0.1f));
        subFilterArrayList12.add(new SaturationSubFilter(-0.3f));
        MidNight.addSubFilters(subFilterArrayList12);


        // Black Fire Filter
        ArrayList<SubFilter> subFilterArrayList13 = new ArrayList<>();
        subFilterArrayList13.add(new ContrastSubFilter(1.5f));
        subFilterArrayList13.add(new SaturationSubFilter(0.0f));
        BlackFire.addSubFilters(subFilterArrayList13);

        //Dark moon 2 filter
        ArrayList<SubFilter> subFilterArrayList14 = new ArrayList<>();
        subFilterArrayList14.add(new BrightnessSubFilter(-3));
        subFilterArrayList14.add(new SaturationSubFilter(0.0f));
        DarkMoon2.addSubFilters(subFilterArrayList14);


        // White Filter
        ArrayList<SubFilter> subFilterArrayList15 = new ArrayList<>();
        subFilterArrayList15.add(new BrightnessSubFilter(60));
        subFilterArrayList15.add(new ContrastSubFilter(1.1f));
        subFilterArrayList15.add(new SaturationSubFilter(0.0f));
        White.addSubFilters(subFilterArrayList15);

        //Dark Moon 3 filter
        ArrayList<SubFilter> subFilterArrayList16 = new ArrayList<>();
        subFilterArrayList16.add(new BrightnessSubFilter(-6));
        subFilterArrayList16.add(new ContrastSubFilter(1.4f));
        subFilterArrayList16.add(new SaturationSubFilter(0.0f));
        DarkMoon3.addSubFilters(subFilterArrayList16);

        // Fifty shades filter
        ArrayList<SubFilter> subFilterArrayList17 = new ArrayList<>();
        subFilterArrayList17.add(new BrightnessSubFilter(30));
        subFilterArrayList17.add(new ContrastSubFilter(1.4f));
        subFilterArrayList17.add(new SaturationSubFilter(0.0f));
        Shades50.addSubFilters(subFilterArrayList17);


    }

}
