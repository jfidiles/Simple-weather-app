package com.example.jimmy.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy on 1/28/2016.
 */
public class Weather {

    public static JSONObject getTodayDetails(String todayWeather) {
        try {
            //String to json
            JSONObject initialObject = new JSONObject(todayWeather);
            //get coords
            JSONObject coords = initialObject.getJSONObject("coord");
            //create json for return
            JSONObject todayDetailsAsJson = new JSONObject();
            //add lon and lat
            todayDetailsAsJson.put(AppConfig.LNG, coords.getLong(AppConfig.LNG));
            todayDetailsAsJson.put(AppConfig.LAT, coords.getLong(AppConfig.LAT));
            //get weather
            JSONArray getWeather = initialObject.getJSONArray("weather");
            //get details from weather
            JSONObject weatherDetails = getWeather.getJSONObject(0);
            //Add information
            todayDetailsAsJson.put("id", weatherDetails.getInt("id"));
            todayDetailsAsJson.put(AppConfig.WEATHER_TYPE,
                    weatherDetails.getString(AppConfig.WEATHER_TYPE));
            todayDetailsAsJson.put(AppConfig.ICON, weatherDetails.getString(AppConfig.ICON));
            //Get temp
            JSONObject temp = initialObject.getJSONObject("main");
            //Add temp to json
            todayDetailsAsJson.put(AppConfig.CURRENT_DEGREE, temp.getDouble(AppConfig.CURRENT_DEGREE));
            todayDetailsAsJson.put(AppConfig.HUMIDITY, temp.getInt(AppConfig.HUMIDITY));
            todayDetailsAsJson.put(AppConfig.PRESSURE, temp.getLong(AppConfig.PRESSURE));
            todayDetailsAsJson.put(AppConfig.TEMP_MIN, temp.getDouble(AppConfig.TEMP_MIN));
            todayDetailsAsJson.put(AppConfig.TEMP_MAX, temp.getDouble(AppConfig.TEMP_MAX));
            //get details about wind
            JSONObject wind = initialObject.getJSONObject("wind");
            //add details about wind
            todayDetailsAsJson.put(AppConfig.WIND_SPEED, wind.getDouble(AppConfig.WIND_SPEED));
            todayDetailsAsJson.put(AppConfig.WIND_DEGREE, wind.getDouble(AppConfig.WIND_DEGREE));
            //get cloud cover percent
            todayDetailsAsJson.put(AppConfig.CLOUD_PERCENT,
                    initialObject.getJSONObject("clouds").getString(AppConfig.CLOUD_PERCENT));
            //get time when it was updated
            todayDetailsAsJson.put(AppConfig.TIME_UPDATED,
                    initialObject.getLong(AppConfig.TIME_UPDATED));
            //get sunrise, sunset and country
            JSONObject location = initialObject.getJSONObject("sys");

            todayDetailsAsJson.put(AppConfig.COUNTRY, location.getString(AppConfig.COUNTRY));
            todayDetailsAsJson.put(AppConfig.SUNRISE, location.getLong(AppConfig.SUNRISE));
            todayDetailsAsJson.put(AppConfig.SUNSET, location.getLong(AppConfig.SUNSET));
            //set locationName
            todayDetailsAsJson.put(AppConfig.CITY, initialObject.getString("name"));
            return todayDetailsAsJson;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Forecast> getForecast (String forecastWeather) {
        List<Forecast> forecast = new ArrayList<>();
        try {
            //get initial object
            JSONObject initialJSON = new JSONObject(forecastWeather);
            //get array of days
            JSONArray dayList = initialJSON.getJSONArray("list");
            // create new Forecast objects
            int length = dayList.length();
            Long date[] = new Long[length];
            String icon[] = new String[length];
            String weatherType[] = new String[length];
            double maxTemp[] = new double[length];
            double minTemp[] = new double[length];
            for (int i = 0; i < length; i++) {
                //get objects
                JSONObject day = dayList.getJSONObject(i);
                Long dateAsLong = day.getLong(AppConfig.TIME_UPDATED);    //date as long
                //get min and max temp
                JSONObject temp = day.getJSONObject("temp");
                double min = temp.getDouble("min");     //temp min
                double max = temp.getDouble("max");     //temp max
                //get weather details (weather type and icon)
                JSONArray weather = day.getJSONArray("weather");
                String weatherString = weather.getJSONObject(0).getString("description"); //weatherTYpe
                String iconString = weather.getJSONObject(0).getString("icon"); //icon
                //create array with details
                date[i] = dateAsLong;
                minTemp[i] = min;
                maxTemp[i] = max;
                weatherType[i] = weatherString;
                icon[i] = iconString;
            }
            for (int i = 0; i < length; i++) {
                Forecast current = new Forecast();
                current.date = date[i];
                current.max = maxTemp[i];
                current.min = minTemp[i];
                current.weatherType = weatherType[i];
                current.icon = icon[i];
                forecast.add(current);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return forecast;
    }
    public static String getCity (JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.CITY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getCountry (JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.COUNTRY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getWeatherType (JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.WEATHER_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getIcon (JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.ICON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getCurrentTemp(JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.CURRENT_DEGREE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getMinimumTemp(JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.TEMP_MIN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMaximumTemp(JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.TEMP_MAX);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWindSpeed(JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.WIND_SPEED);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWindDegree(JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.WIND_DEGREE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCloudPercent(JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.CLOUD_PERCENT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSunrise (JSONObject timeZone) {
        try {
            return timeZone.getString(AppConfig.SUNRISE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSunset (JSONObject timeZone) {
        try {
            return timeZone.getString(AppConfig.SUNSET);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getHumidity (JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.HUMIDITY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPressure (JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.PRESSURE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTimeUpdated (JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.TIME_UPDATED);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLng (JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.LNG);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLat (JSONObject todayWeather) {
        try {
            return todayWeather.getString(AppConfig.LAT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static JSONObject getLocation(String weatherResponse) {
//        try {
//            JSONObject response = new JSONObject(weatherResponse);
//            JSONObject city = response.getJSONObject(AppConfig.CITY);
//            JSONObject location = new JSONObject();
//            location.put(AppConfig.LOCATION, city.getString(AppConfig.NAME));
//            location.put(AppConfig.COUNTRY, city.getString(AppConfig.COUNTRY));
//            return location;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static JSONObject today (String weatherResponse) {
//        try {
//            //string to jsonobj
//            JSONObject response = new JSONObject(weatherResponse);
//            //get list array
//            JSONArray jsonArray = response.getJSONArray("list");
//            //get today object
//            JSONObject today = jsonArray.getJSONObject(0);
//            //get temp
//            JSONObject temp = today.getJSONObject(AppConfig.CURRENT_DEGREE);
//            //add today details
//            JSONObject todayDetails = new JSONObject();
//            todayDetails.put("currentTemp", temp.getDouble("day"));
//            todayDetails.put("minTemp", temp.getDouble("min"));
//            todayDetails.put("maxTemp", temp.getDouble("max"));
//            todayDetails.put("minTemp", temp.getDouble("min"));
//            //get weather
//            JSONArray weatherArray = today.getJSONArray("weather");
//            JSONObject weatherObject = weatherArray.getJSONObject(0);
//            todayDetails.put("id", weatherObject.getInt("id"));
//            todayDetails.put(AppConfig.DESCRIPTION, weatherObject.getString(AppConfig.DESCRIPTION));
//            todayDetails.put("windSpeed", today.getString(AppConfig.WIND_SPEED));
//            todayDetails.put("windDegree", today.getString(AppConfig.WIND_DEGREE));
//            todayDetails.put("clouds", today.getString("clouds"));
//            return todayDetails;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
