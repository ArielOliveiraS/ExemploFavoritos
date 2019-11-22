package com.example.movieapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.movieapp.R;
import com.example.movieapp.view.fragment.FavoritosFragment;
import com.example.movieapp.view.fragment.FilmeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_filmes, R.id.navigation_favoritos)
                .build();

        navigationView.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null){
            navigationView.setSelectedItemId(R.id.navigation_filmes);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navigation_filmes:{
                replaceFragment(new FilmeFragment());
                break;
            }
            case R.id.navigation_favoritos:{
                replaceFragment(new FavoritosFragment());
                break;
            }
        }
        return true;
    }

    public void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerPrincipal,fragment)
                .addToBackStack(null)
                .commit();
    }
}
