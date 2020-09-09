package com.afshan.android.photolab;

import com.zomato.photofilters.imageprocessors.Filter;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup;

public class MenuFilter {
    private Filter filter;
    private String filterName;
    private GPUImageFilterGroup gpuImageFiltergroup = null;
    private GPUImageFilter gpuImageFilter = null;
    MenuFilter(Filter filter, String filterName) {
        this.filter = filter;
        this.filterName = filterName;
    }

    MenuFilter(GPUImageFilterGroup gpuImageFilter, String filterName) {
        this.gpuImageFiltergroup = gpuImageFilter;
        this.filterName = filterName;
    }

    MenuFilter(GPUImageFilter gpuImageFilter, String filterName) {
        this.gpuImageFilter = gpuImageFilter;
        this.filterName = filterName;
    }

    public GPUImageFilter getGpuImageFilter() {
        return gpuImageFilter;
    }

    public GPUImageFilter getGpuImageFilterGroup() {
        return gpuImageFiltergroup;
    }

    Filter getFilter() {
        return filter;
    }
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    String getFilterName() {
        return filterName;
    }
}
