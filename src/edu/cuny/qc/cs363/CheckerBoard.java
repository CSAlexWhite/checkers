package edu.cuny.qc.cs363;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class CheckerBoard { 
	
	/************** STATIC VARIABLES *****************/
	
	static int[][] adjacence;
	
	Vector<CheckerBoard> movesList;
	int possibleMoves;
	int myValue;

	ArrayList<CheckerPiece> board; 
	
	/**
	 * Starting Board
	 */
	public CheckerBoard(){
		
		board = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++) board.add(new CheckerPiece('0'));
		adjacence = Main.globals.ADJACENCE;
		movesList = new Stack<CheckerBoard>();
	}
	
	/**
	 * 
	 * @param key
	 */
	public CheckerBoard(String key){
		
		board = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++)
			board.add(0, new CheckerPiece(key.charAt(i)));
		
		adjacence = Main.globals.ADJACENCE;
		movesList = new Stack<CheckerBoard>();		
		
		myValue = evaluate();
		
		System.out.println("This board's value is " + myValue);
	}
	
	public CheckerBoard(ArrayList<CheckerPiece> inputBoard){
		
		board = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++) board.add(new CheckerPiece(inputBoard.get(i)));
		
		adjacence = Main.globals.ADJACENCE;
		movesList = new Stack<CheckerBoard>();		
		
		//movesList.add(this);
		//getChildren();
		evaluate();
	}
		
	int currentPiece, target1, target2, jump;
	public /*Vector<CheckerBoard>*/ void getChildren(int player /*0-black, 1-red*/){
		
		CheckerPiece targetSquare = null;
		
		for(int currentPiece=0; currentPiece<32; currentPiece++){							// FOR EACH SQUARE ON THE BOARD
			
			if(board.get(currentPiece).isEmpty()) continue; 							// IF THE SPACE IS EMPTY, KEEP GOING
			
			ArrayList<CheckerPiece> newBoard = new ArrayList<CheckerPiece>(32);	// CLONE THE CURRENT BOARD
			
			for(int square=0; square<32; square++) 
				newBoard.add(board.get(square));
			
			if(newBoard.get(currentPiece).isBlack()){			 						// IF MY PIECE OCCUPIES THE SPACE		
		
				/************************* NOT A KING *************************/				
						
				for(int neighbor = 0; 										// LOOK AT THE ADJACENCY LIST
						neighbor<newBoard.get(currentPiece).numNeighbors(); 
						neighbor++)
				{  	
//					System.out.print("Position " + currentPiece);
//					System.out.print(" Neighbor " + neighbor + " is ");		// DEPENDING ON WHETHER KING OR NOT, SEARCH
					
					target1 = adjacence[currentPiece][neighbor];				// EITHER TWO OR FOUR NEIGHBORS
					if(target1 == -1){
						
						//System.out.println("Out of Bounds");
						continue;										// KEEP GOING WHEN YOU CAN'T MOVE THERE
					}
					
					targetSquare = newBoard.get(target1);				// LOOK AT AN AVAILABLE MOVE
					
					if(targetSquare.isBlack()){ 
						
						//System.out.println("Black!");
						continue;										// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
					}
					
					if(targetSquare.isEmpty()){ 						// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
						
						//System.out.println("Empty!");
//						System.out.println("We're switching " + currentPiece
//								+ " with " + target1);
						//printArray(newBoard);
						swap(newBoard, target1, currentPiece);			// SWAP PIECES
						//printArray(newBoard);
						movesList.add(new CheckerBoard(newBoard)); 		// RECORD THE MOVE
						swap(newBoard, target1, currentPiece);			// SWAP THEM BACK
						
						continue;
					}
															 
					if(targetSquare.isRed()){								// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE

						target2 = adjacence[adjacence[currentPiece][neighbor]][neighbor];
						//System.out.println("Jump to is: " + target2);
						if(target2 != -1)
						jump = target2;
						
						if(newBoard.get(jump).isEmpty()){					// AND THE PIECE ACROSS IS EMPTY
							
							swap(newBoard, currentPiece, jump);
							//newBoard.get(target1).setEmpty();				// REMOVE RED PIECE, TRACK CAPTURED PIECES?
							jump(newBoard, currentPiece, jump);				// JUMP!
							swap(newBoard, currentPiece, jump);
						}
					}
				} // FOR NEIGHBORS		
			} // IF OUR PIECE 
		} // FOR ALL SQUARES
		
		int boardNo = 1; 
		for(int i=0; i<movesList.size(); i++) movesList.get(i).printBoard(boardNo++);
	}
		
	public void jump(ArrayList<CheckerPiece> currentBoard, int previousPiece, int currentPiece){ 
		
		int target1, target2;
		//System.out.println("JUMPING?");
		// WE'RE JUMPING, WE CAN'T STOP!
		
		CheckerPiece targetSquare;
		movesList.add(new CheckerBoard(currentBoard));				// BOARD WITH THIS CAPTURE GOES ON THE STACK
		swap(currentBoard, previousPiece, jump);
		
		for(int neighbor=0; neighbor<currentBoard.get(currentPiece).numNeighbors(); neighbor++){
			
			target1 = adjacence[currentPiece][neighbor];		// TARGET1 IS THE IMMEDIATE NEIGHBOR
			if(target1 == -1){
				
				//System.out.print("target1 =" + target1);
				continue;	
			}
			
			target2 = adjacence[target1][neighbor];				// TARGET2 IS THE NEIGHBORS'S NEIGHBOR			
			if(target2 == -1){
				
				continue;								// KEEP GOING WHEN YOU CAN'T MOVE THERE
			}
			
			targetSquare = currentBoard.get(target2);								// LOOK AT AN AVAILABLE MOVE
			
			if(targetSquare.isBlack()) continue;							// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
			
			if(targetSquare.isEmpty()) continue;							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
									
			if(targetSquare.isRed()){										// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
				
				jump = target2;
				
				if(currentBoard.get(jump).isEmpty()){				// AND THERE'S A SPACE ON THE OTHER SIDE OF IT
					
					System.out.println("What now?");
					//currentBoard.get(target1).setEmpty();				// CAPTURE THE RED PIECE
					swap(currentBoard, jump, currentPiece);			// SET THE CURRENT PIECE ACROSS THE RED PIECE
					jump(currentBoard, currentPiece, jump);						// JUMP
				}
				
				else continue;
			}					
		}	
		
		return;
	}
	
	public void swap(ArrayList<CheckerPiece> board, int from, int to){
		
		CheckerPiece temp = board.get(to);
		board.set(to, board.get(from));
		board.set(from, temp);
	}
	
	public void printArray(ArrayList<CheckerPiece> input){
		
		for(int i=0; i<32; i++) System.out.println(i + "\t" + input.get(i).type);
		System.out.println();
	}
	
	public void printPositions(){
		
		int k = 31;
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(i%2 == j%2){ 
					
					System.out.print(k-- + " ");
				}
				else System.out.print("  ");
			}
			
			System.out.println();
		}
	}
	
	public int evaluate(){
		
		int value = 0;
				
		// COUNT THE KINGS
		for(int i=0; i<32; i++){ 
			
			if(board.get(i).isBlack() && !board.get(i).isKing()) value += 1;			
				
			if(board.get(i).isBlack() && board.get(i).isKing())	value += 5;
			
			if(board.get(i).isRed() && !board.get(i).isKing()) value -= 1;
			
			if(board.get(i).isRed() && board.get(i).isKing()) value -= 5;		
		}
		
		value += movesList.size();
		
		// ideas: more possible moves is good, less middle board space is better
		return value;
	}
	
	public void printBoard(int number){
		
		System.out.println("\n       Move " + number);
		System.out.println("  ---------------");
		int position = 0;
		for(int i=0; i<8; i++){
			
			System.out.print("| ");
			
			for(int j=0; j<8; j++){
				
				if(i%2 == j%2){ 
					
					System.out.print(board.get(position++).type + " ");
				}
				
				else System.out.print("  ");			
			}
			
			System.out.println("|");
		}
		
		System.out.println("  ---------------");
	}

	public boolean member(int square, int[] set){
		
		for (int i=0; i<set.length; i++) if(set[i]==square) return true;
		return false;
	}
	
}
