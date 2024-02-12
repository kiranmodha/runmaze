package com.example.runmaze;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runmaze.data.DatabaseHandler;
import com.example.runmaze.utils.Settings;
import com.example.runmaze.data.model.Workout;
import com.example.runmaze.data.adapter.WorkoutRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutListFragment extends Fragment implements WorkoutRecyclerAdapter.OnWorkoutClickListener {

    ArrayList<Workout> arrayList;
    RecyclerView recyclerView;
    FloatingActionButton addButton;
    DatabaseHandler db;
    View fragmentView;

    Settings settings;
    int mAthleteId;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public WorkoutListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    public static WorkoutListFragment newInstance(String param1, String param2) {
        WorkoutListFragment fragment = new WorkoutListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_workout_list, container, false);


        db = new DatabaseHandler(getContext());

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.list_recyclerview);
        addButton = (FloatingActionButton) fragmentView.findViewById(R.id.list_addButton);
        arrayList = new ArrayList<Workout>();

        //SharedPreferences settings = getContext().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        //mAthleteId = settings.getInt("id", 0);

        settings = new Settings(getContext());
        mAthleteId = settings.getAthleteId();

        displayWorkouts();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(fragmentView).navigate(R.id.action_listFragment_to_workoutFragment);
            }
        });

        return fragmentView;
    }

    public void displayWorkouts() {
        arrayList = new ArrayList<>(db.workoutTable.getWorkouts(-1, mAthleteId));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // WorkoutRecyclerAdapter adapter = new WorkoutRecyclerAdapter(getContext(), getActivity(), arrayList, this);
        WorkoutRecyclerAdapter adapter = new WorkoutRecyclerAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEditClick(int Position) {
       // Log.d("MyLog", String.format("Edit Clicked on position # %d", Position));
        Bundle bundle = new Bundle();
        bundle.putString("id",String.format("%d",arrayList.get(Position).getId()));
        Navigation.findNavController(fragmentView).navigate(R.id.action_listFragment_to_workoutFragment,bundle);
    }

    @Override
    public void onDeleteClick(int Position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Confirm Delete...");
        alertDialog.setMessage("Are you sure you want delete this workout?");
        //alertDialog.setIcon(R.drawable.delete);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.workoutTable.deleteWorkout(arrayList.get(Position).getId());
                        arrayList.remove(Position);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
        );
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        alertDialog.show();
    }
}