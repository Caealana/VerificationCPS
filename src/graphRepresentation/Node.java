package graphRepresentation;

import java.util.*;

public class Node {
	private String type;	// start, stop, assign, if, else, end
	private ArrayList<String> ref, def;
	private List<Integer> blocksInside;
	private int index;
	
	public Node(boolean start) {	// default to start/stop node
		type = start ? "start" : "stop";
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		index = 0;
		blocksInside = new ArrayList<Integer>();
	}

	public Node(String type, ArrayList<String> r, ArrayList<String> d, int index) {
		this.type = type;
		ref = r;
		def = d;
		this.index = index;
		blocksInside = new ArrayList<Integer>();
	}
	
	public String getType() {
		return type;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public ArrayList<Integer> getBlocksInside() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i : blocksInside) {
			result.add(i);
		}
		return result;
	}
	
	public void setBlocksInside(List<Integer> blocks) {
		blocksInside = new ArrayList<Integer>();
		for(int i : blocks) {
			blocksInside.add(i);
		}
	}
	
	public int removeLastBlock() {
		//System.out.println("size is " + blocksInside.size());
		return blocksInside.remove(blocksInside.size() - 1);
	}
	
	public void addBlockInside(int bi) {
		blocksInside.add(bi);
	}

	public ArrayList<String> getRef() {
		return ref;
	}

	public ArrayList<String> getDef() {
		return def;
	}

	public boolean equals(Node n, boolean indexSpecific) {
		if(type.equals(n.getType()) && ref.equals(n.getRef()) && def.equals(n.getDef())) {
			if(indexSpecific && index != n.getIndex()) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public String toString() {
		return index + " " + type + " REF " + ref.toString() + " DEF " + def.toString();
	}
}
