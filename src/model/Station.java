package model;
import java.util.ArrayList;
import java.util.List;

public class Station extends Location implements Cloneable {
    
	int nS;
	double timeToS;
	double stoptime;
	List<StateStation> states;
	List<Student> students;
	public double getTimeToS() {
		return timeToS;
	}
	public void setTimeToS(double timeToS) {
		this.timeToS = timeToS;
	}
	
	public Station(int id, double x, double y) {
		super(id, x, y);
		// TODO Auto-generated constructor stub
	    nS=0;
	    timeToS=0;
	    students=new ArrayList<Student>();
	    states=new ArrayList<StateStation>();
	    this.addState(new StateStation(this,-1));  //khoi tao khi chua co bus di qua
	}
	public void addState(StateStation st){
		this.states.add(st);
	}
	public StateStation getStateStation(int idBus){
		for(StateStation state:this.states)
			if(state.getIdBus()==idBus)
				return state;
		return null;
	}
	public double getStoptime() {
		return stoptime;
	}

	public void setStoptime(double stoptime) {
		this.stoptime = stoptime;
	}

	public boolean isEmpty(){
		if(nS==0) return true;
		else return false;
	}
	public int getnS() {
		return nS;
	}
	public void setnS(int nS) {
		this.nS = nS;
	}
	public Student releaseStudent(){
		Student s=this.students.remove(0);
		this.nS=this.students.size();
		return s;
		
	}
		
	public void addStudent(Student s){
		this.students.add(s);
		nS++;
	}
	@Override
	public int compareTo(Location o) {
		// TODO Auto-generated method stub
		if(this.getDistanceToSchool()>o.getDistanceToSchool())
		return -1;
		else if(this.getDistanceToSchool()<o.getDistanceToSchool())return 1;
		    else return 0;
	}
	
	public void rollback(StateStation st){
		this.nS=st.getnS();
		this.states=st.getStates();
		this.students=st.getStudents();
	}
	public List<Student> getStudents() {
		return students;
	}
	public List<StateStation> getStates() {
		return states;
	}
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
