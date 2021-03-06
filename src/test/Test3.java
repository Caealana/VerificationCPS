package test;

import graphRepresentation.*;
import slicing.*;
import java.util.*;

/**
 * 	0	start
 * 	1	read(n)
 * 	2	a1 = 1;
 * 	3	a2 = 1;
 * 	4	a3 = 1;
 * 	5	a4 = 1;
 * 	6	a5 = 1;
 * 	7	a6 = 1;
 *	8	a7 = 1;
 *	9	a8 = 1;
 *	10	a9 = 1;
 * 	11	a10 = 1;
 * 	12	a11 = 1;
 * 	13	a12 = 1;
 * 	14	a13 = 1;
 * 	15	a14 = 1;
 *	16	a15 = 1;
 *	17	a16 = 1;
 *	18	b1 = 2;
 * 	19	b2 = 2;
 * 	20	b3 = 2;
 * 	21	b4 = 2;
 * 	22	b5 = 2;
 * 	23	b6 = 2;
 *	24	b7 = 2;
 *	25	b8 = 2;
 *	26	b9 = 2;
 * 	27	b10 = 2;
 * 	28	b11 = 2;
 * 	29	b12 = 2;
 * 	30	b13 = 2;
 * 	31	b14 = 2;
 *	32	b15 = 2;
 *	33	b16 = 2;
 *	34	sumA = 0
 *	35	sumB = 0
 *	36	if(n == 1)
 *	37		sumA += a1
 *	38		sumA += a2
 *	39		sumA += a3
 *	40		sumA += a4
 *	41		sumA += a5
 *	42		sumA += a6
 *	43		sumA += a7
 *	44		sumA += a8
 *	45		sumA += a9
 *	46		sumA += a10
 *	47		sumA += a11
 *	48		sumA += a12
 *	49		sumA += a13
 *	50		sumA += a14
 *	51		sumA += a15
 *	52		sumA += a16
 *	53	else
 *	54		sumB += b1
 *	55		sumB += b2
 *	56		sumB += b3
 *	57		sumB += b4
 *	58		sumB += b5
 *	59		sumB += b6
 *	60		sumB += b7
 *	61		sumB += b8
 *	62		sumB += b9
 *	63		sumB += b10
 *	64		sumB += b11
 *	65		sumB += b12
 *	66		sumB += b13
 *	67		sumB += b14
 *	68		sumB += b15
 *	69		sumB += b16
 *	70	end
 *	71	result = sumA
 *	72 	stop
 */	

