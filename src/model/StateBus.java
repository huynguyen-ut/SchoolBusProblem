package model;

import java.util.ArrayList;
import java.util.List;

public class StateBus extends State {
	int idStation;// id of station which bus run through with 
	int nS;
	double runningTime;
	int nSUP;
	Station curStation; 
	Path path;
	List<Student> students;
	List<StateBus> states;
	boolean complete;
	public StateBus(Bus bus,int idStation) throws CloneNotSupportedException{
		 this.idStation=idStation;
		 this.nS=bus.getnS();
		 this.runningTime=bus.getRunningTime();
		 if(bus.getCurStopBus()!=null)
		 this.curStation=(Station) bus.getCurStopBus().clone(); 
		 this.path=(Path)bus.getPath().clone();
		 this.students=new ArrayList<Student>(bus.getStudents());
		 this.states=new ArrayList<StateBus>(bus.getStates());
		 this.complete=bus.isComplete();
		 this.nSUP=bus.getnSUP();
		 
	}
	public int getnSUP() {
		return nSUP;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public List<StateBus> getStates() {
		return states;
	}
	public int getIdStation() {
		return idStation;
	}
	public int getnS() {
		return nS;
	}
	public double getRunningTime() {
		return runningTime;
	}
	public Station getCurStation() {
		return curStation;
	}
	public Path getPath() {
		return path;
	}
	public List<Student> getStudents() {
		return students;
	}
	public boolean isComplete() {
		return complete;
	}
	
}
