package com.its.camcar.view_holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.its.camcar.CustomerBookingActivity;
import com.its.camcar.R;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.Schedule;
import com.its.camcar.model.User;

public class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tvStartTime;
    private TextView tvArriveTime;
    private TextView tvPrice;
    private TextView tvDriverName;
    private TextView tvDriverPhone;
    private FirebaseHelper firebaseHelper;
    private Schedule schedule;


    public SearchResultViewHolder(@NonNull View itemView) {
        super(itemView);
        firebaseHelper = new FirebaseHelper(itemView.getContext());
        tvStartTime = itemView.findViewById(R.id.crvh_tv_start_time);
        tvArriveTime = itemView.findViewById(R.id.crvh_tv_arrive_time);
        tvPrice = itemView.findViewById(R.id.crvh_tv_price);
        tvDriverPhone = itemView.findViewById(R.id.crvh_tv_phone);
        tvDriverName = itemView.findViewById(R.id.crvh_tv_driver_name);


        itemView.setOnClickListener(this);

    }

    public void init(Schedule schedule){
        this.schedule = schedule;
        tvPrice.setText(schedule.getPrice());
        tvArriveTime.setText(schedule.getArrivedTime());
        tvStartTime.setText(schedule.getStartTime());
        getDriverInfo(schedule.getUserId());

    }
    private void getDriverInfo(String driverId){
        firebaseHelper.getUser(driverId)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == itemView.getId()){
            Intent intent = new Intent(itemView.getContext(), CustomerBookingActivity.class);
            intent.putExtra("schedule",schedule);
            intent.putExtra("driver_name",tvDriverName.getText().toString());
            intent.putExtra("driver_phone",tvDriverPhone.getText().toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            itemView.getContext().startActivity(intent);
        }
    }
}
