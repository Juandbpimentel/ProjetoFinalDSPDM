package br.ufc.quixada.projetofinalperseo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.ufc.quixada.projetofinalperseo.utilities.AuthService;
import br.ufc.quixada.projetofinalperseo.view_models.UsuarioViewModel;

public class MainActivity extends AppCompatActivity {
    public UsuarioViewModel usuarioViewModel;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_constraint_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET}, 101);
            }
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        usuarioViewModel = new UsuarioViewModel();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int position = item.getItemId();
            if (position == R.id.home_navbar) {
                Fragment fragment = new TelaInicial();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
            } else if (position == R.id.grupos_navbar) {
                Fragment fragment = new TelaInicial();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();

            } else if (position == R.id.perfil_navbar) {
                String idUsuario = "3PSVpy77p3CqYr8NMSB2";
                Fragment fragment = PerfilUsuario.newInstance(idUsuario);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
            }
            return true;
        });
        bottomNavigationView.setVisibility(BottomNavigationView.GONE);
        if (AuthService.fauth.getCurrentUser() != null) {
            AuthService.usuario = AuthService.fauth.getCurrentUser();
            usuarioViewModel.setUsuarioPorAuth();
            bottomNavigationView.setVisibility(BottomNavigationView.VISIBLE);
            bottomNavigationView.setSelectedItemId(R.id.home_navbar);
        }else{
            Fragment fragmentInicial = new TelaLogin();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragmentInicial).commit();
        }



    }
}