//takes a CFG and does depth first search through it
package evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

public class DFS {
	ControlFlowGraph cfg;
	//HashMap<Node, List<Node>> edges;
	
	public DFS(ControlFlowGraph cfg){
		this.cfg = cfg;
	}
	
	public void runDFS(){
		HashMap<Node, List<Node>> edges = cfg.getEdges();
		Stack<Node> stack = new Stack<Node>();
		stack.push(cfg.getStartNode());
		ArrayList<Node> visited = new ArrayList<Node>();
		while(stack.isEmpty() == false){
			Node current = stack.pop();
			if(visited.contains(current) == false){
				List<Node> currentEdges = edges.get(current);
				if(currentEdges != null){
					stack.addAll(currentEdges);
				}
			}
			
		}
	}
}
