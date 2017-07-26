//all info and functions needed to create the Sk+1C set from weiser's alg
package slicing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

public class Sk1C{
	//same as S0C, except using Rk1C instead of R0C
	private Rk1C Rk1C;
	private BkC BkC;
	private Set<Node> Sk1CSet;
	Node start;
	HashMap<Node, List<Node>> revEdges;
	HashMap<Node, List<Node>> edges;
	Node criterionNode;
	
	public Sk1C(ControlFlowGraph cfg, Node criterionNode, Node start, BkC BkC, Rk1C Rk1C){
		this.BkC = BkC;
		this.Rk1C = Rk1C;
		this.Sk1CSet = new HashSet<Node>();
		this.revEdges = cfg.getReversedEdges();
		this.edges = cfg.getEdges();
		this.start = start;
		this.criterionNode = criterionNode;
	}
	
	public Set<Node> getSk1CSet(){
		return this.Sk1CSet;
	}
	
	public void buildSk1C(){
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
				innerLoop(i);
				//add edges of i to stack
				List<Node> iEdges = edges.get(i);
				if(iEdges != null){
					stack.addAll(iEdges);
				}
			}
		}
	}
	
	public void innerLoop(Node i){

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
			//stack.addAll(iEdges);
		}
		outerLoop(i, successors);
	}
	
	//compare DEF(i) R0C(j)
	public void outerLoop(Node i, Stack<Node> successors){
		ArrayList<String> iDEF = i.getDef();
		HashSet<String> iDEFSet = new HashSet<String>(iDEF);
		while(successors.isEmpty() == false){ //while this current node still has successors to check
			Node j = successors.pop();
			HashMap<Node, Set<String>> R0CSet = Rk1C.getRk1CSet();
			//System.out.println("R0C in innerloop b4 get j: " + R0C.getR0CSet());
			Set<String> jR0C = R0CSet.get(j);
			if(jR0C != null & iDEFSet != null){ //can't intersect null
				iDEFSet.retainAll(jR0C); //INTERSECTIOn of Def(i) R0C(j)
				//System.out.println("R0CSet: " + R0CSet);
				//System.out.println("intersection of R0Cj and DEFi: " + jR0C);
				if(iDEFSet.isEmpty() == false){ //intersection not empty
					Sk1CSet.add(i); //add node i to set of S0C
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
}
