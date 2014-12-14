package edu.cuny.qc.cs363;

import java.util.Vector;

public class MiniMax implements Runnable{

	int max_depth, alpha, beta, player;
	boolean maximizingPlayer;
	CheckerBoard startingBoard;
	Thread thisThread;
	static int nodesSearched;
	Game currentGame;
	
	int value;
	
	public MiniMax(Game game, CheckerBoard node, int depth, int alpha, int beta, boolean maximizingPlayer, int player){
		
		nodesSearched = 0;
		startingBoard = node;
		max_depth = depth;
				
		//if(node.turn > 20) max_depth = depth+1; 
		
		currentGame = game;
		
		this.alpha = alpha;
		this.beta = beta;
		this.maximizingPlayer = maximizingPlayer;
		this.player = player;
		
		thisThread = new Thread(this);
		thisThread.start();
	}
	
	public static int alphabeta(CheckerBoard node, int depth, int alpha, int beta, boolean maximizingPlayer, int player){
		
		if(depth == 0 || node.gameOver()){
			
			node.evaluate();
			//System.out.println(node.boardValue);
			return node.boardValue;
		}
		
		if(maximizingPlayer){

			Vector<CheckerBoard> children = node.getChildren(player);
			
			if(children.size() == 0){ alpha +=1000; return alpha;}
			
			for(int i=0; i<children.size(); i++){
				
				nodesSearched++;
				int value = alphabeta(children.get(i), depth -1, alpha, beta, false, Math.abs(player - 1));
				alpha = Math.max(alpha, value);
				if(beta <= alpha) break;
			}
			
			return alpha;
		}
		
		else{
			
			Vector<CheckerBoard> children = node.getChildren(Math.abs(player - 1));
			
			if(children.size() == 0){ beta+=1000; return beta;}
			
			for(int i=0; i<children.size(); i++){
				
				nodesSearched++;
				int value = alphabeta(children.get(i), depth - 1, alpha, beta, true, player);
				beta = Math.min(beta, value);
				if(beta <= alpha) break;
			}
			
			return beta;
		}
	}
	
	static int bestValue = 0;
	public static int minimax(CheckerBoard node, int depth, boolean maximizingPlayer, int player){
					
		if(depth == 0 || node.gameOver())
			return node.boardValue;
		
		if(maximizingPlayer){

			Vector<CheckerBoard> children = node.getChildren(player);
			
			bestValue = Integer.MIN_VALUE;
			for(int i=0; i<children.size(); i++){
				
				nodesSearched++;
				int value = minimax(children.get(i), depth -1, false, Math.abs(player - 1));
				bestValue = Math.max(bestValue, value);
			}
			
			return bestValue;
		}
		
		else{
			
			Vector<CheckerBoard> children = node.getChildren(Math.abs(player - 1));
			
			bestValue = Integer.MAX_VALUE;
			for(int i=0; i<children.size(); i++){
				
				nodesSearched++;
				int value = minimax(children.get(i), depth - 1, true, player);
				bestValue = Math.min(bestValue, value);
			}
			
			return bestValue;
		}
	}

	@Override
	public void run() {
			
		thisThread.setPriority(1);
		value = alphabeta(startingBoard,max_depth,alpha,beta,maximizingPlayer,player);
		//value = minimax(startingBoard,max_depth,maximizingPlayer,player);
		currentGame.incrementNodesSearched(nodesSearched);
		//System.out.println(value);
		//System.out.println(nodesSearched);
	}
}
