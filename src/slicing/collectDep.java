package slicing;

import graphRepresentation.BranchNode;
import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;


public class collectDep { //collect dependencies
	private ControlFlowGraph CFG;
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
	private Set<Node> BkC;
	private int nodeCount;
	private HashMap<Node, Set<String>> Rk1C;
	
	public collectDep(ControlFlowGraph cfg, HashMap<String, Set<Object>> depList, Set<Integer> slice,
			boolean[] visited, Node criterionNode, Set<String> criterionVars, int nodeCount) {
		revCFG = new ControlFlowGraph(cfg.getReversedEdges());
		this.CFG = cfg;
		this.depList = depList;
		this.slice = slice;
		this.criterionNode = criterionNode;
		this.criterionVars = criterionVars;
		this.visited = visited;
		bNodesInBlocks = new HashMap<Integer, Set<Node>>();
		this.R0C = new HashMap<Node, Set<String>>();
		this.S0C = new HashSet<Node>();
		this.BkC = new HashSet<Node>();
		this.nodeCount = nodeCount;
		this.Rk1C = new HashMap<Node, Set<String>>();
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
	
	public Set<Node> getBkC(){
		return BkC;
	}
	
	public HashMap<Node, Set<String>> getRk1C(){
		return Rk1C;
	}
	
	public void buildR0CS0C(Node endN, HashMap<Node, List<Node>> edges, Set<String> vars, boolean buildRk1C){
		
		Stack<Node> dfs = new Stack(); //for dfs
		boolean[] visited = new boolean[nodeCount]; //visited for each node
		//System.out.println(edges.keySet());
		System.out.println(visited.length);
		//initial visited set to false for all nodes
		dfs.push(endN);
		
		Node after = null;
		Node current;
		boolean start = false;
		//System.out.println(queue.size());
		while(dfs.empty() == false) {
			//after building up q, we update dep list
			current = dfs.pop();
			//not sure if my image of the graph is right...
			//System.out.println(current);
			
			//dfs push new nodes onto stack
			if(visited[current.getIndex()] == false){
				visited[current.getIndex()] = true;
				List<Node> currentEdges = edges.get(current);
				if(currentEdges != null){
					//push edges to stack
					Iterator<Node> edgeIt = currentEdges.iterator();
					while(edgeIt.hasNext()){
						dfs.push(edgeIt.next());
					}
				}
				//dep list check points
				if(criterionNode.equals(current)){
					//the criterion node is the ENDING node
					//we want to start saving dependences from this point on
					System.out.println("found criterion node");
					
					//do we add slicing criterion ending node to S0C?
					//my initial answer is yes
					if(buildRk1C == false){
						S0C.add(current);
					}
					//R0C case 1
					if(vars.isEmpty() == false){
						if(buildRk1C == false){ //checks if we are adding to Rk1C based on branch nodes
							R0C.put(current, vars); //R0C(i) = V when i = n
						}
						else{
							Rk1C.put(current, vars);
						}
					}
					else{
						//case of empty criterion - no vars
						if(buildRk1C == false){
							R0C.put(current, Collections.emptySet());
						}
						else{
							Rk1C.put(current, Collections.emptySet());
						}
						//System.out.println("empty set");
					}
					start = true;
					after = current;
				}
				
				else if (start == true){
					//System.out.println(current);
					//System.out.println(after);
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
							if(buildRk1C == false){
								R0C.put(current, refn);
							}
							else{
								Rk1C.put(current, refn);
							}
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
					if(buildRk1C == false){
						R0C.put(current, v);
					}
					else{
						Rk1C.put(current, v);
					}
					//S0C
					//intersection def(current) and R0C(after) not empty, then add current to S0C
					if(buildRk1C == false){
						Set<String> S0CTest = new HashSet<String>(current.getDef());//def(current)
						S0CTest.retainAll(R0C.get(after));
						System.out.print("S0C Test");
						System.out.println(S0CTest);
						if(S0CTest.isEmpty() == false){
							S0C.add(current);
							System.out.println("Current Node ");
							System.out.print(current);
						}
					}
					
					after = current;
				}
			}
		}
	}
	
	//Bkc such that...node is in SkC, node is INFL(b)
	//INFL(B) of a branch statement b ...set of statements control dependent on b...the statements that are part of the if/while/etc.
	public void buildBkC(Set<BranchNode> branchNodes){
		//System.out.println(branchNodes);
		Object[] bnArray = branchNodes.toArray();
		System.out.println("bnArray Length");
		System.out.println(bnArray.length);
		for(int i = 0; i < bnArray.length; i++){
			BranchNode currentBn = (BranchNode) bnArray[i];
			Set<Node> INFLSet = currentBn.getINFL();
			Object[] INFLarr =  INFLSet.toArray();
			System.out.println("INFLarray");
			System.out.print(INFLarr);
			for(int j = 0; j < INFLarr.length; j++){
				Node inflNode = (Node) INFLarr[j];
				if(S0C.contains(inflNode)){
					BkC.add(inflNode);
				}
			}
		}
	}
	
	//rk1C contains vars that have a transitive data dependence on nodes in BkC
	//do R0C, S0C on new criteria (branch, REF(b))
	//each branch node i suppose
	public void buildRk1C(HashMap<Node, List<Node>> edges){
		//call buildR0CS0C on branch node as end node, but set new criterion variables as REF(B)
		//need to save 
		Object[] BkCArr = BkC.toArray();
		for(int i = 0; i <BkCArr.length; i++){
			Node current = (Node)BkCArr[i];
			Set<String> currentDef = new HashSet<String>(current.getDef()); 
			buildR0CS0C(current, edges, currentDef, true );
		}
		//buildR0CS0C();
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
		
		//dfs through statements
		HashMap<Node, List<Node>> edges = revCFG.getEdges();
		Iterator<Node> nodes = edges.keySet().iterator(); //nodes
		Node current = null;
		while(nodes.hasNext()){
			current = nodes.next();
			if(current.getType() == "stop"){ //ending node...go backwards from here
				break; //we found the node to start at
			}
		}
		
		//build R0C
		if(current == null){
			System.out.println("current node is null");
		}
		buildR0CS0C(current, edges, criterionVars, false);
		
		//build BkC
		//System.out.println("revCFG get branchNodes");
		buildBkC(CFG.getBranchNodes());
		
		//build Rk+1C
		buildRk1C(edges);
	}
}
