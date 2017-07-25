//builds S0C and R0C
package slicing;

import java.util.ArrayList;
import java.util.Stack;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

public class FirstPass {
	S0C S0C;
	R0C R0C;
	Node cNode;
	ArrayList<String> cVars;
	ControlFlowGraph cfg;
	public FirstPass(Node cNode, ArrayList<String> cVars, ControlFlowGraph cfg){
		this.cNode = cNode;
		this.cVars = cVars;
		this.cfg = cfg;
		this.R0C = new R0C(this.cNode, this.cVars, this.cfg);
		this.S0C = new S0C(this.R0C,this.cfg, this.cNode);
	}
	
	public void dfsFirstPass(){
		Stack<Node> stack = new Stack<Node>();
		stack.push(cNode);
		ArrayList<Node> visited = new ArrayList<Node>();
		R0C.case1a();
		while(stack.isEmpty() == false){
			Node i = stack.pop();
		}
	}
	
}
