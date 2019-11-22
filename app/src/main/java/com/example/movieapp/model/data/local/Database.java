package com.example.movieapp.model.data.local;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movieapp.model.pojos.Favorito;
import com.example.movieapp.model.pojos.Filme;

@androidx.room.Database(entities = {Filme.class, Favorito.class}, version = 6, exportSchema = false)

public abstract class Database extends RoomDatabase {

    private static volatile Database INSTANCE;

    public abstract FilmesDao filmesDao();

    public static Database getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, Database.class, "filmes_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
