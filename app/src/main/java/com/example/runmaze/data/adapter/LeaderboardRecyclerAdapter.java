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

public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerAdapter.ViewHolder> {

    ArrayList<LeaderboardItem> arrayList;


    public LeaderboardRecyclerAdapter(ArrayList<LeaderboardItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_leaderboard, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardItem leaderboardItem = arrayList.get(position);
        holder.serial_no.setText(String.format("%d", leaderboardItem.getSerialNo()));
        holder.name.setText(CommonUtils.TitleCase(leaderboardItem.getName()));
        holder.activity_count.setText(String.format("%d", leaderboardItem.getActivityCount()));
        holder.distance.setText(String.format("%.2f", leaderboardItem.getDistance()));

        switch (leaderboardItem.getActivityType()) {
            case "Run":
                holder.activityImage.setImageResource(R.drawable.run);
                break;
            case "Ride":
                holder.activityImage.setImageResource(R.drawable.ride);
                break;
            case "Swim":
                holder.activityImage.setImageResource(R.drawable.swim);
                break;
            case "Walk":
                holder.activityImage.setImageResource(R.drawable.walk);
                break;
            case "Yoga":
                holder.activityImage.setImageResource(R.drawable.yoga);
                break;
        }
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
          TextView serial_no, name, activity_count, distance;
          ImageView activityImage;

          public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serial_no = (TextView) itemView.findViewById(R.id.list_gd_serial_no);
            name = (TextView) itemView.findViewById(R.id.list_gd_name);
            activity_count = (TextView) itemView.findViewById(R.id.list_gd_activity_count);
            distance = (TextView) itemView.findViewById(R.id.list_gd_distance);
              activityImage= (ImageView) itemView.findViewById(R.id.imageActivity);

        }

    }


}

