package evaluation;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.Set;

public class CFGFromSlice {
	Set<Node> slice;
	ControlFlowGraph cfg;
	public CFGFromSlice(Set<Node> slice){
		this.slice = slice;
	}
	
}
