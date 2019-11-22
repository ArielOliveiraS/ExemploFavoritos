package com.example.movieapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.model.pojos.Favorito;
import com.example.movieapp.model.pojos.Filme;
import com.example.movieapp.view.onclick.FavoritosOnClick;
import com.example.movieapp.view.onclick.FilmeOnCLick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolder> {
    private List<Favorito> favoritoList = new ArrayList<>();
    private FilmeOnCLick listener;
    private FavoritosOnClick addFavorite;

    public FavoritosAdapter(List<Favorito> favoritoList, FilmeOnCLick listener, FavoritosOnClick addFavorite) {
        this.favoritoList = favoritoList;
        this.listener = listener;
        this.addFavorite = addFavorite;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorito favorito = favoritoList.get(position);
        holder.onBind(favorito.getFilme());

        holder.itemView.setOnClickListener(view -> listener.filmeOnClick(favorito.getFilme()));

        holder.imagemFavoritos.setOnClickListener(view -> addFavorite.removeFavoriteClickListener(favorito));
    }

    public void atualizaListaFavoritos(List<Favorito> filmes) {
        this.favoritoList.clear();
        favoritoList = filmes;
        notifyDataSetChanged();
    }

    public void removeItem(Filme filme){
        favoritoList.remove(filme);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return favoritoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterFavoritos;
        private TextView tituloFavoritos;
        private ImageView imagemFavoritos;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            posterFavoritos = itemView.findViewById(R.id.imageFundo);
            tituloFavoritos = itemView.findViewById(R.id.titulo_item);
            imagemFavoritos = itemView.findViewById(R.id.imageFavorite);
        }

        public void onBind(Filme filme) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + filme.getPosterPath()).into(imagemFavoritos);
            tituloFavoritos.setText(filme.getTitle());
        }
    }
}
