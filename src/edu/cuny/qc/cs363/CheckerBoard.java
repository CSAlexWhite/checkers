package edu.cuny.qc.cs363;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class CheckerBoard { 
	
	/************** STATIC VARIABLES *****************/
	
	static int[][] adjacence;
	
	Stack<CheckerBoard> movesList;
	int possibleMoves;

	ArrayList<CheckerPiece> board; 
	
	/**
	 * Starting Board
	 */
	public CheckerBoard(){
		
		board = new ArrayList<CheckerPiece>(32);
		for(int i=0; i<32; i++) board.set(i, new CheckerPiece('0'));
		adjacence = Main.globals.ADJACENCE;
		movesList = new Stack<CheckerBoard>();
	}
	
	/**
	 * 
	 * @param key
	 */
	public CheckerBoard(String key){
		
		board = new ArrayList<CheckerPiece>(0);
		for(int i=0; i<32; i++)
			board.add(0, new CheckerPiece(key.charAt(i)));
		
		adjacence = Main.globals.ADJACENCE;
		movesList = new Stack<CheckerBoard>();		
	}
	
	public CheckerBoard(ArrayList<CheckerPiece> board){
		
		this.board = board;
	}
	
	public CheckerBoard(CheckerBoard previous, CheckerMove move){
		
		
	}
		
	int currentPiece, target, jump;
	public /*Vector<CheckerBoard>*/ void getChildren(){
		
		CheckerPiece targetSquare = null;
		
		for(int position=0; position<32; position++){							// FOR EACH SQUARE ON THE BOARD
			
			currentPiece = position;
			if(board.get(currentPiece).isEmpty()) continue; 							// IF THE SPACE IS EMPTY, KEEP GOING
			
			ArrayList<CheckerPiece> currentBoard = new ArrayList<CheckerPiece>(32);
			ArrayList<CheckerPiece> newBoard = new ArrayList<CheckerPiece>(32);	// CLONE THE CURRENT BOARD
			
			for(int square=0; square<32; square++){ 
				
				currentBoard.add(square, board.get(square));
				newBoard.add(square, board.get(square));
			}
			
			
			if(board.get(currentPiece).isBlack()){			 						// IF MY PIECE OCCUPIES THE SPACE		
		
				/************************* NOT A KING *************************/				
						
				for(int neighbor = 0; 										// LOOK AT THE ADJACENCY LIST
						neighbor<board.get(currentPiece).numNeighbors(); 
						neighbor++)
				{  	
					System.out.print("Position " + position);
					System.out.print(" Neighbor " + neighbor + " is ");		// DEPENDING ON WHETHER KING OR NOT, SEARCH
					
					target = adjacence[currentPiece][neighbor];				// EITHER TWO OR FOUR NEIGHBORS
					if(target == -1){
						
						System.out.println("Out of Bounds");
						continue;								// KEEP GOING WHEN YOU CAN'T MOVE THERE
					}
					
					targetSquare = board.get(target);						// LOOK AT AN AVAILABLE MOVE
					
					if(targetSquare.isBlack()){ 
						
						System.out.println("Black!");
						continue;					// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
					}
					
					if(targetSquare.isEmpty()){ 							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
						
						System.out.println("Empty!");
						newBoard.get(currentPiece).setEmpty();				
						newBoard.get(										// MOVE THE PIECE 
							adjacence[currentPiece][neighbor]).setBlack();
						
						movesList.push(new CheckerBoard(newBoard)); 		// RECORD THE MOVE
						
						newBoard.get(currentPiece).setBlack();				// MOVE THE PIECE BACK
						newBoard.get(
								adjacence[currentPiece][neighbor]).setEmpty();
						
						continue;
					}
															
					if(targetSquare.isRed()){								// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
						
						jump = adjacence[neighbor][neighbor];
						
						if(newBoard.get(jump).isEmpty()){					// AND THE PIECE ACROSS IS EMPTY
							
							jump(newBoard, jump);							// JUMP!
				
						}
					}
				} // FOR NEIGHBORS		
			} // IF OUR PIECE 
		} // FOR ALL SQUARES
		
		while(!movesList.isEmpty()) movesList.pop().printBoard();
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
	
	public void printBoard(){
		System.out.println("_____________________\nBOARD:");
		int position = 0;
		for(int i=0; i<8; i++){
			
			for(int j=0; j<8; j++){
				
				// if the row is even
				if(i%2 == j%2){ 
					
					System.out.print(board.get(position).type + " ");
					position++;
				}
				else System.out.print("  ");
				
			}
			
			System.out.println("");
		}
	}

	public boolean member(int square, int[] set){
		
		for (int i=0; i<set.length; i++) if(set[i]==square) return true;
		return false;
	}
	
}
