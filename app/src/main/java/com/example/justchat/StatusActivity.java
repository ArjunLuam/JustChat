package com.example.justchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private EditText mupstatus;
    private Button mupdatebtn;
    private DatabaseReference mStatusdatabase;
    private FirebaseUser muser;
    private LoadingDialogue loadingDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mtoolbar=findViewById(R.id.status_appbar);
        mupstatus=findViewById(R.id.status_update);
        mupdatebtn=findViewById(R.id.update_button);
        //Firebase
        muser= FirebaseAuth.getInstance().getCurrentUser();
        String current_uid=muser.getUid();
        mStatusdatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialogue.startLoading();
                String status=mupstatus.getText().toString();
                mStatusdatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            loadingDialogue.dismissDialog();
                        }
                       else{
                            Toast.makeText(StatusActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}