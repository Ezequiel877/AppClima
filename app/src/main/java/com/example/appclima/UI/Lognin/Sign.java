package com.example.appclima.UI.Lognin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appclima.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Sign extends Fragment {

    Button registrame;
    FirebaseAuth auth;
    TextInputEditText text_contraseña;
    TextInputEditText text_name;
    TextInputEditText text_email;
    public Sign() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign, container, false);
        auth = FirebaseAuth.getInstance();
        registrame = view.findViewById(R.id.registrarse);
        text_contraseña = view.findViewById(R.id.contraseña);
        text_email = view.findViewById(R.id.email);
        text_name = view.findViewById(R.id.name);
        registrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrame_firebase(v);
            }
        });

        return view;
    }
    void registrame_firebase(View v) {
        final String email_id = text_email.getText().toString();
        final String contraseña = text_contraseña.getText().toString();
        if (!email_id.isEmpty() && !contraseña.isEmpty()) {
            if (contraseña.length() >= 6) {
                register( email_id, contraseña, v);

            }
        }
    }
    void register(final String emial, String password, View v) {
        auth.createUserWithEmailAndPassword(emial, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Navigation.findNavController(v).navigate(R.id.home2);

                    Toast.makeText(getContext(), "se realizo exito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "el email o la contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}