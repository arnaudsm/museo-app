package com.epf.museo.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.epf.museo.models.Musee;

import java.io.ByteArrayOutputStream;

@Entity(tableName = "images")
public class MuseeImage {
    @PrimaryKey
    @NonNull
    private String url;

    @NonNull
    public String getUrl() {
        return url;
    }

    public String getMusee_id() {
        return musee_id;
    }

    public String getImage_string() {
        return image_string;
    }

    @ForeignKey(entity= Musee.class, parentColumns = "id", childColumns = "musee_id")
    private String musee_id;
    private String image_string;

    public Bitmap getImage() {
        return string_to_bitmap(image_string);
    }

    public MuseeImage(@NonNull String url, String musee_id, String image_string) {
        this.url = url;
        this.musee_id = musee_id;
        this.image_string = image_string;
    }

    public MuseeImage(@NonNull String url, String musee_id, Bitmap image) {
        this.url = url;
        this.musee_id = musee_id;
        this.image_string =bitmap_to_string(image);
    }

    public String bitmap_to_string(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // Could be Bitmap.CompressFormat.PNG or Bitmap.CompressFormat.WEBP
        byte[] bai = baos.toByteArray();
        String string = Base64.encodeToString(bai, Base64.DEFAULT);

        return string;
    }

    public Bitmap string_to_bitmap(String string) {
        byte[] data = Base64.decode(string, Base64.DEFAULT);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opt);

        return bitmap;
    }
}
