package edu.cuny.qc.cs363;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class CheckerBoard { 
	
	/************** STATIC VARIABLES *****************/
	
	static int[][] black_adjacence;
	static int[][] red_adjacence;
	
	Vector<CheckerBoard> movesList;
	int possibleMoves;
	int myValue;
	int boardPlayer;
	
	String moveFromLast, tempMove;
	
	int turn;

	ArrayList<CheckerPiece> board; 
	
	/**
	 * Starting Board
	 */
//	public CheckerBoard(){
//		
//		moveFromLast = "Opening Board";
//		board = new ArrayList<CheckerPiece>();
//		for(int i=0; i<32; i++) board.add(new CheckerPiece('0'));
//		black_adjacence = Main.globals.BLACK_ADJACENCE;
//		red_adjacence = Main.globals.RED_ADJACENCE;
//		movesList = new Stack<CheckerBoard>();
//		turn = -1;
//	}
	
	/**
	 * 
	 * @param key
	 */
	public CheckerBoard(String key){
		
		printPositions();
		moveFromLast = "Opening Board";
		turn = -1;
		board = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++)
			board.add(0, new CheckerPiece(key.charAt(i)));
		
		black_adjacence = Main.globals.BLACK_ADJACENCE;
		red_adjacence = Main.globals.RED_ADJACENCE;
		movesList = new Stack<CheckerBoard>();		
	}
	
	public CheckerBoard(ArrayList<CheckerPiece> inputBoard, int turn, String move){
		
		turn++;
		moveFromLast = move;
		board = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++) board.add(new CheckerPiece(inputBoard.get(i)));
		
		black_adjacence = Main.globals.BLACK_ADJACENCE;
		red_adjacence = Main.globals.RED_ADJACENCE;
		movesList = new Stack<CheckerBoard>();		
		
		myValue = evaluate();
	}
			
	public Vector<CheckerBoard> getChildren(int player){
			
		boardPlayer = player;
		if(player == 0){
			
			CheckerPiece targetSquare = null;			
			for(int currentPiece=0; currentPiece<32; currentPiece++){			// FOR EACH SQUARE ON THE BOARD
				
				if(board.get(currentPiece).isEmpty()) continue; 				// IF THE SPACE IS EMPTY, KEEP GOING
				
				ArrayList<CheckerPiece> newBoard = new ArrayList<CheckerPiece>(32);	// CLONE THE CURRENT BOARD				
				for(int square=0; square<32; square++) newBoard.add(board.get(square));
				
				if(newBoard.get(currentPiece).isBlack()){			 			// IF MY PIECE OCCUPIES THE SPACE		
										
					for(int neighbor = 0; 										// LOOK AT THE ADJACENCY LIST
							neighbor<newBoard.get(currentPiece).numNeighbors(); 
							neighbor++)
					{  	
																				// DEPENDING ON WHETHER KING OR NOT, SEARCH
						
						int target1 = black_adjacence[currentPiece][neighbor];				// EITHER TWO OR FOUR NEIGHBORS
						if(target1 == -1) continue;								// KEEP GOING WHEN YOU CAN'T MOVE THERE									
												
						targetSquare = newBoard.get(target1);					// LOOK AT AN AVAILABLE MOVE
						
						if(targetSquare.isBlack()) continue;					// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING

						if(targetSquare.isEmpty()){ 							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
							
							boolean kinged = false;
							tempMove = "Black moved " + currentPiece + " to " + target1;
							
							kinged = move(newBoard, currentPiece, target1, false);				// SWAP PIECES
							movesList.add(new CheckerBoard(newBoard, player, tempMove)); 	// RECORD THE MOVE
							
//							System.out.println("Moving Back");
							move(newBoard, target1, currentPiece, kinged);				// SWAP THEM BACK						
							continue;
						}
																 
						if(targetSquare.isRed()){								// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
	
							int target2 = black_adjacence[black_adjacence[currentPiece][neighbor]][neighbor];
							if(target2 != -1){ 
								
								int jump = target2;
								if(newBoard.get(jump).isEmpty()){				// AND THE PIECE ACROSS IS EMPTY
									
									boolean kinged = false;
									kinged = move(newBoard, currentPiece, jump, false);
									tempMove = "Black captured " + target1;
									newBoard.get(target1).setEmpty();			// REMOVE RED PIECE, TRACK CAPTURED PIECES?
									jump(player, newBoard, currentPiece, jump, kinged);	// JUMP!
								}
							}
							
							else continue;
						}
					} // FOR NEIGHBORS		
				} // IF OUR PIECE 
			} // FOR ALL SQUARES
		}
		
		if(player == 1){
			
			CheckerPiece targetSquare = null;			
			for(int currentPiece=0; currentPiece<32; currentPiece++){			// FOR EACH SQUARE ON THE BOARD
				
				if(board.get(currentPiece).isEmpty()) continue; 				// IF THE SPACE IS EMPTY, KEEP GOING
				
				ArrayList<CheckerPiece> newBoard = new ArrayList<CheckerPiece>(32);	// CLONE THE CURRENT BOARD			
				for(int square=0; square<32; square++) newBoard.add(board.get(square));
				
				if(newBoard.get(currentPiece).isRed()){			 				// IF MY PIECE OCCUPIES THE SPACE		
									
					for(int neighbor = 0; 										// LOOK AT THE ADJACENCY LIST
							neighbor<newBoard.get(currentPiece).numNeighbors(); 
							neighbor++)
					{  															// DEPENDING ON WHETHER KING OR NOT, SEARCH
						
						int target1 = red_adjacence[currentPiece][neighbor];		// EITHER TWO OR FOUR NEIGHBORS
						if(target1 == -1) continue;								// KEEP GOING WHEN YOU CAN'T MOVE THERE
						
						targetSquare = newBoard.get(target1);					// LOOK AT AN AVAILABLE MOVE
						
						if(targetSquare.isRed()) continue;						// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
											
						if(targetSquare.isEmpty()){ 							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
							
							boolean kinged = false;
							tempMove = "Red moved from " + currentPiece + " to " + target1;
							kinged = move(newBoard, currentPiece, target1, false);				// SWAP PIECES
							movesList.add(new CheckerBoard(newBoard, player, tempMove)); 	// RECORD THE MOVE							
		//					System.out.println("Moving Back");
							move(newBoard, target1, currentPiece, kinged);				// SWAP THEM BACK						
							continue;
						}
																 
						if(targetSquare.isBlack()){								// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
		
							int target2 = red_adjacence[red_adjacence[currentPiece][neighbor]][neighbor];
							if(target2 != -1){
														
								int jump = target2;			
								if(newBoard.get(jump).isEmpty()){				// AND THE PIECE ACROSS IS EMPTY
									
									boolean kinged = false;
									kinged = move(newBoard, currentPiece, jump, false);
									tempMove += "Red captured " + target1;
									newBoard.get(target1).setEmpty();			// REMOVE RED PIECE, TRACK CAPTURED PIECES?
									jump(player, newBoard, currentPiece, jump, kinged);				// JUMP!
								}
							}
							
							else continue;
						}
					} // FOR NEIGHBORS		
				} // IF OUR PIECE 
			} // FOR ALL SQUARES			
		}
		
		return movesList;
		
//		int boardNo = 1; 
//		for(int i=0; i<movesList.size(); i++) movesList.get(i).printBoard(player, boardNo++);
	}
		
	public void jump(int player, ArrayList<CheckerPiece> currentBoard, int previousPiece, int currentPiece, boolean justKinged){ 
		
//		System.out.println("Jumping!");
		
		if(player == 0){

			int target1, target2;		
			CheckerPiece targetSquare;
			tempMove += " by jumping from " + previousPiece + " to " + currentPiece;
			movesList.add(new CheckerBoard(currentBoard, player, tempMove));				// BOARD WITH THIS CAPTURE GOES ON THE STACK
			move(currentBoard, currentPiece, previousPiece, justKinged);
			
			for(int neighbor=0; neighbor<currentBoard.get(currentPiece).numNeighbors(); neighbor++){
				
				target1 = black_adjacence[currentPiece][neighbor];				// TARGET1 IS THE IMMEDIATE NEIGHBOR
				if(target1 == -1) continue;	
				
				target2 = black_adjacence[target1][neighbor];					// TARGET2 IS THE NEIGHBORS'S NEIGHBOR			
				if(target2 == -1) continue;										// KEEP GOING WHEN YOU CAN'T MOVE THERE
				
				targetSquare = currentBoard.get(target2);						// LOOK AT AN AVAILABLE MOVE
				
				if(targetSquare.isBlack()) continue;							// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
				
				if(targetSquare.isEmpty()) continue;							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
										
				if(targetSquare.isRed()){										// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
					
					int jump = target2;					
					if(currentBoard.get(jump).isEmpty()){						// AND THERE'S A SPACE ON THE OTHER SIDE OF IT
						
						boolean kinged = false;
						System.out.println("What now?");
						currentBoard.get(target1).setEmpty();					// CAPTURE THE RED PIECE
						kinged = move(currentBoard, currentPiece, jump, false);					// SET THE CURRENT PIECE ACROSS THE RED PIECE
						jump(player, currentBoard, currentPiece, jump, kinged);			// JUMP
					}
					
					else continue;
				}					
			}	
			
			return;
		}
		
		if(player == 1){
			
			int target1, target2;
			
			CheckerPiece targetSquare;
			tempMove += " by jumping from " + previousPiece + " to " + currentPiece;
			movesList.add(new CheckerBoard(currentBoard, player, tempMove));				// BOARD WITH THIS CAPTURE GOES ON THE STACK
			move(currentBoard, currentPiece, previousPiece, justKinged);
			
			for(int neighbor=0; neighbor<currentBoard.get(currentPiece).numNeighbors(); neighbor++){
				
				target1 = red_adjacence[currentPiece][neighbor];				// TARGET1 IS THE IMMEDIATE NEIGHBOR
				if(target1 == -1) continue;	
				
				target2 = red_adjacence[target1][neighbor];						// TARGET2 IS THE NEIGHBORS'S NEIGHBOR	
				
				if(target2 == -1) continue;										// KEEP GOING WHEN YOU CAN'T MOVE THERE
				
				targetSquare = currentBoard.get(target2);						// LOOK AT AN AVAILABLE MOVE
				
				if(targetSquare.isRed()) continue;								// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
				
				if(targetSquare.isEmpty()) continue;							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
										
				if(targetSquare.isBlack()){										// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
					
					int jump = target2;					
					if(currentBoard.get(jump).isEmpty()){						// AND THERE'S A SPACE ON THE OTHER SIDE OF IT
						
						boolean kinged = false;
						System.out.println("What now?");
						currentBoard.get(target1).setEmpty();					// CAPTURE THE RED PIECE
						
						kinged = move(currentBoard, currentPiece, jump, false);	// SET THE CURRENT PIECE ACROSS THE RED PIECE
						jump(player, currentBoard, currentPiece, jump, kinged);	// JUMP
					}
					
					else continue;
				}					
			}	
			
			return;
		}
	}
	
	public boolean move(ArrayList<CheckerPiece> board, int from, int to, boolean justKinged){
			
		if(!board.get(from).isKing()){
			if(board.get(from).isBlack()){
				
				if(to>27){ // DOESN'T MATTER IF WE RE-KING
										
					board.get(from).setEmpty();
					board.get(to).setBlack();
					board.get(to).setKing();					
					return true;
				}
			
				if(to<28){ 
					
					board.get(from).setEmpty();
					board.get(to).setBlack();		
					return false;
				}			
			}
																				
			if(board.get(from).isRed()){
			
				if(to<4){ // DOESN'T MATTER IF WE RE-KING
				
					board.get(from).setEmpty();
					board.get(to).setRed();
					board.get(to).setKing();				
					return true;
				}

				if(to>3){ 
					
					board.get(from).setEmpty();
					board.get(to).setRed();
					return false;
				}
			}
		}

		if(board.get(from).isKing()){
			
			if(board.get(from).isBlack()){ 
					
				if(to<28){ 
				
					if(justKinged){
						
						board.get(from).setEmpty();
						board.get(to).setBlack();
						board.get(to).unKing();
						return false;
					}
					
					else {
						
						board.get(from).setEmpty();
						board.get(to).setBlack();
						board.get(to).setKing();
						return false;
					}
				}
				
				if(to>27){ 
					
					board.get(from).setEmpty();
					board.get(to).setBlack();
					board.get(to).setKing();	
					return false;
				}
			}
		
			if(board.get(from).isRed()){ 
				
				if(to>3){ 
			
					if(justKinged){
						
						board.get(from).setEmpty();
						board.get(to).setRed();
						board.get(to).unKing();
						return false;
					}
					
					else {
						
						board.get(from).setEmpty();
						board.get(to).setRed();
						board.get(to).setKing();
						return false;
					}
				}								
		
				if(to<4){ 
					
					board.get(from).setEmpty();
					board.get(to).setRed();
					board.get(to).setKing();		
					return false;
				}	
			}
		}

		return false;
	}
	
	public void printArray(ArrayList<CheckerPiece> input){
		
		for(int i=0; i<32; i++) System.out.println(i + "\t" + input.get(i).type);
		System.out.println();
	}
	
	public void printPositions(){
		
		int k = 0;
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(i%2 == j%2){ 
					
					System.out.print(k++ + " ");
				}
				else System.out.print("  ");
			}
			
			System.out.println();
		}
	}
	
	public int evaluate(){
		
		int value = 0;
		if(boardPlayer == 0){	
			
			for(int i=0; i<32; i++){ 
				
				if(board.get(i).isBlack() && !board.get(i).isKing()) value += 1;							
				if(board.get(i).isBlack() && board.get(i).isKing())	value += 5;				
				if(board.get(i).isRed() && !board.get(i).isKing()) value -= 1;				
				if(board.get(i).isRed() && board.get(i).isKing()) value -= 5;		
			}
		}
		
		if(boardPlayer == 1){	
			
			for(int i=0; i<32; i++){ 
				
				if(board.get(i).isBlack() && !board.get(i).isKing()) value -= 1;								
				if(board.get(i).isBlack() && board.get(i).isKing())	value -= 5;				
				if(board.get(i).isRed() && !board.get(i).isKing()) value += 1;				
				if(board.get(i).isRed() && board.get(i).isKing()) value += 5;		
			}
		}
			
		value += movesList.size();
		
		// ideas: more possible moves is good, less middle board space is better
		return value;
	}
	
	public void printBoard(int turn){//, int number){

		System.out.println("\nTurn " + turn/2);//  + " Choice " + number);
		System.out.println(moveFromLast);
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
