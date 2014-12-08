package edu.cuny.qc.cs363;

import java.util.Vector;

import javafx.concurrent.Task;

public class Game extends Task<Game>{
	
	CheckerBoard currentBoard;
	Vector<CheckerBoard> history;
	Player p1, p2;
	GUIController gameBoard;
	Thread thisThread;
	
	public Game(){
		
		//gameBoard = board;
		p1 = new Player(0);
		p2 = new Player(1);
//		p1 = player1; p2 = player2;
		history = new Vector<CheckerBoard>();
		currentBoard = 	new CheckerBoard("OOOOOOOOOOOO        XXXXXXXXXXXX");
						//new CheckerBoard("OOOOOOOO KK OOOOOOOO KK OOOOO KK");
				//new CheckerBoard("K                   OO    K     ");
				//new CheckerBoard("    XXXX                OOOO    ");
		currentBoard.printBoard(0);
		
		thisThread = new Thread(this);
		
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

	@Override
	public void run() {
		
		CheckerBoard tempBoard = p1.move(currentBoard);
		
		//gameBoard.setup(tempBoard);	
		add(tempBoard);
		
		int move = 0;
		while(tempBoard != null){
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			add(tempBoard = p2.move(tempBoard));
			
			tempBoard.printBoard(move);
			//gameBoard.setup(tempBoard);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			add(tempBoard = p1.move(tempBoard));
			//gameBoard.setup(tempBoard);
			tempBoard.printBoard(move);
			
			if(move++ >200) break;
		}
		
		printHistory();		
	}

	@Override
	protected Game call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
