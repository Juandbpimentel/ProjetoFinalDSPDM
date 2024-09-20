// CriarAtividade.java
package br.ufc.quixada.projetofinalperseo.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CalendarView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.Calendar;

import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.models.Atividade;
import br.ufc.quixada.projetofinalperseo.models.Grupo;
import br.ufc.quixada.projetofinalperseo.models.Localizacao;
import br.ufc.quixada.projetofinalperseo.utilities.GpsTracker;

public class CriarAtividade extends Fragment {

    private EditText campoNome;
    private EditText campoDescricao;
    private CalendarView campoData;
    private TimePicker campoHora;
    private Button criarAtividadeButton;
    private String idGrupo;
    private FusedLocationProviderClient fusedLocationClient;
    private Grupo grupoAtual;

    public CriarAtividade() {
    }

    public static CriarAtividade newInstance(String idGrupo) {
        CriarAtividade fragment = new CriarAtividade();
        Bundle args = new Bundle();
        args.putString("idGrupo", idGrupo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idGrupo = getArguments().getString("idGrupo");
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criar_atividade, container, false);
        campoNome = view.findViewById(R.id.criar_atividade_campo_nome);
        campoDescricao = view.findViewById(R.id.criar_atividade_campo_descricao);
        campoData = view.findViewById(R.id.criar_atividade_agenda);
        campoHora = view.findViewById(R.id.criar_atividade_hora);
        criarAtividadeButton = view.findViewById(R.id.criar_atividade_botao);

        criarAtividadeButton.setOnClickListener(v -> criarAtividade());
        getGrupoAtual();

        return view;
    }

    private void criarAtividade() {
        String nome = campoNome.getText().toString();
        String descricao = campoDescricao.getText().toString();
        long dataMillis = campoData.getDate();
        int hora = campoHora.getHour();
        int minuto = campoHora.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataMillis);
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minuto);
        Timestamp dataAgenda = new Timestamp(calendar.getTime());

        Localizacao localizacaoAtual = new Localizacao();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            GpsTracker gpsTracker = new GpsTracker(getContext());
            localizacaoAtual.setCidade("cidade");
            localizacaoAtual.setEstado("estado");
            localizacaoAtual.setPais("pais");
            localizacaoAtual.setGeoPoint(new GeoPoint(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
        } else {
            Toast.makeText(getContext(), "Permissão de localização não concedida", Toast.LENGTH_SHORT).show();
            return;
        }

        if (localizacaoAtual.getId() == null) {
            Toast.makeText(getContext(), "Erro ao obter localização", Toast.LENGTH_SHORT).show();
            return;
        }

        if (grupoAtual.getId() == null) {
            Toast.makeText(getContext(), "Erro ao obter grupo", Toast.LENGTH_SHORT).show();
            return;
        }

        Atividade atividade = new Atividade(nome, descricao, dataAgenda, grupoAtual, localizacaoAtual);
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        db.collection("atividades").document(atividade.getId()).set(atividade)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Atividade criada com sucesso", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Erro ao criar atividade: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void getGrupoAtual() {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        DocumentReference grupoRef = db.collection("grupos").document(idGrupo);
        grupoAtual = new Grupo();
        grupoRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Grupo grp = documentSnapshot.toObject(Grupo.class);
                if (grp != null) {
                    grupoAtual.setId(grp.getId());
                    grupoAtual.setNome(grp.getNome());
                    grupoAtual.setDescricao(grp.getDescricao());
                    grupoAtual.setAtividades(grp.getAtividades());
                    grupoAtual.setEsporte(grp.getEsporte());
                    grupoAtual.setAdministrador(grp.getAdministrador());
                }
            } else {
                Log.d("CriarAtividade", "Documento de grupo não encontrado");
            }
        }).addOnFailureListener(e -> {
            Log.d("CriarAtividade", "Erro ao carregar grupo: " + e.getLocalizedMessage());
        });
    }

//    private Localizacao getLocalizacaoAtual() {
//        final Localizacao[] localizacaoAtual = {new Localizacao()};
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//                            localizacaoAtual[0].setGeoPoint(new GeoPoint(location.getLatitude(), location.getLongitude()));
//                            // Use a localização conforme necessário
//                        } else {
//                            Log.d("CriarAtividade", "Localização não encontrada");
//                        }
//                    }
//                });
//        return localizacaoAtual[0];
//    }
}