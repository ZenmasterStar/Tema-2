package ro.mta.se.lab.model;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import javafx.beans.property.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Forecast {
    StringProperty city;
    StringProperty country;
    StringProperty time;
    StringProperty weather;
    DoubleProperty temperature;
    DoubleProperty humidity;
    DoubleProperty wind;

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

    public Forecast(City city) throws IOException {
        this.city = new SimpleStringProperty(city.getNm());
        this.country = new SimpleStringProperty(city.getCountryCode());

        String API = getAPI(city.getLat(), city.getLon());

        JsonObject one = Json.parse(API).asObject();
        JsonObject two = one.get("current").asObject();
        //JsonObject three = two.get("weather").asObject();
        this.weather = new SimpleStringProperty("Light Rain Showers");

        JsonObject object = Json.parse(API).asObject();
        String timezone = object.get("timezone").asString();
        JsonObject current = object.get("current").asObject();
        Long dt = current.get("dt").asLong();
        Double temperature = current.get("temp").asDouble();
        Double humidity = current.get("humidity").asDouble();
        Double wind = current.get("wind_speed").asDouble();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE HH:mm a", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        this.time = new SimpleStringProperty(sdf.format(dt*1000));

        //JsonObject weatherObject = current.get("weather").asObject();
        //String weather = weatherObject.get("main").asString();

        this.temperature = new SimpleDoubleProperty(temperature);
        this.humidity = new SimpleDoubleProperty(humidity);
        this.wind = new SimpleDoubleProperty(wind);
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

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
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

    public Double getTemperature() {
        return temperature.get();
    }

    public DoubleProperty temperatureProperty() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature.set(temperature);
    }

    public Double getHumidity() {
        return humidity.get();
    }

    public DoubleProperty humidityProperty() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity.set(humidity);
    }

    public Double getWind() {
        return wind.get();
    }

    public DoubleProperty windProperty() {
        return wind;
    }

    public void setWind(Double wind) {
        this.wind.set(wind);
    }

    public void printForecast(){
        System.out.println(getCity());
        System.out.println(getCountry());
        System.out.println(getTime());
        System.out.println(getWeather());
        System.out.println(getTemperature());
        System.out.println(getHumidity());
        System.out.println(getWind());
        System.out.println();
    }

}