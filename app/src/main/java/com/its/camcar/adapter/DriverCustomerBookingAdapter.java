package com.its.camcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.its.camcar.R;
import com.its.camcar.model.Booking;
import com.its.camcar.view_holder.DriverCustomerBookingViewHolder;

import java.util.ArrayList;

public class DriverCustomerBookingAdapter extends RecyclerView.Adapter<DriverCustomerBookingViewHolder> {

    private ArrayList<Booking> bookings;
    private Context context;

    public DriverCustomerBookingAdapter(ArrayList<Booking> bookings,Context context){
        this.bookings = bookings;
        this.context = context;
    }


    @NonNull
    @Override
    public DriverCustomerBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.driver_booked_view_holder,parent,false);
        return new DriverCustomerBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverCustomerBookingViewHolder holder, int position) {
        holder.init(bookings.get(position));
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }
}
