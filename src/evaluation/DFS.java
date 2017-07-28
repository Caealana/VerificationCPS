//takes a CFG and does depth first search through it
package evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
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
	
	public void runSlicedDFS(Set<Node> slice){
		HashMap<Node, List<Node>> edges = cfg.getEdges();
		Stack<Node> stack = new Stack<Node>();
		stack.push(cfg.getStartNode());
		ArrayList<Node> visited = new ArrayList<Node>();
		while(stack.isEmpty() == false){
			Node current = stack.pop();
			System.out.println("currentNode: " + current);
			if(visited.contains(current) == false){
				List<Node> currentEdges = edges.get(current);
				if(currentEdges != null){
					//go through the edges, see if they are in slice, if they are, add it
					ListIterator<Node> currentEdgesIterator = currentEdges.listIterator();
					while(currentEdgesIterator.hasNext()){
						Node edgeNode = currentEdgesIterator.next();
						if(slice.contains(edgeNode)){ //if slice contains edgenode
							stack.push(edgeNode); //add edgenode to stack
						}
					}
					
				}
			}
			
		}
	}
}
