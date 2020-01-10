package com.its.camcar.view_holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.its.camcar.R;
import com.its.camcar.model.Schedule;


public class ScheduleViewHolder extends RecyclerView.ViewHolder {

    private TextView tvStartLocation;
    private TextView tvArriveLocation;
    private TextView tvTimeStart;
    private TextView tvTimeArrive;
    private TextView tvPrice;


    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);

        tvPrice = itemView.findViewById(R.id.svh_tv_price);
        tvStartLocation = itemView.findViewById(R.id.svh_tv_start);
        tvArriveLocation = itemView.findViewById(R.id.svh_tv_arrive);
        tvTimeStart = itemView.findViewById(R.id.svh_tv_start_time);
        tvTimeArrive = itemView.findViewById(R.id.svh_tv_arrive_time);



    }

    public void init(Schedule schedule){

        String arriveLocation = "Arrive Location : " + schedule.getArrivedExactLocation() + " ," +
                                schedule.getArrivedCommune() + " ,"+
                                schedule.getArrivedDistrict() + " ,"+
                                schedule.getArrivedProvince();
        String startLocation = "Start Location : " + schedule.getStartExactLocation() + " ,"+
                                schedule.getStartCommune()+" ,"+
                                schedule.getStartDistrict() + " ,"+
                                schedule.getStartProvince();


        tvStartLocation.setText(startLocation);
        tvArriveLocation.setText(arriveLocation);
        tvTimeStart.setText("Start Time : "+schedule.getStartTime());
        tvTimeArrive.setText("Arrive Time : "+schedule.getArrivedTime());
        tvPrice.setText("Price : "+schedule.getPrice());

    }

}
