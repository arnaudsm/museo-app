package com.epf.museo.interfaces;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.epf.museo.MuseeActivity;
import com.epf.museo.database.database;
import com.epf.museo.models.MuseeImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ImageDownloader implements Target {
    String src;
    String museum_id;
    private static com.epf.museo.database.database database;
    private MuseeActivity museeActivity;

    public ImageDownloader(String src, String museum_id, com.epf.museo.database.database database, MuseeActivity museeActivity ) {
        this.src = src;
        this.museum_id = museum_id;
        this.database = database;
        this.museeActivity = museeActivity;
    }

    @Override
    public void onBitmapLoaded(final Bitmap image, Picasso.LoadedFrom from) {
        MuseeImage museeImage = new MuseeImage(src, museum_id, image);
        Log.e("Good", "Image Saved");

        database.insertImage(museeImage);
        museeActivity.display_picture(museeImage);
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }
}
