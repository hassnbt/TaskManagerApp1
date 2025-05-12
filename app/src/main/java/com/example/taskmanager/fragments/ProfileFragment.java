package com.example.taskmanager.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.taskmanager.R;
import com.example.taskmanager.utils.ThemeUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private TextInputEditText editTextName;
    private TextInputEditText editTextEmail;
    private SwitchMaterial switchTheme;
    private MaterialButton buttonSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        try {
            editTextName = view.findViewById(R.id.editTextName);
            editTextEmail = view.findViewById(R.id.editTextEmail);
            switchTheme = view.findViewById(R.id.switchTheme);
            buttonSave = view.findViewById(R.id.buttonSave);

            loadUserData();
            setupListeners();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreateView: " + e.getMessage());
            Toast.makeText(requireContext(), "Error initializing profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserData(); // Reload data when fragment resumes
    }

    private void loadUserData() {
        try {
            String name = ThemeUtils.getUserName(requireContext());
            String email = ThemeUtils.getUserEmail(requireContext());
            boolean isDarkTheme = ThemeUtils.getThemeMode(requireContext()) == ThemeUtils.THEME_DARK;

            editTextName.setText(name);
            editTextEmail.setText(email);
            switchTheme.setChecked(isDarkTheme);
        } catch (Exception e) {
            Log.e(TAG, "Error loading user data: " + e.getMessage());
            Toast.makeText(requireContext(), "Error loading profile data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupListeners() {
        try {
            switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
                try {
                    int themeMode = isChecked ? ThemeUtils.THEME_DARK : ThemeUtils.THEME_LIGHT;
                    ThemeUtils.setThemeMode(requireContext(), themeMode);
                    
                    // Apply theme change
                    AppCompatDelegate.setDefaultNightMode(
                            isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
                    );
                    
                    // Show confirmation
                    String themeName = isChecked ? "Dark" : "Light";
                    Toast.makeText(requireContext(), 
                            "Theme changed to " + themeName + " mode", 
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, "Error changing theme: " + e.getMessage());
                    Toast.makeText(requireContext(), "Error changing theme", Toast.LENGTH_SHORT).show();
                }
            });

            buttonSave.setOnClickListener(v -> {
                try {
                    String name = editTextName.getText().toString().trim();
                    String email = editTextEmail.getText().toString().trim();

                    if (name.isEmpty()) {
                        Toast.makeText(requireContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (email.isEmpty()) {
                        Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Save profile data
                    ThemeUtils.setUserName(requireContext(), name);
                    ThemeUtils.setUserEmail(requireContext(), email);
                    
                    Toast.makeText(requireContext(), "Profile saved successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, "Error saving profile: " + e.getMessage());
                    Toast.makeText(requireContext(), "Error saving profile", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error setting up listeners: " + e.getMessage());
            Toast.makeText(requireContext(), "Error setting up profile controls", Toast.LENGTH_SHORT).show();
        }
    }
} 