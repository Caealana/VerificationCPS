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
			System.out.println("Node current: " + current);
			if(visited.contains(current) == false){
				visited.add(current);
				List<Node> currentEdges = edges.get(current);
				//System.out.println("currentEdges: " + currentEdges);
				if(currentEdges != null){
					//System.out.println("currentEdges trying to add: " + currentEdges);
					ListIterator<Node> currentEdgesIt = currentEdges.listIterator();
					//we only want to add to the stack nodes we havent visited already
					while(currentEdgesIt.hasNext() == true){
						Node possibleEdge = currentEdgesIt.next();
						//if(visited.contains(possibleEdge) == false){
							stack.add(possibleEdge);
						//}
					}
					//stack.addAll(currentEdges);
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
