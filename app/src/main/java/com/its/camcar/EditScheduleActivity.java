package com.its.camcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.its.camcar.model.Schedule;

public class EditScheduleActivity extends AppCompatActivity {

    private EditText edtStartProvince;
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

        edtStartProvince.setText(schedule.getStartProvince());
    }
}
