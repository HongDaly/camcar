package com.its.camcar.ui.schedule;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.its.camcar.R;
import com.its.camcar.ScheduleActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment implements View.OnClickListener {


    private RecyclerView rcScheduleList;
    private FloatingActionButton fbtnAddSchedule;


    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_schedule, container, false);


        fbtnAddSchedule = v.findViewById(R.id.fs_fbtn_add_schedule);



        fbtnAddSchedule.setOnClickListener(this);



        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == fbtnAddSchedule.getId()){
            startActivity(new Intent(getContext(), ScheduleActivity.class));
        }
    }
}
