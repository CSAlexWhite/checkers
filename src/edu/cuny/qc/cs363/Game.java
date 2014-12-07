package edu.cuny.qc.cs363;

import java.util.Vector;

public class Game {
	
	CheckerBoard currentBoard;
	Vector<CheckerBoard> history;
	
	public Game(){
		
		history = new Vector<CheckerBoard>();
		currentBoard = //new CheckerBoard("OOOOOOOOOOOO        XXXXXXXXXXXX");
				new CheckerBoard("K                   OO    K     ");
				//new CheckerBoard("    XXXX                OOOO    ");
		currentBoard.printBoard(0);
	}
	
	public void add(CheckerBoard nextMove){
		
		history.add(nextMove);
	}

	public void printHistory(){
		
		try{
			
			for (int i=0; i<history.size(); i++) history.get(i).printBoard(i);
		}
		
		catch (NullPointerException npe){ 
			
			System.out.println("\nGAME OVER");
		}
	}
	
}
