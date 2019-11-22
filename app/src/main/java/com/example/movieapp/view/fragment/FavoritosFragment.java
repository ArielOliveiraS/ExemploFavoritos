package com.example.movieapp.view.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.model.pojos.Favorito;
import com.example.movieapp.model.pojos.Filme;
import com.example.movieapp.view.adapter.FavoritosAdapter;
import com.example.movieapp.view.onclick.FavoritosOnClick;
import com.example.movieapp.view.onclick.FilmeOnCLick;
import com.example.movieapp.viewmodel.ViewModelFavoritos;

import java.util.ArrayList;
import java.util.List;

import static com.example.movieapp.view.fragment.FilmeFragment.FILME;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends Fragment implements FilmeOnCLick, FavoritosOnClick {
    private RecyclerView recyclerFavoritos;
    private ProgressBar progressFavoritos;
    private FavoritosAdapter adapter;
    private List<Favorito> filmesFavoritos = new ArrayList<>();
    private ViewModelFavoritos viewModel;

    public FavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        viewModel.getAllFavoritos();

        viewModel.getListaFilmes().observe(this, filmes ->{
            adapter.atualizaListaFavoritos(filmes);
        });


        viewModel.getError().observe(this, s -> {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        });

        viewModel.getLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressFavoritos.setVisibility(View.VISIBLE);
            } else {
                progressFavoritos.setVisibility(View.GONE);
            }
        });
        return view;
    }
    public void initViews(View view) {
        recyclerFavoritos = view.findViewById(R.id.recyclerViewFavoritos);
        progressFavoritos = view.findViewById(R.id.progressBarFavoritos);
        adapter = new FavoritosAdapter(filmesFavoritos, this, this);
        viewModel = ViewModelProviders.of(this).get(ViewModelFavoritos.class);
        recyclerFavoritos.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerFavoritos.setAdapter(adapter);
    }

    @Override
    public void addFavoriteClickListener(Filme filme) {

    }

    @Override
    public void removeFavoriteClickListener(Favorito filme) {

    }

    @Override
    public void filmeOnClick(Filme filme) {
        Intent intent = new Intent(getContext(), DetalheFilmeFragment.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(FILME, filme);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
