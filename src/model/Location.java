package model;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.maps.model.LatLng;
public abstract class Location implements Comparable<Location> {
     private int id;
     private  double lat;
     private double lon;
     private  double distanceToSchool; 
     private double durationToSchool; // duration of google
    public double getDurationToSchool() {
	    return durationToSchool;
	}
	public void setDurationToSchool(double durationToSchool) {
		this.durationToSchool = durationToSchool;
	}
	OkHttpClient client;
      private static final String API_KEY = "AIzaSyCI23HUoG4zqAvSwheflDAXaoYDX0cB96c";
      public Location(int id,double x,double y){
    	  this.id=id;
    	  this.lat=x;
    	  this.lon=y;
    	  client = new OkHttpClient();
      }
      public int getId() {
		return id;
	}
	public double distanceToX1(Location x){
    	  double e=Math.sqrt(Math.pow(this.lat-x.lat,2)+Math.pow(this.lon-x.lon,2));
    	  return e;
      }
	
	public Elements getDistanceDuration(Location s) throws IOException, JSONException{
		LatLng start=new LatLng(this.lat,this.lon);
		LatLng end=new LatLng(s.lat,s.lon);
		String url_request = "https://maps.googleapis.com/maps/api/distancematrix/json?" +

                "origins="+ start.toString() + "&destinations="+ end.toString() +

                "&mode=driving"+ "&language=en-EN"+ "&key=" + API_KEY;
		 URL url = new URL(url_request);
		 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		  conn.setRequestMethod("GET");
		  String line, outputString = "";
		  BufferedReader reader = new BufferedReader(
		  new InputStreamReader(conn.getInputStream()));
		  while ((line = reader.readLine()) != null) {
		       outputString += line;
		  }
		  JSONObject jsonObject = new JSONObject(outputString);
          JSONArray rows = jsonObject.getJSONArray("rows");
          JSONObject temp = rows.getJSONObject(0).getJSONArray("elements").getJSONObject(0);
          Elements elements = new Elements();
          elements.setDistance(temp.getJSONObject("distance").getDouble("value")/1000);
          elements.setDuration(Math.round(temp.getJSONObject("duration").getDouble("value")/60));
		return elements;

	}
	public double distanceToX2(Location x){
		double theta = this.lon - x.getLon();
		double dist = Math.sin(deg2rad(this.lat)) * Math.sin(deg2rad(x.getLat())) + Math.cos(deg2rad(this.lat)) * Math.cos(deg2rad(x.getLat())) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		//if (unit == "K") {
			dist = dist * 1.609344;
		//} else if (unit == "N") {
		//	dist = dist * 0.8684;
	//	}
		return (dist);
     } 
	

    public String run(String url) throws IOException  {
        Request request = new Request.Builder()
               .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}


	public double getDistanceToSchool() {
		return distanceToSchool;
	}
	public void setDistanceToSchool(double distanceToSchool) {
		this.distanceToSchool = distanceToSchool;
	}
	public double getLat() {
		return lat;
	}
	public double getLon() {
		return lon;
	}
	
      
}
