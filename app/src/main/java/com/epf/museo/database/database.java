package com.epf.museo.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.epf.museo.models.Musee;
import com.epf.museo.models.MuseeImage;


import java.util.List;

@Dao
public interface database {
    @Insert
    public void insert(Musee musee);

    @Query("SELECT * FROM musees WHERE id = :id")
    public Musee getItemById(String id);

    @Query("SELECT * FROM musees")
    public List<Musee> getItems();

    @Query("SELECT * FROM images where musee_id = :musee_id")
    public List<MuseeImage> getMuseumImages(String musee_id);

    @Query("SELECT * FROM images where musee_id = :musee_id limit 1")
    public MuseeImage getMuseumImage(String musee_id);

    @Query("SELECT * FROM images where url= :url")
    public MuseeImage getImage(String url);

    @Insert
    public void insertImage(MuseeImage image);

}