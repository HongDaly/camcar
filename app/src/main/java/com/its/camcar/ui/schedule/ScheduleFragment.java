package com.its.camcar.ui.schedule;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.camcar.R;
import com.its.camcar.AddScheduleActivity;
import com.its.camcar.adapter.ScheduleAdapter;
import com.its.camcar.model.Schedule;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment implements View.OnClickListener {


    private RecyclerView rcScheduleList;
    private FloatingActionButton fbtnAddSchedule;
    private ArrayList<Schedule> schedules = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;
    private ScheduleAdapter scheduleAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_schedule, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();

        fbtnAddSchedule = v.findViewById(R.id.fs_fbtn_add_schedule);
        rcScheduleList = v.findViewById(R.id.fs_rc_list);


        scheduleAdapter = new ScheduleAdapter(getContext(),schedules);
        layoutManager = new LinearLayoutManager(getContext());



        rcScheduleList.setAdapter(scheduleAdapter);
        rcScheduleList.setLayoutManager(layoutManager);


        fbtnAddSchedule.setOnClickListener(this);
        getSchedule();


        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == fbtnAddSchedule.getId()){
            startActivity(new Intent(getContext(), AddScheduleActivity.class));
        }
    }

    private void getSchedule(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getUid();
        firebaseFirestore.
                collection("schedule").
                document(userId).collection("list").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null){
                   for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                       Schedule schedule = snapshot.toObject(Schedule.class);
                       schedules.add(schedule);
                   }
                   if(schedules.size() !=0 ){
                       scheduleAdapter.updateSchedule(schedules);
                   }
                }
            }
        });


    }

}
