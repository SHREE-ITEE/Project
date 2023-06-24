package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText email,password,fullName,userName;
    MaterialCardView signUpBtn;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        fullName=findViewById(R.id.fullName);
        userName=findViewById(R.id.userName);
        signUpBtn=findViewById(R.id.signUpBtn);
        firebaseAuth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
    signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=email.getText().toString().trim();
                String userPassword=password.getText().toString().trim();
                String userUName=userName.getText().toString().trim();
                String userFullName=fullName.getText().toString().trim();

                if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)||TextUtils.isEmpty(userUName) ||TextUtils.isEmpty(userFullName)) {
                    email.setError("Email cannot be blank");
                    password.setError("Password cannot be blank");
                    fullName.setError("Full Name cannot be blank");
                    userName.setError("User Name cannot be blank");
                }
                else{
                    pd.setMessage("Signing in...");
                    pd.show();
                    register(userEmail,userPassword,userFullName,userUName);

                }
            }

        });
    }

    private void register(String userEmail, String userPassword, String userFullName, String userUName) {
        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid());
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("Name",userFullName);
                    hashMap.put("Email",userEmail);
                    hashMap.put("User Name",userUName);
                    hashMap.put("Password",userPassword);
                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("User name");
                            reference1.setValue(userUName).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    pd.dismiss();
                                    Toast.makeText(getApplicationContext(),"Registration successful",Toast.LENGTH_SHORT).show();
                                    Intent main=new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(main);
                                    RegisterActivity.this.finish();

                                }
                            });

                        }
                    });

                }
                else
                {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"Registration Unsuccessful",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}