package heuristic;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import model.Bus;
import model.Path;
import model.Station;

public class Heuristic_2 extends Heuristic_1 {

	public Heuristic_2(String file, int typeDistance) throws IOException, JSONException, CloneNotSupportedException {
		super(file, typeDistance);
		// TODO Auto-generated constructor stub
		this.run();
		this.retry();
	}
	public void retry() throws IOException, JSONException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		int distance=100;//distance max
		List<Station> stations=this.getStations();
		Path path1=null;
		Path path2=null;
		Station station1=null;
		Station station2=null;
		for(int i=0;i<stations.size()-1;i++)
			for(int j=0;j<stations.size();j++)
		{
			if(stations.get(i).distanceToX2(stations.get(i))<distance) //
		        for(Bus ib:this.getBuses())
					if(ib.getPath().isContain(stations.get(i))^ib.getPath().isContain(stations.get(j))){
						station1=stations.get(i);
						station2=stations.get(j);
			        }
		}
		  for(Bus ib:this.getBuses()){
			  if(ib.getPath().isContain(station1))
				  path1=ib.getPath();
			  if(ib.getPath().isContain(station2))
				  path2=ib.getPath();
		  }
		  
		Path pathC1=(Path)path1.clone();// path contain station1
		Path pathC2=(Path)path2.clone();
		for(Bus ib:this.getBuses())
			ib.rollback(ib.getStates().get(0));
		for(Station is:this.getStations())
			is.rollback(is.getStates().get(0));
		// run on path 1
		
		this.run();
	}

}
