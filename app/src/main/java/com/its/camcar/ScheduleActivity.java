package com.its.camcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScheduleActivity extends AppCompatActivity {


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

    private FloatingActionButton fBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        edtFromProvince = findViewById(R.id.sd_tv_from_province);
        edtFromCommune = findViewById(R.id.sd_tv_from_commune);


    }
}
