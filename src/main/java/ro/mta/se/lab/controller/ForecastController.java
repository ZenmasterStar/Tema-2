package ro.mta.se.lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ro.mta.se.lab.model.Forecast;

import javax.net.ssl.HttpsURLConnection;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ForecastController {
    private ObservableList<Forecast> forecastData;

    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private Label cityLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label weatherLabel;
    @FXML
    private Label temperatureLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label windLabel;

    @FXML
    private ImageView icon;

    public ForecastController(ObservableList<Forecast> forecastData) {
        this.forecastData = forecastData;
    }

    private void getCountries() {
        for(int i = 0; i < forecastData.size(); i++)
        {
            int logic = 1;
            for(int j = 0; j < i; j ++)
            {
                if(forecastData.get(j).getCountry().equals(forecastData.get(i).getCountry()))
                    logic = 0;
            }
            if(logic == 1)
                countryComboBox.getItems().add(forecastData.get(i).getCountry());
        }
    }

    private void getCities(String value) {
        cityComboBox.getItems().clear();
        for(int i = 0; i < forecastData.size(); i++)
        {
            int logic = 1;
            for(int j = 0; j < i; j ++)
            {
                if(forecastData.get(j).getCity().equals(forecastData.get(i).getCity()))
                    logic = 0;
            }
            if(logic == 1)
                if(forecastData.get(i).getCountry().equals(value)) {
                    cityComboBox.getItems().add(forecastData.get(i).getCity());
                }
        }
    }

    private void getForecast(String value) {
        try {
        FileWriter myWriter= new FileWriter("src/main/resources/out/history.txt", true);
        for(int i = 0; i < forecastData.size(); i++)
        {
            if(forecastData.get(i).getCity().equals(value))
            {
                Forecast forecast = forecastData.get(i);
                cityLabel.setText(forecast.getCity());
                timeLabel.setText(forecast.getTime());
                weatherLabel.setText(forecast.getWeather());
                temperatureLabel.setText(forecast.getTemperature().toString());
                humidityLabel.setText(forecast.getHumidity().toString());
                windLabel.setText(forecast.getWind().toString());
                icon.setImage(new Image(forecast.getIconLink()));

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                myWriter.append("Recorded at: " + dtf.format(now));
                myWriter.append("\nForecast Time: " + forecast.getTime());
                myWriter.append("\nForecast Country: " + forecast.getCountry());
                myWriter.append("\nForecast City: " + forecast.getCity());
                myWriter.append("\nForecast Weather: " + forecast.getWeather());
                myWriter.append("\nForecast Temperature: " + forecast.getTemperature() + " °C");
                myWriter.append("\nForecast Humidity: " + forecast.getHumidity() + " %");
                myWriter.append("\nForecast Wind speed: " + forecast.getWind() + " km/h");
                myWriter.append("\nForecast Icon: " + forecast.getIconLink());
                myWriter.append("\n\n");

                break;
            }
        }
        myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred at writing the history.");
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        getCountries();
        countryComboBox.setOnAction( e-> getCities(countryComboBox.getValue()));
        cityComboBox.setOnAction( e-> getForecast(cityComboBox.getValue()));
    }

}
