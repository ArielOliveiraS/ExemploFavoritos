package com.example.movieapp.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.model.pojos.Filme;
import com.squareup.picasso.Picasso;

import static com.example.movieapp.view.fragment.FilmeFragment.FILME;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalheFilmeFragment extends Fragment {
    private ImageView imgBanner;
    private TextView titulo;
    private TextView descricao;


    public DetalheFilmeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalhe_filme, container, false);
        initViews(view);

        if (getArguments() != null){
            Filme filme = getArguments().getParcelable(FILME);

            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+ filme.getPosterPath()).into(imgBanner);


            titulo.setText(filme.getTitle());
            descricao.setText(filme.getOverview());
        }

            return view;
    }

    public void initViews(View view) {
        imgBanner = view.findViewById(R.id.imageFundo);
        titulo = view.findViewById(R.id.text_titulo);
        descricao = view.findViewById(R.id.descricao);
    }
}
