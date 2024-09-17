package br.ufc.quixada.projetofinalperseo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ViewPager2 viewPager = findViewById(R.id.main_view_pager);
        AplicacaoViewPagerAdapter aplicacaoViewPagerAdapter = new AplicacaoViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(aplicacaoViewPagerAdapter);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int position = item.getItemId();
            if (position == R.id.home_navbar) {
                viewPager.setCurrentItem(0);
            } else if (position == R.id.grupos_navbar) {
                viewPager.setCurrentItem(0);
            } else if (position == R.id.perfil_navbar) {
                viewPager.setCurrentItem(2);
            }
            return true;
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.setSelectedItemId(
                        switch (position) {
                            case 0 -> R.id.home_navbar;
                            case 1 -> R.id.grupos_navbar;
                            case 2 -> R.id.perfil_navbar;
                            default -> R.id.home_navbar;
                        }
                );
            }
        });


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