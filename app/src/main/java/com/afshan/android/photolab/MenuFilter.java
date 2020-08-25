package com.afshan.android.photolab;

import com.zomato.photofilters.imageprocessors.Filter;

public class MenuFilter {
    private Filter filter;
    private String filterName;

    MenuFilter(Filter filter, String filterName) {
        this.filter = filter;
        this.filterName = filterName;
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

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
}
