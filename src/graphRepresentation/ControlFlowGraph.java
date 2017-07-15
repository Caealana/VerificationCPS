package graphRepresentation;

import java.util.*;

public class ControlFlowGraph {
	private HashMap<Node, List<Node>> edges;
	private int nodeCount;
	private Set<BranchNode> branchNodes;
	private Node start;
	
	public ControlFlowGraph() {
		edges = new HashMap<Node, List<Node>>();
		nodeCount = 0;
		branchNodes = new HashSet<BranchNode>();

	}
	
	public ControlFlowGraph(HashMap<Node, List<Node>> edges) {
		this.edges = edges;
		branchNodes = new HashSet<BranchNode>();
	}
	
	public void setStart(Node n){
		this.start = n;
	}
	
	public Node getStart(){
		return start;
	}
	
	public HashMap<Node, List<Node>> getEdges() {
		return edges;
	}
	
	public Set<Node> getKeys() {
		return edges.keySet();
	}
	
	public Iterator<Node> getKeyIterator() {
		return edges.keySet().iterator();
	}
	
	public Node getNode(int index) {
		for(Node n : edges.keySet()) {
			if(n.getIndex() == index) {
				return n;
			}
		}
		return null;
	}
	
	public void setBranchNodes(Set<BranchNode> bn){
		branchNodes = bn;
	}
	
	public void addBranchNode(BranchNode bn){
		//System.out.println(branchNodes);
		branchNodes.add(bn);
		//System.out.println("Adding branch node");
		//System.out.println(branchNodes);
	}
	
	public Set<BranchNode> getBranchNodes(){
		//System.out.println("getBranchNodes");
		//System.out.println(branchNodes);
		return branchNodes;
	}
	
	public int countNodes() {
		return nodeCount;
	}
	
	public List<Node> getParents(Node n) {
		List<Node> parents = new ArrayList<Node>();
		for(Node parent : edges.keySet()) {
			for(Node child : edges.get(parent)) {
				if(child.equals(n, true)) {
					parents.add(parent);
				}
			}
		}
		return parents;
	}
	
	public List<Node> getChildren(Node n) {
		return edges.get(n) == null ? new ArrayList<Node>() : edges.get(n);
	}
	
	public List<Node> getChildren(int index) {
		for(Node n : edges.keySet()) {
			if(n.getIndex() == index) {
				return edges.get(n);
			}
		}
		return null;
	}
	
	public HashMap<Node, List<Node>> getReversedEdges() {
		HashMap<Node, List<Node>> revGraph = new HashMap<Node, List<Node>>();
		Iterator<Node> keyIt = edges.keySet().iterator();
		while(keyIt.hasNext()) {
			Node parent = keyIt.next();
			for(Node child : edges.get(parent)) {
				if(revGraph.containsKey(child)) {
					revGraph.get(child).add(parent);
				} else {
					List<Node> newChildren = new ArrayList<Node>();
					newChildren.add(parent);
					revGraph.put(child, newChildren);
				}
			}
		}
		return revGraph;
	}
	
	private void updateBFPrintNodes(List<Node> queue, Node n, Set<Node> bfNodes) {
		if(getChildren(n) != null) {
			queue.addAll(getChildren(n));
		}
		bfNodes.add(n);
	}
	
	public void bfPrintNodes(boolean forward) {	// demonstrates forward/backward traversal of graph
		List<Node> queue = new ArrayList<Node>();
		Set<Node> bfNodes = new LinkedHashSet<Node>();
		if(!forward) {
			getReversedEdges();
		}
		Iterator<Node> keyIt = edges.keySet().iterator();
		while(keyIt.hasNext()) {
			Node n = keyIt.next();
			if(n.equals(new Node(forward), false)) {
				queue.add(n);
			}
		}
		while(queue.size() > 0) {
			
			updateBFPrintNodes(queue, queue.remove(0), bfNodes);
		}
		if(!forward) {
			getReversedEdges();
		}
		System.out.println(bfNodes);
	}
	
	public String toString() {
		String s = "";
		for(Node parent : edges.keySet()) {
			s += parent.toString() + " -> ";
			for(Node child : edges.get(parent)) {
				s += child.toString() + " | ";
			}
			s += "\n";
		}
		return s;
	}
}
