package ro.mta.se.lab;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mta.se.lab.controller.ForecastController;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.Forecast;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import static java.lang.Double.parseDouble;


public class MainApp extends Application {
    public static void main(String[] args)
    {
        launch(args);
    }

    //ArrayList<City> CityList = new ArrayList<City>();
    //ArrayList<Forecast> ForecastList = new ArrayList<Forecast>();
    private ObservableList<City> cityData = FXCollections.observableArrayList();
    private ObservableList<Forecast> forecastData = FXCollections.observableArrayList();


    void getAPI() {
        String API_KEY = "210a3979b414e1bbdaab37a766008f2f";
        //String LOCATION = "London";
        String LAT = "55.591667";
        String LON = "37.740833";
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
            while((line = rd.readLine()) != null){
                result.append(line);
            }
            rd.close();

            try{
                FileWriter myWriter = new FileWriter("src/main/resources/out/out.json");
                myWriter.write(String.valueOf(result));
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getCityList() {
        try {
            int i = 0;

            String data;

            long id;

            String nm;
            double lat;
            double lon;
            String countryCode;

            File myObj = new File("src/main/resources/in/in.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();

                //skips first line
                if(i == 0)
                {
                    i = 1;
                    continue;
                }

                StringTokenizer st = new StringTokenizer(data);
                while (st.hasMoreTokens()) {
                    id = Long.parseLong(st.nextToken());
                    nm = st.nextToken();
                    lat = parseDouble(st.nextToken());
                    lon = parseDouble(st.nextToken());
                    countryCode = st.nextToken();
                    City newCity = new City(id,nm,lat,lon,countryCode);
                    cityData.add(newCity);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void printCityList() {
            for (City city : cityData) {
                city.printCity();
            }
    }

    public void getForecastList()
    {
        Integer i;
        for(i = 0; i < cityData.size(); i++) {
            try {
                Forecast forecast = new Forecast(cityData.get(i));
                forecastData.add(forecast);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void printForecastList() {
        for (Forecast forecast : forecastData) {
            forecast.printForecast();
        }
    }

    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();

        try {
            getAPI();
            getCityList();
            //printCityList();
            getForecastList();
            //printForecastList();

            loader.setLocation(this.getClass().getResource("/view/View.fxml"));
            loader.setController(new ForecastController(forecastData));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}