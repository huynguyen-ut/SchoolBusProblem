package model;
import java.util.ArrayList;
import java.util.List;

public class Path  implements Cloneable {
       private int id;
       private int nStation;
       private List<Station> stations;
       private double distance;
       private double duration;
       public Path(int id){
    	   this.id=id;
    	   this.stations=new ArrayList<Station>();
    	   this.nStation=0;
       }
       public boolean isContain(Location l){
    	   for(Location i:this.stations)
    	     if(l.getId()==i.getId())
    	    	 return true;
		return false;
       }
       public boolean isEmpty(){
    	   if(this.nStation==0)
    		   return true;
    	   return false;
       }
       public int getId() {
		return id;
       }
       public int getnStation() {
		return nStation;
       }
       public Location remove(int index){
    	   this.nStation=0;
    	   return this.stations.remove(index);
    			   
       }
       public void clearPath(){
    	   this.stations.clear();
    	   this.nStation=0;
       }
       public void addStation(Station s){
    	   this.stations.add(s);
    	   this.nStation++;
       }
	  public List<Station> getStations() {
		return stations;
	}
	  public Object clone() throws CloneNotSupportedException {
	        return super.clone();
	    }
}
  