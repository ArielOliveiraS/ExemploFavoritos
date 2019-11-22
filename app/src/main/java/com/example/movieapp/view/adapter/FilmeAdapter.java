package com.example.movieapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.model.pojos.Filme;
import com.example.movieapp.view.onclick.FilmeOnCLick;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.xml.transform.Result;

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.ViewHolder> {
    private List<Filme> filmeList;
    private FilmeOnCLick listener;

    public FilmeAdapter(List<Filme> filmeList, FilmeOnCLick listener) {
        this.filmeList = filmeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Filme filme = filmeList.get(position);

        holder.onBind(filme);

        holder.itemView.setOnClickListener(v -> listener.filmeOnClick(filme));
    }

    public void atualizaLista(List<Filme> populares) {
        if (this.filmeList.isEmpty()) {
            this.filmeList = populares;
        } else {
            this.filmeList.addAll(populares);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filmeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagem_item);
            textView = itemView.findViewById(R.id.titulo_item);
        }

        public void onBind(Filme filme) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + filme.getPosterPath()).into(imageView);
            textView.setText(filme.getTitle());
        }
    }
}
