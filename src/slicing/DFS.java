package slicing;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.Stack;

//forward DFS traversal based on dependency info
public class DFS {
	collectDep depList;
	Stack<Node> stack;
	ControlFlowGraph cfg;
	
	public DFS(collectDep depList, ControlFlowGraph cfg){
		this.depList = depList;
		this.stack = new Stack<Node>();
		this.cfg = cfg;
	}
	
	//how should we stop traversal?
	//we come to a branch statement and decide which one to take?
	//also some nodes omitted due to slicing
	
}
