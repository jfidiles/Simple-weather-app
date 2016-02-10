package com.example.jimmy.weather;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * Created by Jimmy on 1/28/2016.
 */
public class UpdateUI {

    public static void setLocation(JSONObject todayWeather, TextView tvLocation) {
        String city = Weather.getCity(todayWeather);
        String country = Weather.getCountry(todayWeather);
        tvLocation.setText(city + " - " + country);
    }

    public static void setWeatherType(JSONObject todayWeather, TextView tvWeatherType) {
        String weatherType = Weather.getWeatherType(todayWeather);
        tvWeatherType.setText(weatherType);
    }

    public static void setIcon(JSONObject todayWeather, ImageView ivIcon) {
        String iconName = Weather.getIcon(todayWeather);
        Utilities.setIcon(iconName, ivIcon);

    }
    public static void setCurrentTemp(JSONObject todayWeather, TextView tvCurrentDegree) {
        Double currentTemp = Double.parseDouble(Weather.getCurrentTemp(todayWeather));
        Double temp = Utilities.getOneDecimal(currentTemp);
        tvCurrentDegree.setText(Double.toString(temp));
    }

    public static void setMinimumTemp(JSONObject todayWeather, TextView tvTempMin) {
        Double minTemp = Double.parseDouble(Weather.getMinimumTemp(todayWeather));
        Double temp = Utilities.getOneDecimal(minTemp);
        tvTempMin.setText(Double.toString(temp));
    }

    public static void setMaximumTemp(JSONObject todayWeather, TextView tvTempMax) {
        Double maxTemp = Double.parseDouble(Weather.getMaximumTemp(todayWeather));
        Double temp = Utilities.getOneDecimal(maxTemp);
        tvTempMax.setText(Double.toString(temp));
    }

    public static void setWind(JSONObject todayWeather, TextView tvTempMax) {
        String windSpeed = Weather.getWindSpeed(todayWeather);
        Double windDegree = Double.parseDouble(Weather.getWindDegree(todayWeather));
        Double degree = Utilities.getOneDecimal(windDegree);
        String wind = windSpeed + " km/h " + degree + "°";
        tvTempMax.setText(wind);
    }

    public static void setCloudPercent(JSONObject todayWeather, TextView tvTempMax) {
        String cloudPercentage = Weather.getCloudPercent(todayWeather);
        tvTempMax.setText(cloudPercentage + "%");
    }

    public static void setHumidity(JSONObject todayWeather, TextView tvHumidity) {
        String humidity = Weather.getHumidity(todayWeather);
        tvHumidity.setText(humidity + "%");
    }

    public static void setSunrise(JSONObject timeZone, TextView tvSunrise) {
        String timeAsLong = Weather.getSunrise(timeZone);
        String sunrise = Utilities.getTimeByZone(timeAsLong);
        tvSunrise.setText(sunrise);
    }

    public static void setSunset(JSONObject timeZone, TextView tvSunset) {
        String timeAsLong = Weather.getSunset(timeZone);
        String sunset = Utilities.getTimeByZone(timeAsLong);
        tvSunset.setText(sunset);
    }

    public static void setPressure(JSONObject todayWeather, TextView tvPressure) {
        String pressure = Weather.getPressure(todayWeather);
        tvPressure.setText(pressure + " mb");
    }

    public static void setTimeUpdated(JSONObject todayWeather, TextView tvPressure) {
        String timeAsString = Weather.getTimeUpdated(todayWeather);
        String time = Utilities.getTime(timeAsString);
        tvPressure.setText(AppConfig.SET_TIME + time);
    }

}
