package com.its.camcar.ui.customer_home;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.camcar.CustomerResultActivity;
import com.its.camcar.R;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.Schedule;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerHomeFragment extends Fragment implements View.OnClickListener {

    private AutoCompleteTextView actvFrom;
    private AutoCompleteTextView actvDistination;
    private Button btnSearch;

    private FirebaseHelper firebaseHelper;

    public CustomerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_customer_home, container, false);

        actvFrom = v.findViewById(R.id.fch_actv_from);
        actvDistination = v.findViewById(R.id.fch_actv_destination);
        btnSearch = v.findViewById(R.id.fch_btn_search);

        firebaseHelper = new FirebaseHelper(getContext());
//        get location sh

        getSchedule();



        btnSearch.setOnClickListener(this);

        return  v;
    }



    private void getSchedule(){
        firebaseHelper.getSchedules().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<String> locationsFrom = new ArrayList<>();
                ArrayList<String> locationsDestination = new ArrayList<>();
                if(queryDocumentSnapshots != null){
                    for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                        Schedule schedule = snapshot.toObject(Schedule.class);
                        String from =
                                        schedule.getStartDistrict()+","+
                                        schedule.getStartProvince();

                        String destination =
                                        schedule.getArrivedDistrict()+","+
                                        schedule.getArrivedProvince();


                        locationsFrom.add(from);
                        locationsDestination.add(destination);
                    }

                    HashSet<String> lfs = new HashSet<>(locationsFrom);
                    locationsFrom.clear();
                    locationsFrom.addAll(lfs);

                    HashSet<String> lds = new HashSet<>(locationsDestination);
                    locationsDestination.clear();
                    locationsDestination.addAll(lds);


                    initAutoCompleteTextView(locationsFrom,locationsDestination);
                }
            }
        });
    }
    private void initAutoCompleteTextView(ArrayList<String> lFroms,ArrayList<String> lDestination){
        ArrayAdapter<String> lFromAdapter =
                new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,lFroms);
        ArrayAdapter<String> lDestinationAdapter =
                new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,lDestination);

        actvFrom.setAdapter(lFromAdapter);
        actvDistination.setAdapter(lDestinationAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == btnSearch.getId()){
//
            if(actvFrom.getText().toString().isEmpty() || actvDistination.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"Please choose direction! Thank You",Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(getContext(), CustomerResultActivity.class);
                intent.putExtra("location_from",actvFrom.getText().toString());
                intent.putExtra("location_destination",actvDistination.getText().toString());
                startActivity(intent);
            }
        }
    }
}
