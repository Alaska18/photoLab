package com.afshan.android.photolab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {
    private ArrayList<MenuFilter> filters;
    private ImageView imageView;
    private TextView textView;
    private ViewGroup viewGroup;
    private View filterTemplate;
    private Context context;
    private ProgressBar progressBar;

    MyAdapter(Context context, ArrayList<MenuFilter> filters) {

        this.context = context;
        this.filters = filters;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewGroup = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_template, parent, false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        //progressBar.setVisibility(View.VISIBLE);
        if (filters.get(position).getGpuImageFilterGroup() == null && filters.get(position).getGpuImageFilter() == null) {
            LoadFilter loadFilter = new LoadFilter(filters.get(position).getFilter(), filters.get(position).getFilterName(), filters, imageView, progressBar);
            loadFilter.execute();
        } else {
            LoadFilter loadFilter = new LoadFilter(filters, imageView, progressBar, context, position);
            loadFilter.execute();
        }
        final FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        final FilterFragment filterFragment = (FilterFragment) fragmentManager.findFragmentById(R.id.fragment_frame);


        filterFragment.setTextColor(position);
        textView.setText(filters.get(position).getFilterName());
        filterTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoFragment photoFragment = (PhotoFragment) fragmentManager.findFragmentById(R.id.fragmentImage);
                assert photoFragment != null;
                if (filters.get(position).getGpuImageFilter() == null && filters.get(position).getGpuImageFilterGroup() == null) {
                    photoFragment.setFilter(filters.get(position).getFilter());
                } else if (filters.get(position).getGpuImageFilterGroup() != null) {
                    photoFragment.setGPUImageFilterGroup(filters.get(position).getGpuImageFilterGroup());
                } else {
                    photoFragment.setGPUImageFilter(filters.get(position).getGpuImageFilter());
                }
                filterFragment.setName(filters.get(position).getFilterName());
            }
        });

    }

    @Override
    public int getItemCount() {

        return filters.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public class MyView extends RecyclerView.ViewHolder {
        MyView(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menu_image);
            textView = itemView.findViewById(R.id.menu_text);
            progressBar = itemView.findViewById(R.id.progressBarT);
            filterTemplate = itemView.findViewById(R.id.filter_template);
        }
    }
}
