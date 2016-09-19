package test;
import java.io.IOException;

import org.json.JSONException;

import heuristic.Heuristic;
import heuristic.Heuristic_1;

public class test {

	public static void main(String[] args) throws JSONException, IOException, CloneNotSupportedException {
		Heuristic h=new Heuristic_1("test3.txt",3);
		h.run();
		h.printSolution();
		h.printRoutingBasic("solutionBasic_3.txt");
		
		}

}
