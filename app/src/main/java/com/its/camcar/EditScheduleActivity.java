package com.its.camcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.its.camcar.model.Schedule;

public class EditScheduleActivity extends AppCompatActivity {

    private EditText edtStartProvince;
    private EditText edtStartDistrict;
    private EditText edtStartCommune;
    private EditText edtStartExactLocation;
    private EditText edtArriveProvince;
    private EditText edtArriveDistrict;
    private EditText edtArriveCommune;
    private EditText edtArriveExactLocation;
    private EditText edtPrice;
    private EditText edtStartTime;
    private EditText edtArriveTime;

    private Schedule schedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);


        if(getIntent().getExtras() != null){
            schedule = (Schedule)getIntent().getExtras().getSerializable("schedule");
        }else{
            schedule = new Schedule();
        }

        edtStartProvince = findViewById(R.id.esd_edt_from_province);
        edtStartDistrict = findViewById(R.id.esd_edt_from_district);
        edtStartCommune = findViewById(R.id.esd_edt_from_commune);
        edtStartExactLocation = findViewById(R.id.esd_edt_from_exact_location);

        edtArriveProvince = findViewById(R.id.esd_edt_to_province);
        edtArriveDistrict = findViewById(R.id.esd_edt_to_district);
        edtArriveCommune = findViewById(R.id.esd_edt_to_commune);
        edtArriveExactLocation = findViewById(R.id.esd_edt_to_exact_location);

        edtStartTime = findViewById(R.id.esd_edt_start_time);
        edtArriveTime = findViewById(R.id.esd_edt_finish_time);
        edtPrice = findViewById(R.id.esd_edt_price);
    }
}
