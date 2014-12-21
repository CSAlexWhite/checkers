package edu.cuny.qc.cs363;

import java.util.Random;
import java.util.Vector;

public class Player {

	boolean black;
	int playerNumber;
	CheckerBoard lastMove;
	Vector<CheckerBoard> choices;
	static Random rand;
	Game currentGame;
	
	public Player(Game game, int assignedNumber){
			
		playerNumber = assignedNumber;
		if(assignedNumber == 0) black = true;
		else black = false;
		currentGame = game;
	}
	
	public void go(){
		
		
	}
	
	public synchronized CheckerBoard move(CheckerBoard inBoard) throws IllegalArgumentException{
		
		int bestChoice = 0;
		int bestValue = Integer.MIN_VALUE;
		Vector<CheckerBoard> choices = inBoard.getChildren(playerNumber);
		Vector<CheckerBoard> capturingMoves = new Vector<CheckerBoard>();
		for(int i=0; i<choices.size(); i++){

			MiniMax decision = new MiniMax(currentGame, choices.get(i), 6, Integer.MIN_VALUE, Integer.MAX_VALUE,  true, playerNumber);
			int value = decision.value;
			
			if(value > bestValue){
				
				bestValue = value;
				bestChoice = i;
			}
						
			if(choices.get(i).capture){
				
				capturingMoves.add(choices.get(i));
			}			
		}
		
		//System.out.println("Best Value = " + bestValue);
		
//		if(capturingMoves.size() == 0 && bestValue < 2){
//			
//			bestChoice = getRandom(0, choices.size()-1);
//		}
		
		if(capturingMoves.size() > 0) {
			
			bestValue = 0;

			for(int i=0; i<capturingMoves.size(); i++){
				
				if(capturingMoves.get(i).myValue > bestValue){;
				
					bestValue = capturingMoves.get(i).myValue;
					bestChoice = i;	
				}
			}
			
			//System.out.println("Best Value = " + bestValue);
			return capturingMoves.get(bestChoice);
		}	

		//System.out.println("Best Value = " + bestValue);
		return choices.get(bestChoice);
	}
	
	public CheckerBoard move1(CheckerBoard inBoard) throws IllegalArgumentException{
		
		int bestChoice = 0;
		int bestValue = Integer.MIN_VALUE;
		Vector<CheckerBoard> choices = inBoard.getChildren(playerNumber);
		Vector<CheckerBoard> capturingMoves = new Vector<CheckerBoard>();
		
		for(int i=0; i<choices.size(); i++){
			
			MiniMax decision = new MiniMax(currentGame, inBoard, 8, Integer.MIN_VALUE, Integer.MAX_VALUE,  true, playerNumber);
			int value = decision.value;
			
			if(value > bestValue){
				
				bestValue = value;
				bestChoice = i;
			}
			
			if(choices.get(i).capture){
				
				capturingMoves.add(choices.get(i));
			}			
		}
		
		if(capturingMoves.size() == 0 && bestValue < 2){
			
			bestChoice = getRandom(0, choices.size()-1);
		}
		
		if(capturingMoves.size() > 0) {
			
			bestValue = 0;

			for(int i=0; i<capturingMoves.size(); i++){
				
				if(capturingMoves.get(i).myValue > bestValue){;
				
					bestValue = capturingMoves.get(i).myValue;
					bestChoice = i;	
				}
			}
			
			return capturingMoves.get(bestChoice);
		}	

		return choices.get(bestChoice);
	}
	
	public CheckerBoard move2(CheckerBoard inBoard){

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
					
					capturingMoves.add(choices.get(i));
				}
				
				if(choices.get(i).gameOver()) return choices.get(i);
			}
			
			bestBoard = choices.get(bestChoice);
			
			if(choices.size() == 0) return null;
					
			if(capturingMoves.size() == 0){
							
				if(bestChoice == 0){
					
					bestChoice = getRandom(0, choices.size()-1);
					bestBoard = choices.get(bestChoice);
				}
					
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
		return randomNum;
	}
}

