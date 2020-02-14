package com.its.camcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.User;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseHelper firebaseHelper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseHelper =  new FirebaseHelper(getApplicationContext());

        checkCurrentUser();
    }


    private void checkCurrentUser(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            Task<DocumentSnapshot> task = firebaseHelper.getUser(firebaseUser.getUid());
            task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot != null){
                        User user = documentSnapshot.toObject(User.class);
                        Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }else{
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
                    finish();
                }
            }, 3000);
        }
    }
}
