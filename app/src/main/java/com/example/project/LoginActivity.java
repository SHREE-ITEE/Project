package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth userAuth;
    FirebaseUser currentUser;
    EditText email,password;
    MaterialCardView loginbtn;
    TextView signUp;
    String userEmail,userPassword;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userAuth= FirebaseAuth.getInstance();
        currentUser = userAuth.getCurrentUser();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        signUp = findViewById(R.id.signUp);
        pd= new ProgressDialog(this);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail = email.getText().toString().trim();
                userPassword = password.getText().toString().trim();
                if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
                    email.setError("Email cannot be blank");
                    password.setError("Password cannot be blank");
                } else {
                    pd.setMessage("Logging in...");
                    pd.show();
                    signIn(userEmail, userPassword);
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }

    private void signIn(String userEmail, String userPassword) {
        userAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_SHORT).show();
                    Intent main=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(main);
                    LoginActivity.this.finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Login Unsuccessful",Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();

            }
    });


}
}