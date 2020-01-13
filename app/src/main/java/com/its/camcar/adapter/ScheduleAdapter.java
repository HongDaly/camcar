package com.its.camcar.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.its.camcar.EditScheduleActivity;
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

        final Schedule schedule = schedules.get(position);
        holder.init(schedule);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] options = new String[]{"View","Edit","Delete"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Options");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            // start schedule info
                        }else if(which == 1){
//                            start schedule edit
                            Intent intent = new Intent(context, EditScheduleActivity.class);

                            intent.putExtra("schedule",schedule);
                            context.startActivity(intent);


                        }else{
//                            delete

                        }
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }
}
