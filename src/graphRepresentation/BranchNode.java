package graphRepresentation;

import java.util.ArrayList;
import java.util.Set;

public class BranchNode extends Node{
	//has INFL set
	private Set<Node> INFL;
	
	public BranchNode(String type, ArrayList<String> r, ArrayList<String> d, int index, Set<Node> INFL){
		super(type, r, d, index);
		this.INFL = INFL;
	}
	
	public void setINFL(Set<Node> infl){
		this.INFL = infl;
	}
	
	public Set<Node> getINFL(){
		return INFL;
	}
	
	public void addINFL(Node node){
		INFL.add(node);
	}
}
