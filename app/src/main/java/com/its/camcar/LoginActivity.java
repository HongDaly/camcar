package com.its.camcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText edtUsername;
    private EditText edtPassword;
    private TextView tvCreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(getApplicationContext());

        btnLogin = findViewById(R.id.btn_login);
        tvCreateAccount = findViewById(R.id.tv_create_account);


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
//            start to main_activity
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }else if( id == tvCreateAccount.getId()){
//            start register activity
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }
    }
}
