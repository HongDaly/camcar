package com.its.camcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.its.camcar.helper.FireAuthHelper;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.Booking;
import com.its.camcar.model.Schedule;

public class CustomerBookingActivity extends AppCompatActivity implements View.OnClickListener {

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

    private FireAuthHelper fireAuthHelper;
    private FirebaseHelper firebaseHelper;


    private AlertDialog.Builder builder;
    private AlertDialog dialog;

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

        fireAuthHelper = new FireAuthHelper(getApplicationContext());
        firebaseHelper = new FirebaseHelper(getApplicationContext());


//
        initUI();
        initAlertLoading();

        btnBooking.setOnClickListener(this);
    }

    private void initAlertLoading(){
        builder = new AlertDialog.Builder(this);
        ProgressBar progressBar = new ProgressBar(this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(lp);

        builder.setView(progressBar);

        builder.setTitle("Booking....");

        dialog = builder.create();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == btnBooking.getId()){
//            get date from user input
            String getNumOfPerson = spNumOfPerson.getSelectedItem().toString();
            if(edtCustomerPhone.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Please input phone number",Toast.LENGTH_LONG).show();
            }else if(getNumOfPerson.toLowerCase().equals("number of person"))
            {
                Toast.makeText(getApplicationContext(),"Please select number of person",Toast.LENGTH_LONG).show();
            }else{
                dialog.show();

                Booking booking = new Booking();
                booking.setDriverId(schedule.getUserId());
                booking.setCustomerId(fireAuthHelper.getCurrentUser().getUid());
                booking.setScheduleId(schedule.getId());
                booking.setCustomerPhone(edtCustomerPhone.getText().toString());
                booking.setNumOfPerson(Integer.valueOf(getNumOfPerson));
                booking.setCreatedAt(System.currentTimeMillis());

                int day = dbDatePicker.getDayOfMonth();
                int month = dbDatePicker.getMonth();
                int year = dbDatePicker.getYear();
                String date = day +"-"+month+"-"+year;
                booking.setDate(date);

        //            store booking
        //
                firebaseHelper.addBooking(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(dialog.isShowing()) dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Booking succefully! Thank you",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
                        if(dialog.isShowing()) dialog.dismiss();
                    }
                })
                ;
            }
        }
    }
}
