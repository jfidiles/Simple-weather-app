package com.example.jimmy.weather;

import android.widget.ImageView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Jimmy on 1/29/2016.
 */
public class Utilities {
    public static void setIcon (String iconName, ImageView ivIcon) {
        switch (iconName) {
            case "01d" : ivIcon.setBackgroundResource(R.drawable.clearsky); break;
            case "01n" : ivIcon.setBackgroundResource(R.drawable.clearmoon); break;
            case "02d" : ivIcon.setBackgroundResource(R.drawable.dayfewclouds); break;
            case "02n" : ivIcon.setBackgroundResource(R.drawable.nightfewclouds); break;
            case "03d" : ivIcon.setBackgroundResource(R.drawable.scateredclouds); break;
            case "03n" : ivIcon.setBackgroundResource(R.drawable.scateredclouds); break;
            case "04d" : ivIcon.setBackgroundResource(R.drawable.brokenclouds); break;
            case "04n" : ivIcon.setBackgroundResource(R.drawable.brokenclouds); break;
            case "09d" : ivIcon.setBackgroundResource(R.drawable.showerrain); break;
            case "09n" : ivIcon.setBackgroundResource(R.drawable.showerrain); break;
            case "10d" : ivIcon.setBackgroundResource(R.drawable.rainsun); break;
            case "10n" : ivIcon.setBackgroundResource(R.drawable.rainmoon); break;
            case "11d" : ivIcon.setBackgroundResource(R.drawable.thuderstorm); break;
            case "11n" : ivIcon.setBackgroundResource(R.drawable.thuderstorm); break;
            case "13d" : ivIcon.setBackgroundResource(R.drawable.snow); break;
            case "13n" : ivIcon.setBackgroundResource(R.drawable.snow); break;
            case "50d" : ivIcon.setBackgroundResource(R.drawable.fog); break;
            case "50n" : ivIcon.setBackgroundResource(R.drawable.fog); break;
        }
    }
    public static double getOneDecimal (Double number) {
        DecimalFormat oneDForm = new DecimalFormat("#.#");
        Double temp = Double.valueOf(oneDForm.format(number));
        return temp;
    }

    public static String getTime(String dateString) {
        Date date = new Date(Long.parseLong(dateString) * 1000);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone(Calendar.getInstance().getTimeZone().getID()));
        return dateFormat.format(date);
    }

    public static String getTimeByZone(String dateString) {
//        Date date = new Date(Long.parseLong(dateString) * 1000);
        String time = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
            DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            time = timeFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
