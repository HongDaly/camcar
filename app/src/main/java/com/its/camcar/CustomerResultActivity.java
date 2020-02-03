package com.its.camcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.camcar.adapter.SearchResultAdapter;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.Schedule;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomerResultActivity extends AppCompatActivity {
    private String locationFrom = "";
    private String locationDestination = "";

    private FirebaseHelper firebaseHelper;

    private TextView tvDirection;
    private RecyclerView rcSearchResult;
    private ProgressBar pbLoading;
    private ArrayList<Schedule> schedules = new ArrayList<>();


    private SearchResultAdapter resultAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_result);

        firebaseHelper = new FirebaseHelper(getApplicationContext());
        tvDirection = findViewById(R.id.acr_tv_direction);
        rcSearchResult = findViewById(R.id.acr_rc_search_result);
        pbLoading = findViewById(R.id.acr_loading);


        if(getIntent().getExtras() != null){

            locationFrom = getIntent().getExtras().getString("location_from");
            locationDestination = getIntent().getExtras().getString("location_destination");

//          search
            tvDirection.setText(locationFrom + " -> "+locationDestination);
            pbLoading.setVisibility(View.VISIBLE);

            firebaseHelper.search(locationFrom,locationDestination)
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
                            if(queryDocumentSnapshots != null){
                                for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                                    Schedule schedule = snapshot.toObject(Schedule.class);
                                    schedules.add(schedule);
                                }
                            }
                            initSearchResults(schedules);
                            pbLoading.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }
    private void initSearchResults(ArrayList<Schedule> schedules){

        resultAdapter = new SearchResultAdapter(getApplicationContext(),schedules);
        rcSearchResult.setAdapter(resultAdapter);

    }
}
