package com.epf.museo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.epf.museo.models.Musee;
import com.epf.museo.database.database;
import com.epf.museo.models.MuseeImage;

@Database(entities = {Musee.class, MuseeImage.class}, version = 2)
public abstract class MuseumDatabase extends RoomDatabase  {
    public abstract database getDatabase();
}
