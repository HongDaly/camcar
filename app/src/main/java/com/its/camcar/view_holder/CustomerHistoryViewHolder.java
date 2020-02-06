package com.its.camcar.view_holder;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.its.camcar.R;
import com.its.camcar.helper.FireAuthHelper;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.Booking;
import com.its.camcar.model.Schedule;
import com.its.camcar.model.User;

import java.sql.Timestamp;
import java.util.Date;


public class CustomerHistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView tvDriverName;
    private TextView tvDriverPhone;
    private TextView tvStartLocation;
    private TextView tvArriveLocation;
    private TextView tvTimeStart;
    private TextView tvTimeArrive;
    private TextView tvPrice;
    private TextView tvPerson;
    private TextView tvTotalPrice;
    private TextView tvCreatedAt;
    private TextView tvDateOnGoing;
    private FirebaseHelper firebaseHelper;


    public CustomerHistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        tvPrice = itemView.findViewById(R.id.chvh_tv_price);
        tvStartLocation = itemView.findViewById(R.id.chvh_tv_start);
        tvArriveLocation = itemView.findViewById(R.id.chvh_tv_arrive);
        tvTimeStart = itemView.findViewById(R.id.chvh_tv_start_time);
        tvTimeArrive = itemView.findViewById(R.id.chvh_tv_arrive_time);


        tvDriverName = itemView.findViewById(R.id.chvh_tv_driver_name);
        tvDriverPhone = itemView.findViewById(R.id.chvh_tv_driver_phone);
        tvPerson = itemView.findViewById(R.id.chvh_tv_person);
        tvTotalPrice = itemView.findViewById(R.id.chvh_tv_total_price);
        tvDateOnGoing = itemView.findViewById(R.id.chvh_tv_date_going);
        tvCreatedAt = itemView.findViewById(R.id.chvh_tv_created_at);

        firebaseHelper = new FirebaseHelper(itemView.getContext());

    }

    public void init(final Booking booking){
        tvPerson.setText(String.valueOf(booking.getNumOfPerson()));
        tvDateOnGoing.setText(booking.getDate());

        Timestamp timestamp = new Timestamp(booking.getCreatedAt());
        Date date = new Date(timestamp.getTime());
        tvCreatedAt.setText(date.toString());

//        Driver Information
        firebaseHelper.getUser(booking.getDriverId()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot != null){
                    User user = documentSnapshot.toObject(User.class);
                    if(user != null){
                        tvDriverName.setText(user.getFullName());
                        tvDriverPhone.setText(user.getPhone());
                    }
                }
            }
        });
//        Get Schedule Information
        firebaseHelper.getScheduleByID(booking.getScheduleId()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot != null){
                    Schedule schedule = documentSnapshot.toObject(Schedule.class);
                    if(schedule != null){
                        String arriveLocation = schedule.getArrivedExactLocation() + " ," +
                                schedule.getArrivedCommune() + " ,"+
                                schedule.getArrivedDistrict() + " ,"+
                                schedule.getArrivedProvince();
                        String startLocation = schedule.getStartExactLocation() + " ,"+
                                schedule.getStartCommune()+" ,"+
                                schedule.getStartDistrict() + " ,"+
                                schedule.getStartProvince();


                        tvStartLocation.setText(startLocation);
                        tvArriveLocation.setText(arriveLocation);
                        tvTimeStart.setText(schedule.getStartTime());
                        tvTimeArrive.setText(schedule.getArrivedTime());
                        tvPrice.setText(schedule.getPrice());

                        String price = schedule.getPrice().replaceAll("[^0-9?!.]","");
                        float totalPrice = Float.valueOf(price)*booking.getNumOfPerson();
                        tvTotalPrice.setText(String.valueOf(totalPrice));
                    }
                }
            }
        });
    }
}
