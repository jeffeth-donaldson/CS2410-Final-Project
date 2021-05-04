package com.jeffethdonaldson.cs2410_final_project.db;

import android.util.Log;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class Converters {
    @TypeConverter
    public static ArrayList<LocalDate> fromString(String value) {
        Log.d(TAG, "fromString() called with: value = [" + value + "]");
        try {
            if (value.equals("")) {
                return new ArrayList<>();
            }
            String[] split = value.split(",");
            ArrayList<LocalDate> result = new ArrayList<>();
            for (String i : split) {
                result.add(LocalDate.ofEpochDay(Long.parseLong(i)));
            }
            Log.d(TAG, "fromString() returned: " + result);
            return result;
        } catch (Exception exception){
            Log.e(TAG, "fromString: Exception Occurred", exception);
        }
        return new ArrayList<>();
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<LocalDate> list) {
        try {
            Log.d(TAG, "fromArrayList() called with: list = [" + list + "]");
            if (list == null) {
                return "";
            }
            StringBuilder result = new StringBuilder();
            for (LocalDate val : list) {
                result.append(val.toEpochDay()).append(",");
            }
            Log.d(TAG, "fromArrayList: Result" + result.toString());
            return result.toString();
        } catch (Exception exception) {
            Log.e(TAG, "fromArrayList: Exception Occurred", exception);
        }
        return "";
    }

    @TypeConverter
    public static LocalDate fromLong(Long value){
        if (value == null || value == -1){
            return null;
        }
        return LocalDate.ofEpochDay(value);
    }

    @TypeConverter
    public static Long fromLocalDate(LocalDate value){
        if(value == null){
            return (long)-1;
        }
        return value.toEpochDay();
    }
}
