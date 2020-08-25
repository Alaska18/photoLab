package com.afshan.android.photolab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private Context context;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        LoadFilter loadFilter = new LoadFilter(filters.get(position).getFilter(), imageView, context);
        loadFilter.execute();
        textView.setText(filters.get(position).getFilterName());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                PhotoFragment photoFragment = (PhotoFragment) fragmentManager.findFragmentById(R.id.fragmentImage);
                assert photoFragment != null;
                photoFragment.setFilter(filters.get(position).getFilter());
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
        }
    }
}
