package edu.cuny.qc.cs363;

import java.util.Random;
import java.util.Vector;

public class Player {

	boolean black;
	int playerNumber;
	Vector<CheckerBoard> choices;
	static Random rand;
	
	public Player(int assignedNumber){

		playerNumber = assignedNumber-1;
	}

	public CheckerBoard move(CheckerBoard inBoard) throws IllegalArgumentException{
		
		int bestChoice = 0;
		int bestValue = Integer.MIN_VALUE;
		
		Vector<CheckerBoard> choices = inBoard.getChildren(playerNumber);
		
		if(choices.size()==0) return null;
		
		Vector<CheckerBoard> capturingMoves = new Vector<CheckerBoard>();
		Vector<CheckerBoard> regularMoves = new Vector<CheckerBoard>();
		boolean captured = false;
		for(int i=0; i<choices.size(); i++){
 
			if(choices.get(i).capture){ 
				
				capturingMoves.add(choices.get(i));
				captured = true;
			}
			
			if(captured) continue;
			

			regularMoves.add(choices.get(i));						
		}	
       
		if(capturingMoves.size() == 0){

			int value = 0;
			int subtime = 3000 / regularMoves.size();
			
			for(int i=0; i<regularMoves.size(); i++){
				
				regularMoves.get(i).setMiniMax(value = MiniMax.alphabeta(choices.get(i), 5, 
						Integer.MIN_VALUE, Integer.MAX_VALUE,  true, playerNumber, subtime));
				
				if(value > bestValue){
					
					bestValue = value;
					bestChoice = i;
				}
			}	
		}
		
		if(capturingMoves.size() == 1) return capturingMoves.get(0);
		
		if(capturingMoves.size() > 1) {
						
			bestValue = 0;
			bestChoice = 0;

			for(int i=0; i<capturingMoves.size(); i++){
				
				if(capturingMoves.get(i).miniMax > bestValue){;
				
					bestValue = capturingMoves.get(i).miniMax;
					bestChoice = i;	
				}
			}       
				return capturingMoves.get(bestChoice);
		}	   	return choices.get(bestChoice);
	}
	
	public CheckerBoard badmove(CheckerBoard inBoard) throws IllegalArgumentException{
	
		Vector<CheckerBoard> choices = inBoard.getChildren(playerNumber);
		
		return choices.get(getRandom(0, choices.size()));
	}
			
	public static int getRandom(int min, int max){
		
	
		try{
			
			rand = new Random();
			int randomNum = rand.nextInt(max-min);
			return randomNum;
		}
		
		catch (IllegalArgumentException iae) {return 0;}
	}
}

