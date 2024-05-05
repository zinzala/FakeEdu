package com.example.fakeedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText userNameEdt,pwdEdt,cnfPwdEdt;
    private Button registerBtn;
    private TextView loginTV;
    private ProgressBar loadingPB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userNameEdt = findViewById(R.id.idEdtUserName);
        pwdEdt = findViewById(R.id.idEdtPwd);
        cnfPwdEdt = findViewById(R.id.idEdtCnfPwd);
        registerBtn = findViewById(R.id.idBtnRegister);
        loginTV = findViewById(R.id.idTVLogin);
        loadingPB = findViewById(R.id.idPBLoading);

        mAuth = FirebaseAuth.getInstance();

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                String cnfPwd = cnfPwdEdt.getText().toString();

                // validation and creating new user with email and password

                if (!pwd.equals(cnfPwd)){
                    Toast.makeText(RegistrationActivity.this, "password mis-match", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)){
                    Toast.makeText(RegistrationActivity.this, "enter credentials...", Toast.LENGTH_SHORT).show();
                }else{
                    // creating a new user with email and password
                    mAuth.createUserWithEmailAndPassword(userName,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "you are Registered.", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                                startActivity(i);
                                //finish();
                                finishAffinity();
                            }else {
                                loadingPB.setVisibility(View.GONE);
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(RegistrationActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });
    }
}