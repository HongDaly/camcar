package com.its.camcar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.Schedule;

import java.io.ByteArrayOutputStream;

public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseHelper firebaseHelper;

    private EditText edtFromProvince;
    private EditText edtFromDistrict;
    private EditText edtFromCommune;
    private EditText edtFromExactLocation;
    private EditText edtStartTime;
    private EditText edtArriveTime;
    private EditText edtPrice;


    private EditText edtToProvince;
    private EditText edtToDistrict;
    private EditText edtToCommune;
    private EditText edtToExactLocation;

    private FloatingActionButton fBtnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        firebaseHelper = new FirebaseHelper(getApplicationContext());

//start location
        edtFromProvince = findViewById(R.id.sd_edt_from_province);
        edtFromCommune = findViewById(R.id.sd_edt_from_commune);
        edtFromDistrict = findViewById(R.id.sd_edt_from_district);
        edtFromExactLocation = findViewById(R.id.sd_edt_from_exact_location);
// schedule

        edtStartTime = findViewById(R.id.sd_edt_start_time);
        edtArriveTime = findViewById(R.id.sd_edt_finish_time);
        edtPrice = findViewById(R.id.sd_edt_price);


//arrive location
        edtToProvince = findViewById(R.id.sd_edt_to_province);
        edtToDistrict = findViewById(R.id.sd_edt_to_district);
        edtToCommune = findViewById(R.id.sd_edt_to_commune);
        edtToExactLocation = findViewById(R.id.sd_edt_to_exact_location);


        fBtnAdd = findViewById(R.id.sd_fbtn_add);
        fBtnAdd.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

       if(id == fBtnAdd.getId()){
            String userId = "-1";
            if(FirebaseAuth.getInstance().getCurrentUser() != null)
                 userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Schedule schedule =
                    new Schedule(
                            edtPrice.getText().toString(),
                            edtStartTime.getText().toString(),
                            edtArriveTime.getText().toString(),
                            edtFromProvince.getText().toString(),
                            edtFromDistrict.getText().toString(),
                            edtFromCommune.getText().toString(),
                            edtFromExactLocation.getText().toString(),
                            edtToProvince.getText().toString(),
                            edtToDistrict.getText().toString(),
                            edtToCommune.getText().toString(),
                            edtToExactLocation.getText().toString(),
                            userId
                    );

            firebaseHelper.addScheduleToFirestore(schedule);

            finish();
        }
    }
//
//    private void openGallery(){
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setType("image/*");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivityForResult(intent,20202);
//    }
//
//
//    private void openCamera(){
//
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, 10101);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 10101 || requestCode==20202 ){
//            if(resultCode == RESULT_OK){
//                if(data != null && data.getExtras() != null){
//                    Bitmap a = (Bitmap)  data.getExtras().get("data");
//                    image = getImageUri(a);
//                    ivProfile.setImageBitmap(a);
//                }
//
//            }
//        }
//    }
//    public Uri getImageUri(Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
}
