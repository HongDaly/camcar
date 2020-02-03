package com.its.camcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.its.camcar.R;
import com.its.camcar.model.Schedule;
import com.its.camcar.view_holder.SearchResultViewHolder;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private Context context;
    private ArrayList<Schedule> schedules;

    public SearchResultAdapter(Context context,ArrayList<Schedule> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_result_view_holder,parent,false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        holder.init(schedules.get(position));
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }
}
