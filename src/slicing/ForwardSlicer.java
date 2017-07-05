package slicing;

import java.util.*;
import graphRepresentation.*;

public class ForwardSlicer implements Runnable {
	private ControlFlowGraph cfg;
	private HashMap<String, Set<Object>> depList;
	private Set<Integer> slice;
	private boolean[] visited;
	private String criterion;
	
	public ForwardSlicer(ControlFlowGraph cfg, HashMap<String, Set<Object>> depList, Set<Integer> slice,
			boolean[] visited, String criterion) {
		this.cfg = cfg;
		this.depList = depList;
		this.slice = slice;
		this.criterion = criterion;
		this.visited = visited;
	}
	
	public ControlFlowGraph getCFG() {
		return cfg;
	}
	
	public void extractNodeNumbers(String var) {
		if(!depList.containsKey(var)) {
			return;
		}
		for(Object o : depList.get(var)) {
//			if(o == null) {
//				return;
//			}
			if(o instanceof String) {
				extractNodeNumbers((String) o);
			} else {
				slice.add((int) o);
			}
		}
	}
	
	private void updateForwardDepList(List<Node> queue, Node n) {
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
							depList.get(d).add(i);
							depList.get(d).addAll(block.getRef());
						} else {
							Set<Object> refs = new HashSet<Object>();
							refs.add(i);
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
		// slice = new HashSet<Integer>();
		// System.out.println(" - Forward - ");
		// System.out.println(cfg);
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
//		System.out.println(depList);
		extractNodeNumbers(criterion);
		// System.out.println(slice);
	}
	
	public void run() {
		generateForwardSlice();
	}
}