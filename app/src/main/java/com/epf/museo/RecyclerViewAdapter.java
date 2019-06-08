package com.epf.museo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epf.museo.models.Musee;

import java.util.ArrayList;
import java.util.List;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageView;
    public TextView txtDescription;
    RecyclerViewAdapter.OnMuseeListener onMuseeListener;


    public RecyclerViewHolder(@NonNull View itemView, RecyclerViewAdapter.OnMuseeListener onMuseeListener) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.imageView);
        txtDescription = (TextView)itemView.findViewById(R.id.txtDescription);
        this.onMuseeListener = onMuseeListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMuseeListener.onMuseeClick(getAdapterPosition());
    }
}

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Data> listData = new ArrayList<Data>();
    private OnMuseeListener mOnMuseeListener;


    public RecyclerViewAdapter(List<Data> listData, OnMuseeListener onMuseeListener) {
        this.listData = listData;
        this.mOnMuseeListener = onMuseeListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(itemView, mOnMuseeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.imageView.setImageResource(listData.get(position).getImageID());
        holder.txtDescription.setText(listData.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface OnMuseeListener {
        void onMuseeClick(int position);
    }
}
