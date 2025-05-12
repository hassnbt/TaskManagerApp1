package com.example.taskmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeUtils {
    private static final String PREF_NAME = "TaskManagerPrefs";
    private static final String KEY_THEME_MODE = "theme_mode";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";

    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static int getThemeMode(Context context) {
        return getPrefs(context).getInt(KEY_THEME_MODE, THEME_LIGHT);
    }

    public static void setThemeMode(Context context, int themeMode) {
        getPrefs(context).edit().putInt(KEY_THEME_MODE, themeMode).apply();
    }

    public static String getUserName(Context context) {
        return getPrefs(context).getString(KEY_USER_NAME, "");
    }

    public static void setUserName(Context context, String name) {
        getPrefs(context).edit().putString(KEY_USER_NAME, name).apply();
    }

    public static String getUserEmail(Context context) {
        return getPrefs(context).getString(KEY_USER_EMAIL, "");
    }

    public static void setUserEmail(Context context, String email) {
        getPrefs(context).edit().putString(KEY_USER_EMAIL, email).apply();
    }
} 