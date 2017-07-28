/*simple sum and product calculations for slicing example
 * 
(1) read(n);
(2) i := 1;
(3) sum := 0;
(4) product := 1;
(5) sum := sum + 1;
(6) product := product * 1
(7)	i := i + 1
(8) write(sum);
(9) write(product);
(10) stop;
 */
package evaluation;

import graphRepresentation.BranchNode;
import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFGSequential {
	ControlFlowGraph cfg;
	
	public CFGSequential(){
		this.cfg = buildCFG();
	}
	
	public ControlFlowGraph getCFG(){
		return this.cfg;
	}
	
	public ControlFlowGraph buildCFG(){
		int nodeCount = 0;
		HashMap<Node, List<Node>> edges = new HashMap<Node, List<Node>>();
		Node start = new Node(true); //node 0
		nodeCount++;
		
		ArrayList<String> ref = new ArrayList<String>();
		ArrayList<String> def = new ArrayList<String>();
		def.add("n");
		Node n1 = new Node("assign", ref, def, 1); //assign as node type?
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
		
		//sum = sum + 1
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("sum");
		def.add("sum");
		Set<Node> INFL = new HashSet<Node>();
		Node n5 = new Node("assign", ref, def, 5);
		nodeCount++;
		
		//product = product * 1
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("product");
		def.add("product");
		Node n6 = new Node("assign", ref, def, 6);
		nodeCount++;
		
		//i = i + 1
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("i");
		def.add("i");
		Node n7 = new Node("assign", ref, def, 7);
		nodeCount++;
		
		//write(sum)
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("sum");
		def.add("sum");
		Node n8 = new Node("assign", ref, def, 8);
		nodeCount++;
		
		//write(product)
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("product");
		def.add("product");
		Node n9 = new Node("assign", ref, def, 9);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		Node n10 = new Node("stop", ref, def, 10);
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
		edges.put(n5, children);
		
		children = new ArrayList<Node>();
		children.add(n7);
		edges.put(n6, children);
		
		children = new ArrayList<Node>();
		children.add(n8);
		edges.put(n7, children);
		
		children = new ArrayList<Node>();
		children.add(n9);
		edges.put(n8, children);
		
		children = new ArrayList<Node>();
		children.add(n10);
		edges.put(n9, children);
		
		//get dep lists
		ControlFlowGraph cfg = new ControlFlowGraph(edges);
		cfg.setStartNode(start);
		cfg.setEndNode(n10);
		HashMap<String, Set<Object>> depList = new HashMap<String, Set<Object>>();
		Set<Integer> slice = new HashSet<Integer>();
		boolean[] visited = new boolean[1000];
		ArrayList<String> criterionVars = new ArrayList<String>();
		criterionVars.add("product");
		return cfg;
	}
}
