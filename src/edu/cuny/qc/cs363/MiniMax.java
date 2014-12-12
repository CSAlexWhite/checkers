package edu.cuny.qc.cs363;

import java.util.Vector;

public class MiniMax implements Runnable{

	int max_depth, alpha, beta, player;
	boolean maximizingPlayer;
	CheckerBoard startingBoard;
	Thread thisThread;
	
	int value;
	
	public MiniMax(CheckerBoard node, int depth, int alpha, int beta, boolean maximizingPlayer, int player){
		
		startingBoard = node;
		max_depth = depth;
		this.alpha = alpha;
		this.beta = beta;
		this.maximizingPlayer = maximizingPlayer;
		this.player = player;
		
		thisThread = new Thread(this);
		thisThread.start();
	}
	
	public static int minimax(CheckerBoard node, int depth, int alpha, int beta, boolean maximizingPlayer, int player){
		
		if(depth == 0 || node.gameOver())
			return node.boardValue;
		
		if(maximizingPlayer){

			Vector<CheckerBoard> children = node.getChildren(player);
			
			if(children.size() == 0){ alpha +=1000; return alpha;}
			
			for(int i=0; i<children.size(); i++){
				
				int value = minimax(children.get(i), depth -1, alpha, beta, false, Math.abs(player - 1));
				alpha = Math.max(alpha, value);
				if(beta <= alpha) break;
			}
			
			return alpha;
		}
		
		else{
			
			Vector<CheckerBoard> children = node.getChildren(Math.abs(player - 1));
			
			if(children.size() == 0){ beta+=1000; return beta;}
			
			for(int i=0; i<children.size(); i++){
				
				int value = minimax(children.get(i), depth - 1, alpha, beta, true, player);
				beta = Math.min(beta, value);
				if(beta <= alpha) break;
			}
			
			return beta;
		}
	}

	@Override
	public void run() {
		
		value = minimax(startingBoard,max_depth,alpha,beta,maximizingPlayer,player);
	}
}
