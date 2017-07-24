package slicing;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.Stack;

import SDG.EdgeKind;
import SDG.SystemDependenceGraph;
import SDG.Vertice;

public class VerticesOfSlice {
	//from Binkley
	//returns a set of vertices
	//G an SDG
	//V, V', Answer: sets of vertices of G
	
	SystemDependenceGraph G;
	Set<Vertice> V;
	Set<Vertice> VMark;
	Set<Vertice> Answer;
	
	ArrayList<Vertice> marked;
	public VerticesOfSlice(){
		marked = new ArrayList<Vertice>();
	}
	
	public Set<Vertice> ReachingVertices(){
		//G an SDG
		//V, WorkList, Answer, sets of vertices of G
		//ExcludedEdgeKinds: a set of edge-kinds
		//v, w: vertices of G
		Set<Vertice> WorkList;
		Set<Vertice> Answer;
		Set<EdgeKind> ExcludedEdgesKinds;
		Vertice v;
		Vertice w;
		
		WorkList = V;
		Answer = Collections.emptySet();
		Stack<Vertice> WorkListStack = new Stack<Vertice>();
		for(){
			
		}
		while(WorkList.isEmpty() == false){
			//select and remove a vertex v from WorkList
			
		}
		return null;
	}
}
