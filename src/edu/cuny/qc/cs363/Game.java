package edu.cuny.qc.cs363;

import java.util.Vector;

public class Game{
	
	CheckerBoard currentBoard;
	Vector<CheckerBoard> history;
	Player p1, p2;
	int turn, winner;
	
	/*
	 * Starts a new checkers game with the usual configuration.
	 */
	public Game(){
		
		winner = -1;
		turn = 0;
		p1 = new Player(1);
		p2 = new Player(2);
		history = new Vector<CheckerBoard>();
		currentBoard = 	new CheckerBoard("OOOOOOOOOOOO        XXXXXXXXXXXX");
	}
	
	/*
	 * This class also tracks the history of the game.
	 */
	public void add(CheckerBoard nextMove){
		
		history.add(nextMove);
	}
	
	/*
	 * When asked to move, it asks for which player, then asks the player
	 * for its best choice given the current board.
	 */
	public CheckerBoard nextBoard() throws IllegalArgumentException{
		
		turn++;
		
		if(turn%2 == 0){ 
			
			currentBoard = p1.move(currentBoard);
			add(currentBoard);
			if(currentBoard.gameOver()){ 
				
				winner = 0;
				System.out.println("BLACK WINS");
			}
			return currentBoard;
		}
		
		else {
			
			currentBoard = p2.badmove(currentBoard);
			add(currentBoard);
			if(currentBoard.gameOver()){ 
				
				winner = 1;
				System.out.println("RED WINS");
			}
			return currentBoard;
		}
	}
	
	/*
	 * At the end of a game, we like to print the history to review it.
	 */
	public void printHistory(){
		
		try{
			
			for (int i=0; i<history.size(); i++){ 
				
				history.get(i).printBoard();
				history.get(i).printTopography();
			}
		}
		
		catch (NullPointerException npe){ 
			
			System.out.println("\nGAME OVER");
		}
	}	
}
