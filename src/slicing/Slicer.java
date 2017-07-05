package slicing;

import java.util.*;
import graphRepresentation.*;

public class Slicer implements Runnable {
	private ControlFlowGraph cfg, revCFG;
	private HashMap<Integer, Set<Node>> bNodesInBlocks;
	private HashMap<String, Set<Object>> depList;
	private Set<Integer> slice;
	private String criterion;
	public boolean forward;
	private boolean[] visited;
	
	public Slicer(ControlFlowGraph graph, boolean forward, String criterion) {
		cfg = graph;
		revCFG = new ControlFlowGraph(cfg.getReversedEdges());
		bNodesInBlocks = new HashMap<Integer, Set<Node>>();
		depList = new HashMap<String, Set<Object>>();
		slice = new HashSet<Integer>();
		this.criterion = criterion; //string
		this.forward = forward; //why is forward just forward instead of boolean?
		visited = new boolean[1000];
	}
	
	public void setCFG(ControlFlowGraph cfg) {
		this.cfg = cfg;
		revCFG = new ControlFlowGraph(cfg.getReversedEdges()); //what do we need reversed cfg for
	}
	
	public ControlFlowGraph getCFG() {
		return cfg;
	}
	
	public void extractNodeNumbers(String var) {
		for(Object o : depList.get(var)) {
			if(o instanceof String) {
				extractNodeNumbers((String) o);
			} else {
				slice.add((int) o);
			}
		}
	}
	
	private void updateBackwardDepList(List<Node> queue, Node n) {
		if(visited[n.getIndex()]) {
			switch(n.getType()) {
				case "assign":
					n.setBlocksInside(revCFG.getParents(n).get(0).getBlocksInside());
					for(int i : n.getBlocksInside()) {
						
					}
					return;
				case "if":	// do what is normally done when "if" encountered in backward propagation
					slice.add(n.getIndex());
					n.setBlocksInside(revCFG.getParents(n).get(0).getBlocksInside());
					int curBlock = n.removeLastBlock();	// last end statement
					for(Node nodeInCurBlock : bNodesInBlocks.get(curBlock)) {
						for(String d : nodeInCurBlock.getDef()) {
							if(depList.containsKey(d)) {
								depList.get(d).add(n.getIndex());
								depList.get(d).addAll(n.getRef());
							} else {
								Set<Object> refs = new HashSet<Object>();
								refs.add(n.getIndex());
								refs.addAll(n.getRef());
								depList.put(d, refs);
							}
						}
					}
					break;
				default:	// don't care about anything else
					return;
			}
			return;
		}
		List<Node> children = revCFG.getChildren(n);
		for(Node child : children) {
			if(queue.indexOf(child) < 0) {
				queue.add(child);
			}
		}
		switch(n.getType()) {
			case "start":
				slice.add(n.getIndex());
				break;
			case "assign":
				n.setBlocksInside(revCFG.getParents(n).get(0).getBlocksInside());
				for(int i : n.getBlocksInside()) {
					if(bNodesInBlocks.containsKey(i)) {
						bNodesInBlocks.get(i).add(n);
					} else {
						Set<Node> nodes = new HashSet<Node>();
						nodes.add(n);
						bNodesInBlocks.put(i, nodes);
					}
				}
				// update dependences within node n
				for(String d : n.getDef()) {
					if(depList.containsKey(d)) {
						depList.get(d).add(n.getIndex());
						depList.get(d).addAll(n.getRef());
					} else {
						Set<Object> refs = new HashSet<Object>();
						refs.add(n.getIndex());
						refs.addAll(n.getRef());
						depList.put(d, refs);
					}
				}
				break;
			case "end":
				slice.add(n.getIndex());
				n.setBlocksInside(revCFG.getParents(n).get(0).getBlocksInside());
				n.addBlockInside(n.getIndex());
				for(int i : n.getBlocksInside()) {
					if(bNodesInBlocks.containsKey(i)) {
						bNodesInBlocks.get(i).add(n);
					} else {
						Set<Node> nodes = new HashSet<Node>();
						nodes.add(n);
						bNodesInBlocks.put(i, nodes);
					}
				}
				break;
			case "if":
				slice.add(n.getIndex());
				n.setBlocksInside(revCFG.getParents(n).get(0).getBlocksInside());
				int curBlock = n.removeLastBlock();	// last end statement
				for(Node nodeInCurBlock : bNodesInBlocks.get(curBlock)) {
					for(String d : nodeInCurBlock.getDef()) {
						if(depList.containsKey(d)) {
							depList.get(d).add(n.getIndex());
							depList.get(d).addAll(n.getRef());
						} else {
							Set<Object> refs = new HashSet<Object>();
							refs.add(n.getIndex());
							refs.addAll(n.getRef());
							depList.put(d, refs);
						}
					}
				}
				break;
			case "stop":
				slice.add(n.getIndex());
				break;
			default:
				slice.add(n.getIndex());
				break;
		}
		visited[n.getIndex()] = true;
	}
	
