package com.example.ea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    TextView tvEmail, tvPassword;
    TextView tvLogin;



    FirebaseAuth mAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvLogin = findViewById(R.id.textLogin);
        tvEmail = findViewById(R.id.editTextEmailRegister);
        tvPassword = findViewById(R.id.editTextPasswordRegister);
        btnRegister = findViewById(R.id.buttonRegister);

        pd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String txtUsername = tvEmail.getText().toString();
//                String txtName = tvPassword.getText().toString();
                String txtEmail = tvEmail.getText().toString();
                String txtPassword = tvPassword.getText().toString();

                if (/*TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtName) || */TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (txtPassword.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(/*txtUsername , txtName , */txtEmail , txtPassword);
                }
            }
        });



    }

    private void registerUser(String txtEmail, String txtPassword) {
        pd.setMessage("Please Wail!");
        pd.show();
        mAuth.createUserWithEmailAndPassword(txtEmail , txtPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // cmt create user in cloud fire store

        // cmt create user in cloud fire store
    }
}