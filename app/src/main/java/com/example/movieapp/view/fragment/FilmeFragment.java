package com.example.movieapp.view.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.movieapp.R;
import com.example.movieapp.model.pojos.Filme;
import com.example.movieapp.view.adapter.FilmeAdapter;
import com.example.movieapp.view.onclick.FilmeOnCLick;
import com.example.movieapp.viewmodel.ViewModelFilme;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilmeFragment extends Fragment implements FilmeOnCLick {
    private RecyclerView recyclerFilmes;
    private ProgressBar progressBarFilme;
    private FilmeAdapter adapter;
    private ViewModelFilme viewModel;
    private List<Filme> listaFilmes = new ArrayList<>();

    public static final String API_KEY = "bde8033d3274c91b292a5293c6349052";
    public static final String FILME = "filme";
    private int pagina = 1;
    private String title;
    private String overview;


    public FilmeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filme, container, false);
        initViews(view);
        setPaginationOnScroll();

        viewModel.getLista(API_KEY, pagina, title,overview);
        viewModel.getFilmes().observe(this,filmes -> {
            adapter.atualizaLista(filmes);
        });

        recyclerFilmes.setAdapter(adapter);
        recyclerFilmes.setLayoutManager(new GridLayoutManager(getContext(), 3));

        viewModel.getLoading().observe(this,load -> {
            if (load){
                progressBarFilme.setVisibility(View.VISIBLE);
            }else{
                progressBarFilme.setVisibility(View.GONE);
            }
        });



        return view;
    }

    public void initViews(View view) {
        recyclerFilmes = view.findViewById(R.id.recyclerViewFilme);
        progressBarFilme = view.findViewById(R.id.progressBarFilme);
        adapter = new FilmeAdapter(listaFilmes, this);
        viewModel = ViewModelProviders.of(this).get(ViewModelFilme.class);
        recyclerFilmes.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerFilmes.setAdapter(adapter);

    }

    @Override
    public void filmeOnClick(Filme filme) {
        Intent intent = new Intent(getContext(), DetalheFilmeFragment.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(FILME, filme);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void setPaginationOnScroll(){
        recyclerFilmes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                boolean ultimoItem = lastVisible + 5 >= totalItemCount;

                if (totalItemCount > 0 && ultimoItem){
                    pagina++;
                    viewModel.getLista(API_KEY, pagina, title, overview);
                }
            }
        });
    }
}
