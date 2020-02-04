package com.its.camcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.its.camcar.model.Schedule;

public class CustomerBookingActivity extends AppCompatActivity {

    private TextView tvDriverName;
    private TextView tvDriverPhone;
    private TextView tvPrice;
    private TextView tvSTime;
    private TextView tvATime;
    private TextView tvSLocation;
    private TextView tvALocation;
    private DatePicker dbDatePicker;
    private Spinner spNumOfPerson;
    private EditText edtCustomerPhone;
    private Button btnBooking;

    private Schedule schedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_booking);

        tvDriverName = findViewById(R.id.acb_tv_driver_name);
        tvDriverPhone = findViewById(R.id.acb_tv_driver_phone);
        tvPrice = findViewById(R.id.acb_tv_price);

        tvSTime = findViewById(R.id.acb_tv_start_time);
        tvATime = findViewById(R.id.acb_tv_arrive_time);

        tvSLocation = findViewById(R.id.acb_tv_start_location);
        tvALocation = findViewById(R.id.acb_tv_arrive_location);

        dbDatePicker = findViewById(R.id.acb_dp_pick_date);
        spNumOfPerson = findViewById(R.id.acb_sp_number_of_person);

        edtCustomerPhone = findViewById(R.id.acb_edt_phone);
        btnBooking = findViewById(R.id.acb_btn_booking);

//
        initUI();
    }



    private void initUI(){
        if(getIntent().getExtras() != null){
            schedule = (Schedule)getIntent().getExtras().getSerializable("schedule");
            String driverName = getIntent().getExtras().getString("driver_name");
            String driverPhone = getIntent().getExtras().getString("driver_phone");

            if(schedule != null){
                String sLocation = schedule.getStartExactLocation() + ", "+
                        schedule.getStartCommune() +", "+
                        schedule.getStartDistrict() + ", "+
                        schedule.getStartProvince();
                String aLocation = schedule.getArrivedExactLocation() +", "+
                                   schedule.getArrivedCommune() + ", "+
                                    schedule.getArrivedDistrict() + ", "+
                                    schedule.getArrivedProvince() ;

                tvSTime.setText(schedule.getStartTime());
                tvATime.setText(schedule.getArrivedTime());
                tvSLocation.setText(sLocation);
                tvALocation.setText(aLocation);
                tvPrice.setText(schedule.getPrice());
            }
            if(driverName != null) tvDriverName.setText(driverName);
            if(driverPhone != null) tvDriverPhone.setText(driverPhone);
        }
    }
}
