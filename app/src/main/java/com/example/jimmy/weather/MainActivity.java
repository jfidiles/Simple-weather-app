package com.example.jimmy.weather;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.util.List;

public class MainActivity extends AppCompatActivity implements onWeatherResponse {
    TextView tvLocation, tvWeatherType, tvTimeUpdated,
            tvCurrentTemp, tvMinimumTemp, tvMaximumTemp,
            tvWind, tvCloudPercent,
            tvHumidity, tvSunrise, tvSunset, tvPressure;
    ImageView ivIcon;
    RecyclerView recyclerView;
    private WeatherAdapter adapter;
    JSONObject timeZone;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvWeatherType = (TextView) findViewById(R.id.tvWeatherType);
        tvTimeUpdated = (TextView) findViewById(R.id.tvTimeUpdated);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        tvCurrentTemp = (TextView) findViewById(R.id.tvCurrentDegrees);
        tvMinimumTemp = (TextView) findViewById(R.id.tvMinDegrees);
        tvMaximumTemp = (TextView) findViewById(R.id.tvMaxDegrees);
        tvWind = (TextView) findViewById(R.id.tvWindSpeed);
        tvCloudPercent = (TextView) findViewById(R.id.tvCloudPercent);
        tvHumidity = (TextView) findViewById(R.id.tvHumidity);
        tvSunrise = (TextView) findViewById(R.id.tvSunrise);
        tvSunset = (TextView) findViewById(R.id.tvSunset);
        tvPressure = (TextView) findViewById(R.id.tvPressure);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ivIcon = (ImageView) findViewById(R.id.ivWeatherIcon);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadWeather();
            }
        });
        if (CityPreference.isEmpty(this))
            CityPreference.storePreference("London", this);
        String city = CityPreference.getPreference(AppConfig.CITY, this);
        getCurrentWeather(city);
    }

    private void reloadWeather() {
        String city = CityPreference.getPreference(AppConfig.CITY,
                getApplicationContext());
        getCurrentWeather(city);
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private void getCurrentWeather(String cityName) {
        String location;
        String degreesType;
        if (cityName == null) {
            location = "London";
            degreesType = "metric";
        } else {
            location = cityName;
            degreesType = "metric";
        }
        APIConnection.GetForecastWeather getForecastWeather = new APIConnection.GetForecastWeather();
        getForecastWeather.delegate = this;
        getForecastWeather.execute(location, degreesType);
    }

    @Override
    public void getWeather(WeatherResponse getWeather) {
        HttpStatus statusCode = getWeather.statusCode;
        String codeError = getWeather.codeError;
        if (statusCode == HttpStatus.OK) {
            //get body for forecast and today weather
            String forecast = getWeather.forecastDetails;
            String today = getWeather.todayWeather;
            //check if there is any error
            JSONObject response = null;
            try {
                response = new JSONObject(forecast);
                if (response.getString("cod").equals("404")) {
                    Toast.makeText(MainActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    //get today weather as json
                    JSONObject todayWeather = Weather.getTodayDetails(today);
                    List<Forecast> forecastData = Weather.getForecast(forecast);
                    //Check if there are some elements missing from JSON
                    if (todayWeather == null)
                        Toast.makeText(MainActivity.this, "API error", Toast.LENGTH_SHORT).show();
                    else {
                        String lng = Weather.getLng(todayWeather);
                        String lat = Weather.getLat(todayWeather);

                        UpdateUI.setLocation(todayWeather, tvLocation);
                        UpdateUI.setWeatherType(todayWeather, tvWeatherType);
                        UpdateUI.setIcon(todayWeather, ivIcon);
                        UpdateUI.setCurrentTemp(todayWeather, tvCurrentTemp);
                        UpdateUI.setMinimumTemp(todayWeather, tvMinimumTemp);
                        UpdateUI.setMaximumTemp(todayWeather, tvMaximumTemp);
                        UpdateUI.setWind(todayWeather, tvWind);
                        UpdateUI.setCloudPercent(todayWeather, tvCloudPercent);
                        UpdateUI.setHumidity(todayWeather, tvHumidity);
                        UpdateUI.setPressure(todayWeather, tvPressure);
                        UpdateUI.setTimeUpdated(todayWeather, tvTimeUpdated);
                        //Set forecast
                        //set recyclerview adapter
                        adapter = new WeatherAdapter(this, forecastData);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        getCoords(lng, lat);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (TextUtils.isEmpty(codeError)) {
            Toast.makeText(MainActivity.this, statusCode.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, codeError, Toast.LENGTH_SHORT).show();
        }
    }

    private void getCoords(String lng, String lat) {
        APIConnection.GetTimeZone getTimeZone = new APIConnection.GetTimeZone();
        getTimeZone.delegate = this;
        getTimeZone.execute(lng, lat);
    }

    @Override
    public void getTimeZone(String timeZoneResponse) {
        try {
            timeZone = new JSONObject(timeZoneResponse);
            UpdateUI.setSunrise(timeZone, tvSunrise);
            UpdateUI.setSunset(timeZone, tvSunset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change city");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String city = input.getText().toString();
                CityPreference.storePreference(city, getApplicationContext());
                getCurrentWeather(city);
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
