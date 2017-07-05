package test;

import graphRepresentation.*;
import slicing.*;

import java.util.*;

/**
 * 0	start
 * 1	read(n)
 * 2	LL1 = some nonempty LL
 * 3	LL2 = some nonempty LL
 * 4	sum1 = 0
 * 5	sum2 = 0
 * 6	for(m : LL1)	% treat as m is defined and LL1 is referenced
 * 7		sum1 += m
 * 8	end
 * 9	for(k : LL2)	% treat as k is defined and LL2 is referenced
 * 10		sum2 += k
 * 11	end
 * 12	result = sum1
 * 13	stop
 */

public class Test2 {
	public static void main(String[] args) throws InterruptedException {
		HashMap<Node, List<Node>> edges = new HashMap<Node, List<Node>>();
		Node start = new Node(true); //default values for start node
		
		ArrayList<String> ref = new ArrayList<String>();
		ArrayList<String> def = new ArrayList<String>();
		def.add("n");
		Node n1 = new Node("assign", ref, def, 1); //String type, ArrayList<String> r, ArrayList<String> d, int index
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("LL1");
		Node n2 = new Node("assign", ref, def, 2);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("LL2");
		Node n3 = new Node("assign", ref, def, 3);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("sum1");
		Node n4 = new Node("assign", ref, def, 4);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("sum2");
		Node n5 = new Node("assign", ref, def, 5);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("LL1");
		def.add("m");
		Node n6 = new Node("assign", ref, def, 6);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("m");
		def.add("sum1");
		Node n7 = new Node("assign", ref, def, 7);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		Node n8 = new Node("endfor", ref, def, 8);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("LL2");
		def.add("k");
		Node n9 = new Node("assign", ref, def, 9);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("k");
		def.add("sum2");
		Node n10 = new Node("assign", ref, def, 10);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		Node n11 = new Node("endfor", ref, def, 11);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("sum1");
		def.add("result");
		Node n12 = new Node("assign", ref, def, 12);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		Node stop = new Node("stop", ref, def, 13);
		
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
		
		children = new ArrayList<Node>();
		children.add(n11);
		edges.put(n10, children);
		
		children = new ArrayList<Node>();
		children.add(n12);
		edges.put(n11, children);
		
		children = new ArrayList<Node>();
		children.add(stop);
		edges.put(n12, children);
		
		boolean forward = false;
		boolean backward = true;
		final int REPS = 5000;
		
		if(forward) {
			long totalTime = 0;
			for(int i = 0; i < REPS; i++) {
				ControlFlowGraph cfg = new ControlFlowGraph(edges);
				HashMap<String, Set<Object>> depList = new HashMap<String, Set<Object>>();
				Set<Integer> slice = new HashSet<Integer>();
				boolean[] visited = new boolean[1000];
				ForwardSlicer test = new ForwardSlicer(cfg, depList, slice, visited, "result");
				long startTime = System.currentTimeMillis();
				test.generateForwardSlice();
				System.out.println(slice);
				totalTime += System.currentTimeMillis() - startTime;
			}
			System.out.println(totalTime);
		} else if(backward) {
			long totalTime = 0;
			for(int i = 0; i < REPS; i++) {
				ControlFlowGraph cfg = new ControlFlowGraph(edges);
				HashMap<String, Set<Object>> depList = new HashMap<String, Set<Object>>(); //dep list
				Set<Integer> slice = new HashSet<Integer>();
				boolean[] visited = new boolean[1000];
				BackwardSlicer test = new BackwardSlicer(cfg, depList, slice, visited, "result");
				//ControlFlowGraph cfg, HashMap<String, Set<Object>> depList, Set<Integer> slice,
				//boolean[] visited, String criterion
				long startTime = System.currentTimeMillis();
				test.generateBackwardSlice();
				System.out.println(slice);
				totalTime += System.currentTimeMillis() - startTime;
			}
			System.out.println(totalTime);
		} else {
			long totalTime = 0;
			for(int i = 0; i < REPS; i++) {
				ControlFlowGraph cfg = new ControlFlowGraph(edges);
				HashMap<String, Set<Object>> depList = new HashMap<String, Set<Object>>();
				Set<Integer> slice = new HashSet<Integer>();
				boolean[] visited = new boolean[1000];
				ForwardSlicer fSlicer = new ForwardSlicer(cfg, depList, slice, visited, "result");
				BackwardSlicer bSlicer = new BackwardSlicer(cfg, depList, slice, visited, "result");
				Thread fThread = new Thread(fSlicer);
				Thread bThread = new Thread(bSlicer);
				long startTime = System.currentTimeMillis();
				fThread.start();
				bThread.start();
				fThread.join();
				bThread.join();
				totalTime += System.currentTimeMillis() - startTime;
				System.out.println(slice);
			}
			System.out.println(totalTime);
		}		
	}
}
