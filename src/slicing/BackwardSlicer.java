package slicing;

import java.util.*;
import graphRepresentation.*;

public class BackwardSlicer implements Runnable {
	private ControlFlowGraph revCFG;
	private HashMap<String, Set<Object>> depList; //in test 1, they give an empty hashmap dep list
	private Set<Integer> slice;
	private boolean[] visited;
	private String criterion;
	private HashMap<Integer, Set<Node>> bNodesInBlocks;
	
	public BackwardSlicer(ControlFlowGraph cfg, HashMap<String, Set<Object>> depList, Set<Integer> slice,
			boolean[] visited, String criterion) {
		revCFG = new ControlFlowGraph(cfg.getReversedEdges());
		this.depList = depList;
		this.slice = slice;
		this.criterion = criterion;
		this.visited = visited;
		bNodesInBlocks = new HashMap<Integer, Set<Node>>();
	}
	
	public ControlFlowGraph getCFG() {
		return revCFG;
	}
	
	public void extractNodeNumbers(String var) {
		if(!depList.containsKey(var)) {
			return;
		}
		for(Object o : depList.get(var)) {
//			 if(o == null) {
//			 return;
//			 }
			if(o instanceof String) {
				extractNodeNumbers((String) o);
			} else {
				slice.add((int) o);
			}
		}
	}
	
	private void updateBackwardDepList(List<Node> queue, Node n) {
		if(visited[n.getIndex()]) {
			// System.out.println("meet on " + n.getIndex());
			switch(n.getType()) {
				case "assign":
					ArrayList<Integer> backBlocks = revCFG.getParents(n).get(0).getBlocksInside();
					ArrayList<Integer> forwardBlocks = n.getBlocksInside();
					// update every node in the blocks that n is in
					for(int i = 0; i < backBlocks.size(); i++) {
						int curBackBlock = backBlocks.get(i);
						int curForwardBlock = forwardBlocks.get(i);
						Node curForwardNode = revCFG.getNode(curForwardBlock);
						// add n to nodes vs blocks table
						if(bNodesInBlocks.containsKey(curBackBlock)) {
							bNodesInBlocks.get(curBackBlock).add(n);
						} else {
							Set<Node> curNode = new HashSet<Node>();
							curNode.add(n);
							bNodesInBlocks.put(curBackBlock, curNode);
						}
						for(Node curBackNode : bNodesInBlocks.get(curBackBlock)) {
							for(String d : curBackNode.getDef()) {
								if(depList.containsKey(d)) {
									depList.get(d).add(curForwardBlock);
									depList.get(d).addAll(curForwardNode.getRef());
								} else {
									Set<Object> refs = new HashSet<Object>();
									refs.add(n.getIndex());
									refs.addAll(n.getRef());
									depList.put(d, refs);
								}
							}
						}
					}
					break;
				case "if": // do what is normally done when "if" encountered in
							// backward propagation
					slice.add(n.getIndex());
					n.setBlocksInside(revCFG.getParents(n).get(0).getBlocksInside());
					int curBlock = n.removeLastBlock(); // last end statement
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
				default: // don't care about anything else
					break;
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
				int curBlock = n.removeLastBlock(); // last end statement
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
	
	public void generateBackwardSlice() {
		// slice = new HashSet<Integer>();
		// System.out.println(" - Backward - ");
		// System.out.println(revCFG);
		List<Node> queue = new ArrayList<Node>();
		Iterator<Node> keyIt = revCFG.getKeyIterator();
		while(keyIt.hasNext()) {
			Node n = keyIt.next();
			// go through the nodes
			//if node isn't starting node
			if(n.equals(new Node(false), false)) {
				queue.add(n); //add to queue from iterator that goes through cfg
			}
		}
		while(queue.size() > 0) {
			//after building up q, we update dep list
			updateBackwardDepList(queue, queue.remove(0)); //give it q, head of q
		}
		// System.out.println("back " + depList);
		extractNodeNumbers(criterion);
		// System.out.println(slice);
	}
	
	public void run() {
		generateBackwardSlice();
	}
}
