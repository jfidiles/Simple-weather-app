package com.example.jimmy.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Jimmy on 1/29/2016.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>{
    //Initializations
    private LayoutInflater inflater;
    List<Forecast> data = Collections.emptyList();
    //Constructor
    public WeatherAdapter(Context context,List<Forecast> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.forecastlayout, parent, false);
        return new WeatherViewHolder(view);
    }
    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        String day, dbMonth;
        Forecast forecastInfo = data.get(position);

        //Populate RecyclerView
        long date = forecastInfo.date;
        Date covertDate = new Date (date * 1000);
        //get day of week
        SimpleDateFormat getDay = new SimpleDateFormat("E");
        day = getDay.format(covertDate);
        holder.tvDate.setText(day); //set day

        //set icon
        String icon = forecastInfo.icon;
        Utilities.setIcon(icon, holder.ivIcon);

        //set weatherType
        String weatherType = forecastInfo.weatherType;
        holder.tvWeatherType.setText(weatherType);
        //set max temp
        double maxTemp = Utilities.getOneDecimal(forecastInfo.max);
        holder.tvMaxTemp.setText(Double.toString(maxTemp));
        //set max temp
        double minTemp = Utilities.getOneDecimal(forecastInfo.min);
        holder.tvMinTemp.setText(Double.toString(minTemp));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class WeatherViewHolder extends  RecyclerView.ViewHolder{
        TextView tvDate, tvWeatherType, tvMinTemp, tvMaxTemp;
        ImageView ivIcon;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDay);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvWeatherType = (TextView) itemView.findViewById(R.id.tvWeatherType);
            tvMinTemp = (TextView) itemView.findViewById(R.id.tvMinTemp);
            tvMaxTemp = (TextView) itemView.findViewById(R.id.tvMaxTemp);
        }
    }
}
