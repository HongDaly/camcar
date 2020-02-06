package com.its.camcar.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.its.camcar.R;
import com.its.camcar.model.Booking;
import com.its.camcar.view_holder.CustomerHistoryViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class CustomerHistoryAdapter extends RecyclerView.Adapter<CustomerHistoryViewHolder> {

    private ArrayList<Booking> bookings;
    private Context context;


    public CustomerHistoryAdapter(ArrayList<Booking> bookings, Context context) {
        this.bookings = bookings;
        this.context = context;
    }


    @NonNull
    @Override
    public CustomerHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_booking_history_view_holder,parent,false);
        return new CustomerHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHistoryViewHolder holder, int position) {
        holder.init(bookings.get(position));
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }
}
