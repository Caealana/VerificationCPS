//all info and functions needed to create the Sk+1C set from weiser's alg
package slicing;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

public class Sk1C extends S0C{
	//same as S0C, except using Rk1C instead of R0C
	private Rk1C Rk1C;
	private BkC BkC;
	
	public Sk1C(R0C R0C, ControlFlowGraph cfg, Node criterionNode, Node start, BkC BkC, Rk1C Rk1C){
		super(R0C, cfg, criterionNode, start);
		this.BkC = BkC;
		this.Rk1C = Rk1C;
	}
	

}