	public Set<Integer> generateBackwardSlice() {
		slice = new HashSet<Integer>();
		System.out.println(" - Backward - ");
		System.out.println(revCFG);
		List<Node> queue = new ArrayList<Node>();
		Iterator<Node> keyIt = revCFG.getKeyIterator();
		while(keyIt.hasNext()) {
			Node n = keyIt.next();
			if(n.equals(new Node(false), false)) {
				queue.add(n);
			}
		}
		while(queue.size() > 0) {
			updateBackwardDepList(queue, queue.remove(0));
		}
		System.out.println(depList);
		extractNodeNumbers(criterion);
		return slice;
	}
	
	public void updateForwardDepList(List<Node> queue, Node n) {
		if(visited[n.getIndex()]) {
			return;
		}
		List<Node> children = cfg.getChildren(n);
		for(Node child : children) {
			if(queue.indexOf(child) < 0) {
				queue.add(child);
			}
		}
		switch(n.getType()) {
			case "start":
				slice.add(n.getIndex());
				break;
			case "assign":
				n.setBlocksInside(cfg.getParents(n).get(0).getBlocksInside());
				// update block predicate dependencies for node n
				for(int i : n.getBlocksInside()) {
					Node block = cfg.getNode(i);
					for(String d : n.getDef()) {
						if(depList.containsKey(d)) {
							depList.get(d).addAll(block.getRef());
						} else {
							Set<Object> refs = new HashSet<Object>();
							refs.addAll(block.getRef());
							depList.put(d, refs);
						}
					}
				}
				// update dependences within node n
				for(String d : n.getDef()) {
					if(depList.containsKey(d)) {
						depList.get(d).add(n.getIndex());
						depList.get(d).addAll(n.getRef());
					} else {
						Set<Object> refs = new HashSet<Object>();
						refs.add(n.getIndex());
						refs.addAll(n.getRef());
						depList.put(d, refs);
					}
				}
				break;
			case "if":
				slice.add(n.getIndex());
				n.setBlocksInside(cfg.getParents(n).get(0).getBlocksInside());
				n.addBlockInside(n.getIndex());
				break;
			case "end":
				slice.add(n.getIndex());
				n.setBlocksInside(cfg.getParents(n).get(0).getBlocksInside());
				n.removeLastBlock();
				break;
			case "stop":
				slice.add(n.getIndex());
				break;
			default:
				slice.add(n.getIndex());
				break;
		}
		visited[n.getIndex()] = true;
	}
	
	public void generateForwardSlice() {
		slice = new HashSet<Integer>();
		System.out.println(" - Forward - ");
		System.out.println(cfg);
		List<Node> queue = new ArrayList<Node>();
		Iterator<Node> keyIt = cfg.getKeyIterator();
		while(keyIt.hasNext()) {
			Node n = keyIt.next();
			if(n.equals(new Node(true), false)) {
				queue.add(n);
				break;
			}
		}
		while(queue.size() > 0) {
			updateForwardDepList(queue, queue.remove(0));
		}
		System.out.println(depList);
		extractNodeNumbers(criterion);
	}
	
	public void run() {
		if(forward) {
			generateForwardSlice();
		} else {
			generateBackwardSlice();
		}
	}
}
