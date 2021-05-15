package ro.mta.se.lab.model;

import javafx.beans.property.*;

public class City {
    LongProperty ID;
    StringProperty nm;
    DoubleProperty lat;
    DoubleProperty lon;
    StringProperty countryCode;

    public City(long ID, String nm, double lat, double lon, String countryCode) {
        this.ID = new SimpleLongProperty(ID);
        this.nm = new SimpleStringProperty(nm);
        this.lat = new SimpleDoubleProperty(lat);
        this.lon = new SimpleDoubleProperty(lon);
        this.countryCode = new SimpleStringProperty(countryCode);
    }

    public long getID() {
        return ID.get();
    }

    public LongProperty IDProperty() {
        return ID;
    }

    public void setID(long ID) {
        this.ID.set(ID);
    }

    public String getNm() {
        return nm.get();
    }

    public StringProperty nmProperty() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm.set(nm);
    }

    public double getLat() {
        return lat.get();
    }

    public DoubleProperty latProperty() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat.set(lat);
    }

    public double getLon() {
        return lon.get();
    }

    public DoubleProperty lonProperty() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon.set(lon);
    }

    public String getCountryCode() {
        return countryCode.get();
    }

    public StringProperty countryCodeProperty() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode.set(countryCode);
    }

    public void printCity(){
        System.out.println(getID());
        System.out.println(getNm());
        System.out.println(getLat());
        System.out.println(getLon());
        System.out.println(getCountryCode());
        System.out.println();
    }
}
