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
	
	public CheckerBoard(ArrayList<CheckerPiece> board){
		
		this.board = board;
	}
	
	public CheckerBoard(CheckerBoard previous, CheckerMove move){
		
		
	}
	
	public CheckerBoard nextBoard(){
		
		
		int move = 0;
		while(move < movesList.size()){
			
			
		}
		
		//return 
	}
	
	public Vector<CheckerBoard> getChildren(){
		
		Stack<CheckerBoard> moves = new Stack<CheckerBoard>(); 	// TODO CHANGE TO PRIORITY QUEUE?
		CheckerPiece square = null;
		
		for(int position=0; position<32; position++){			 		// FOR EACH SQUARE ON THE BOARD
			
			if(board.get(position).isEmpty()) continue; 				// IF THE SPACE IS EMPTY, KEEP GOING
			
			ArrayList<CheckerPiece> newBoard = new ArrayList<CheckerPiece>(32);	 // CLONE THE CURRENT BOARD
			for(int l=0; l<32; l++) newBoard.set(position, board.get(position));
			
			if(board.get(position).isBlack()){			 				// IF MY PIECE OCCUPIES THE SPACE
				
				/************************* NOT A KING *************************/
				
				if(!board.get(position).isKing()){		 				// AND THE PIECE ISN'T A KING
					
					int neighbor=0;
					while(normal[position][neighbor] != -1){  				// LOOK AT THE NORMAL ADJACENCY LIST
						square = board.get(normal[position][neighbor]);		// LOOK AT AN AVAILABLE MOVE
						
						if(!square.isEmpty() && square.isBlack()) continue;	// IF OUR PIECE IS IN THE WAY
																			// IGNORE THIS SPACE KEEP GOING
						
						if(square.isEmpty()){ 					// IF SPACE IS EMPTY, ADD MOVING THERE
																// TO THE LIST OF AVAILABLE MOVES		
							newBoard.get(position).setEmpty();
							newBoard.get(normal[position][neighbor]).setBlack();
							moves.push(new CheckerBoard(newBoard)); 
							continue;
						}																															
												
						if(square.isRed()){				// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
							
							jump(newBoard, position, true);
														
														// IF THE ORIGINAL SQUARE IS A LEFTY, 
														// JUMPS CAN ONLY BE TO THE RIGHT
							if(member(position,lefties)) 
								moves.push(new CheckerBoard(this, new CheckerMove(position, normal[position][neighbor]))); 			
						}
						
						neighbor++;
					}
				}
				
				/************************** IS A KING *************************/
				
				if(board.get(position).isKing()){
					
					
				}
				
				//while()
			}
		}
	}
	
	Vector<CheckerBoard> jumpBoards = new Vector<CheckerBoard>();
	
	public Vector<CheckerBoard> jump(ArrayList<CheckerPiece> current, int square, boolean king){ // RETURNS A VECTOR OF BOARDS RESULTING FROM JUMPS
				
		if(king){
		
			if(member(square, lefties)){
				
				 
			}
			
			if(member(square, righties)){
				
				
			}
			
			else{}
		}
		
		if(!king){
			
			if(member(square, lefties)){
				
				// 
			}
			
			if(member(square, righties)){
				
				
			}
			
			else{}
		}
		
		return jumpBoards;
	}
	
	public void jump(){
		
		
	}
	
	public boolean member(int square, int[] set){
		
		for (int i=0; i<set.length; i++) if(set[i]==square) return true;
		return false;
	}
	
}
