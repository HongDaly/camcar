package com.its.camcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.its.camcar.R;
import com.its.camcar.model.Schedule;
import com.its.camcar.view_holder.ScheduleViewHolder;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {


    private Context context;
    private ArrayList<Schedule> schedules;

    public ScheduleAdapter(Context context, ArrayList<Schedule> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    public void updateSchedule(ArrayList<Schedule> schedules){
        this.schedules = schedules;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.schedule_view_holder,parent,false);
        return new ScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }
}
