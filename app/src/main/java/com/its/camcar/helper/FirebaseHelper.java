package com.its.camcar.helper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.its.camcar.model.User;

public class FirebaseHelper {

    private FirebaseFirestore db;
    private Context context;
    private FirebaseStorage storage;

    public FirebaseHelper(Context context){
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        this.context = context;
    }

    public void saveUser(final Uri image, final User user){
        final String name = String.valueOf(System.currentTimeMillis()/1000);
        storage.getReference("card_id/"+name).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storage.getReference("card_id/"+name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("Image Url", "onSuccess: uri= "+ uri.toString());
//                        add user to firestore
                        user.setVerifyCardUrl(uri.toString());
                        addUserToFirestore(user);

                    }
                });
            }
        });
    }

    public void addUserToFirestore(User user){

        db.collection("users").add(user);
    }

//    add profile user
    public void addProfileUser(final Uri image){
        final String name = String.valueOf(System.currentTimeMillis()/1000);
        storage.getReference("profile/"+name).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storage.getReference("profile/"+name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("Image Url", "onSuccess: uri= "+ uri.toString());
//                        add user to firestore
                       String profileUrl = uri.toString();
                       String userId = FirebaseAuth.getInstance().getUid();
                        if (userId != null) {
                            db.collection("users").document(userId)
                                    .set(profileUrl, SetOptions.merge());
                        }

                    }
                });
            }
        });
    }

}
