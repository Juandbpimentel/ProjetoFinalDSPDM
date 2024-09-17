package br.ufc.quixada.projetofinalperseo.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AuthService {
    public static FirebaseUser usuario = null;
    public static final FirebaseAuth fauth = FirebaseAuth.getInstance();

    public static boolean fazerLogin(String email , String senha, Context context){
        fauth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                usuario = fauth.getCurrentUser();
            }else {
                usuario = null;
                Log.d("AuthService", "Erro ao fazer login: "+task.getException());
                Toast.makeText(context, "Erro ao fazer login: "+task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
        return usuario != null;
    }

    public static void fazerLogout(){
        fauth.signOut();
        usuario = null;
    }

    public static boolean criarUsuario(String email, String senha, Context context){
        fauth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                usuario = fauth.getCurrentUser();
            }else {
                usuario = null;
                Toast.makeText(context, "Erro ao criar usuário: "+task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
        return usuario != null;
    }

    public static boolean alterarEmail(String email, Context context){
        usuario.verifyBeforeUpdateEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                usuario = fauth.getCurrentUser();
            }else {
                usuario = null;
                Toast.makeText(context, "Erro ao atualizar email de usuário: "+task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
        return usuario != null;
    }

    public static boolean alterarSenha(String senha, Context context){
        usuario.updatePassword(senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                usuario = fauth.getCurrentUser();
            }else {
                usuario = null;
                Toast.makeText(context, "Erro ao atualizar senha de usuário: "+task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
        return usuario != null;
    }
}
