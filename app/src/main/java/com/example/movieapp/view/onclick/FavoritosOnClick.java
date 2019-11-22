package com.example.movieapp.view.onclick;

import com.example.movieapp.model.pojos.Favorito;
import com.example.movieapp.model.pojos.Filme;

public interface FavoritosOnClick {
    void addFavoriteClickListener(Filme filme);
    void removeFavoriteClickListener(Favorito filme);
}
