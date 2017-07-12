package slicing;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

//forward DFS traversal based on dependency info
public class DFS {
	collectDep depList;

	ControlFlowGraph cfg;
	int nodeCount;
	Node start;
	
	public DFS(collectDep depList, ControlFlowGraph cfg, int nodeCount, Node start){
		this.depList = depList;
		this.cfg = cfg;
		this.nodeCount = nodeCount;
		this.start = start;
	}
	
	//how should we stop traversal?
	//we come to a branch statement and decide which one to take?
	//also some nodes omitted due to slicing
	public void forwardDFS(){
		Stack<Node> stack = new Stack<Node>(); //keep track of dfs traversal
		boolean[] visited = new boolean[nodeCount];
		stack.push(start);
		HashMap<Node, List<Node>> edges = cfg.getEdges();
		Set<Node> S0C = depList.getS0C();
		Set<Node> Sk1C = depList.getSk1C();
		Set<Node> BkC = depList.getBkC();
		
		while(stack.isEmpty() == false){
			Node current = stack.pop();
			System.out.println("current node: ");
			System.out.println(current);
			
			//add nodes to stack from edge list
			if(visited[current.getIndex()] == false){
				visited[current.getIndex()] = true;
				List<Node> currentEdges = edges.get(current);
				if(currentEdges != null){
					//push edges to stack
					Iterator<Node> edgeIt = currentEdges.iterator();
					while(edgeIt.hasNext()){
						//check if we want to push the next edge to the stack?
						//if edge in directly relevant statements, in indiirectly relevant statements
						Node next = edgeIt.next();
						//S0C, Sk1C, BkC <- if a node is in one of these sets, include it
						if(S0C.contains(next) | Sk1C.contains(next) | BkC.contains(next)){
							stack.push(next);
						}
					}
				}
			}
			
		}
	}
	
}
