package com.example.justchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserdatabase;
    private FirebaseUser mCurrentUser;
    private CircleImageView mDisplayimage;
    private TextView mname;
    private TextView mstatus;
    private Button mstatusbutton;
    private Button mnamebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Declaring fields

        mDisplayimage=findViewById(R.id.dp);
        mname=findViewById(R.id.settings_username);
        mstatus=findViewById(R.id.settings_status);
        mstatusbutton=findViewById(R.id.changestatus);
        mnamebutton=findViewById(R.id.changename);

        //fething current user

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();

        //fetching his uid

        String current_uid=mCurrentUser.getUid();

        //taking out his database

        mUserdatabase= FirebaseDatabase.getInstance().getReference().child("User").child(current_uid);
        mUserdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
                String image=dataSnapshot.child("image").getValue().toString();
                String thumb_image=dataSnapshot.child("thumb_image").getValue().toString();

                mname.setText(name);
                mstatus.setText(status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mstatusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status_val=mstatus.getText().toString();
                //sending old status
                Intent status_intent=new Intent(SettingsActivity.this,StatusActivity.class);
                status_intent.putExtra("status_value",status_val);
                startActivity(status_intent);
            }
        });

    }
}