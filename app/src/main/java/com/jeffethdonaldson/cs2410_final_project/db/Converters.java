package com.jeffethdonaldson.cs2410_final_project.db;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static ArrayList<Date> fromString(String value) {
       String[] split = value.split(",");
       ArrayList<Date> result = new ArrayList<>();
        for (String i: split) {
            result.add(new Date(Long.parseLong(i)));
        }
        return result;
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Date> list) {
        StringBuilder result = new StringBuilder();
        for (Date val : list) {
            result.append(val.getTime()).append(",");
        }
        return result.toString();
    }

    @TypeConverter
    public static Date fromLong(Long value){
        if (value == null || value == -1){
            return null;
        }
        return new Date(value);
    }

    @TypeConverter
    public static Long fromDate(Date value){
        if(value == null){
            return (long)-1;
        }
        return value.getTime();
    }
}
