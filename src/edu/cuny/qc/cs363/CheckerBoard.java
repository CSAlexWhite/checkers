package edu.cuny.qc.cs363;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class CheckerBoard { 
	
	/************** STATIC VARIABLES *****************/
	
	static int[][] adjacence;
	static int[][] king;

	
	Stack<CheckerBoard> movesList;
	int possibleMoves;

	ArrayList<CheckerPiece> board; 
	
	/**
	 * Starting Board
	 */
	public CheckerBoard(){
		
		board = new ArrayList<CheckerPiece>(32);
		adjacence = Main.globals.ADJACENCE;
		movesList = new Stack<CheckerBoard>();
	}
	
	/**
	 * 
	 * @param key
	 */
	public CheckerBoard(String key){
		
		
	}
	
	public CheckerBoard(ArrayList<CheckerPiece> board){
		
		this.board = board;
	}
	
	public CheckerBoard(CheckerBoard previous, CheckerMove move){
		
		
	}
	
//	public CheckerBoard nextBoard(){
//		
//		
//		int move = 0;
//		while(move < movesList.size()){
//			
//			
//		}
//		
//		//return 
//	}
	
	int currentPiece, target, jump;
	public /*Vector<CheckerBoard>*/ void getChildren(){
		
		CheckerPiece targetSquare = null;
		
		for(int position=0; position<32; position++){							// FOR EACH SQUARE ON THE BOARD
			
			currentPiece = position;
			if(board.get(currentPiece).isEmpty()) continue; 							// IF THE SPACE IS EMPTY, KEEP GOING
			
			ArrayList<CheckerPiece> newBoard = new ArrayList<CheckerPiece>(32);	// CLONE THE CURRENT BOARD
			for(int l=0; l<32; l++) newBoard.set(currentPiece, board.get(currentPiece));
			
			
			if(board.get(currentPiece).isBlack()){			 						// IF MY PIECE OCCUPIES THE SPACE		
		
				/************************* NOT A KING *************************/
				
				if(!board.get(currentPiece).isKing()){		 						// AND THE PIECE ISN'T A KING
						
					for(int neighbor = 0; 										// LOOK AT THE ADJACENCY LIST
							neighbor<board.get(currentPiece).numNeighbors(); 
							neighbor++)
					{  	
																				// DEPENDING ON WHETHER KING OR NOT, SEARCH
						target = adjacence[currentPiece][neighbor];				// EITHER TWO OR FOUR NEIGHBORS
						if(target == -1) continue;								// KEEP GOING WHEN YOU CAN'T MOVE THERE
						
						targetSquare = board.get(target);								// LOOK AT AN AVAILABLE MOVE
						
						if(targetSquare.isBlack()) continue;							// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
						
						if(targetSquare.isEmpty()){ 						// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
																					
							newBoard.get(currentPiece).setEmpty();				// REMOVE OUR PIECE FROM THE CURRENT SPACE
							newBoard.get(										// PUT IT ON THE NEW SPACE
								adjacence[currentPiece][neighbor]).setBlack();
							movesList.push(new CheckerBoard(newBoard)); 			// ADD THIS BOARD TO THE LIST OF NEW BOARDS
							continue;
						}
																
						if(targetSquare.isRed()){										// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
							
							jump = adjacence[neighbor][neighbor];
							
							if(newBoard.get(jump).isEmpty()){
								
								jump(newBoard, jump);
					
							}
						}
					} /* FOR */
				} /* IF */
			}
		}
	}
		
	public void jump(ArrayList<CheckerPiece> currentBoard, int currentPiece){ 
		
		// WE'RE JUMPING, WE CAN'T STOP!
		
		CheckerPiece targetSquare;
		movesList.push(new CheckerBoard(currentBoard));				// BOARD WITH THIS CAPTURE GOES ON THE STACK
		
		for(int neighbor=0; neighbor<currentBoard.get(currentPiece).numNeighbors(); neighbor++){
			
			target = adjacence[currentPiece][neighbor];				// EITHER TWO OR FOUR NEIGHBORS
			if(target == -1) continue;								// KEEP GOING WHEN YOU CAN'T MOVE THERE
			
			targetSquare = board.get(target);								// LOOK AT AN AVAILABLE MOVE
			
			if(targetSquare.isBlack()) continue;							// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
			
			if(targetSquare.isEmpty()) continue;							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
									
			if(targetSquare.isRed()){										// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
				
				jump = adjacence[neighbor][neighbor];
				
				if(currentBoard.get(jump).isEmpty()){				// AND THERE'S A SPACE ON THE OTHER SIDE OF IT
					
					currentBoard.get(currentPiece).setEmpty();		// REMOVE THE CURRENT PIECE
					currentBoard.get(target).setEmpty();			// CAPTURE THE RED PIECE
					currentBoard.get(jump).setBlack();				// SET THE CURRENT PIECE ACROSS THE RED PIECE
					jump(currentBoard, jump);						// JUMP
				}
				
				else continue;
			}			
		
		}
		
		return;
	}
	
	public void jump(){
		
		
	}
	
	public boolean member(int square, int[] set){
		
		for (int i=0; i<set.length; i++) if(set[i]==square) return true;
		return false;
	}
	
}
