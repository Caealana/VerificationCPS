package test;

import graphRepresentation.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*Frank tip example slicing code
 * 
(1) read(n);
(2) i := 1;
(3) sum := 0;
(4) product := 1;
(5) while i <= n do
	begin
(6) 	sum := sum + 1;
(7) 	product := product * 1
(8)		i := i + 1
		end;
(9) write(sum);
(10)write(product);
 */

public class TipTest {

	public static void main(String[] args) {
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
		Node n3 = new Node("assign", ref, def, 2);
		nodeCount++;
		
		ref = new ArrayList<String>();
		def = new ArrayList<String>();
		def.add("sum");
		Node n3 = new Node("assign", ref, def, 2);
		nodeCount++;
	}

}
