package slicing;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class R0C {
	//needed: criterion node
	HashMap<Node, Set<String>> R0CSet; //final R0C
	Node criterionNode;
	ArrayList<String> criterionVars;
	ControlFlowGraph cfg;
	HashMap<Node, List<Node>> revEdges;
	
	public R0C(Node cNode, ArrayList<String> cVars, ControlFlowGraph cfg){
		this.R0CSet = new HashMap<Node, Set<String>>();
		this.criterionNode = cNode;
		this.criterionVars = cVars;
		this.cfg = cfg;
		this.revEdges = cfg.getReversedEdges();
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
				stack.addAll(connectedNodes);
			}
			//node i -> node j edge
			case2a(i); //weiser 2a for adding vars to R0C set
			
		}
		
	}
	
	public void case1a(){
		//current node is criterion node. set criterion vars for its R0CSet
		Set<String> criterionVarsSet = new HashSet<String>(criterionVars); //turn criterionvars to a set from arraylist
		R0CSet.put(criterionNode, criterionVarsSet);
		
	}
	
	public void case2a(Node i){
		//all edges from i to j,
	}
	public void case2b(){
		
	}
}
