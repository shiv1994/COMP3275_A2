package comp3275.uwi.comp3275_a2.models;

/**
 * Created by Shiva on 4/1/2016.
 */
public class LocationObject {
    private String time, latitude, longtitude;

    public LocationObject(String time, String latitude, String longtitude){
        this.time = time;
        this.latitude = latitude;
        this.longtitude = longtitude;
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
}
