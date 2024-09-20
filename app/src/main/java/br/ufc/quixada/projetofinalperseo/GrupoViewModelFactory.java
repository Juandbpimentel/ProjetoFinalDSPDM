package br.ufc.quixada.projetofinalperseo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.ufc.quixada.projetofinalperseo.view_models.GrupoViewModel;

public class GrupoViewModelFactory implements ViewModelProvider.Factory {
    private final String id;

    public GrupoViewModelFactory(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GrupoViewModel.class)) {
            return modelClass.cast(new GrupoViewModel(id));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}