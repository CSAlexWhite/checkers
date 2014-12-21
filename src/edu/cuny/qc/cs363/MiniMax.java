package edu.cuny.qc.cs363;

import java.util.Vector;

public class MiniMax{

	static int nodesSearched;
	static long startTime = System.currentTimeMillis();

	public static int alphabeta(CheckerBoard node, int depth, int alpha, int beta, 
			boolean maximizingPlayer, int player, int maxtime){
		
		if(System.currentTimeMillis() - startTime > maxtime) return node.evaluate(); 
		if(depth == 0 || node.gameOver()) return node.evaluate();
				
		if(maximizingPlayer){

			Vector<CheckerBoard> children = node.getChildren(player);
			
			for(int i=0; i<children.size(); i++){
				
				nodesSearched++;
				int value = alphabeta(children.get(i), depth -1, alpha, beta, false, Math.abs(player - 1), maxtime);
				alpha = Math.max(alpha, value);
				if(beta <= alpha) break;
			}
			
			return alpha;
		}
		
		else{
			
			Vector<CheckerBoard> children = node.getChildren(Math.abs(player - 1));
			
			for(int i=0; i<children.size(); i++){
				
				nodesSearched++;
				int value = alphabeta(children.get(i), depth - 1, alpha, beta, true, player, maxtime);
				beta = Math.min(beta, value);
				if(beta <= alpha) break;
			}
			
			return beta;
		}
	}
}
