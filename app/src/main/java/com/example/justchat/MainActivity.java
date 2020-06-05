package com.example.justchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mtoolbar;
    private ViewPager mviewPager;
    private SectionPagerAdapter mSectionPagerAdapter;
    private TabLayout mtablayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        //Toolbar
        mtoolbar=findViewById(R.id.main_appbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Just Chat");

        //Tabs
        mviewPager=findViewById(R.id.tabpager);
        mSectionPagerAdapter=new SectionPagerAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(mSectionPagerAdapter);
        mtablayout=findViewById(R.id.tabs);
        mtablayout.setupWithViewPager(mviewPager);



    }
        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser==null){
                settostart();
            }
        }

    private void settostart() {
        Intent startIntent=new Intent(MainActivity.this,start_activity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.log_out_btn){
            FirebaseAuth.getInstance().signOut();
            settostart();
        }
        if(item.getItemId()==R.id.acc_set_btn){
            Intent settingsintent=new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(settingsintent);
        }
        return true;
    }
}
