package br.ufc.quixada.projetofinalperseo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


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

        Button botaoLogin = view.findViewById(R.id.fazer_login_button);

        botaoLogin.setOnClickListener(
                v->{
                    Toast.makeText(this.getContext(), "Clicou no botão de login", Toast.LENGTH_SHORT).show();
                }
        );

        return view;
    }
}