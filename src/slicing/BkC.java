//all functions and info needed to create BkC set from weiser's alg
package slicing;

import java.util.HashSet;
import java.util.Set;

import graphRepresentation.BranchNode;
import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

public class BkC {
	//branch statements. exist such that there is
	//a node within its set of INFL and that same node is in S0C/SkC
	//this BkC doesn't do a pass through cfg, just takes list of branch statements and their INFL sets
	S0C S0C;
	ControlFlowGraph cfg;
	Set<Node> S0CSet;
	Set<Node> BkCSet;
	
	public BkC(S0C S0C, ControlFlowGraph cfg){
		this.S0C = S0C;
		this.cfg = cfg;
		this.S0CSet = S0C.getS0CSet();
		this.BkCSet = new HashSet<Node>();
	}
	
	public Set<Node> getBkCSet(){
		return this.BkCSet;
	}
	
	public void buildBkC(){
		Set<BranchNode> branches = cfg.getBranchNodes();
		Object[] branchesArr = branches.toArray();
		//go through branch statements, get INFL
		for(int k = 0; k < branchesArr.length; k++){
			BranchNode b = (BranchNode) branchesArr[k];
			Set<Node> INFL = b.getINFL();
			Object[] INFLArr = INFL.toArray();
			//loop to go through nodes in INFL set of branch
			for(int m = 0; m < INFLArr.length; m++){
				Node INFLNode = (Node) INFLArr[m];
				if(S0CSet.contains(INFLNode)){ //same node i within both INFL and S0C
					BkCSet.add(b); //then we add branch node to set
				}
			}
		}
	}
}
