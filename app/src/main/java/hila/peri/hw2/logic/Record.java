package hila.peri.hw2.logic;

public class Record implements Comparable {

    private int score;
    private double lon, lat;
    private String name,date;


    public Record(String name, int score, String date, double lat, double lon) {
        this.name = name;
        this.score = score;
        this.date = date;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public Record setName(String name) {
        this.name = name;
        return this;
    }

    public String getDate() {
        return date;
    }


    public int getScore() {
        return score;
    }
    

    public double getLat() {
        return lat;
    }

    public Record setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Record setLon(double lon) {
        this.lon = lon;
        return this;
    }

    @Override
    public int compareTo(Object record) {
        return ((Record) record).getScore() - this.score;
    }

    @Override
    public String toString() {
        return "Record{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", score=" + score +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
