package edu.cuny.qc.cs363;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class CheckerBoard { 
	
	/************** STATIC VARIABLES *****************/
	
	static int[][] normal;
	static int[][] king;
	static int[] lefties;
	static int[] righties;
	
	Vector<CheckerMove> movesList;
	int possibleMoves;

	ArrayList<CheckerPiece> board; 
	
	/**
	 * Starting Board
	 */
	public CheckerBoard(){
		
		board = new ArrayList<CheckerPiece>(32);
		normal = Main.globals.NORMAL_ADJACENCE;
		king = Main.globals.KING_ADJACENCE;
		lefties = Main.globals.LEFTIES;
		righties = Main.globals.RIGHTIES;
	}
	
	/**
	 * 
	 * @param key
	 */
	public CheckerBoard(String key){
		
		
	}
	
	public CheckerBoard nextBoard(){
		
		
		int move = 0;
		while(move < movesList.size()){
			
			
		}
		
		//return 
	}
	
	public Vector<CheckerMove> getMoveList(){
		
		Stack<CheckerMove> moves = new Stack<CheckerMove>(); // TODO CHANGE TO PRIORITY QUEUE?
		CheckerPiece square = null;
		
		for(int i=0; i<32; i++){			 				// FOR EACH SQUARE ON THE BOARD
			
			if(board.get(i).isEmpty()) continue; 				// IF THE SPACE IS EMPTY, KEEP GOING
			if(board.get(i).isBlack()){			 				// IF MY PIECE OCCUPIES THE SPACE
				
				/************************* NOT A KING *************************/
				
				if(!board.get(i).isKing()){		 				// AND THE PIECE ISN'T A KING
					
					int j=0;
					while(normal[i][j] != -1){  			// LOOK AT THE NORMAL ADJACENCY LIST
						square = board.get(normal[i][j]);	// LOOK AT AN AVAILABLE MOVE
						
						if(!square.isEmpty() && square.isBlack()) continue;
						
						if(square.isEmpty()){ 					// IF SPACE IS EMPTY, ADD MOVING THERE
															// TO THE LIST OF AVAILABLE MOVES							
							moves.push(new CheckerMove(i,normal[i][j])); 
							continue;
						}						
																									
						// IF THE TARGET SQUARE IS OCCUPIED BY A RED (NOT BLACK) PIECE
						if(!square.isBlack() && !square.isEmpty())
							
							// IF THE ORIGINAL SQUARE IS A LEFTY, JUMPS CAN ONLY BE TO THE RIGHT
							if(member(i,lefties)) 
								moves.push(new CheckerMove(i,normal[i][j]));
												
						j++;
					}
				}
				
				/************************** IS A KING *************************/
				
				if(board.get(i).king){
					
					
				}
				
				while()
			}
		}
	}
	
	public boolean canJump(int square){
		
		return true;
	}
	
	public boolean member(int square, int[] set){
		
		for (int i=0; i<set.length; i++) if(set[i]==square) return true;
		return false;
	}
	
}
