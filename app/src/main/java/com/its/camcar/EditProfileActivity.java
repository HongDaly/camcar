package com.its.camcar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.its.camcar.helper.PermissionController;
import com.its.camcar.model.User;
import com.master.permissionhelper.PermissionHelper;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivProfile;
    private EditText edtFullName;
    private EditText edtPhone;
    private Button btnUpdate;
    private AlertDialog dialog ;
    private AlertDialog.Builder builder;
    private PermissionController permissionController;
    private Uri image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ivProfile = findViewById(R.id.aep_iv_profile);
        edtFullName = findViewById(R.id.aep_edt_full_name);
        edtPhone = findViewById(R.id.aep_edt_phone);
        btnUpdate = findViewById(R.id.aep_btn_update);
        permissionController = new PermissionController(this);

        initUI();



        ivProfile.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

    }


    private void initUI(){

        if(getIntent().getExtras() !=null){
            String imageUrl = getIntent().getExtras().getString("image_url");
            if(!imageUrl.equals("none")){
                Glide.with(getApplicationContext()).load(imageUrl).into(ivProfile);
            }
            String fullname = getIntent().getExtras().getString("fullname");
            edtFullName.setText(fullname);

            String phone = getIntent().getExtras().getString("phone");
            edtPhone.setText(phone);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == btnUpdate.getId()){
//            update user information
            Map<String,Object> objectMap = new HashMap<>();
            objectMap.put("fullName",edtFullName.getText().toString());
            objectMap.put("phone",edtPhone.getText().toString());


        }else if(id == ivProfile.getId()){
            initPermission();
        }
    }

    private void initPermission(){
        permissionController.requestPermission().request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                optionImagePicker();
            }

            @Override
            public void onIndividualPermissionGranted(@NotNull String[] strings) {
                Toast.makeText(getApplicationContext(),"Please allow all permission to access this feature! thank you",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(getApplicationContext(),"Please allow permissions to access this feature! thank you",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionDeniedBySystem() {
                Toast.makeText(getApplicationContext(),"Permissions has been denied by device system! thank you",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void optionImagePicker(){
        builder = new AlertDialog.Builder(this);
        String[] option = new String[]{"Gallery","Camera"};
        builder.setTitle("Pick Option");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    openGallery();
                }else
                {
                    openCamera();
                }
            }
        });
        dialog = builder.create();

        dialog.show();
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,110);
    }
    private void openCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 111);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 111 && resultCode == RESULT_OK){
            if(data != null){
                Bitmap a = (Bitmap)  data.getExtras().get("data");
                image = getImageUri(a);
                ivProfile.setImageBitmap(a);
            }
        }else if(requestCode == 110 && resultCode == RESULT_OK){
            if(data != null){

                image = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(image);
                    Bitmap a = BitmapFactory.decodeStream(inputStream);
                    ivProfile.setImageBitmap(a);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
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
}
