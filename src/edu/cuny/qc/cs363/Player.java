package edu.cuny.qc.cs363;

import java.util.Random;
import java.util.Vector;

public class Player {

	int playerNumber;
	CheckerBoard lastMove;
	Vector<CheckerBoard> choices;
	
	public Player(int assignedNumber){
		
		playerNumber = assignedNumber;
	}
	
	public void go(){
		
		
	}
	
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
			}
				
			bestBoard = choices.get(bestChoice);
			
			if(bestChoice == 0) 
				bestChoice = getRandom(0, choices.size());						
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
		
		Random rand = new Random();
		int randomNum = rand.nextInt((max-min) + 1) + min;
		return randomNum;
	}
}

