package com.example.justchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register_activity extends AppCompatActivity {
    private EditText musername,memail,mpassword;
    private Button mcreateacc;
    private Toolbar mtoolbar;
    FirebaseAuth mAuth;
    LoadingDialogue loadingDialogue;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        mAuth=FirebaseAuth.getInstance();
        musername=findViewById(R.id.name);
        mpassword=findViewById(R.id.password);
        mcreateacc=findViewById(R.id.reg_create_btn);
        memail=findViewById(R.id.email);
        mtoolbar=findViewById(R.id.reg_toolbar);
        loadingDialogue=new LoadingDialogue(register_activity.this);


        mcreateacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toolbar setup
                mtoolbar=findViewById(R.id.reg_toolbar);
                setSupportActionBar(mtoolbar);
                getSupportActionBar().setTitle("Create Account");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                //progress dialog

                // loadingDialogue=new LoadingDialogue(register_activity.this);
                //Firebase setup
                mAuth=FirebaseAuth.getInstance();
                //Declartion
                String username=musername.getText().toString();
                String email=memail.getText().toString();
                String password=mpassword.getText().toString();
                if(! TextUtils.isEmpty(username)|| ! TextUtils.isEmpty(email) || ! TextUtils.isEmpty(password)){
                    loadingDialogue.startLoading();
                    register_user(username,email,password);
                }


            }
        });
    }

    public void register_user(final String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //storing data in firebase
                    FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                    String uid=current_user.getUid();
                    mdatabase=FirebaseDatabase.getInstance().getReference().child("User").child(uid);
                    HashMap<String,String>user_map=new HashMap<>();
                    user_map.put("name",username);
                    user_map.put("status","Hi there I'm using JustChat");
                    user_map.put("image","default");
                    user_map.put("thumb_image","default");

                    mdatabase.setValue(user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                loadingDialogue.dismissDialog();
                                Intent mainIntent=new Intent(register_activity.this,MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });


                }else{
                    Toast.makeText(register_activity.this,"Error",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}