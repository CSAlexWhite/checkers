package edu.cuny.qc.cs363;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class CheckerBoard { 
	
	int[][] normal;
	int[][] king;
	int[] lefties;
	int[] righties;

	ArrayList<CheckerPiece> board; 
	
	/**
	 * Starting Board
	 */
	public CheckerBoard(){
		
		board = new ArrayList<CheckerPiece>(32);
	}
	
	/**
	 * 
	 * @param key
	 */
	public CheckerBoard(String key){
		
		
	}
	
	public CheckerBoard nextBoard(){
		
		int move = 0;
		while(move < possibleMoves()){
			
			
		}
		
		//return 
	}
	
	public Vector<CheckerMove> possibleMoves(){
		
		Stack<CheckerMove> moves = new Stack<CheckerMove>();
		CheckerPiece target = null;
		
		for(int i=0; i<32; i++){			 // FOR EACH SQUARE ON THE BOARD
			
			if(board.get(i).empty) continue; // IF THE SPACE IS EMPTY, KEEP GOING
			if(board.get(i).black){			 // IF MY PIECE OCCUPIES THE SPACE
				
				if(!board.get(i).king){		 // AND THE PIECE ISN'T A KING
					
					int j=0;
					while(normal[i][j] != -1){  			// LOOK AT THE NORMAL ADJACENCY LIST
						target = board.get(normal[i][j]);	// LOOK AT AN AVAILABLE MOVE
						
						if(target.empty){ 					// IF SPACE IS EMPTY, ADD MOVING THERE
															// TO THE LIST OF AVAILABLE MOVES							
							moves.push(new CheckerMove(i,normal[i][j])); 
							continue;
						}
						
						
															// IF THE POTENTIAL SPACE HAS A RED PIECE
															// LOOK ON THE OTHER SIDE OF IT
						
						/***************JUMPING W/O A KING*********************/
						
						
						
						// IF THE TARGET SQUARE IS OCCUPIED BY A RED (NOT BLACK) PIECE
						if(!target.black && !target.empty)
							
							// IF THE ORIGINAL SQUARE IS A LEFTY, JUMPS CAN ONLY BE TO THE RIGHT
							if(member(i,lefties) 
								moves.push(new CheckerMove(i,normal[i][j]));
						
						
						j++;
					}
				}			
				
				while()
			}
		}
	}
	
	public boolean member(int square, int[] set){
		
		for (int i=0; i<set.length; i++) if(set[i]==square) return true;
		return false;
	}
	
}
