package com.its.camcar.ui.customer;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.camcar.R;
import com.its.camcar.adapter.DriverCustomerBookingAdapter;
import com.its.camcar.helper.FireAuthHelper;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.Booking;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment {

    private RecyclerView rcCustomerList;
    private DriverCustomerBookingAdapter driverCustomerBookingAdapter;
    private FirebaseHelper firebaseHelper;
    private FireAuthHelper fireAuthHelper;

    public CustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_customer, container, false);

        rcCustomerList = view.findViewById(R.id.fc_rl_list_book);
        firebaseHelper = new FirebaseHelper(getContext());
        fireAuthHelper = new FireAuthHelper(getContext());
        initDataList();
        return view;
    }



    private void initDataList(){
        String driverId = fireAuthHelper.getCurrentUser().getUid();
        firebaseHelper.getBookingCustomer(driverId)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Booking> bookings = new ArrayList<>();
                        if(queryDocumentSnapshots != null){
                            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                Booking booking = documentSnapshot.toObject(Booking.class);
                                if(booking !=null){
                                    bookings.add(booking);
                                }
                            }
                            if(bookings.size() != 0){
                                driverCustomerBookingAdapter = new DriverCustomerBookingAdapter(bookings,getContext());
                                rcCustomerList.setAdapter(driverCustomerBookingAdapter);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Eroo", "onFailure: "+e.getMessage());
                Toast.makeText(getContext(),"Something went wrong"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
