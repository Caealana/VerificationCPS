package evaluation;

import graphRepresentation.BranchNode;
import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class CFGFromSlice {
	//create a new cfg from the slice
	Set<Node> slice;
	Object[] sliceArr;
	ControlFlowGraph cfg;
	ArrayList<Node> INFLNodes;
	HashMap<Node, List<Node>> edges;
	ControlFlowGraph originalCFG;
	Set<BranchNode> branchNodes;
	public CFGFromSlice(Set<Node> slice, ControlFlowGraph originalCFG){
		this.slice = slice;
		this.sliceArr = slice.toArray();
		this.originalCFG = originalCFG;
		this.edges = new HashMap<Node, List<Node>>();
		this.branchNodes = new HashSet<BranchNode>();
	}
	public ControlFlowGraph buildCFG(){
		Arrays.sort(sliceArr);
		System.out.println("slice: " + slice);
		Node before = (Node)sliceArr[0];
		Node after;
		for(int i = 1; i < sliceArr.length; i++){
			after = (Node)sliceArr[i];
			if(sliceArr[i] instanceof BranchNode){
				BranchNode bNode = (BranchNode)sliceArr[i];
				this.branchNodes.add(bNode);
				Set<Node> INFL = bNode.getINFL();
				List<Node> bNodeEdges = originalCFG.getEdges().get(bNode);
				ListIterator<Node> bNodeEdgesIterator =  bNodeEdges.listIterator();
				//if the branch node's edge node is contained within slice, add it to our list of edges
				ArrayList<Node> bNodeEdgesToAdd = new ArrayList<Node>();
				while(bNodeEdgesIterator.hasNext()){
					Node edgeNode = bNodeEdgesIterator.next();
					if(slice.contains(edgeNode)){
						bNodeEdgesToAdd.add(edgeNode); //create list of nodes to connect to branch
					}
				}
				edges.put(bNode, bNodeEdgesToAdd);
			}
			if(edges.get(before)!= null){
				List<Node> currentEdges = edges.get(before);
				currentEdges.add(after);
				edges.put(before, currentEdges);
			}
			else{
				List<Node> currentEdges = new ArrayList<Node>();
				currentEdges.add(after);
				edges.put(before, currentEdges);

			}
		}
		cfg = new ControlFlowGraph(edges);
		cfg.setStartNode((Node)sliceArr[0]);
		cfg.setEndNode((Node)sliceArr[sliceArr.length-1]);
		return cfg;
	}
}
