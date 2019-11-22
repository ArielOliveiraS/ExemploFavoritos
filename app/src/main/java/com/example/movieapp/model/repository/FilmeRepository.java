package com.example.movieapp.model.repository;

import android.content.Context;

import com.example.movieapp.model.data.local.Database;
import com.example.movieapp.model.data.local.FilmesDao;
import com.example.movieapp.model.pojos.Favorito;
import com.example.movieapp.model.pojos.FilmeResult;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

import static com.example.movieapp.model.data.remote.Retrofit.getApiService;

public class FilmeRepository {
    public Observable<FilmeResult> getFilmes(String apiKey, int pagina, String title, String overview) {
        return getApiService().getAllFilmes(apiKey, pagina, title, overview);
    }

    public Flowable<List<Favorito>> getFavoritos(Context context) {
        Database room = Database.getDatabase(context);
        FilmesDao filmesDao = room.filmesDao();
        return filmesDao.listaFavoritos();
    }

    public void insertFavorito(Context context, Favorito favorito) {
        Database room = Database.getDatabase(context);
        FilmesDao filmesDao = room.filmesDao();
        filmesDao.insereFavorito(favorito);
    }

    public void deletarFavorito(Context context, Favorito favorito) {
        Database room = Database.getDatabase(context);
        FilmesDao filmesDao = room.filmesDao();
        filmesDao.deleteFavorito(favorito);
    }

    public Single<Integer> checkFavoritoById(Context context, long id) {
        Database room = Database.getDatabase(context);
        FilmesDao filmesDao = room.filmesDao();

        return filmesDao.checkFavoritoById(id);
    }
}
