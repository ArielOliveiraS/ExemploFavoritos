package com.example.movieapp.viewmodel;

import android.app.Application;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.model.pojos.Favorito;
import com.example.movieapp.model.pojos.Filme;
import com.example.movieapp.model.repository.FilmeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ViewModeleDetalheFilme extends AndroidViewModel {
    private MutableLiveData<Filme> filme = new MutableLiveData<>();
    private MutableLiveData<Boolean> favorito = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<String> erro = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmeRepository repository = new FilmeRepository();
    private MutableLiveData<List<MediaStore.Video>> listaVideos = new MutableLiveData<>();


    public ViewModeleDetalheFilme(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getFavorito() {
        return this.favorito;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public LiveData<String> getError() {
        return this.erro;
    }
    public void verificarFavorito(long id) {
        disposable.add(
                repository.checkFavoritoById(getApplication().getApplicationContext(), id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(integer -> {
                            if (integer == 1) {
                                favorito.setValue(true);
                            } else {
                                favorito.setValue(false);
                            }
                        }, throwable -> {
                            erro.setValue(throwable.getMessage());
                        })
        );
    }

    public void inserirFavorito(Filme filme) {
        Favorito novoFavorito = new Favorito(filme.getId(), filme);
        new Thread(() -> {
            repository.insertFavorito(getApplication().getApplicationContext(), novoFavorito);
        }).start();
    }

    public void removerFavorito(Filme filme) {
        Favorito novoFavorito = new Favorito(filme.getId(), filme);
        new Thread(() -> {
            repository.deletarFavorito(getApplication().getApplicationContext(), novoFavorito);
        }).start();
    }


}

