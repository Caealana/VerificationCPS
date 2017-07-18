//all info and functions needed to compute S0C set from Weiser's Algorithm
package slicing;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class S0C {
	R0C R0C;
	Set<Node> S0C;
	ControlFlowGraph cfg;
	HashMap<Node, List<Node>> revEdges;
	HashMap<Node, List<Node>> edges;
	Node criterionNode;
	
	public S0C(R0C R0C, ControlFlowGraph cfg, Node criterionNode){
		this.R0C = R0C;
		this.S0C = new HashSet<Node>();
		this.cfg = cfg;
		this.revEdges = cfg.getReversedEdges();
		this.edges = cfg.getEdges();
		this.criterionNode = criterionNode;
	}
	
	public void buildS0C(){
		//node i, if DEF(i) intersection R0C(j) is not empty.
		//j is every successor node to i
		//dfs to go through every node
		Stack<Node> stack = new Stack<Node>();
		stack.push(criterionNode);
		while(stack.isEmpty() == false){
			Node i = stack.pop();
		}
	}
}
