package com.epf.museo.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.epf.museo.R;
import com.epf.museo.models.MuseeImage;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private List<MuseeImage> items;

    public HorizontalAdapter(List<MuseeImage> items) {
        Log.e("Good", "Recycler view created");
        this.items = items;

    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photo, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
        public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        Log.e("Good", "Recycler image added");

        holder.image.setImageBitmap(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.photo_list);
        } }
}