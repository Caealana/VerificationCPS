//simple example code with if statement to test slicing
/*(1) read(n);
(2) i := 1;
(3) sum := 0;
(4) product := 1;
(5) if i <= n do
(6) 	sum := sum + 1;
(7) 	product := product * 1
(8)		i := i + 1
	else:
(9)		sum := sum + 2
(10)	product := product * 2
(11)	i := i * 2
(12) write(sum);
(13)write(product);*/
//we are acting as if/else is 1 branch statement together
package evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graphRepresentation.BranchNode;
import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

public class CFGIf {
	ControlFlowGraph cfg;
	
	public CFGIf(){
		this.cfg = buildCFG();
	}
	
	public ControlFlowGraph getCFG(){
		return this.cfg;
	}
	
	ControlFlowGraph buildCFG(){
		int nodeCount = 0;
		HashMap<Node, List<Node>> edges = new HashMap<Node, List<Node>>();
		Node start = new Node(true); //node 0
		nodeCount++;
		
		ArrayList<String> ref = new ArrayList<String>();
		ArrayList<String> def = new ArrayList<String>();
		def.add("n");
		Node n1 = new Node("assign", ref, def, 1);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("i");
		Node n2 = new Node("assign", ref, def, 2);
		nodeCount++;

		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("sum");
		Node n3 = new Node("assign", ref, def, 3);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("product");
		Node n4 = new Node("assign", ref, def, 4);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("i");
		ref.add("n");
		Set<Node> INFL = new HashSet<Node>();
		BranchNode n5 = new BranchNode("assign", ref, def, 5, INFL);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("sum");
		def.add("sum");
		Node n6 = new Node("assign", ref, def, 6);
		n5.addINFL(n6); //n6 part of loop add it
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("product");
		def.add("product");
		Node n7 = new Node("assign", ref, def, 7);
		n5.addINFL(n7);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("i");
		def.add("i");
		Node n8 = new Node("assign", ref, def, 8);
		n5.addINFL(n8);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("sum");
		def.add("sum");
		Node n9 = new Node("assign", ref, def, 9);
		n5.addINFL(n9);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("product");
		def.add("product");
		Node n10 = new Node("assign", ref, def, 10);
		n5.addINFL(n10);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("i");
		def.add("i");
		Node n11 = new Node("assign", ref, def, 11);
		n5.addINFL(n11);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("sum");
		def.add("sum");
		Node n12 = new Node("assign", ref, def, 12);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("product");
		def.add("product");
		Node n13 = new Node("assign", ref, def, 13);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		Node stop = new Node("stop", ref, def, 14);
		nodeCount++;
		
		List<Node> children = new ArrayList<Node>();
		children.add(n1);
		edges.put(start, children);
		
		children = new ArrayList<Node>();
		children.add(n2);
		edges.put(n1, children);
		
		children = new ArrayList<Node>();
		children.add(n3);
		edges.put(n2, children);
		
		children = new ArrayList<Node>();
		children.add(n4);
		edges.put(n3, children);
		
		children = new ArrayList<Node>();
		children.add(n5);
		edges.put(n4, children);
		
		children = new ArrayList<Node>();
		children.add(n6);
		children.add(n9);
		edges.put(n5, children);
		
		children = new ArrayList<Node>();
		children.add(n7);
		edges.put(n6, children);
		
		children = new ArrayList<Node>();
		children.add(n8);
		edges.put(n7, children);
		
		children = new ArrayList<Node>();
		children.add(n12);
		edges.put(n8, children);
		
		children = new ArrayList<Node>();
		children.add(n10);
		edges.put(n9, children);
		
		children = new ArrayList<Node>();
		children.add(n11);
		edges.put(n10, children);
		
		children = new ArrayList<Node>();
		children.add(n12);
		edges.put(n11, children);
		
		children = new ArrayList<Node>();
		children.add(n13);
		edges.put(n12, children);
		
		//get dep lists
		ControlFlowGraph cfg = new ControlFlowGraph(edges);
		cfg.setStartNode(start);
		cfg.addBranchNode(n5);
		HashMap<String, Set<Object>> depList = new HashMap<String, Set<Object>>();
		Set<Integer> slice = new HashSet<Integer>();
		boolean[] visited = new boolean[1000];
		ArrayList<String> criterionVars = new ArrayList<String>();
		criterionVars.add("product");
		
		return cfg;
	}
	
}
