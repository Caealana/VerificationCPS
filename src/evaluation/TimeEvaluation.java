package evaluation;

public class TimeEvaluation {

	public static void main(String[] args) {
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
	}

}
