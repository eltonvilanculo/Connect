package com.example.connect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect.R;
import com.example.connect.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class ContentActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;

    FirebaseAuth firebaseAuth;

  TextView userProfileEmail, userProfileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar_id);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onBackPressed() {



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar,menu);




        return  true;
    }

    public void showPerfil(MenuItem item) {

        showProfileDialog();

    }

    private void showProfileDialog() {

        View view = getLayoutInflater().inflate(R.layout.perfil_layout,null,false);

        userProfileEmail = view.findViewById(R.id.user_profile_email);
        userProfileName = view.findViewById(R.id.user_profile_name);


        try {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            userProfileName.setText(firebaseUser.getDisplayName().toString());
            userProfileEmail.setText(firebaseUser.getEmail().toString());

            AlertDialog.Builder alBuilder = new AlertDialog.Builder(ContentActivity.this);
            alBuilder.setView(view);
            AlertDialog alertDialog = alBuilder.create();
            alertDialog.show();
        } catch (Exception e) {

            Toast.makeText(this, "Perfil nao disponivel", Toast.LENGTH_SHORT).show();
        }


    }

    public void logout(MenuItem item) {

        firebaseAuth.signOut();

        startActivity(new Intent(ContentActivity.this,MainActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
