package domain.messages;

/**
 * Entity representing a location represented in a longitude and a latitude
 */
public class Location {
    private int lat;
    private int longitude;

    public int getLat()              { return lat; }
    public int getLongitude()       { return longitude; }

    public void setLat(int lat)         { this.lat = lat; }
    public void setLongitude(int longitude)     { this.longitude = longitude; }
}
