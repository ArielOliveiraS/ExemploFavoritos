package com.example.movieapp.model.data.local;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converters {
//
//    @TypeConverter
//    public static Date fromTimestamp(Long value) {
//        return value == null ? null : new Date(value);
//    }
//    
//
//    @TypeConverter
//    public static Long dateToTimestamp(Date date) {
//        return date == null ? null : date.getTime();
//    }

    @TypeConverter
    public List<Long> gettingListFromString(String genreIds) {
        List<Long> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Long.parseLong(s));
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<Long> list) {
        String genreIds = "";
        if (list != null) {
            for (Long i : list) {
                genreIds += "," + i;
            }
        }

        return genreIds;
    }

}
