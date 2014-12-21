package edu.cuny.qc.cs363;

import java.util.Vector;

public class Game{
	
	CheckerBoard currentBoard;
	Vector<CheckerBoard> history;
	Player p1, p2;
	int turn; 
	long nodesSearched;
	double avgBranchingFactor;
	Thread thisThread;
	int winner;
	
	public Game(){
		
		winner = -1;
		turn = 0;
		nodesSearched = 0;
		//gameBoard = board;
		p1 = new Player(1);
		p2 = new Player(2);
		history = new Vector<CheckerBoard>();
		currentBoard = 	new CheckerBoard("OOOOOOOOOOOO        XXXXXXXXXXXX");
	}
	
	public void add(CheckerBoard nextMove){
		
		history.add(nextMove);
	}
	
	public void incrementNodesSearched(int increment){
		
		//System.out.println("INCREMENTED BY: " + increment);
		nodesSearched += increment;
	}

	public CheckerBoard nextBoard() throws IllegalArgumentException{
		
		turn++;
		if(turn>100) winner = 2;
		
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
