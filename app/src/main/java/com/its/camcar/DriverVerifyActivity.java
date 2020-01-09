package com.its.camcar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class DriverVerifyActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivIDCard;
    Button btnVerify;
    private User user;
    private FirebaseHelper firebaseHelper;
    private Uri image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_verify);

        if(getIntent().getExtras() != null){
            user = (User) getIntent().getExtras().getSerializable("user");
        }


        firebaseHelper = new FirebaseHelper(getApplicationContext());


        ivIDCard = findViewById(R.id.verify_iv_id_card);
        btnVerify = findViewById(R.id.verify_btn_verify);

        ivIDCard.setOnClickListener(this);
        btnVerify.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == ivIDCard.getId()){
//            OpenCamera
            if(checkPermissionStorage()){
                openCamera();
            }else {
                Toast.makeText(getApplicationContext(),"Please allow permission to use this app",Toast.LENGTH_LONG).show();
            }


        }else if(id == btnVerify.getId()){
//  upload image
//  save user to Firestore
//  Start AddScheduleActivity
            firebaseHelper.saveUser(image,user);

            startActivity(new Intent(DriverVerifyActivity.this,MainActivity.class));
        }
    }


    private void openCamera(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1010);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1010){
            if(resultCode == RESULT_OK){
                if(data != null){
                    Bitmap a = (Bitmap)  data.getExtras().get("data");
                    image = getImageUri(a);
                    ivIDCard.setImageBitmap(a);
                }

            }
        }
    }
    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private boolean checkPermissionStorage(){

        if(ContextCompat.checkSelfPermission(getApplicationContext()
        , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(
                        this,new String[]{
                          Manifest.permission.READ_EXTERNAL_STORAGE,
                          Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        101
                );
                return false;

            }
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 101){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else {
                Toast.makeText(getApplicationContext(),"Please allow permission to use this app",Toast.LENGTH_LONG).show();

            }
        }

    }
}


