package heuristic;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.json.JSONException;

import model.Bus;
import model.Elements;
import model.School;
import model.Station;
import model.Student;

public abstract class Heuristic {
	  School sch;
	  List<Station> stations;
	  List<Bus> buses;
	  double limitTime;
	  double delta;	
	  int typeDistance;
	  public abstract void run() throws IOException, JSONException, CloneNotSupportedException;
	  public Heuristic(String file,int typeDistance) throws IOException, JSONException, NumberFormatException, CloneNotSupportedException{
		  this.limitTime=30;
		  this.delta=0.00834;
		  this.typeDistance=typeDistance;
		  stations=new ArrayList<Station>(); 
		  buses=new ArrayList<Bus>();
		  Scanner inFile = new Scanner(new File(file));
     	  String t = inFile.nextLine();
     	  String[] tokens = t.split("[\\t]");
     	  sch=new School(1,Double.parseDouble(tokens[0]),Double.parseDouble(tokens[1]));
     	  Bus b;
     	  for(int i=0;i<Integer.parseInt(tokens[2]);i++) // initial bus
     	  { b=new Bus(i,Integer.parseInt(tokens[3]),Double.parseDouble(tokens[4]),this.sch);
     	    this.buses.add(b);
     	  }
     	  Station sb;
     	  int ib=1;
     	  while(inFile.hasNextLine()){  // initial bus stop
     		  t = inFile.nextLine();
     		  tokens = t.split("[\\t]");
     		 sb=new Station(ib,Double.parseDouble(tokens[0]),Double.parseDouble(tokens[1]));
     		
     		 switch (typeDistance){//compute distance
     		  case 1: sb.setDistanceToSchool(sb.distanceToX1(sch)); // 2d
     		  		  break;
     		  case 2: sb.setDistanceToSchool(sb.distanceToX2(sch)); // with lat lon
     		  		  break;
     		  case 3: Elements e=sb.getDistanceDuration(sch);
     			      sb.setDistanceToSchool(e.getDistance());// Distance
     			      sb.setTimeToS(e.getDuration());        // set duration
     			      break;
     		  default: break;
     		  } 
     		 
     		
     		 for(int i=0;i<Integer.parseInt(tokens[2]);i++)// add student
     			 sb.addStudent(new Student(i,0,0));
     		 ib++;
     		stations.add(sb);
     	   }	   
		  
		  Collections.sort(stations);
		  inFile.close();
       }
	  
	  public Bus chooseBus(){
		  for(Bus i:this.buses)// Check bus
			  if(!i.isComplete()){
				 return i;
			  }
		  return null;
	  }
	  
	  public void printSolution(){
		  for(Bus b:this.buses){
			  if(!b.getPath().isEmpty())
			  {System.out.print(b.getId()+":");
			  for(Station s:b.getPath().getStations()){
				   System.out.print(s.getId()+"->");
				  
			    }
			  System.out.println("School");
			  }}
	  }
	  public double getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(double limitTime) {
		this.limitTime = limitTime;
	}
	public int getTypeDistance() {
		return typeDistance;
	}
	public void setTypeDistance(int typeDistance) {
		this.typeDistance = typeDistance;
	}
	public List<Bus> getBuses() {
		return buses;
	}
	public List<Station> getStations() {
		return stations;
	}
	public void printRouting(String file) throws IOException, JSONException{
		  PrintWriter writer = new PrintWriter(file, "UTF-8");
	        for(Bus b:this.buses)
	        {
	             
	    	  if(!b.getPath().isEmpty())
	    		{
	    		  writer.println(b.getId()+"\t"+"\t"+"\t"+"\t"+b.getnS());
	    		  for(Station s:b.getPath().getStations())
	    				  writer.println(s.getId()+"\t"+s.getLat()+"\t"+s.getLon()
	    				          +"\t"+(s.getStateStation(b.getId()).getnS()+b.getStateBus(s.getId()).getnSUP())
	    						  +"\t"+b.getStateBus(s.getId()).getnSUP()+"\t"+b.getStateBus(s.getId()).getRunningTime());
	    		    			    
	    		  writer.println("X"+"\t"+this.sch.getLat()+"\t"+this.sch.getLon()+"\t"+"\t"+"\t"+b.getCompletionTime());
	    		  writer.println("*"+"\t"+"*");
	    		}
	        }
	      writer.close();
	  }
	public void printRoutingBasic(String file) throws IOException, JSONException{
		  PrintWriter writer = new PrintWriter(file, "UTF-8");
	        for(Bus b:this.buses)
	        {
	             
	    	  if(!b.getPath().isEmpty())
	    		{
	    		  for(Station s:b.getPath().getStations())
	    				  writer.println(b.getId()+"\t"+s.getLat()+"\t"+s.getLon()
	    				          +"\t"+b.getStateBus(s.getId()).getnSUP());
	    		  writer.println(b.getId()+"\t"+this.sch.getLat()+"\t"+this.sch.getLon());
	    		  writer.println("*");
	    		}
	        }
	      writer.close();
	  }
	

}
