package com.its.camcar.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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


    public void createAccount(final User user){
        firebaseAuth
                .createUserWithEmailAndPassword(user.getPhone(),user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
//                            if role customer
//                          Start to MainActivity
//                            else go to doc verify diver (ID Card)
//                            Create user
                            if(task.getResult() != null && task.getResult().getUser()!=null){

                                FirebaseHelper firebaseHelper = new FirebaseHelper(context);
                                user.setId(task.getResult().getUser().getUid());

                                firebaseHelper.addUserToFirestore(user);
                                if(user.getRole().toLowerCase().equals("customer")){
                                    Intent intent = new Intent(context,MainActivity.class);
                                    intent.putExtra("user",user);
                                    context.startActivity(intent);
                                }else{
                                    Intent intent = new Intent(context,MainActivity.class);
                                    intent.putExtra("user",user);
                                    context.startActivity(intent);
                                }
                            }
                        }else {
                            Log.d("Create User","Error");
                        }
                    }
                });
    }
}
