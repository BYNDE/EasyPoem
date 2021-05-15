package com.example.easypoem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_or_register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        getSupportActionBar().hide();

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button register = findViewById(R.id.register);
        Button login = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text = email.getText().toString();
                String password_text = password.getText().toString();
                if (email_text.length() != 0 && password_text.length() != 0){
                    registration(email_text,password_text);
                }
                else{
                    Toast.makeText(Login_or_register.this,"Заполните все поля", Toast.LENGTH_LONG).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text = email.getText().toString();
                String password_text = password.getText().toString();
                if (email_text.length() != 0 && password_text.length() != 0){
                    loging(email_text,password_text);
                }
                else{
                    Toast.makeText(Login_or_register.this,"Заполните все поля", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void loging(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(Login_or_register.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Login_or_register.this,"Такого пользователя нет", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void registration(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mAuth.signInWithEmailAndPassword(email, password);
                    Intent intent = new Intent(Login_or_register.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Login_or_register.this,"Такой пользователь уже есть", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}