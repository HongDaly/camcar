package com.its.camcar.ui.customer_history;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.camcar.R;
import com.its.camcar.adapter.CustomerHistoryAdapter;
import com.its.camcar.helper.FireAuthHelper;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.Booking;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerHistoryFragment extends Fragment {

    private RecyclerView rcHistory;
    private FirebaseHelper firebaseHelper;
    private FireAuthHelper fireAuthHelper;
    private ArrayList<Booking> bookings = new ArrayList<>();

    private CustomerHistoryAdapter customerHistoryAdapter;


    public CustomerHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_customer_history, container, false);

        rcHistory = v.findViewById(R.id.fch_rc_history);
        firebaseHelper = new FirebaseHelper(getContext());
        fireAuthHelper = new FireAuthHelper(getContext());

        initData();

        return  v;
    }


    private void initData(){
        String userId = fireAuthHelper.getCurrentUser().getUid();
        firebaseHelper.getHistory(userId).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots !=null){
                    for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        Booking booking = documentSnapshot.toObject(Booking.class);
                        bookings.add(booking);
                    }
                    if(bookings.size() != 0 ){
                        initHistoryList(bookings);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    private void initHistoryList(ArrayList<Booking> bookings){
        customerHistoryAdapter = new CustomerHistoryAdapter(bookings,getContext());
        rcHistory.setAdapter(customerHistoryAdapter);
    }

}
