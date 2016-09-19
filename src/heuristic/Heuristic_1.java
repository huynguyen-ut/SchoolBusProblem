package heuristic;
import java.io.IOException;
import org.json.JSONException;

import model.Bus;
import model.Elements;
import model.Path;
import model.StateStation;
import model.Station;

public class Heuristic_1 extends Heuristic {
	  
	public Heuristic_1(String file, int typeDistance) throws IOException, JSONException, NumberFormatException, CloneNotSupportedException {
		super(file, typeDistance);
		// TODO Auto-generated constructor stub
	}
	public void run(Bus bus,Path path) throws IOException, JSONException, CloneNotSupportedException{
		double distance=0;
		Elements e=null;
		if(!bus.isFull()&&
			bus.getRunningTime()+bus.getCurStopBus().getDurationToSchool()<this.limitTime)
		   for(Station station:path.getStations())
			 {
			   if(!bus.isFull()&&station.isEmpty()){  
				   switch (typeDistance){
				    case 1: distance=bus.getCurStopBus().distanceToX1(station); // 2d
    		  		        break;
				    case 2: distance=bus.getCurStopBus().distanceToX2(station);
				            break;
				    case 3: e=bus.getCurStopBus().getDistanceDuration(station);
				    		distance=e.getDistance(); // google
				            break;
				    default: break;
				    }
				 if(bus.getRunningTime()+e.getDuration()+station.getDurationToSchool()<this.limitTime){
					 bus.addStation(station, e);
				   }
				 }
			   }
			
		   
	}
	public void run(Bus bus,Station station) throws CloneNotSupportedException, IOException, JSONException{
		bus.addStation(station, null);
		double distance=0;
		Elements e=null;
		 Elements el=null;
		 while(!bus.isFull()&&
				bus.getRunningTime()+bus.getCurStopBus().getDurationToSchool()<this.limitTime)
		   {
			 Station Sb=null;
			  for(Station ib:this.stations)  //determine first next station
				  if(ib.getId()!=bus.getCurStopBus().getId()&&!ib.isEmpty()){
					 						  
					  switch (typeDistance){
					    case 1: distance=bus.getCurStopBus().distanceToX1(ib); // 2d
	     		  		        break;
					    case 2: distance=bus.getCurStopBus().distanceToX2(ib);
					            break;
					    case 3: e=bus.getCurStopBus().getDistanceDuration(ib);
					    		distance=e.getDistance(); // google
					            break;
					    default: break;
					    }
					  if(bus.getRunningTime()+e.getDuration()+ib.getDurationToSchool()<this.limitTime)
					  { Sb=ib;
					    break;
					  }
				  }// lay 1 station thoa man
			     if(Sb==null)
			    	break;
			     for(int i=0;i<stations.size();i++){// find next station
				     if(!this.stations.get(i).isEmpty()&&this.stations.get(i).getId()!=Sb.getId()){
				    	 double temp=0;
				    	 switch (typeDistance){
						    case 1: temp=bus.getCurStopBus().distanceToX1(this.stations.get(i)); // 2d
		     		  		        break;
						    case 2: temp=bus.getCurStopBus().distanceToX2(this.stations.get(i));
						            break;
						    case 3: el=bus.getCurStopBus().getDistanceDuration(this.stations.get(i));
						    	    temp=el.getDistance(); // google
						            break;
						    default: break;
						    }
				    	 if(temp<distance&&
				    			 bus.getRunningTime()+el.getDuration()+this.stations.get(i).getDurationToSchool()<this.limitTime){
				    	   Sb=this.stations.get(i);
				    	   e=el;
				    	   distance=temp; 
				    	 }
				    
				      }
				    }
			     bus.addStation(Sb,e);
		        }
		
	}
	public void run() throws IOException, JSONException, CloneNotSupportedException{
		  boolean flag=true;
		  double d=0;
		  Elements el=null;
		  Elements e=null;
		  while(flag){
			  Bus b = null;
			  Station s = null;
			  e=el=null;
			  for(Bus i:this.buses)// Check bus
				  if(!i.isComplete()){
					  b=i;
					  flag=true;
					 break; 
				  }else flag=false;
			  for(Station si:this.stations)// Check station
				  if(!si.isEmpty()){
					  s=si;
					  flag=true;
					  break;
				  }else flag=false;
			  if(flag){
				   b.addStation(s,el);
				  //determine path of bus
				   while(!b.isFull()&&b.getRunningTime()+b.getCurStopBus().getDurationToSchool()<this.limitTime)
				   {
					  Station Sb=null;
					  for(Station ib:this.stations)  //determine first next station
						  if(ib.getId()!=b.getCurStopBus().getId()&&!ib.isEmpty()){
							 						  
							  switch (typeDistance){
							    case 1: d=b.getCurStopBus().distanceToX1(ib); // 2d
			     		  		        break;
							    case 2: d=b.getCurStopBus().distanceToX2(ib);
							            break;
							    case 3: e=b.getCurStopBus().getDistanceDuration(ib);
							    	    d=e.getDistance(); // google
							            break;
							    default: break;
							    }
							  if(b.getRunningTime()+e.getDuration()+ib.getDurationToSchool()<this.limitTime)
							  { Sb=ib;
							    break;
							  }
						  }
					     
					    if(Sb==null)
					    	break;
					    
					   
					 	for(int i=0;i<stations.size();i++){// find next station
					     if(!this.stations.get(i).isEmpty()&&this.stations.get(i).getId()!=Sb.getId()){
					    	 double temp=0;
					    	 switch (typeDistance){
							    case 1: temp=b.getCurStopBus().distanceToX1(this.stations.get(i)); // 2d
			     		  		        break;
							    case 2: temp=b.getCurStopBus().distanceToX2(this.stations.get(i));
							            break;
							    case 3: el=b.getCurStopBus().getDistanceDuration(this.stations.get(i));
							    	    temp=el.getDistance(); // google
							            break;
							    default: break;
							    }
					    	 if(temp<d&&
					    	       b.getRunningTime()+el.getDuration()+this.stations.get(i).getDurationToSchool()<this.limitTime){
					    	   Sb=this.stations.get(i);
					    	   e=el;
					    	   d=temp; 
					    	 }
					    
					      }
					    }
					 	b.addStation(Sb,e);
				  }
				  
			  }else break;
			  b.setComplete(true);   
					     
		  }
	  }
	 
	  
}
