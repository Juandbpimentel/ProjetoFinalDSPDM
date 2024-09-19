package br.ufc.quixada.projetofinalperseo.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufc.quixada.projetofinalperseo.R;

public class CriarConta extends Fragment {

    public CriarConta() {
        // Required empty public constructor
    }
    public static CriarConta newInstance() {
        CriarConta fragment = new CriarConta();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_conta, container, false);
        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            public void handleOnBackPressed() {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, TelaLogin.newInstance()).commit();
            }
        });
        return view;
    }
}