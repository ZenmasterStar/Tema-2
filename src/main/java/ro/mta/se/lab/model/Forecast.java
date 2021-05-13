package ro.mta.se.lab.model;

import javafx.beans.property.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Forecast {
    StringProperty city;
    StringProperty time;
    StringProperty weather;
    IntegerProperty temperature;
    IntegerProperty humidity;
    IntegerProperty wind;

    String getAPI(double lat, double lon) {
        String API_KEY = "210a3979b414e1bbdaab37a766008f2f";
        //String LOCATION = "London";
        String LAT = String.valueOf(lat);
        String LON = String.valueOf(lon);
        String UNITS = "metric";
        String LANG = "english";
        String EXCLUDE = "minutely,hourly,daily,alerts";
        String urlString = "https://api.openweathermap.org/data/2.5/onecall?lat=" + LAT + "&lon=" + LON + "&units=" + UNITS + "&lang=" + LANG + "&exclude=" + EXCLUDE + "&appid=" + API_KEY;

        try{
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception";
        }
    }

    public Forecast(City city) {
        this.city = new SimpleStringProperty(city.getNm());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E H:m a");
        LocalDateTime now = LocalDateTime.now();
        this.time = new SimpleStringProperty(dtf.format(now));
        String API = getAPI(city.getLat(), city.getLon());
        
        //this.ID = new SimpleLongProperty(ID);
        //this.nm = new SimpleStringProperty(nm);
        //this.lat = new SimpleDoubleProperty(lat);
        //this.lon = new SimpleDoubleProperty(lon);
        //this.countryCode = new SimpleStringProperty(countryCode);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getWeather() {
        return weather.get();
    }

    public StringProperty weatherProperty() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather.set(weather);
    }

    public Integer getTemperature() {
        return temperature.get();
    }

    public IntegerProperty temperatureProperty() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature.set(temperature);
    }

    public Integer getHumidity() {
        return humidity.get();
    }

    public IntegerProperty humidityProperty() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity.set(humidity);
    }

    public Integer getWind() {
        return wind.get();
    }

    public IntegerProperty windProperty() {
        return wind;
    }

    public void setWind(Integer wind) {
        this.wind.set(wind);
    }



}