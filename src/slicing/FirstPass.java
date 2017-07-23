package slicing;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class FirstPass {
	private R0C R0C;
	private S0C S0C;
	ControlFlowGraph cfg;
	Node criterionNode;
	ArrayList<String> criterionVars;
	HashMap<Node, List<Node>> revEdges;
	HashMap<Node, List<Node>> edges;
	
	public FirstPass(R0C R0C, S0C S0C, ControlFlowGraph cfg, Node cNode, ArrayList<String> cVars){
		this.R0C = R0C;
		this.S0C = S0C;
		this.cfg = cfg;
		this.criterionNode = cNode;
		this.criterionVars = cVars;
		this.edges = this.cfg.getEdges();
		this.revEdges = this.cfg.getReversedEdges();
		
	}
	
	public void cfgFirstPass(){
		Stack<Node> stack = new Stack<Node>();
		R0C.case1a(); //set base case - initial criterion node
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
			R0C.case2a(i); //weiser 2a for adding vars to R0C set
			R0C.case2b(i); //weiser 2b
			
		}
	}

}
