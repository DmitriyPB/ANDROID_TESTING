package com.testing.android.android_testing;

import android.annotation.SuppressLint;

import com.testing.android.android_testing.db.entity.SpecialtyEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class TestingUtils {
    private final static SimpleDateFormat printDateFormat = new SimpleDateFormat("dd.MM.yyyy г.");
    private final static SimpleDateFormat parseDateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
    private final static SimpleDateFormat parseDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    public final static String dash = "-";

    /**
     * This Method is unit tested properly for very different cases ,
     * taking care of Leap Year days difference in a year,
     * and date cases month and Year boundary cases (12/31/1980, 01/01/1980 etc)
     **/

    public static int getAge(Date dateOfBirth) {

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age = 0;

        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ((birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
                (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        } else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH)) &&
                (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }

    public static String specialtiesToString(List<SpecialtyEntity> specialties) {
        if (specialties != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < specialties.size(); i++) {
                SpecialtyEntity specialty = specialties.get(i);
                if (i != 0) sb.append(", ");
                sb.append(specialty.getName());
            }
            return sb.toString();
        }
        return dash;
    }

    public static String formatBirthday(Date birthday) {
        if (birthday != null) {
            return printDateFormat.format(birthday);
        }
        return dash;
    }

    public static String getAgeFormatted(Date birthday) {
        if (birthday != null) {
            return String.valueOf(TestingUtils.getAge(birthday));
        }
        return dash;
    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static Date extractDate(String date) {
        if (date != null) {
            DateFormat format = parseDateFormat1;
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                format = parseDateFormat2;
            }
            try {
                return format.parse(date);
            } catch (Throwable ignored) {}
        }
        return null;
    }
}
