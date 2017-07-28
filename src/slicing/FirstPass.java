//builds S0C and R0C
package slicing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

public class FirstPass {
	S0C S0C;
	R0C R0C;
	Node cNode;
	ArrayList<String> cVars;
	ControlFlowGraph cfg;
	HashMap<Node, List<Node>> edges;
	HashMap<Node, List<Node>> revEdges;
	public FirstPass(Node cNode, ArrayList<String> cVars, ControlFlowGraph cfg){
		this.cNode = cNode;
		this.cVars = cVars;
		this.cfg = cfg;
		this.R0C = new R0C(this.cNode, this.cVars, this.cfg);
		this.S0C = new S0C(this.R0C,this.cfg, this.cNode);
		this.edges = this.cfg.getEdges();
		this.revEdges = this.cfg.getReversedEdges();
	}
	
	public R0C getR0C(){
		return this.R0C;
	}
	
	public S0C getS0C(){
		return this.S0C;
	}
	
	public void dfsFirstPass(){
		Stack<Node> stack = new Stack<Node>(); //dfs stack
		HashMap<Node, List<Node>> revEdges = cfg.getReversedEdges();
		ArrayList<Node> visited = new ArrayList<Node>();
		R0C.case1a(); //set base case - initial criterion node for R0C
		//add all the edges of the criterion node to the stack
		List<Node> criterionEdges = revEdges.get(cNode);
		if(criterionEdges != null){
			stack.addAll(criterionEdges);
		}
		visited.add(cNode);
		while(stack.isEmpty() == false){
			Node i = stack.pop();
			if(visited.contains(i) == false){ //node hasn't been visited yet
				//finish building R0C sets
				R0C.case2a(i);
				R0C.case2b(i);
				//while doing that, do S0C
				S0C.innerLoop(i);
				//add edges to stack
				List<Node> iEdges = revEdges.get(i);
				if(iEdges != null){ //edges not null, don't go past criterion
					stack.addAll(iEdges); //add the successors of i, also to stack
					//stack.addAll(iEdges);
				}
			}
			
		}
	}
	
}
