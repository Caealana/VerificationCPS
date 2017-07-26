package slicing;


import java.util.ArrayList;
import java.util.Arrays;
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
	//this version uses array list instead of set for easy removal
	
	SystemDependenceGraph G;
	ArrayList<Vertice> V;
	ArrayList<Vertice> VMark;
	ArrayList<Vertice> Answer;
	ArrayList<Vertice> marked;
	public VerticesOfSlice(){
		marked = new ArrayList<Vertice>();
	}
	
	public Set<Vertice> buildVerticesOfSlice(){
		//Phase1: Slice without decending into called procedures
		//V' = ReachingVertices(G, V, [def-order, parameter-out])
		
		//Phase2: Slice called procedures without ascending to call sites
		//Answer := ReachingVertices(G, V', {def-order, parameter-in, call}})
		return null;
	}
	
	public Set<Vertice> ReachingVertices(){
		//G an SDG
		//V, WorkList, Answer, sets of vertices of G
		//ExcludedEdgeKinds: a set of edge-kinds
		//v, w: vertices of G
		ArrayList<Vertice> WorkList; //difficult to remove from set in java, start with arraylist
		ArrayList<EdgeKind> ExcludedEdgesKinds;
		Vertice v;
		Vertice w;
		WorkList = V; //initial WorkList is V
		while(WorkList.isEmpty() == false){
			//select and remove a vertex v from WorkList
			Vertice current = WorkList.get(WorkList.size()-1); //get last item in WorkList
			
			//remove vertice from worklist at end
		}
		return null;
	}
}
