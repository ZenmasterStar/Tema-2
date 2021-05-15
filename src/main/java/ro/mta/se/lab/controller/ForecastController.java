package ro.mta.se.lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import ro.mta.se.lab.model.Forecast;

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

    public ForecastController(ObservableList<Forecast> forecastData) {
        this.forecastData = forecastData;
    }

    @FXML
    void Select(ActionEvent event){
        //String s = countryComboBox.getSelectionModel().getSelectedItem().toString();
        //label.setText(s);
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
                break;
            }
        }
    }


    @FXML
    private void initialize() {
        getCountries();
        countryComboBox.setOnAction( e-> getCities(countryComboBox.getValue()));
        cityComboBox.setOnAction( e-> getForecast(cityComboBox.getValue()));

//        for(Forecast forecast:forecastData) {
//            cityComboBox.getItems().add(forecast.getCity());
//        }
//        numeColumn.setCellValueFactory(cellData -> cellData.getValue().numeProperty());
//        prenumeColumn.setCellValueFactory(cellData -> cellData.getValue().prenumeProperty());
//        medieColumn.setCellValueFactory(cellData -> cellData.getValue().medieProperty().asObject());
//
//        studentTable.setItems(studentData);
    }

}
