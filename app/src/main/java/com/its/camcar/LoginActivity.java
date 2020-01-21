package com.its.camcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.its.camcar.helper.CamcarHelper;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText edtUsername;
    private EditText edtPassword;
    private TextView tvCreateAccount;
    private FirebaseAuth firebaseAuth;
    private FirebaseHelper firebaseHelper;

    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(getApplicationContext());

        btnLogin = findViewById(R.id.btn_login);
        tvCreateAccount = findViewById(R.id.tv_create_account);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        progressBar = findViewById(R.id.pb_login);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseHelper = new FirebaseHelper(getApplicationContext());

        btnLogin.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);

//        check if has user (driver/customer)
        checkCurrentUser();

        progressBar.setVisibility(View.INVISIBLE);



    }




    private void checkCurrentUser(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            Task<DocumentSnapshot> task = firebaseHelper.getUser(firebaseUser.getUid());
            task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if(documentSnapshot != null){
                        User user = documentSnapshot.toObject(User.class);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                }
            });
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if( id == btnLogin.getId()){
            progressBar.setVisibility(View.VISIBLE);
//            verify username and password with server
            if(!edtPassword.getText().toString().isEmpty() && !edtUsername.getText().toString().isEmpty()){
                firebaseAuth.
                        signInWithEmailAndPassword(edtUsername.getText().toString(),edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                if(authResult.getUser() != null){
//                                    getUser
                                    Task<DocumentSnapshot> task = firebaseHelper.getUser(authResult.getUser().getUid());
                                    task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            if(documentSnapshot != null){
                                                User user = documentSnapshot.toObject(User.class);
                                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                                intent.putExtra("user",user);
                                                startActivity(intent);
                                            }
                                        }
                                    });
//                                    progressBar.setVisibility(View.INVISIBLE);


                                }else{
                                    Toast.makeText(getApplicationContext(),"Username and password incorrect",Toast.LENGTH_LONG).show();
                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getApplicationContext(),"Username and password incorrect",Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                progressBar.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(),"Please fill information",Toast.LENGTH_LONG).show();
            }

//            start to main_activity

        }else if( id == tvCreateAccount.getId()){
//            start register activity
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }
    }



}
