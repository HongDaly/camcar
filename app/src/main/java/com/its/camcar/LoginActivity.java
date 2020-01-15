package com.its.camcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText edtUsername;
    private EditText edtPassword;
    private TextView tvCreateAccount;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(getApplicationContext());

        btnLogin = findViewById(R.id.btn_login);
        tvCreateAccount = findViewById(R.id.tv_create_account);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);


        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);

//        check if has user (driver/customer)
        checkCurrentUser();


    }

    private void checkCurrentUser(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if( id == btnLogin.getId()){
//            verify username and password with server
            if(!edtPassword.getText().toString().isEmpty() && !edtUsername.getText().toString().isEmpty()){
                firebaseAuth.
                        signInWithEmailAndPassword(edtUsername.getText().toString(),edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                if(authResult.getUser() != null){
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                }else{
                                    Toast.makeText(getApplicationContext(),"Username and password incorrect",Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Username and password incorrect",Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(),"Please fill information",Toast.LENGTH_LONG).show();
            }

//            start to main_activity

        }else if( id == tvCreateAccount.getId()){
//            start register activity
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }
    }
}
