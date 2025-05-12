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
import com.example.taskmanager.adapters.NotificationAdapter;
import com.example.taskmanager.database.DatabaseHelper;
import com.example.taskmanager.models.Notification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NotificationFragment extends Fragment {
    private static final String TAG = "NotificationFragment";
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        
        try {
            recyclerView = view.findViewById(R.id.recyclerViewNotifications);
            databaseHelper = new DatabaseHelper(requireContext());

            setupRecyclerView();
            // First try to load existing notifications
            loadNotifications();
            // Then add dummy notifications if needed
            addDummyNotifications();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreateView: " + e.getMessage());
            Toast.makeText(requireContext(), "Error initializing notifications: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadNotifications();
    }

    private void setupRecyclerView() {
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            notificationAdapter = new NotificationAdapter();
            recyclerView.setAdapter(notificationAdapter);
        } catch (Exception e) {
            Log.e(TAG, "Error setting up RecyclerView: " + e.getMessage());
            Toast.makeText(requireContext(), "Error setting up notifications list", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNotifications() {
        try {
            List<Notification> notifications = databaseHelper.getAllNotifications();
            Log.d(TAG, "Loaded " + notifications.size() + " notifications");
            if (notifications.isEmpty()) {
                Log.d(TAG, "No notifications found in database");
            }
            notificationAdapter.setNotifications(notifications);
        } catch (Exception e) {
            Log.e(TAG, "Error loading notifications: " + e.getMessage());
            Toast.makeText(requireContext(), "Error loading notifications: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addDummyNotifications() {
        try {
            List<Notification> existingNotifications = databaseHelper.getAllNotifications();
            if (existingNotifications.isEmpty()) {
                Log.d(TAG, "Adding dummy notifications");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();

                String[] messages = {
                    "Welcome to Task Manager!",
                    "You have 3 upcoming tasks",
                    "Don't forget to check your schedule",
                    "Task Manager is now ready to use",
                    "Your profile has been updated"
                };

                for (int i = 0; i < messages.length; i++) {
                    calendar.add(Calendar.HOUR, -i); // Space out notifications by hours
                    String dateTime = dateFormat.format(calendar.getTime());
                    Notification notification = new Notification(messages[i], dateTime);
                    long id = databaseHelper.createNotification(notification);
                    Log.d(TAG, "Added notification: " + messages[i] + " with ID: " + id);
                }
                
                // Reload notifications after adding dummy data
                loadNotifications();
            } else {
                Log.d(TAG, "Dummy notifications already exist, skipping");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error adding dummy notifications: " + e.getMessage());
            Toast.makeText(requireContext(), "Error adding dummy notifications: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
} 