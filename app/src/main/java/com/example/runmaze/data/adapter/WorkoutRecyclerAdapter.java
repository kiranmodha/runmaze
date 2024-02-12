package com.example.runmaze.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runmaze.R;
import com.example.runmaze.data.model.Workout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class WorkoutRecyclerAdapter extends RecyclerView.Adapter<WorkoutRecyclerAdapter.ViewHolder> {

    ArrayList<Workout> arrayList;
    OnWorkoutClickListener onWorkoutClickListener;

    public WorkoutRecyclerAdapter(ArrayList<Workout> arrayList, OnWorkoutClickListener onWorkoutClickListener) {
        this.arrayList = arrayList;
        this.onWorkoutClickListener = onWorkoutClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_workout, viewGroup, false);
        return new ViewHolder(view, onWorkoutClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout workout = arrayList.get(position);
        holder.activity_type.setText(workout.getActivityType());
        holder.date.setText(formatDateTimeToLocalTimeZone(workout.getDateTime()));
        holder.distance.setText(String.format("%.2f km", workout.getDistance()));
        holder.duration.setText(String.format("%02d:%02d:%02d", workout.getHH(), workout.getMM(), workout.getSS()));
        holder.pace.setText(workout.getPace());

        switch (workout.getActivityType()) {
            case "Run":
                holder.activityimage.setImageResource(R.drawable.run);
                break;
            case "Ride":
                holder.activityimage.setImageResource(R.drawable.ride);
                break;
            case "Swim":
                holder.activityimage.setImageResource(R.drawable.swim);
                break;
            case "Walk":
                holder.activityimage.setImageResource(R.drawable.walk);
                break;
            case "Yoga":
                holder.activityimage.setImageResource(R.drawable.yoga);
                break;
        }

    }

    //TODO same function also exists in RecentActivitiesFragment - need to move it in common util class
    public String formatDateTimeToLocalTimeZone(String inputDateTimeGMT) {
        // Following is the data taken from the database
        //String datetimeString = "27/12/2021 21:42 GMT";
        Date today = null;
      //  DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm z");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            // Convert string into Date
            today = df.parse(inputDateTimeGMT);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        DateFormat df1 = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm");
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        df1.setTimeZone(tz);
        String dateString = df1.format(today);

        return dateString;
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView activity_type, distance, duration, date, pace;
        ImageView delete, edit, activityimage;
        OnWorkoutClickListener onWorkoutListener;

        public ViewHolder(@NonNull View itemView, OnWorkoutClickListener onWorkoutClickListener) {
            super(itemView);
            this.onWorkoutListener = onWorkoutClickListener;

            activity_type = (TextView) itemView.findViewById(R.id.list_activity_type);
            distance = (TextView) itemView.findViewById(R.id.list_distance);
            duration = (TextView) itemView.findViewById(R.id.list_duration);
            date = (TextView) itemView.findViewById(R.id.list_date);
            pace = (TextView) itemView.findViewById(R.id.list_pace);
            activityimage = (ImageView) itemView.findViewById(R.id.imageActivity);


            // Later decided to make delete & edit button not required, so made it disappered
            // However the remaining code kept as it is for future reference
            delete = (ImageView) itemView.findViewById(R.id.list_delete);
            edit = (ImageView) itemView.findViewById(R.id.list_edit);

            delete.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);

            // Do not delete following commented block

/*            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onWorkoutClickListener.onEditClick(getAdapterPosition());
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onWorkoutClickListener.onDeleteClick(getAdapterPosition());
                }
            });*/


            // instead of edit image click, edit mode made available on clicking of whole itemview
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onWorkoutClickListener.onEditClick(getAdapterPosition());
                }
            });

        }

    }

    public interface OnWorkoutClickListener {
        void onEditClick(int Position);
        void onDeleteClick(int Position);
    }
}

