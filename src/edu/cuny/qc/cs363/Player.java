package edu.cuny.qc.cs363;

import java.util.Random;
import java.util.Vector;

public class Player {

	boolean black;
	int playerNumber;
	CheckerBoard lastMove;
	Vector<CheckerBoard> choices;
	static Random rand;
	
	public Player(int assignedNumber){
			
		playerNumber = assignedNumber;
		if(assignedNumber == 0) black = true;
		else black = false;
	}
	
	public void go(){
		
		
	}
	
//	public CheckerBoard move1(CheckerBoard inBoard){
//		
//		return MiniMax.minimax_decision(inBoard);
//	}
	
	public CheckerBoard move(CheckerBoard inBoard){

		int bestScore = 0, bestChoice = 0;
		CheckerBoard bestBoard = null;
		
		try{
					
			choices = inBoard.getChildren(playerNumber);
			
			for(int i=0; i<choices.size(); i++){
				
				if(choices.get(i).myValue > bestScore){;
				
					bestScore = choices.get(i).myValue;
					bestChoice = i;	
				}
				
				if(choices.get(i).myCaptures > 0){
					
					bestChoice = i;
					break;
				}
			}
			
			if(choices.size() == 0) return null;
			
			if(bestChoice == 0) bestChoice = getRandom(0, choices.size()-1);
				
			bestBoard = choices.get(bestChoice);									
		}
		
		catch(ArrayIndexOutOfBoundsException aioobe){
			
			return null;
		}
		
		catch(NullPointerException npe){
			
			return null;
		}
		
		lastMove = bestBoard;
		return bestBoard;
	}
	
	public static int getRandom(int min, int max){
		
		rand = new Random();
		int randomNum = rand.nextInt((max-min) + 1) + min;
		//System.out.println(randomNum);
		return randomNum;
	}
}

