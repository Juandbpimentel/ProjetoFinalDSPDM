package br.ufc.quixada.projetofinalperseo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_constraint_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED)
        {
            Log.d("Permissão", "Permissão de internet concedida");
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            Log.d("Permissão", "Permissão de localização precisa");
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            Log.d("Permissão", "Permissão de localização aproximada");
        }

//        ViewPager2 viewPager = findViewById(R.id.main_frame_layout);
//        AplicacaoViewPagerAdapter aplicacaoViewPagerAdapter = new AplicacaoViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
//        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//        viewPager.setUserInputEnabled(false);
//        viewPager.setAdapter(aplicacaoViewPagerAdapter);
//        PerfilUsuario.idUsuario = null;
//        viewPager.setCurrentItem(2);
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
        Fragment fragmentInicial = new TelaLogin();
        bottomNavigationView.setVisibility(BottomNavigationView.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragmentInicial).commit();




        /*TelaLogin fragmentLogin = TelaLogin.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainFrameLayout,fragmentLogin);

        ft.commit();*/


////        CriarConta fragment_criar_conta = CriarConta.newInstance();
//        CriarAtividade fragment_criar_atividade = CriarAtividade.newInstance();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
////        ft.replace(R.id.mainFrameLayout,fragment_criar_conta);
//        ft.replace(R.id.main_view_pager,fragment_criar_atividade);
//        ft.commit();

    }
}