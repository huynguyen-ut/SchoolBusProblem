package model;

import java.util.ArrayList;
import java.util.List;

public class StateStation extends State {
	int idBus;
	int nS;
	List<StateStation> states;
	List<Student> students;
	public StateStation(Station s,int idBus) {
		// TODO Auto-generated constructor stub
		this.idBus=idBus;
		this.nS=s.getnS();
		this.students=new ArrayList<Student>(s.getStudents());
	    this.states=new ArrayList<StateStation>(s.getStates());
	}
	
	public int getIdBus() {
		return idBus;
	}

	public int getnS() {
		return nS;
	}
	public List<StateStation> getStates() {
		return states;
	}
	public List<Student> getStudents() {
		return students;
	}
	

}
