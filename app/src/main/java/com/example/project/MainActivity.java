package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth userAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userAuth=FirebaseAuth.getInstance();
        currentUser= userAuth.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser==null)
        {
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
            MainActivity.this.finish();
        }

    }
}