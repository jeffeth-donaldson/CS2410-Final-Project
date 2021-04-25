package com.jeffethdonaldson.cs2410_final_project.db;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static ArrayList<Long> fromString(String value) {
       String[] split = value.split(",");
       ArrayList<Long> result = new ArrayList<>();
        for (String i: split) {
            result.add(Long.parseLong(i));
        }
        return result;
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Long> list) {
        StringBuilder result = new StringBuilder();
        for (Long val : list) {
            result.append(val).append(",");
        }
        return result.toString();
    }

    @TypeConverter
    public static Date fromLong(Long value){
        return new Date(value);
    }

    @TypeConverter
    public static Long fromDate(Date value){
        return value.getTime();
    }
}
