package br.ufc.quixada.projetofinalperseo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AplicacaoViewPagerAdapter extends FragmentStateAdapter {
    public AplicacaoViewPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager,lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position) {
            case 0 -> TelaInicial.newInstance();
            case 2 -> PerfilUsuario.newInstance("3PSVpy77p3CqYr8NMSB2");
            case 3 -> CriarAtividade.newInstance();
            case 4 -> CriarConta.newInstance();
            case 5 -> CriarGrupo.newInstance();
            default -> TelaLogin.newInstance();
        };
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
