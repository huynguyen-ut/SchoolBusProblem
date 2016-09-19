package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

public class Bus {
	int id;
	int capacity;
	int nS;
	double speed;
	double runningTime;
	Station curStation; 
	Path path;
	List<Student> students;
	int nSUP;// HS len xe o moi tram
	Location target;
	List<StateBus> states;
	public Bus(int id,int c,double p,Location tar) throws CloneNotSupportedException{
		this.id=id;
		this.capacity=c;
		this.nS=0;
		speed=p;
		nSUP=0;
		path=new Path(id);
		curStation=null;
		students=new ArrayList<Student>();
		states=new ArrayList<StateBus>();
		runningTime=0;
		complete=false;
		this.target=tar;
		this.addState(new StateBus(this,-1)); // khoi tao khi bus chi di
	}
public void addStation(Station s,Elements e) throws CloneNotSupportedException{
		
		if(!this.path.isEmpty())
	         this.runningTime+=e.getDuration()+s.getStoptime();
		this.path.addStation(s);
		this.curStation=s;
	    int  t=0;
	    if(this.getSits()> s.getnS())
		   t=s.getnS(); else t=this.getSits();
	    this.nSUP=t;
	    for(int i=0;i<t;i++)
	    this.students.add(s.releaseStudent());
	    this.nS=this.students.size();
	    // State of station
	     s.addState(new StateStation(s,this.id));
	    // State of bus
	    this.addState(new StateBus(this,s.getId()));
	     
	}

    public int getnSUP() {
	return nSUP;
}
	public void rollback(StateBus statebus){
    	this.nS=statebus.getnS();
		path=statebus.getPath();
		students=statebus.getStudents();
		states=statebus.getStates();
		runningTime=statebus.getRunningTime();
		complete=statebus.isComplete();
    }
	public List<StateBus> getStates() {
		return states;
	}
    public StateBus getStateBus(int idStation){
    	for(StateBus statebus:this.states)
    		if(statebus.getIdStation()==idStation)
    			return statebus;
    	return null;
    }
	public void setStates(List<StateBus> states) {
		this.states = states;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Student> getStudents() {
		return students;
	}
	
	boolean complete;
	public double getSpeed() {
		return speed;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
		this.runningTime+=this.getCurStopBus().getDurationToSchool();
	}

	public Station getCurStopBus() {
		return curStation;
	}

	public void setCurStateBus(Station curStopBus) {
		this.curStation = curStopBus;
	}

	public double getRunningTime() {
		return runningTime;
	}
	public void setRunningTime(double runningTime) {
		this.runningTime = runningTime;
	}
	public int getSits(){
		return this.capacity-this.nS;
	}
	public boolean isFull(){
		if(this.nS==this.capacity)
			return true;
		else return false;
	}
	public int getnS() {
		return nS;
	}
	
	public Path getPath() {
		return path;
	}
	
	
	public void addState(StateBus e){
		this.states.add(e);
	}
	public int getId() {
		return id;
	}

	public boolean isTour(){
		return true;
	}
	public double gettimeXtoYEclid(Location a,Location b){
		return a.distanceToX1(b)/this.speed;
	}
	public double getCompletionTime() throws IOException, JSONException{
		if(this.isComplete())
		return this.runningTime+this.curStation.getDistanceDuration(this.target).getDuration();
		return 0;
	}
}
