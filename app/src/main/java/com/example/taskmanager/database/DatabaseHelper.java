package com.example.taskmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.Notification;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TaskManager.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_NOTIFICATIONS = "notifications";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_DATETIME = "datetime";

    // Tasks table columns
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_STATUS = "status";

    // Notifications table columns
    private static final String KEY_MESSAGE = "message";

    // Create table statements
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " + TABLE_TASKS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TITLE + " TEXT,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_DATETIME + " TEXT,"
            + KEY_STATUS + " INTEGER" + ")";

    private static final String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_MESSAGE + " TEXT,"
            + KEY_DATETIME + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
        db.execSQL(CREATE_TABLE_NOTIFICATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        onCreate(db);
    }

    // Task operations
    public long createTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, task.getTitle());
        values.put(KEY_DESCRIPTION, task.getDescription());
        values.put(KEY_DATETIME, task.getDateTime());
        values.put(KEY_STATUS, task.getStatus());
        return db.insert(TABLE_TASKS, null, values);
    }

    public List<Task> getUpcomingTasks() {
        List<Task> tasks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + 
                           " WHERE " + KEY_DATETIME + " > datetime('now')" +
                           " ORDER BY " + KEY_DATETIME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION)));
                task.setDateTime(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATETIME)));
                task.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_STATUS)));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public List<Task> getPastTasks() {
        List<Task> tasks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + 
                           " WHERE " + KEY_DATETIME + " <= datetime('now')" +
                           " ORDER BY " + KEY_DATETIME + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION)));
                task.setDateTime(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATETIME)));
                task.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_STATUS)));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    // Notification operations
    public long createNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, notification.getMessage());
        values.put(KEY_DATETIME, notification.getDateTime());
        return db.insert(TABLE_NOTIFICATIONS, null, values);
    }

    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTIFICATIONS + 
                           " ORDER BY " + KEY_DATETIME + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification();
                notification.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
                notification.setMessage(cursor.getString(cursor.getColumnIndexOrThrow(KEY_MESSAGE)));
                notification.setDateTime(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATETIME)));
                notifications.add(notification);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notifications;
    }
} 