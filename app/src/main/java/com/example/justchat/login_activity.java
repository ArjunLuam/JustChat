package com.example.justchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_activity extends AppCompatActivity {
        Toolbar mtoolbar;
        private EditText user_mail;
        private EditText user_pass;
        private Button login;
        LoadingDialogue loadingDialogue;
        FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        //Firebase
        mAuth=FirebaseAuth.getInstance();
        //loading dialogue
        loadingDialogue=new LoadingDialogue(login_activity.this);
        mtoolbar=findViewById(R.id.reg_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Initialization
        user_mail=findViewById(R.id.log_email);
        user_pass=findViewById(R.id.log_pass);
        login=findViewById(R.id.log_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=user_mail.getText().toString();
                String password=user_pass.getText().toString();

                if( !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                    loadingDialogue.startLoading();
                    login_user(email,password);
                }
            }
        });
    }

    private void login_user(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    loadingDialogue.dismissDialog();
                    Intent logintent=new Intent(login_activity.this,MainActivity.class);
                    logintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(logintent);
                    finish();
                }
                else{
                    Toast.makeText(login_activity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}