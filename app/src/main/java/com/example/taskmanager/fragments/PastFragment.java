package com.example.taskmanager.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.R;
import com.example.taskmanager.adapters.TaskAdapter;
import com.example.taskmanager.database.DatabaseHelper;
import com.example.taskmanager.models.Task;

import java.util.List;

public class PastFragment extends Fragment {
    private static final String TAG = "PastFragment";
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);
        
        try {
            recyclerView = view.findViewById(R.id.recyclerViewPastTasks);
            databaseHelper = new DatabaseHelper(requireContext());

            setupRecyclerView();
            loadPastTasks();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreateView: " + e.getMessage());
            Toast.makeText(requireContext(), "Error initializing past tasks: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPastTasks(); // Reload tasks when fragment resumes
    }

    private void setupRecyclerView() {
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            taskAdapter = new TaskAdapter();
            recyclerView.setAdapter(taskAdapter);
        } catch (Exception e) {
            Log.e(TAG, "Error setting up RecyclerView: " + e.getMessage());
            Toast.makeText(requireContext(), "Error setting up past tasks list", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPastTasks() {
        try {
            List<Task> pastTasks = databaseHelper.getPastTasks();
            Log.d(TAG, "Loaded " + pastTasks.size() + " past tasks");
            
            if (pastTasks.isEmpty()) {
                Toast.makeText(requireContext(), "No past tasks found", Toast.LENGTH_SHORT).show();
            }
            
            taskAdapter.setTasks(pastTasks);
        } catch (Exception e) {
            Log.e(TAG, "Error loading past tasks: " + e.getMessage());
            Toast.makeText(requireContext(), "Error loading past tasks: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
} 