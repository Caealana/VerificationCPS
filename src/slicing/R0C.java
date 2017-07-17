package slicing;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

public class R0C {
	//needed: criterion node
	HashMap<Node, Set<String>> R0CSet; //final R0C
	Node criterionNode;
	ArrayList<String> criterionVars;
	ControlFlowGraph cfg;
	HashMap<Node, List<Node>> revEdges;
	HashMap<Node, List<Node>> edges;
	
	public R0C(Node cNode, ArrayList<String> cVars, ControlFlowGraph cfg){
		this.R0CSet = new HashMap<Node, Set<String>>();
		this.criterionNode = cNode;
		this.criterionVars = cVars;
		this.cfg = cfg;
		this.revEdges = cfg.getReversedEdges();
		this.edges = cfg.getEdges();
	}
	
	public HashMap<Node, Set<String>> getR0CSet(){
		return R0CSet;
	}
	
	public void buildR0C(){
		//build R0CSet through backwards dfs
		Stack<Node> stack = new Stack<Node>();
		case1a(); //set base case - initial criterion node
		//add all the edges of the criterion node to the stack
		List<Node> criterionEdges = revEdges.get(criterionNode);
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
	
	public void case1a(){
		//current node is criterion node. set criterion vars for its R0CSet
		Set<String> criterionVarsSet = new HashSet<String>(criterionVars); //turn criterionvars to a set from arraylist
		R0CSet.put(criterionNode, criterionVarsSet);
		
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
			Set<String> jR0C = R0CSet.get(j);
			//see if any of the vars match what is in set def(i)
			if(jR0C != null){ //we have no var to add if nothing exists in R0C j
				Object[] jR0CArr = jR0C.toArray();
				for(int k = 0; k < jR0CArr.length; k++){
					if(iDEF.contains(jR0CArr[k])){ // a var w in both Def(i) and R0C(j)
						//add the v vars in Refi to R0Cset of i
						if(iREFSet.isEmpty() == false){
							R0CSet.put(i, iREFSet);
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
			Set<String> R0CJ= R0CSet.get(j);
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
			R0CSet.put(i, vars);
		}
	}
}
