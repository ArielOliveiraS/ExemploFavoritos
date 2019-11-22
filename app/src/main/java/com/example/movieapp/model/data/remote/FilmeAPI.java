package com.example.movieapp.model.data.remote;

import com.example.movieapp.model.pojos.FilmeResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmeAPI {

    @GET("movie/now_playing")
    Observable<FilmeResult> getAllFilmes(@Query("api_key") String apiKey,
                                         @Query("page") int pagina,
                                         @Query("title") String title,
                                         @Query("overview") String overview);
}
