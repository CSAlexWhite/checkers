package edu.cuny.qc.cs363;

import java.util.Stack;
import java.util.Vector;

public class MiniMax {

	static Player player;
	static CheckerBoard startingBoard;
	static Stack<Vector<CheckerBoard>> options;
	static int depth, decision, playerNumber;
	
	public MiniMax(CheckerBoard current, Player p, int d){
		
		startingBoard = current;
		depth = d;
		player = p;
		decision = minimax_decision(current);
		playerNumber = player.playerNumber-1;
	}
	
	public static int minimax_decision(CheckerBoard input){
		
		Vector<CheckerBoard> currentChoices;
		CheckerBoard temp = input;
		
		int iteration = 0;
		int value = max_value(input, iteration);
		
		int totalScore = 0;
		int bestChoice = 0;	
		
		for(int i=0; i<=depth; i++){
			
			int choice = 0;
			currentChoices = temp.getChildren((playerNumber+i)%2); 	// get children according to 
																	// which player it is
			
			if((playerNumber+i%2)==playerNumber){	// for me, make the best choice
				
				int bestValue = -1000;
				for(int j=0; j<currentChoices.size(); i++){
					
					if(currentChoices.get(i).myValue > bestValue) choice = j;
				}			
			}
			
			if((playerNumber+i%2)!=playerNumber){	// for you, make the worst choice
				
				int worstValue = 10000;
				for(int j=0; j<currentChoices.size(); i++){
					
					if(currentChoices.get(i).myValue < worstValue) choice = j;
				}			
			}
			
			temp = currentChoices.get(choice);
		}
		
		return bestChoice;
	}
	
	public static int max_value(CheckerBoard input, int iteration){
		
		int value =-100;
		if (input.board.isEmpty() || iteration >=7) // terminal _test ???
			return input.evaluate(); //not sure the Uttility(state)
		else {
			
			input.getChildren(0);
			//------------------???---------------------------------
			// got throught each possible black piece move
			
				value=Math.max(value, min_value(input, iteration++));				
		}
		
		return value;
		
	}
	public static int min_value(CheckerBoard input, int iteration){
		int value = -100;
		// cannot be winning board 
		if (input.possibleMoves==0)// no move move for red piece
			return input.evaluate();
		else {
			input.getChildren(0);
			for (int i=0; i<32 ;i++){ // got throught each possible black piece move
				
				value=Math.max(value, min_value(input, iteration++));		
		     }
		}
		return value;
	}

	private int Utility(CheckerBoard input) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
