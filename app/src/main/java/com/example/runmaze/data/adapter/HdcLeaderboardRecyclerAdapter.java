package com.example.runmaze.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runmaze.R;
import com.example.runmaze.data.model.LeaderboardItem;
import com.example.runmaze.utils.CommonUtils;

import java.util.ArrayList;

public class HdcLeaderboardRecyclerAdapter extends RecyclerView.Adapter<HdcLeaderboardRecyclerAdapter.ViewHolder> {

    ArrayList<LeaderboardItem> arrayList;


    public HdcLeaderboardRecyclerAdapter(ArrayList<LeaderboardItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_hdc_leaderboard, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardItem leaderboardItem = arrayList.get(position);
        holder.serial_no.setText(String.format("%d", leaderboardItem.getSerialNo()));
        holder.name.setText(CommonUtils.TitleCase(leaderboardItem.getName()));
        holder.activity_count.setText(String.format("%d", leaderboardItem.getActivityCount()));
        holder.distance.setText(String.format("%.1f", leaderboardItem.getDistance()));
        holder.days.setText(String.format("%d", leaderboardItem.getDayReported()));


    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView serial_no, name, activity_count, distance,days;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serial_no = (TextView) itemView.findViewById(R.id.list_gd_serial_no1);
            name = (TextView) itemView.findViewById(R.id.list_gd_name1);
            activity_count = (TextView) itemView.findViewById(R.id.list_gd_activity_count1);
            distance = (TextView) itemView.findViewById(R.id.list_gd_distance1);
            days = (TextView) itemView.findViewById(R.id.list_gd_days_count1);


        }

    }


}

