package com.example.movieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.model.pojos.Favorito;
import com.example.movieapp.model.repository.FilmeRepository;

import java.util.List;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ViewModelFavoritos extends AndroidViewModel {
    private MutableLiveData<List<Favorito>> listaFavoritos = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<String> erro = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmeRepository repository = new FilmeRepository();

    public ViewModelFavoritos(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Favorito>> getListaFilmes() {
        return this.listaFavoritos;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public LiveData<String> getError() {
        return this.erro;
    }

    public void getAllFavoritos() {
        disposable.add(
                repository.getFavoritos(getApplication().getApplicationContext())
                        .onBackpressureBuffer(100, () -> {
                        }, BackpressureOverflowStrategy.DROP_OLDEST)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> {
                            loading.setValue(true);
                        })
                        .doOnTerminate(() -> {
                            loading.setValue(false);
//                            Log.i("CICLO", "TERMINATE carregado");
                        })
                        .subscribe(favoritos -> {
                            listaFavoritos.setValue(favoritos);
                            //tira o loading ao carregar os itens
                            loading.setValue(false);
                        }, throwable -> {
                            erro.setValue(throwable.getMessage());
                        })
        );
    }
}
