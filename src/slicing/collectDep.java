package slicing;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class collectDep { //collect dependencies
	private ControlFlowGraph revCFG;
	private HashMap<String, Set<Object>> depList; //in test 1, they give an empty hashmap dep list
	private Set<Integer> slice;
	private boolean[] visited;
	private Node criterionNode;
	private Set<String> criterionVars = new HashSet<String>();
	private HashMap<Integer, Set<Node>> bNodesInBlocks;
	private HashMap<Node, Set<String>> R0C;
	private Set<Node> S0C; //set of all nodes i that define
	//a variable v that is a relevant at a CFG-successor of i.
	
	public collectDep(ControlFlowGraph cfg, HashMap<String, Set<Object>> depList, Set<Integer> slice,
			boolean[] visited, Node criterionNode, Set<String> criterionVars) {
		revCFG = new ControlFlowGraph(cfg.getReversedEdges());
		this.depList = depList;
		this.slice = slice;
		this.criterionNode = criterionNode;
		this.criterionVars = criterionVars;
		this.visited = visited;
		bNodesInBlocks = new HashMap<Integer, Set<Node>>();
		this.R0C = new HashMap<Node, Set<String>>();
		this.S0C = new HashSet<Node>();

	}
	
	public ControlFlowGraph getCFG() {
		return revCFG;
	}
	
	public HashMap<Node, Set<String>> getR0C(){
		return R0C;
	}
	
	public Set<String> getR0CSet(Node node){
		return R0C.get(node);
	}
	
	public Set<Node> getS0C(){
		return S0C;
	}
	
	public void buildR0CS0C(List<Node> queue){
		//does it actually go through every edge?
		
		Node after = null;
		Node current;
		boolean start = false;
		System.out.println(queue.size());
		while(queue.size() > 0) {
			//after building up q, we update dep list
			current = queue.remove(0);
			//not sure if my image of the graph is right...
			
			if(criterionNode.equals(current)){
				//the criterion node is the ENDING node
				//we want to start saving dependences from this point on
				System.out.println("found criterion node");
				
				//do we add slicing criterion ending node to S0C?
				//my initial answer is yes
				S0C.add(current);
				
				//R0C case 1
				if(criterionVars.isEmpty() == false){
					R0C.put(current, criterionVars); //R0C(i) = V when i = n
				}
				else{
					//case of empty criterion - no vars
					R0C.put(current, Collections.emptySet());
					System.out.println("empty set");
				}
				start = true;
				after = current;
			}
			
			else if (start == true){
				System.out.println(current);
				System.out.println(after);
				//current node R0c based on R0c of node after it
				//case 2a
				//n comes before m
				//v is in REF(n), a w in both DEF(n) and R0C(m)
				//how do we handle if we are using an empty set from R0C?
				
				//w both in DEF(n) and R0C(m)
				ArrayList<String> defn = current.getDef();
				for(int i = 0; i <defn.size(); i ++){
					//go through the vars in DEF(n)
					String varW= defn.get(i);
					Set<String> R0Cm = R0C.get(after);
					//see if the var you found is contained withinR0C(m)
					if(R0Cm.contains(varW)){
						//if it is...add all refn to v
						Set<String> refn = new HashSet<String>(current.getRef());
						R0C.put(current, refn);
					}
				}
				
				//case 2b
				//n before m
				//v NOT IN Def(n), v is in R0C(m)
				ArrayList<String> DEFn = current.getDef(); //DEF(n)
				Set<String> R0Cm = R0C.get(after); //R0C(m)
				Object[] R0CmArr = R0Cm.toArray();
				Set<String> v = new HashSet<String>(); //vars to add to dep list
				//go through vars in R0C(m)
				for(int k = 0; k < R0CmArr.length; k++){
					String var = (String) R0CmArr[k]; //v is in R0C(m)
					if(DEFn.contains(var) == false){
						//v is not in DEF(n)
						v.add(var);
					}
				}
				R0C.put(current, v);
				
				//S0C
				//intersection def(current) and R0C(after) not empty, then add current to S0C
				Set<String> S0CTest = new HashSet<String>(current.getDef());
				S0CTest.retainAll(R0C.get(after));
				if(S0CTest.isEmpty() == false){
					S0C.add(current);
				}
				
				
				after = current;
			}
			
		}
	}
	
	public Set<Node> buildS0C(HashMap<Node, Set<String>> R0C){
		return null;
	}
	
	public void buildDep(){
		//based off of weiser's static slicing alg
		//source: http://www.cse.buffalo.edu/LRG/CSE705/Papers/Weiser-Static-Slicing.pdf
		
		//build R0C
		List<Node> queue = new ArrayList<Node>();
		Iterator<Node> keyIt = revCFG.getKeyIterator();
		while(keyIt.hasNext()) {
			//don't think this is really going through all edges? may have to alter this
			Node n = keyIt.next();
			// go through the nodes
			//if node isn't starting node
			/*if(n.equals(new Node(false), false)) {
				queue.add(n); //add to queue from iterator that goes through cfg
			}*/
			queue.add(n);
		}
		
		//build R0C
		buildR0CS0C(queue);
		
		//build S0C
		
	}
}
