package model;
public class Elements {
    double distance;
    double duration;
    public Elements() {
        this.distance = 0;
        this.duration = 0;
    }
    public Elements(int distance, int duration) {
        this.distance = distance;
        this.duration = duration;
    }
    public double getDistance() {
       return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public double getDuration() {
        return duration;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }
}