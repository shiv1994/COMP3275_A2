package comp3275.uwi.comp3275_a2.models;

/**
 * Created by Shiva on 4/1/2016.
 */
public class LocationObject {
    private String time;
    private String latitude;
    private String longtitude;

    private String altitude;

    public LocationObject(String time, String latitude, String longtitude, String altitude){
        this.time = time;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.altitude=altitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }
}
