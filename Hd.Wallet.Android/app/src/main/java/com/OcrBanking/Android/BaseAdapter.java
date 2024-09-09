package com.OcrBanking.Android;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<TModel extends Object> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    protected List<TModel> list = new ArrayList<>();

    public BaseAdapter() {

    }
    public BaseAdapter(List<TModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return getViewHolder(parent, viewType);
    }

    protected abstract RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType);

    protected abstract void bind(@NonNull RecyclerView.ViewHolder viewHolder, TModel model);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bind(holder, list.get(position));
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<TModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }


}
