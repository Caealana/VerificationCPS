//all info and functions needed to compute S0C set from Weiser's Algorithm
package slicing;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class S0C {
	R0C R0C;
	Set<Node> S0CSet;
	ControlFlowGraph cfg;
	HashMap<Node, List<Node>> revEdges;
	HashMap<Node, List<Node>> edges;
	Node criterionNode;
	Node start;
	
	public S0C(R0C R0C, ControlFlowGraph cfg, Node criterionNode, Node start){
		this.R0C = R0C;
		this.S0CSet = new HashSet<Node>();
		this.cfg = cfg;
		this.revEdges = cfg.getReversedEdges();
		this.edges = cfg.getEdges();
		this.criterionNode = criterionNode;
		this.start = start;
	}
	
	public Set<Node> getS0CSet(){
		return this.S0CSet;
	}
	
	public void buildS0C(){
		//node i, if DEF(i) intersection R0C(j) is not empty.
		//j is every successor node to i
		//dfs to go through every node
		Stack<Node> stack = new Stack<Node>();
		stack.push(start);
		ArrayList<Node> visited = new ArrayList<Node>();
		while(stack.isEmpty() == false){
			Node i = stack.pop();
			if(visited.contains(i) == false){ //Only check new nodes
				//set this node as visited
				visited.add(i);
				innerLoop(i, stack);
			}
		}
	}
	
	//compare DEF(i) R0C(j)
	public void outerLoop(Node i, Stack<Node> successors){
		ArrayList<String> iDEF = i.getDef();
		HashSet<String> iDEFSet = new HashSet<String>(iDEF);
		while(successors.isEmpty() == false){ //while this current node still has successors to check
			Node j = successors.pop();
			HashMap<Node, Set<String>> R0CSet = R0C.getR0CSet();
			//System.out.println("R0C in innerloop b4 get j: " + R0C.getR0CSet());
			Set<String> jR0C = R0CSet.get(j);
			if(jR0C != null & iDEFSet != null){ //can't intersect null
				iDEFSet.retainAll(jR0C); //INTERSECTIOn of Def(i) R0C(j)
				//System.out.println("R0CSet: " + R0CSet);
				//System.out.println("intersection of R0Cj and DEFi: " + jR0C);
				if(iDEFSet.isEmpty() == false){ //intersection not empty
					S0CSet.add(i); //add node i to set of S0C
					break; //don't need to check other successors
				}
				//add forward edges of this successor for next generation successors
				List<Node> jEdges = edges.get(j);
				if(jEdges != null & j!= criterionNode){ //don't want to go past criterionNode?
					successors.addAll(jEdges);
				}
			}
		}
	}
	
	public void innerLoop(Node i, Stack<Node> stack){

		//DEF of i
		//ArrayList<String> iDEF = i.getDef();
		//HashSet<String> iDEFSet = new HashSet<String>(iDEF);
		//System.out.println("i node we are checking: " + i);
		//System.out.println("DEF(i) in buildS0C: " + iDEFSet);
		List<Node> iEdges = edges.get(i);
		//another dfs - to go through the successor nodes
		Stack<Node> successors = new Stack<Node>();
		if(iEdges != null & i != criterionNode){ //edges not null, don't go past criterion
			successors.addAll(iEdges); //add the successors of i, also to stack
			stack.addAll(iEdges);
		}
		outerLoop(i, successors);
	}
}
