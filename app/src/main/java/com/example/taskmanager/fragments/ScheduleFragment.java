package com.example.taskmanager.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ScheduleFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton fabAddTask;
    private Calendar selectedDateTime;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        
        recyclerView = view.findViewById(R.id.recyclerViewTasks);
        fabAddTask = view.findViewById(R.id.fabAddTask);
        databaseHelper = new DatabaseHelper(requireContext());

        selectedDateTime = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        setupRecyclerView();
        setupFab();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTasks();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);
    }

    private void setupFab() {
        fabAddTask.setOnClickListener(v -> showAddTaskDialog());
    }

    private void loadTasks() {
        List<Task> tasks = databaseHelper.getUpcomingTasks();
        taskAdapter.setTasks(tasks);
    }

    private void showAddTaskDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        TextInputEditText editTextTitle = dialogView.findViewById(R.id.editTextTitle);
        TextInputEditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        TextInputEditText editTextDate = dialogView.findViewById(R.id.editTextDate);
        TextInputEditText editTextTime = dialogView.findViewById(R.id.editTextTime);

        // Set initial date and time
        editTextDate.setText(dateFormat.format(selectedDateTime.getTime()));
        editTextTime.setText(timeFormat.format(selectedDateTime.getTime()));

        // Date picker
        editTextDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    selectedDateTime.set(Calendar.YEAR, year);
                    selectedDateTime.set(Calendar.MONTH, month);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    editTextDate.setText(dateFormat.format(selectedDateTime.getTime()));
                },
                selectedDateTime.get(Calendar.YEAR),
                selectedDateTime.get(Calendar.MONTH),
                selectedDateTime.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        // Time picker
        editTextTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                (view, hourOfDay, minute) -> {
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, minute);
                    editTextTime.setText(timeFormat.format(selectedDateTime.getTime()));
                },
                selectedDateTime.get(Calendar.HOUR_OF_DAY),
                selectedDateTime.get(Calendar.MINUTE),
                true
            );
            timePickerDialog.show();
        });

        new MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add New Task")
            .setView(dialogView)
            .setPositiveButton("Add", (dialog, which) -> {
                String title = editTextTitle.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String dateTime = dateFormat.format(selectedDateTime.getTime()) + " " + 
                                timeFormat.format(selectedDateTime.getTime());

                if (title.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter a title", Toast.LENGTH_SHORT).show();
                    return;
                }

                Task task = new Task(title, description, dateTime);
                databaseHelper.createTask(task);
                loadTasks();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
} 