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
	private Set<Node> Sk1C;
	
	public collectDep(ControlFlowGraph cfg, HashMap<String, Set<Object>> depList, Set<Integer> slice,
			boolean[] visited, Node criterionNode, Set<String> criterionVars, int nodeCount) throws Exception {
		revCFG = new ControlFlowGraph(cfg.getReversedEdges());
		this.CFG = cfg;
		this.depList = depList;
		this.slice = slice;
		this.visited = visited;
		bNodesInBlocks = new HashMap<Integer, Set<Node>>();
		this.R0C = new HashMap<Node, Set<String>>();
		this.S0C = new HashSet<Node>();
		this.BkC = new HashSet<Node>();
		this.nodeCount = nodeCount;
		this.Rk1C = new HashMap<Node, Set<String>>();
		this.Sk1C = new HashSet<Node>();
		if(criterionVars != null & criterionNode != null ){
			this.criterionNode = criterionNode;
			this.criterionVars = criterionVars;
		}
		else{
			throw new Exception("Null value in slicing criterion.");
		}
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
	
	public Set<Node> getSk1C(){
		return Sk1C;
	}
	
	//
	public void buildR0C(Node endN, HashMap<Node, List<Node>> edges, Set<String> vars, boolean kPlus1){
		//if slicing criterion is null, just throw an error
		
		Stack<Node> dfs = new Stack(); //for dfs
		ArrayList<Node> visited = new ArrayList<Node>();
		//System.out.println(edges.keySet());
		//System.out.println(visited.length);
		//initial visited set to false for all nodes
		System.out.println("End node in dfs: " + endN);
		dfs.push(endN);
		
		Node after = null;
		Node current;
		boolean start = false;
		//System.out.println(queue.size());
		while(dfs.empty() == false) {
			System.out.println("In R0C Loop: " + R0C);
			//after building up q, we update dep list
			current = dfs.pop();
			System.out.println("Current Node in R0C dfs: " + current);
			//not sure if my image of the graph is right...
			//System.out.println("current");
			//System.out.println(current);
			
			//dfs push new nodes onto stack
			if(visited.contains(current) == false){
				visited.add(current);
				List<Node> currentEdges = edges.get(current);

				//System.out.println("current edges");
				//System.out.println(currentEdges);
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
					
					//R0C case 1
					if(vars.isEmpty() == false){
						if(kPlus1 == false){ //checks if we are adding to Rk1C based on branch nodes
							R0C.put(current, vars); //R0C(i) = V when i = n
						}
						else{
							Rk1C.put(current, vars);
						}
					}
					else{
						//case of empty criterion - no vars
						if(kPlus1 == false){
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
							if(kPlus1 == false){
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
					if(kPlus1 == false){
						R0C.put(current, v);
					}
					else{
						Rk1C.put(current, v);
					}

					
					
					after = current;
				}
			}
		}
		Rk1C.putAll(R0C); // R k+1 C contains everything in S0C and R0C
		//Sk1C.addAll(S0C);
		System.out.println("R0C: " + R0C);
	}
	
	public void buildS0C(Node start, Node endN, HashMap<Node, List<Node>> edges, boolean kPlus1){
		//S0C
		//intersection def(current) and R0C(after) not empty, then add current to S0C
		//not just direct edges...and node that is linked and comes after?
		//go forward through graph instead of backward
		//if we find the criterion Node, stop moving forward
		Stack<Node> dfs = new Stack<Node>();
		dfs.push(start);
		ArrayList<Node> visited = new ArrayList<Node>();
		Node current; //current node we are on in dfs
			while(dfs.isEmpty() == false){
				current = dfs.pop();
				if(visited.contains(current) == false){
					visited.add(current);
				/*if(kPlus1 == false){
					S0C.add(current);
				}
				else{
					System.out.println("adding criterion node to sk1c");
					Sk1C.add(current);
				}*/
				
				if(kPlus1 == false){
					Set<String> S0CTest = new HashSet<String>(current.getDef());//def(current)
					/*System.out.println("current node's def");
					System.out.println(S0CTest);
					System.out.println("R0C after");
					System.out.println(R0C.get(after));*/
					//do we do dfs starting from current node - to get all successors?
					Stack<Node> S0CDfs = new Stack<Node>();
					ArrayList<Node> visitedS0C = new ArrayList<Node>();
					visitedS0C.add(current);
					//push all the edges of current node to dfs
					List<Node> edgesS0C = edges.get(current);
					S0CDfs.addAll(edgesS0C);
					Node successor; //node j (i->j)
					while(S0CDfs.isEmpty() == false){
						successor = S0CDfs.pop();
						if(visitedS0C.contains(successor) == false){
							visitedS0C.add(successor);
							System.out.println("Successor Node: " + successor);
							System.out.println("R0C: " + R0C);
							//R0C is not built yet at this point
							Set<String> R0CSuccessor = R0C.get(successor);
							System.out.println("R0CSuccessor: " + R0CSuccessor);
							System.out.println("S0CTest: " + S0CTest);
							
							if(R0CSuccessor != null & S0CTest.isEmpty() == false){
								(R0CSuccessor).retainAll(S0CTest);
								if(R0CSuccessor.isEmpty() == false){
									//not just comparing
									S0C.add(current);
								}
							}
							//push edges to dfs - as long as we are not pushing the edges of the end Node
							if(successor != endN){
								List<Node> successorEdges = edges.get(successor);
								S0CDfs.addAll(successorEdges);
							}
						}
					}
				}
					else{
						/*Set<String> S0CTest = new HashSet<String>(current.getDef());//def(current)
						S0CTest.retainAll(Rk1C.get(after));
						System.out.print("S0C Test");
						System.out.println(S0CTest);
						if(S0CTest.isEmpty() == false){
							Sk1C.add(current);
							System.out.println("Current Node ");
							System.out.print(current);
						}*/						
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
	public void buildkPlus1(HashMap<Node, List<Node>> edges){
		//call buildR0CS0C on branch node as end node, but set new criterion variables as REF(B)
		//need to save 
		Object[] BkCArr = BkC.toArray();
		for(int i = 0; i <BkCArr.length; i++){
			Node current = (Node)BkCArr[i];
			Set<String> currentDef = new HashSet<String>(current.getDef()); 
			buildR0C(current, edges, currentDef, true );
			//buildS0C(current, current, edges, false);
		}

		Sk1C.addAll(BkC); //Sk+1C contains everything in BkC
	}
	
	/*This set
	consists of the the nodes in Bk
	C together with the nodes i that define a variable that is Rk1
	C -relevant
	to a CFG-successor j*/
	//Sk1C is same as S0C except based on Rk1C instead of R0C
	
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
		HashMap<Node, List<Node>> revEdges = revCFG.getEdges();
		HashMap<Node, List<Node>> edges = CFG.getEdges();
		Iterator<Node> nodes = revCFG.getKeyIterator(); //nodes
		Node end = null;
		Node start = null;
		Node current = null;
		while(nodes.hasNext()){
			current = nodes.next();
			System.out.println("Current getType: " + current.getType());
			if(current.getType() == "stop"){ //ending node...go backwards from here
				end = current;
			}
			else if(current.getType() == "start"){
				start = current;
			}
		}
		
		//build R0C
		if(end == null){
			System.out.println("end node is null");
		}
		buildR0C(end, revEdges, criterionVars, false);
		
		//build S0C
		buildS0C(start, end, edges, false);
		
		//build BkC
		//System.out.println("revCFG get branchNodes");
		buildBkC(CFG.getBranchNodes());
		
		//build build Rk+1C and Sk+1C
		buildkPlus1(edges);
	}
}