public class Test3 {
	public static void main(String[] args) throws InterruptedException {
		boolean forward = false;
		boolean backward = false;
		HashMap<Node, List<Node>> edges = new HashMap<Node, List<Node>>();
		Node start = new Node(true);
		
		ArrayList<String> ref = new ArrayList<String>();
		ArrayList<String> def = new ArrayList<String>();
		def.add("n");
		Node n1 = new Node("assign", ref, def, 1);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a1");
		Node n2 = new Node("assign", ref, def, 2);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a2");
		Node n3 = new Node("assign", ref, def, 3);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a3");
		Node n4 = new Node("assign", ref, def, 4);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a4");
		Node n5 = new Node("assign", ref, def, 5);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a5");
		Node n6 = new Node("assign", ref, def, 6);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a6");
		Node n7 = new Node("assign", ref, def, 7);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a7");
		Node n8 = new Node("assign", ref, def, 8);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a8");
		Node n9 = new Node("assign", ref, def, 9);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a9");
		Node n10 = new Node("assign", ref, def, 10);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a10");
		Node n11 = new Node("assign", ref, def, 11);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a11");
		Node n12 = new Node("assign", ref, def, 12);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a12");
		Node n13 = new Node("assign", ref, def, 13);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a13");
		Node n14 = new Node("assign", ref, def, 14);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a14");
		Node n15 = new Node("assign", ref, def, 15);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a15");
		Node n16 = new Node("assign", ref, def, 16);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("a16");
		Node n17 = new Node("assign", ref, def, 17);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b1");
		Node n18 = new Node("assign", ref, def, 18);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b2");
		Node n19 = new Node("assign", ref, def, 19);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b3");
		Node n20 = new Node("assign", ref, def, 20);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b4");
		Node n21 = new Node("assign", ref, def, 21);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b5");
		Node n22 = new Node("assign", ref, def, 22);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b6");
		Node n23 = new Node("assign", ref, def, 23);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b7");
		Node n24 = new Node("assign", ref, def, 24);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b8");
		Node n25 = new Node("assign", ref, def, 25);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b9");
		Node n26 = new Node("assign", ref, def, 26);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b10");
		Node n27 = new Node("assign", ref, def, 27);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b11");
		Node n28 = new Node("assign", ref, def, 28);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b12");
		Node n29 = new Node("assign", ref, def, 29);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b13");
		Node n30 = new Node("assign", ref, def, 30);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b14");
		Node n31 = new Node("assign", ref, def, 31);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b15");
		Node n32 = new Node("assign", ref, def, 32);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("b16");
		Node n33 = new Node("assign", ref, def, 33);

		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("sumA");
		Node n34 = new Node("assign", ref, def, 34);

		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("sumB");
		Node n35 = new Node("assign", ref, def, 35);

		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("n");
		Node n36 = new Node("if", ref, def, 36);

		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a1");
		def.add("sumA");
		Node n37 = new Node("assign", ref, def, 37);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a2");
		def.add("sumA");
		Node n38 = new Node("assign", ref, def, 38);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a3");
		def.add("sumA");
		Node n39 = new Node("assign", ref, def, 39);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a4");
		def.add("sumA");
		Node n40 = new Node("assign", ref, def, 40);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a5");
		def.add("sumA");
		Node n41 = new Node("assign", ref, def, 41);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a6");
		def.add("sumA");
		Node n42 = new Node("assign", ref, def, 42);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a7");
		def.add("sumA");
		Node n43 = new Node("assign", ref, def, 43);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a8");
		def.add("sumA");
		Node n44 = new Node("assign", ref, def, 44);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a9");
		def.add("sumA");
		Node n45 = new Node("assign", ref, def, 45);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a10");
		def.add("sumA");
		Node n46 = new Node("assign", ref, def, 46);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a11");
		def.add("sumA");
		Node n47 = new Node("assign", ref, def, 47);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a12");
		def.add("sumA");
		Node n48 = new Node("assign", ref, def, 48);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a13");
		def.add("sumA");
		Node n49 = new Node("assign", ref, def, 49);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a14");
		def.add("sumA");
		Node n50 = new Node("assign", ref, def, 50);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a15");
		def.add("sumA");
		Node n51 = new Node("assign", ref, def, 51);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("a16");
		def.add("sumA");
		Node n52 = new Node("assign", ref, def, 52);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		Node n53 = new Node("else", ref, def, 53);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b1");
		def.add("sumB");
		Node n54 = new Node("assign", ref, def, 54);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b2");
		def.add("sumB");
		Node n55 = new Node("assign", ref, def, 55);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b3");
		def.add("sumB");
		Node n56 = new Node("assign", ref, def, 56);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b4");
		def.add("sumB");
		Node n57 = new Node("assign", ref, def, 57);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b5");
		def.add("sumB");
		Node n58 = new Node("assign", ref, def, 58);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b6");
		def.add("sumB");
		Node n59 = new Node("assign", ref, def, 59);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b7");
		def.add("sumB");
		Node n60 = new Node("assign", ref, def, 60);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b8");
		def.add("sumB");
		Node n61 = new Node("assign", ref, def, 61);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b9");
		def.add("sumB");
		Node n62 = new Node("assign", ref, def, 62);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b10");
		def.add("sumB");
		Node n63 = new Node("assign", ref, def, 63);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b11");
		def.add("sumB");
		Node n64 = new Node("assign", ref, def, 64);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b12");
		def.add("sumB");
		Node n65 = new Node("assign", ref, def, 65);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b13");
		def.add("sumB");
		Node n66 = new Node("assign", ref, def, 66);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b14");
		def.add("sumB");
		Node n67 = new Node("assign", ref, def, 67);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b15");
		def.add("sumB");
		Node n68 = new Node("assign", ref, def, 68);

		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("b16");
		def.add("sumB");
		Node n69 = new Node("assign", ref, def, 69);
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		Node n70 = new Node("end", ref, def, 70);

		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		ref.add("sumA");
		def.add("result");
		Node n71 = new Node("assign", ref, def, 71);

		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		Node stop = new Node("stop", ref, def, 72);

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
		
		if(forward) {
			long startTime = System.currentTimeMillis();
			for(int i = 0; i < 100; i++) {
				ControlFlowGraph cfg = new ControlFlowGraph(edges);
				HashMap<String, Set<Object>> depList = new HashMap<String, Set<Object>>();
				Set<Integer> slice = new HashSet<Integer>();
				boolean[] visited = new boolean[1000];
				ForwardSlicer test = new ForwardSlicer(cfg, depList, slice, visited, "result");
				test.generateForwardSlice();
				System.out.println(slice);
			}
			System.out.println(System.currentTimeMillis() - startTime);
		} else if(backward) {
			long startTime = System.currentTimeMillis();
			for(int i = 0; i < 100; i++) {
				ControlFlowGraph cfg = new ControlFlowGraph(edges);
				HashMap<String, Set<Object>> depList = new HashMap<String, Set<Object>>();
				Set<Integer> slice = new HashSet<Integer>();
				boolean[] visited = new boolean[1000];
				BackwardSlicer test = new BackwardSlicer(cfg, depList, slice, visited, "result");
				test.generateBackwardSlice();
				System.out.println(slice);
			}
			System.out.println(System.currentTimeMillis() - startTime);
		} else {
			long startTime = System.currentTimeMillis();
			for(int i = 0; i < 100; i++) {
				ControlFlowGraph cfg = new ControlFlowGraph(edges);
				HashMap<String, Set<Object>> depList = new HashMap<String, Set<Object>>();
				Set<Integer> slice = new HashSet<Integer>();
				boolean[] visited = new boolean[1000];
				ForwardSlicer fSlicer = new ForwardSlicer(cfg, depList, slice, visited, "result");
				BackwardSlicer bSlicer = new BackwardSlicer(cfg, depList, slice, visited, "result");
				Thread fThread = new Thread(fSlicer);
				Thread bThread = new Thread(bSlicer);
				fThread.start();
				bThread.start();
				fThread.join();
				bThread.join();
				System.out.println(slice);
			}
			System.out.println(System.currentTimeMillis() - startTime);
		}		
	}
}
