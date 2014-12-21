package edu.cuny.qc.cs363;

import java.util.Vector;

import javafx.concurrent.Task;

public class Game extends Task<Game>{
	
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
		p1 = new Player(this, 1);
		p2 = new Player(this, 0);
		history = new Vector<CheckerBoard>();
		currentBoard = 	//new CheckerBoard("OOOOOOOOOOOO        XXXXXXXXXXXX");
						//new CheckerBoard("OOOOOOOO KK OOOOOOOO KK OOOOO KK");
						new CheckerBoard("XX O   X   O  Q K  X   O   QO O ");
						//new CheckerBoard("    XXXX                OOOO    ");
		//currentBoard.printBoard(0);
		
		thisThread = new Thread(this);
		
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
				System.out.println("RED WINS");
			}
			return currentBoard;
		}
		
		else {
			
			currentBoard = p2.move2(currentBoard);
			add(currentBoard);
			if(currentBoard.gameOver()){ 
				
				winner = 1;
				System.out.println("BLACK WINS");
			}
			return currentBoard;
		}
	}
	
	public void printHistory(){
		
		try{
			
			for (int i=0; i<history.size(); i++) history.get(i).printBoard(i);
		}
		
		catch (NullPointerException npe){ 
			
			System.out.println("\nGAME OVER");
		}
	}

	@Override
	public void run() {
		
	}

	@Override
	protected Game call() throws Exception {
			
		CheckerBoard tempBoard = p1.move(currentBoard);
		
		//gameBoard.setup(tempBoard);	
		add(tempBoard);
		
		return this;
	
	}
	
}
