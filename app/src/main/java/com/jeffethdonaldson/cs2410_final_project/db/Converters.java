package com.jeffethdonaldson.cs2410_final_project.db;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static ArrayList<LocalDate> fromString(String value) {
        if (value.equals("")) {
            return new ArrayList<LocalDate>();
        }
       String[] split = value.split(",");
       ArrayList<LocalDate> result = new ArrayList<>();
        for (String i: split) {
            result.add(LocalDate.ofEpochDay(Long.parseLong(i)));
        }
        return result;
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<LocalDate> list) {
        if (list == null){
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (LocalDate val : list) {
            result.append(val.toEpochDay()).append(",");
        }
        return result.toString();
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
