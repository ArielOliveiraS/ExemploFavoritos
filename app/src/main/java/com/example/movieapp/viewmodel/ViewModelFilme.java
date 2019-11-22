package com.example.movieapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.model.pojos.Filme;
import com.example.movieapp.model.repository.FilmeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.movieapp.util.Conexao.isNetworkConnected;

public class ViewModelFilme extends AndroidViewModel {
    private MutableLiveData<List<Filme>> listaFilmes = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmeRepository repository = new FilmeRepository();

    public ViewModelFilme(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Filme>> getFilmes() {
        return this.listaFilmes;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public void getLista(String apiKey, int pagina, String title, String overview) {
        if (isNetworkConnected(getApplication())) {
            getAllFilmes(apiKey, pagina, title, overview);
        } else {
            getLocalFilmesPopulares();
        }
    }

    public void getAllFilmes(String apiKey, int pagina, String title, String overview){
        disposable.add(
                repository.getFilmes(apiKey, pagina, title, overview)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscrition ->{
                    loading.setValue(true);
                })
                .doOnTerminate(() -> {
                    loading.postValue(false);
                })
                .subscribe(todosOsFilmes ->{
                    listaFilmes.setValue(todosOsFilmes.getResults());
                }, throwable -> {
                    Log.i("FILME", "getAllFilmes " + throwable.getMessage());
                })
        );
    }

    public void getLocalFilmesPopulares(){

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
