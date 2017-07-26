package slicing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

public class SecondPass {
	BkC BkC;
	Sk1C Sk1C;
	Rk1C Rk1C;
	Node cNode;
	S0C S0C;
	ArrayList<String> cVars;
	ControlFlowGraph cfg;
	HashMap<Node, List<Node>> edges;
	HashMap<Node, List<Node>> revEdges;
	public SecondPass(Node cNode, ArrayList<String> cVars, ControlFlowGraph cfg, S0C S0C){
		this.cNode = cNode;
		this.cVars = cVars;
		this.cfg = cfg;
		this.edges = this.cfg.getEdges();
		this.revEdges = this.cfg.getReversedEdges();
		this.S0C = S0C;
		this.BkC = new BkC(S0C, cfg);
	}
	
	public Rk1C getRk1C(){
		return this.Rk1C;
	}
	
	public S0C getS0C(){
		return this.S0C;
	}
	
	public void dfsSecondPass(){
		this.BkC.buildBkC();
		Stack<Node> stack = new Stack<Node>(); //dfs stack
		HashMap<Node, List<Node>> revEdges = cfg.getReversedEdges();
		ArrayList<Node> visited = new ArrayList<Node>();
		R0C.case1a(); //set base case - initial criterion node for R0C
		//add all the edges of the criterion node to the stack
		List<Node> criterionEdges = revEdges.get(cNode);
		stack.addAll(criterionEdges);
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
