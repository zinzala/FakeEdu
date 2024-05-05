package com.example.fakeedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

     private TextInputEditText userNameEdt,pwdEdt;
     private Button loginBtn;
     private ProgressBar loadingPB;
     private TextView registerTV;
     private TextView adminTV;
     private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEdt = findViewById(R.id.idEdtUserName);
        pwdEdt = findViewById(R.id.idEdtPwd);
        loginBtn = findViewById(R.id.idBtnLogin);
        loadingPB = findViewById(R.id.idPBLoading);
        registerTV = findViewById(R.id.idTVRegister);
        adminTV = findViewById(R.id.idTVAdmin);
        mAuth = FirebaseAuth.getInstance();

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });
        adminTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,AdminActivity.class);
                startActivity(i);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting data from textfiled
                loadingPB.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd)){
                    Toast.makeText(LoginActivity.this, "enter Credentials...", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    mAuth.signInWithEmailAndPassword(userName,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "login successfully..", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(i);
                                //finish();
                                finishAffinity();
                            }else {
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    // checking user is already login or not while opening app
    @Override
    protected void onStart() {
        super.onStart();
        // getting current user
        FirebaseUser user = mAuth.getCurrentUser();
        //  checking
        if (user != null){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}