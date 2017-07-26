//all functions and info needed to create the Rk+1C set from Weiser's alg
package slicing;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Rk1C {
	//set Rk1C contains variables that are relevant
	//because they have a transitive data dependence on statements in BkC
	//b, REFb slicing criterion, b is a branch statement in BkC
	BkC BkC;
	ControlFlowGraph cfg;
	HashMap<Node, List<Node>> edges;
	HashMap<Node, List<Node>> revEdges;
	HashMap<Node, Set<String>> Rk1CSet;
	R0C R0C;
	HashMap<Node, Set<String>> R0CSet;
	Node cNode;
	ArrayList<String> cVars;
	
	public Rk1C(ControlFlowGraph cfg, BkC BkC, R0C R0C, Node cNode, ArrayList<String> cVars){
		this.BkC = BkC;
		this.cfg = cfg;
		this.edges = cfg.getEdges();
		this.revEdges = cfg.getReversedEdges();
		this.R0C = R0C;
		this.R0CSet = R0C.getR0CSet();
		this.Rk1CSet = new HashMap<Node, Set<String>>();
		this.cVars = null;
		this.cNode = null;
		/*
		//copy over all the values from R0CSet into Rk1CSet
		//source for iterating through hashmap: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		Iterator R0CIt = R0CSet.entrySet().iterator();
		 while (R0CIt.hasNext()) {
		        Map.Entry pair = (Map.Entry)R0CIt.next();
		        System.out.println("pair value: " + pair.getValue());
		        System.out.println("pair key: " + pair.getKey());
		        Rk1CSet.put((Node) pair.getKey(), (Set<String>) pair.getValue());
		        System.out.println(pair.getKey() + " = " + pair.getValue());
		        R0CIt.remove(); // avoids a ConcurrentModificationException
		    }*/
	}
	
	public HashMap<Node, Set<String>> getRk1CSet(){
		return this.Rk1CSet;
	}
	
	public void buildRk1C(){
		//node 8 not being included?
		//need to build up Rk1C for each branch node used as slicing criterion
		Set<Node> branchNodes = BkC.getBkCSet();
		Object[] branchNodesArr = branchNodes.toArray();
		for(int k = 0; k < branchNodesArr.length; k++){
			this.cNode = (Node) branchNodesArr[k];
			this.cVars = cNode.getRef();
			//build R0CSet through backwards dfs
			Stack<Node> stack = new Stack<Node>();
			case1a(cNode, cVars); //set base case - initial criterion node
			//add all the edges of the criterion node to the stack
			List<Node> criterionEdges = revEdges.get(cNode);
			stack.addAll(criterionEdges);
			ArrayList<Node> visited = new ArrayList<Node>(); //keep track of the visited nodes
			while(stack.isEmpty() == false){ //while there are still more nodes/edges to go through
				Node i = stack.pop();
				if(visited.contains(i) == false){ //if current node hasn't been visited yet
					//add the edges to stack to explore next
					List<Node> connectedNodes = revEdges.get(i);
					if(connectedNodes != null){ //if we actually have edges to add
						stack.addAll(connectedNodes);
					}
				}
				//node i -> node j edge
				case2a(i); //weiser 2a for adding vars to R0C set
				case2b(i); //weiser 2b
				
			}
		}
	}
	
	
	public void case1a(Node cNode, ArrayList<String> cVars){
		//current node is criterion node. set criterion vars for its R0CSet
		Set<String> criterionVarsSet = new HashSet<String>(cVars); //turn criterionvars to a set from arraylist
		if(criterionVarsSet != null){
			//insert new values into Rk1C
			Rk1CSet.put(cNode, criterionVarsSet);
		}
	}
	
	public void case2a(Node i){
		//all edges from i to j,
		//case 2a
		//i comes before j
		//v is in REF(i), a w in both DEF(i) and R0C(j)
		
		List<Node> jNodes = edges.get(i); //get all the nodes connected to i
		ListIterator<Node> jNodesIt = jNodes.listIterator();
		//possible vars, located in Ref(i)
		ArrayList<String> iREF = i.getRef(); //REFi
		Set<String> iREFSet = new HashSet<String>(iREF);
		ArrayList<String> iDEF = i.getDef(); //DEFi
		while(jNodesIt.hasNext()){//iterate through the nodes connected to i
			Node j = jNodesIt.next();
			//get all vars in R0C(j)
			Set<String> jR0C = Rk1CSet.get(j);
			//see if any of the vars match what is in set def(i)
			if(jR0C != null){ //we have no var to add if nothing exists in R0C j
				Object[] jR0CArr = jR0C.toArray();
				for(int k = 0; k < jR0CArr.length; k++){
					if(iDEF.contains(jR0CArr[k])){ // a var w in both Def(i) and R0C(j)
						//add the v vars in Refi to R0Cset of i
						if(iREFSet.isEmpty() == false){
							Rk1CSet.put(i, iREFSet);
							System.out.println("iREFSET we are adding: " + iREFSet);
						}
					}
		
				}
			}
			
		}
	}
	
	public void case2b(Node i){
		//case 2b
		//i before j
		//v NOT IN Def(i), v is in R0C(j)
		List<Node> jNodes = edges.get(i); //get all the nodes connected to i
		ListIterator<Node> jNodesIt = jNodes.listIterator();
		//list of vars to add to R0CSet for node i
		Set<String> vars = new HashSet<String>();
		ArrayList<String> iDEF = i.getDef(); //v NOT IN Def(i)
		while(jNodesIt.hasNext()){//iterate through the nodes connected to i
			Node j = jNodesIt.next();
			//for the vars in R0C of j
			Set<String> R0CJ= Rk1CSet.get(j);
			if(R0CJ != null){
				Object[] R0CJArr = R0CJ.toArray();
				for(int k = 0; k < R0CJArr.length; k++){
					String v = (String) R0CJArr[k]; //we have found a variable to check for in DEF(i)
					if(iDEF.contains(v) == false){
						vars.add(v);
					}
				}
			}
		}
		//we have found all vars that should be added to R0CSet, set them
		//only set them if vars actually contains something
		if(vars.isEmpty() == false){
			System.out.println("vars we are adding: " + vars);
			Rk1CSet.put(i, vars);
		}
	}

}
