package com.afshan.android.photolab;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;

import java.util.ArrayList;

public class FilterFragment extends Fragment {
    public static ArrayList<MenuFilter> filters;
    private static Bitmap image;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    int position;
    private ImageView imageView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filters, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addData();
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(getActivity(), filters);
        adapter.notifyDataSetChanged();
        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    void addData() {
        filters = new ArrayList<>();
        Filter f = new Filter();
        f.addSubFilter(new SaturationSubFilter(0.7f));
        filters.add(new MenuFilter(f, "a"));
        filters.add(new MenuFilter(f, "a"));
        filters.add(new MenuFilter(f, "a"));
        filters.add(new MenuFilter(f, "a"));
        filters.add(new MenuFilter(f, "a"));
        filters.add(new MenuFilter(f, "a"));
        filters.add(new MenuFilter(f, "a"));
        filters.add(new MenuFilter(f, "a"));
        filters.add(new MenuFilter(f, "a"));

    }
}