package slicing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

public class SecondPass {
	BkC BkC;
	Sk1C Sk1C;
	Rk1C Rk1C;
	Node cNode;
	S0C S0C;
	R0C R0C;
	ArrayList<String> cVars;
	ControlFlowGraph cfg;
	HashMap<Node, List<Node>> edges;
	HashMap<Node, List<Node>> revEdges;
	ArrayList<String> bNodeVars;
	public SecondPass(Node cNode, ArrayList<String> cVars, ControlFlowGraph cfg, S0C S0C, R0C R0C){
		this.cNode = cNode;
		this.cVars = cVars;
		this.cfg = cfg;
		this.edges = this.cfg.getEdges();
		this.revEdges = this.cfg.getReversedEdges();
		this.S0C = S0C;
		this.R0C = R0C;
		this.BkC = new BkC(S0C, this.cfg);
		this.Rk1C = new Rk1C(this.cfg, this.BkC, this.R0C, this.cNode, this.cVars);
		this.Sk1C = new Sk1C(cfg, cNode, this.cfg.getStartNode(), BkC, Rk1C);
		bNodeVars = new ArrayList<String>();
	}
	
	public Rk1C getRk1C(){
		return this.Rk1C;
	}
	
	public Sk1C getSk1C(){
		return this.Sk1C;
	}
	
	public BkC getBkC(){
		return this. BkC;
	}
	
	public void dfsSecondPass(){
		this.BkC.buildBkC();
		//add all the edges of the criterion node to the stack
		Set<Node> branchNodes = BkC.getBkCSet();
		Object[] branchNodesArr = branchNodes.toArray();
		for(int k = 0; k < branchNodesArr.length; k++){
			Node cNodeRk1C = (Node) branchNodesArr[k];
			ArrayList<String> cVarsRk1C = cNodeRk1C.getRef();
			//System.out.println(cVarsRk1C);
			bNodeVars.addAll(cVarsRk1C);
			//build R0CSet through backwards dfs
			Stack<Node> stack = new Stack<Node>();
			Rk1C.case1a(cNodeRk1C, cVarsRk1C); //set base case - initial criterion node
			//add all the edges of the criterion node to the stack
			List<Node> criterionEdges = revEdges.get(cNode);
			stack.addAll(criterionEdges);
			ArrayList<Node> visited = new ArrayList<Node>(); //keep track of the visited nodes
			//this builds Rk1C from branch node up, we also want to include statements within the if/loop
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
				Rk1C.case2a(i); //weiser 2a for adding vars to R0C set
				Rk1C.case2b(i); //weiser 2b
				Sk1C.innerLoop(i);
				
			}
		}
		Rk1C.case1a(cNode, bNodeVars);
		List<Node> criterionEdges = revEdges.get(cNode);
		Stack<Node> stack = new Stack<Node>();
		if(criterionEdges != null){
			stack.addAll(criterionEdges);
		}
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
			if(Rk1C.getRk1CSet().containsKey(i) == false & Sk1C.getSk1CSet().contains(i) == false){
				//node i -> node j edge
				Rk1C.case2a(i); //weiser 2a for adding vars to R0C set
				Rk1C.case2b(i); //weiser 2b
				Sk1C.innerLoop(i);
			}
			
		}
	}
	
}
