package com.its.camcar.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.its.camcar.DriverVerifyActivity;
import com.its.camcar.MainActivity;
import com.its.camcar.model.User;

public class FireAuthHelper {

    private FirebaseAuth firebaseAuth;
    private Context context;

    public FireAuthHelper(Context context){
        firebaseAuth = FirebaseAuth.getInstance();
        this.context = context;
    }


    public Task<AuthResult> createAccount(User user){
        return firebaseAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword());
    }


    public FirebaseUser getCurrentUser(){
        return firebaseAuth.getCurrentUser();
    }
}
