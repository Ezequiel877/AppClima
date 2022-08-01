package com.example.appclima.UI.Lognin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appclima.MainActivity;
import com.example.appclima.R;
import com.example.appclima.UI.home.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class Login extends Fragment {


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button login_button;
    Button gotoRegister;
    Toolbar r_toolbar;
    TextInputEditText password;
    TextInputEditText email;
    FirebaseApp appfirebase;
    CoordinatorLayout coordinatorLayout;
    FirebaseAuth auth;
    SharedPreferences mpref;


    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appfirebase = FirebaseApp.initializeApp(getContext());

        auth = FirebaseAuth.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_login, container, false);
        login_button = vista.findViewById(R.id.button_login);
        gotoRegister = vista.findViewById(R.id.gotoRegister);
        email = vista.findViewById(R.id.name_login);
        coordinatorLayout=vista.findViewById(R.id.fragment);
        password = vista.findViewById(R.id.login_email);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGIN(v);
            }
        });
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.sign);

            }
        });


        return vista;
    }

    private void LOGIN(View v) {
        String string_email = email.getText().toString();
        String password_id = password.getText().toString();

        if (!string_email.isEmpty() && !password_id.isEmpty()) {
            if (password.length() > 6) {
                auth.signInWithEmailAndPassword(string_email, password_id).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Navigation.findNavController(v).navigate(R.id.home2);
                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);

                        } else {
                                mostrarDialog(v);
                        }
                    }

                });
            }
        }

    }

    protected void mostrarDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ERROR DE AUTENTIFICACION");
        builder.setMessage("tu email o contraseña son incorrectas o si no tien una cuenta registrate").
                setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Toast.makeText(getContext(), "el email o la contraseña es incorrecta", Toast.LENGTH_SHORT).show();

                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("ir a registrarme", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController(v).navigate(R.id.home2);
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
                    }
                }).show();
    }

}