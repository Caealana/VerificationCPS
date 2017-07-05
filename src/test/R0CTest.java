package test;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import slicing.collectDep;

public class R0CTest {
	/**
	 * start		0
	 * read(n)		1
	 * a = 1		2
	 * b = 2		3
	 * if(n == 1)	4
	 * 	a = a + 1	5
	 * else			
	 * 	b = b + 2	6
	 * end			7
	 * c = b		8
	 * stop			9
	 */
	public static void main(String[] args) {
		HashMap<Node, List<Node>> edges = new HashMap<Node, List<Node>>();
		Node start = new Node(true);
		
		ArrayList<String> ref = new ArrayList<String>();
		ArrayList<String> def = new ArrayList<String>();
		def.add("n");
		Node n1 = new Node("assign", ref, def, 1); //assign as node type?
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a");
		Node n2 = new Node("assign", ref, def, 2);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b");
		Node n3 = new Node("assign", ref, def, 3);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("n");
		Node n4 = new Node("if", ref, def, 4);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a");
		Node n5 = new Node("assign", ref, def, 5);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b");
		Node n6 = new Node("assign", ref, def, 6);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		Node n7 = new Node("end", ref, def, 7);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b");
		def.add("c");
		Node n8 = new Node("assign", ref, def, 8);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		Node stop = new Node("stop", ref, def, 9);
		
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
		children.add(n6);
		edges.put(n4, children);
		
		children = new ArrayList<Node>();
		children.add(n7);
		edges.put(n5, children);
		
		children = new ArrayList<Node>();
		children.add(n7);
		edges.put(n5, children);
		
		children = new ArrayList<Node>();
		children.add(n7);
		edges.put(n6, children);
		
		children = new ArrayList<Node>();
		children.add(n8);
		edges.put(n7, children);
	
		
		children = new ArrayList<Node>();
		children.add(stop);
		edges.put(n8, children);
		
		boolean forward = false;
		boolean backward = false;
		final int REPS = 5000;
		
		ControlFlowGraph cfg = new ControlFlowGraph(edges);
		HashMap<String, Set<Object>> depList = new HashMap<String, Set<Object>>();
		Set<Integer> slice = new HashSet<Integer>();
		boolean[] visited = new boolean[1000];
		Set<String> criterionVars = new HashSet<String>();
		criterionVars.add("c");
		collectDep test = new collectDep(cfg,depList, slice,
				visited, n6, criterionVars);
		test.buildDep();
		System.out.println(test.getR0C());

	}

}
