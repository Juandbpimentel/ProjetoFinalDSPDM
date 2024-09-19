package br.ufc.quixada.projetofinalperseo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.ufc.quixada.projetofinalperseo.MainActivity;
import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.utilities.AuthService;


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
        EditText email = view.findViewById(R.id.tela_login_campo_email);
        EditText senha = view.findViewById(R.id.tela_login_campo_senha);
        botaoLogin.setOnClickListener(
                v->{
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (email.getText() == null || senha.getText() == null) {
                        Toast.makeText(this.getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String emailString = email.getText() == null ? "" : email.getText().toString();
                    String senhaString = senha.getText() == null ? "" : senha.getText().toString();
                    if (emailString.isEmpty() || senhaString.isEmpty()) {
                        Toast.makeText(this.getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!AuthService.fazerLogin(emailString, senhaString, this.getContext()))
                        return;
                    assert mainActivity != null;
                    mainActivity.usuarioViewModel.setUsuarioPorAuth();
                    BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(BottomNavigationView.VISIBLE);
                    bottomNavigationView.setSelectedItemId(R.id.home_navbar);

                }
        );

        botaoCriarConta.setOnClickListener(
                v->{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, CriarConta.newInstance()).commit();
                }
        );

        return view;
    }
}