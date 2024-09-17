package br.ufc.quixada.projetofinalperseo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class TelaLogin extends Fragment {
    public TelaLogin() {
        // Required empty public constructor
    }

    public static TelaLogin newInstance() {
        TelaLogin fragment = new TelaLogin();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tela_login, container, false);

        Button botaoLogin = view.findViewById(R.id.tela_login_button_fazer_login);
        Button botaoCriarConta = view.findViewById(R.id.tela_login_button_criar_conta);

        botaoLogin.setOnClickListener(
                v->{
                    Toast.makeText(this.getContext(), "Clicou no botão de login", Toast.LENGTH_SHORT).show();
                    BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(BottomNavigationView.VISIBLE);
                    bottomNavigationView.setSelectedItemId(R.id.home_navbar);
                }
        );

        botaoCriarConta.setOnClickListener(
                v->{
                    Toast.makeText(this.getContext(), "Clicou no botão de criar conta", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, CriarConta.newInstance()).commit();
                }
        );

        return view;
    }
}