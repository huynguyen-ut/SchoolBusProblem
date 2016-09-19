package mathmodel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gurobi.GRB;
import gurobi.GRBCallback;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;
import model.Bus;

public class MathModel extends GRBCallback {
        int C;
        int V;
        int E;
        int S;
        int Cij[][];
        int Sil[][];
        GRBEnv    env ;
        GRBModel  model; 
        GRBVar x[][][];
        GRBVar y[][];
        GRBVar z[][][];
        public MathModel(String file) throws FileNotFoundException, GRBException{
        	 @SuppressWarnings("resource")
			Scanner inFile = new Scanner(new File(file));
        	 String s = inFile.nextLine();
        	 GRBVar[][][] x = new GRBVar[V][V][V];
             
             for (int i = 0; i < V; i++)
               for (int j = 0; j <= i; j++)
               for(int k=0;k<V;k++){
                 x[i][j][k] = model.addVar(0.0, 1.0, Cij[i][j],
                                           GRB.BINARY,
                                         "x"+String.valueOf(i)+"_"+String.valueOf(j));
                 //vars[j][i] = vars[i][j];
               }

        }
        public MathModel(GRBVar [][][]x,GRBVar [][][]y){
        	x=x;
        	y=y;
        	
        }
        public void solve() throws GRBException{
     	   this.model.optimize();
     	   
        }
        public void buildModel() throws GRBException{
        	 GRBLinExpr expr = null;
        	 GRBLinExpr expr1 = null;
        	 //Rang buoc 2:
        	   for(int i=0;i<V;i++)
        		   for(int k=0;k<V;k++){
        			   expr = new GRBLinExpr();
        			   expr1 = new GRBLinExpr();
        			   for(int j=0;j<V;j++){
        				   expr.addTerm(1.0,x[i][j][k]);
        				   expr.addTerm(-1.0,x[j][i][k]);
        				   
        				   expr1.addTerm(1.0,x[i][j][k]);
        				   expr1.addTerm(1.0,y[i][k]);
        			   }
        			   model.addConstr(expr, GRB.EQUAL, 0, "2.1"); 
        			   model.addConstr(expr1, GRB.EQUAL, 0, "2.2");
        			}
        	 //rang buoc 3:
        	   for(int i=1;i<V;i++){
        		   expr = new GRBLinExpr();
        		   for(int k=0;k<V;k++)
        		   {
        			 
        			   expr.addTerm(1.0,y[i][k]);
        		   }
        	   model.addConstr(expr, GRB.LESS_EQUAL, 1, "3"); 
        	   }

        	// rang buoc 5:
        	  for(int l=0;l<S;l++)
        		  for(int i=0;i<V;i++){
        			  expr = new GRBLinExpr();
        		  for(int k=0;k<V;k++){
        			  expr.addTerm(1.0,z[i][l][k]);
        			  
        		  }
        		  model.addConstr(expr, GRB.LESS_EQUAL, Sil[i][l], "1"); 
        		  }
        	//rang buoc 6
        	  for(int k=0;k<S;k++)
        	  {
        		  expr = new GRBLinExpr();
        		  for(int i=0;i<V;i++)
        			  for(int l=0;l<S;l++)
        				  expr.addTerm(1.0,z[i][l][k]);
        		  model.addConstr(expr,GRB.LESS_EQUAL, C, "");
        	  }
        	  //rang buoc 7
        	  for(int i=0;i<V;i++)
        		  for(int l=0;l<S;l++)
        			  for(int k=0;k<V;k++){
        				  expr.addTerm(1.0,z[i][l][k]);
        				  expr.addTerm(-1.0, y[i][k]);
        				  model.addConstr(expr,GRB.LESS_EQUAL, 0, "");
        			  }
        	  //rang buoc 8
        	  for(int l=0;l<S;l++)
        		  for(int i=0;i<S;i++)
        			  for(int k=0;k<S;k++){
        				  
        			  }
        }
		@Override
		protected void callback() {
			// TODO Auto-generated method stub
			 try {
			      if (where == GRB.CB_MIPSOL) {
			        // Found an integer feasible solution - does it visit every node?
			        int n = x.length;
			        List<Bus> ListBus= findpath(this.getSolution());
                    for(Bus b:ListBus)
			        if (b.isTour()) {
			          // Add subtour elimination constraint
			          GRBLinExpr expr = new GRBLinExpr();
			          for (int i = 0; i < b.getPath().getnStation(); i++)
			            for (int j = i+1; j < b.getPath().getnStation(); j++)
			            	expr.addTerm(1.0, x[i][j][b.getId()]);
			          addLazy(expr, GRB.LESS_EQUAL, b.getPath().getnStation()-1);
			        }
			      }
			    } catch (GRBException e) {
			      System.out.println("Error code: " + e.getErrorCode() + ". " +
			          e.getMessage());
			      e.printStackTrace();
			    }

		}
		protected static List<Bus> findpath(double[][][] sol)
		  {
		   List<Bus> L=new ArrayList<Bus>();
		   
		   return L;
		  }

		private double[][][] getSolution() {
			// TODO Auto-generated method stub
			double [][][]s = null;
			return s;
		}
}
