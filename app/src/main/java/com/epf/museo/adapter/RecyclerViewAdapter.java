package com.epf.museo.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epf.museo.R;
import com.epf.museo.models.Musee;
import com.epf.museo.models.MuseeImage;

import java.util.ArrayList;
import java.util.List;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageView;
    public TextView txtDescription;
    public TextView city;
    RecyclerViewAdapter.OnMuseeListener onMuseeListener;



    public RecyclerViewHolder(@NonNull View itemView, RecyclerViewAdapter.OnMuseeListener onMuseeListener) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.imageView);
        txtDescription = (TextView)itemView.findViewById(R.id.txtDescription);
        city = (TextView)itemView.findViewById(R.id.city);

        this.onMuseeListener = onMuseeListener;


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMuseeListener.onMuseeClick(getAdapterPosition());
    }
}

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Musee> listData = new ArrayList<Musee>();
    private OnMuseeListener mOnMuseeListener;
    private static com.epf.museo.database.database database;


    public RecyclerViewAdapter(List<Musee> listData, OnMuseeListener onMuseeListener, com.epf.museo.database.database database) {
        this.listData = listData;
        this.mOnMuseeListener = onMuseeListener;

        this.database = database;
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
        holder.txtDescription.setText(listData.get(position).getNom());
        holder.city.setText(listData.get(position).getVille());

        MuseeImage museeImage = database.getMuseumImage(listData.get(position).getId());
        if (museeImage !=null) {
            Bitmap image = museeImage.getImage();
            holder.imageView.setImageBitmap(image);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface OnMuseeListener {
        void onMuseeClick(int position);
    }
}
