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

public class AdminActivity extends AppCompatActivity {

    private TextInputEditText userNameEdt,pwdEdt;
    private Button loginBtn;
    private ProgressBar loadingPB;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userNameEdt = findViewById(R.id.idEdtUserName);
        pwdEdt = findViewById(R.id.idEdtPwd);
        loginBtn = findViewById(R.id.idBtnLogin);
        loadingPB = findViewById(R.id.idPBLoading);
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting data from textfields
                loadingPB.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();

                // Check if the entered username and password match your desired values
                if (userName.equals("sir@gmail.com") && pwd.equals("Si123456")) {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(AdminActivity.this, "Login successfully..", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AdminActivity.this, MainActivity2.class);
                    startActivity(i);
                    finish();
                } else {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(AdminActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}