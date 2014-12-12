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

		Vector<CheckerBoard> capturingMoves = new Vector<CheckerBoard>();
		int bestScore = 0, bestChoice = 0, bestJump = 0;
		CheckerBoard bestBoard = null;
		
		try{
					
			choices = inBoard.getChildren(playerNumber);
			
			for(int i=0; i<choices.size(); i++){
				
				if(choices.get(i).myValue > bestScore){;
				
					bestScore = choices.get(i).myValue;
					bestChoice = i;	
				}
				
				if(choices.get(i).capture){
					
					System.out.println("CAPTURE EXISTS AT MOVE: " + i);
					System.out.println("MOVE: " + i + " IS:");
					choices.get(i).printBoard(0);
					
					capturingMoves.add(choices.get(i));
				}
			}
			
			bestBoard = choices.get(bestChoice);
			
			if(choices.size() == 0) return null;
					
			if(capturingMoves.size() == 0){
							
				if(bestChoice == 0) bestChoice = getRandom(0, choices.size()-1);
				else bestBoard = choices.get(bestChoice);
			}
			
			if(capturingMoves.size() > 0) {
				
				bestScore = 0;

				for(int i=0; i<capturingMoves.size(); i++){
					
					if(capturingMoves.get(i).myValue > bestScore){;
					
						bestScore = capturingMoves.get(i).myValue;
						bestJump = i;	
					}
				}
				
				bestBoard = capturingMoves.get(bestJump);
			}											
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

