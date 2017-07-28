package evaluation;

import java.util.ArrayList;
import java.util.Set;

import graphRepresentation.ControlFlowGraph;
import graphRepresentation.Node;
import slicing.FirstPass;
import slicing.SecondPass;

public class TimeEvaluation {

	public static void main(String[] args) {
		//cfg and dfs structures
		CFGIf CFGIf = new CFGIf();
		CFGSequential CFGSequential = new CFGSequential();
		CFGLoops CFGLoops = new CFGLoops();
		CFGIf.buildCFG();
		CFGSequential.buildCFG();
		CFGLoops.buildCFG();
		DFS dfsLoops = new DFS(CFGLoops.getCFG());
		DFS dfsSequential = new DFS(CFGSequential.getCFG());
		DFS dfsIf = new DFS(CFGIf.getCFG());
		
		long startTime;
		long endTime;
		long totalTime;
		
		//time to run DFS through sequential program 
		startTime = System.nanoTime();
		dfsSequential.runDFS();
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.println("Sequential Program DFS run time in nanoseconds: " + totalTime);
		
		//time to run DFS through If program 
		startTime = System.nanoTime();
		dfsIf.runDFS();
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.println("Program with If statement DFS run time in nanoseconds: " + totalTime);
		
		//time to run DFS through Loops program 
		startTime = System.nanoTime();
		dfsLoops.runDFS();
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.println("Program with Loops DFS run time in nanoseconds: " + totalTime);
		
		//criteironVars
		ControlFlowGraph cfgSeq = CFGSequential.getCFG();
		ArrayList<String> criterionVars = new ArrayList<String>();
		criterionVars.add("sum");
		
		//CFGSequential		
		//first pass
		Node endNode = CFGSequential.getCFG().getEndNode();
		System.out.println("endNode Sequential: " + endNode);
		ControlFlowGraph sequentialCFG = CFGSequential.getCFG();
		FirstPass fpTest = new FirstPass(endNode, criterionVars, sequentialCFG);
		fpTest.dfsFirstPass();
		System.out.println("R0C Set in firstpasstest: " + fpTest.getR0C().getR0CSet());
		System.out.println("S0C Set in firstpasstest: " + fpTest.getS0C().getS0CSet());
		Set<Node> slice = fpTest.getS0C().getS0CSet();
		//Second Pass
		SecondPass spTest = new SecondPass(endNode, criterionVars, sequentialCFG, fpTest.getS0C(), fpTest.getR0C());
		spTest.dfsSecondPass();
		System.out.println("BkC Set in secondpasstest: " + spTest.getBkC().getBkCSet());
		System.out.println("Rk1C set in secondpasstest: " + spTest.getRk1C().getRk1CSet());
		System.out.println("Sk1C set in secondpasstest: " + spTest.getSk1C().getSk1CSet());
		slice.addAll(spTest.getBkC().getBkCSet());
		slice.addAll(spTest.getSk1C().getSk1CSet());
		//run dfs down only the slice nodes, print out totaltime taken
		startTime = System.nanoTime();
		dfsSequential.runSlicedDFS(slice);
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.println("Time DFS takes to run on sliced sequential code cfg: " + totalTime);
		
		//CFGIf		
		//first pass
		endNode = CFGIf.getCFG().getEndNode();
		ControlFlowGraph IfCFG = CFGIf.getCFG();
		//System.out.println("CFG if branch nodes: " + IfCFG.getBranchNodes());
		fpTest = new FirstPass(endNode, criterionVars, IfCFG);
		fpTest.dfsFirstPass();
		System.out.println("R0C Set in firstpasstest: " + fpTest.getR0C().getR0CSet());
		System.out.println("S0C Set in firstpasstest: " + fpTest.getS0C().getS0CSet());
		Set<Node> sliceIf = fpTest.getS0C().getS0CSet();
		//Second Pass
		spTest = new SecondPass(endNode, criterionVars, IfCFG, fpTest.getS0C(), fpTest.getR0C());
		spTest.dfsSecondPass();
		System.out.println("BkC Set in secondpasstest: " + spTest.getBkC().getBkCSet());
		System.out.println("Rk1C set in secondpasstest: " + spTest.getRk1C().getRk1CSet());
		System.out.println("Sk1C set in secondpasstest: " + spTest.getSk1C().getSk1CSet());
		sliceIf.addAll(spTest.getBkC().getBkCSet());
		sliceIf.addAll(spTest.getSk1C().getSk1CSet());
		//run dfs down only the slice nodes, print out totaltime taken
		startTime = System.nanoTime();
		dfsSequential.runSlicedDFS(slice);
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.println("Time DFS takes to run on sliced code with if statement " + totalTime);
		
		//CFGLoops	
		//first pass
		endNode = CFGLoops.getCFG().getEndNode();
		ControlFlowGraph LoopsCFG = CFGLoops.getCFG();
		//System.out.println("CFG if branch nodes: " + IfCFG.getBranchNodes());
		fpTest = new FirstPass(endNode, criterionVars, LoopsCFG);
		fpTest.dfsFirstPass();
		System.out.println("R0C Set in firstpasstest: " + fpTest.getR0C().getR0CSet());
		System.out.println("S0C Set in firstpasstest: " + fpTest.getS0C().getS0CSet());
		slice = fpTest.getS0C().getS0CSet();
		//Second Pass
		spTest = new SecondPass(endNode, criterionVars, LoopsCFG, fpTest.getS0C(), fpTest.getR0C());
		spTest.dfsSecondPass();
		System.out.println("BkC Set in secondpasstest: " + spTest.getBkC().getBkCSet());
		System.out.println("Rk1C set in secondpasstest: " + spTest.getRk1C().getRk1CSet());
		System.out.println("Sk1C set in secondpasstest: " + spTest.getSk1C().getSk1CSet());
		slice.addAll(spTest.getBkC().getBkCSet());
		slice.addAll(spTest.getSk1C().getSk1CSet());
		//run dfs down only the slice nodes, print out totaltime taken
		startTime = System.nanoTime();
		dfsSequential.runSlicedDFS(slice);
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.println("Time DFS takes to run on sliced code with loops: " + totalTime);
	}

}
