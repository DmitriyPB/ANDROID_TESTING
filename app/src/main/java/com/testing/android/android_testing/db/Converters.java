package com.testing.android.android_testing.db;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import androidx.room.TypeConverter;

public class Converters {

    private static JSONParser jsonParser = new JSONParser();

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static List<Long> stringToIds(String stored) {
        LinkedList<Long> list = new LinkedList<>();
        try {
            JSONArray array = (JSONArray) jsonParser.parse(stored);
            for (Object o : array) {
                list.add((Long) o);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @TypeConverter
    public static String idsToString(List<Long> ids) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(ids);
        return jsonArray.toJSONString();
    }
}