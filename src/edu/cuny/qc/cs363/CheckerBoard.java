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
	
	int turn;

	ArrayList<CheckerPiece> board; 
	
	/**
	 * Starting Board
	 */
	public CheckerBoard(){
		
		board = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++) board.add(new CheckerPiece('0'));
		black_adjacence = Main.globals.BLACK_ADJACENCE;
		red_adjacence = Main.globals.RED_ADJACENCE;
		movesList = new Stack<CheckerBoard>();
	}
	
	/**
	 * 
	 * @param key
	 */
	public CheckerBoard(String key){
		
		turn = 0;
		board = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++)
			board.add(0, new CheckerPiece(key.charAt(i)));
		
		black_adjacence = Main.globals.BLACK_ADJACENCE;
		red_adjacence = Main.globals.RED_ADJACENCE;
		movesList = new Stack<CheckerBoard>();		
		
		myValue = evaluate();
		getChildren(turn);
		
		System.out.println("This board's value is " + myValue);
	}
	
	public CheckerBoard(ArrayList<CheckerPiece> inputBoard, int turn){
		
		turn++;
		board = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++) board.add(new CheckerPiece(inputBoard.get(i)));
		
		black_adjacence = Main.globals.BLACK_ADJACENCE;
		red_adjacence = Main.globals.RED_ADJACENCE;
		movesList = new Stack<CheckerBoard>();		
		
		movesList.add(this);
		//if(turn<1) 
			//getChildren(turn);
		//evaluate();
	}
		
	int currentPiece, target1, target2, jump;
	public /*Vector<CheckerBoard>*/ void getChildren(int player /*0-black, 1-red*/){
		
		if(player%2 == 0){
			
			CheckerPiece targetSquare = null;			
			for(int currentPiece=0; currentPiece<32; currentPiece++){			// FOR EACH SQUARE ON THE BOARD
				
				if(board.get(currentPiece).isEmpty()) continue; 				// IF THE SPACE IS EMPTY, KEEP GOING
				
				ArrayList<CheckerPiece> newBoard = new ArrayList<CheckerPiece>(32);	// CLONE THE CURRENT BOARD				
				for(int square=0; square<32; square++) newBoard.add(board.get(square));
				
				if(newBoard.get(currentPiece).isBlack()){			 			// IF MY PIECE OCCUPIES THE SPACE		
			
					/************************* NOT A KING *************************/				
							
					for(int neighbor = 0; 										// LOOK AT THE ADJACENCY LIST
							neighbor<newBoard.get(currentPiece).numNeighbors(); 
							neighbor++)
					{  	
																				// DEPENDING ON WHETHER KING OR NOT, SEARCH
						
						target1 = black_adjacence[currentPiece][neighbor];				// EITHER TWO OR FOUR NEIGHBORS
						if(target1 == -1) continue;								// KEEP GOING WHEN YOU CAN'T MOVE THERE									
												
						targetSquare = newBoard.get(target1);					// LOOK AT AN AVAILABLE MOVE
						
						if(targetSquare.isBlack()) continue;					// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING

						if(targetSquare.isEmpty()){ 							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
							
							boolean kinged = false;
							//System.out.println("Swapping " + currentPiece + " with " + target1);
							kinged = move(newBoard, currentPiece, target1, false);				// SWAP PIECES
							movesList.add(new CheckerBoard(newBoard, player)); 	// RECORD THE MOVE
							move(newBoard, target1, currentPiece, kinged);				// SWAP THEM BACK						
							continue;
						}
																 
						if(targetSquare.isRed()){								// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
	
							target2 = black_adjacence[black_adjacence[currentPiece][neighbor]][neighbor];
							if(target2 != -1){ 
								
								jump = target2;
								if(newBoard.get(jump).isEmpty()){				// AND THE PIECE ACROSS IS EMPTY
									
									boolean kinged = false;
									kinged = move(newBoard, currentPiece, jump, false);
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
		
		if(player%2 == 1){
			
			CheckerPiece targetSquare = null;			
			for(int currentPiece=0; currentPiece<32; currentPiece++){			// FOR EACH SQUARE ON THE BOARD
				
				if(board.get(currentPiece).isEmpty()) continue; 				// IF THE SPACE IS EMPTY, KEEP GOING
				
				ArrayList<CheckerPiece> newBoard = new ArrayList<CheckerPiece>(32);	// CLONE THE CURRENT BOARD			
				for(int square=0; square<32; square++) newBoard.add(board.get(square));
				
				if(newBoard.get(currentPiece).isRed()){			 				// IF MY PIECE OCCUPIES THE SPACE		
			
					/************************* NOT A KING *************************/				
							
					for(int neighbor = 0; 										// LOOK AT THE ADJACENCY LIST
							neighbor<newBoard.get(currentPiece).numNeighbors(); 
							neighbor++)
					{  															// DEPENDING ON WHETHER KING OR NOT, SEARCH
						
						target1 = red_adjacence[currentPiece][neighbor];		// EITHER TWO OR FOUR NEIGHBORS
						if(target1 == -1) continue;								// KEEP GOING WHEN YOU CAN'T MOVE THERE
						
						targetSquare = newBoard.get(target1);					// LOOK AT AN AVAILABLE MOVE
						
						if(targetSquare.isRed()) continue;						// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
											
						if(targetSquare.isEmpty()){ 							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
							
							boolean kinged = false;
							//System.out.println("Swapping " + currentPiece + " with " + target1);
							kinged = move(newBoard, currentPiece, target1, kinged);				// SWAP PIECES
							movesList.add(new CheckerBoard(newBoard, player)); 	// RECORD THE MOVE
							move(newBoard, target1, currentPiece, kinged);				// SWAP THEM BACK						
							continue;
						}
																 
						if(targetSquare.isBlack()){								// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
	
							target2 = red_adjacence[red_adjacence[currentPiece][neighbor]][neighbor];
							if(target2 != -1){
							
								boolean kinged = false;
								jump = target2;			
								if(newBoard.get(jump).isEmpty()){				// AND THE PIECE ACROSS IS EMPTY
									
									kinged = move(newBoard, currentPiece, jump, false);
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
		
		int boardNo = 1; 
		for(int i=0; i<movesList.size(); i++) movesList.get(i).printBoard(player, boardNo++);
	}
		
	public void jump(int player, ArrayList<CheckerPiece> currentBoard, int previousPiece, int currentPiece, boolean justKinged){ 
		
		System.out.println("Jumping!");
		
		if(player%2 == 0){

			int target1, target2;		
			CheckerPiece targetSquare;
			movesList.add(new CheckerBoard(currentBoard, player));				// BOARD WITH THIS CAPTURE GOES ON THE STACK
			move(currentBoard, previousPiece, jump, justKinged);
			
			for(int neighbor=0; neighbor<currentBoard.get(currentPiece).numNeighbors(); neighbor++){
				
				target1 = black_adjacence[currentPiece][neighbor];				// TARGET1 IS THE IMMEDIATE NEIGHBOR
				if(target1 == -1) continue;	
				
				target2 = black_adjacence[target1][neighbor];					// TARGET2 IS THE NEIGHBORS'S NEIGHBOR			
				if(target2 == -1) continue;										// KEEP GOING WHEN YOU CAN'T MOVE THERE
				
				targetSquare = currentBoard.get(target2);						// LOOK AT AN AVAILABLE MOVE
				
				if(targetSquare.isBlack()) continue;							// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
				
				if(targetSquare.isEmpty()) continue;							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
										
				if(targetSquare.isRed()){										// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
					
					jump = target2;					
					if(currentBoard.get(jump).isEmpty()){						// AND THERE'S A SPACE ON THE OTHER SIDE OF IT
						
						boolean kinged = false;
						System.out.println("What now?");
						currentBoard.get(target1).setEmpty();					// CAPTURE THE RED PIECE
						kinged = move(currentBoard, jump, currentPiece, false);					// SET THE CURRENT PIECE ACROSS THE RED PIECE
						jump(player, currentBoard, currentPiece, jump, kinged);			// JUMP
					}
					
					else continue;
				}					
			}	
			
			return;
		}
		
		if(player%2 == 1){
			
			int target1, target2;
			
			CheckerPiece targetSquare;
			movesList.add(new CheckerBoard(currentBoard, player));				// BOARD WITH THIS CAPTURE GOES ON THE STACK
			move(currentBoard, previousPiece, jump, justKinged);
			
			for(int neighbor=0; neighbor<currentBoard.get(currentPiece).numNeighbors(); neighbor++){
				
				target1 = red_adjacence[currentPiece][neighbor];				// TARGET1 IS THE IMMEDIATE NEIGHBOR
				if(target1 == -1) continue;	
				
				target2 = red_adjacence[target1][neighbor];						// TARGET2 IS THE NEIGHBORS'S NEIGHBOR	
				
				if(target2 == -1) continue;										// KEEP GOING WHEN YOU CAN'T MOVE THERE
				
				targetSquare = currentBoard.get(target2);						// LOOK AT AN AVAILABLE MOVE
				
				if(targetSquare.isRed()) continue;								// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
				
				if(targetSquare.isEmpty()) continue;							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
										
				if(targetSquare.isBlack()){										// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
					
					jump = target2;					
					if(currentBoard.get(jump).isEmpty()){						// AND THERE'S A SPACE ON THE OTHER SIDE OF IT
						
						boolean kinged = false;
						System.out.println("What now?");
						currentBoard.get(target1).setEmpty();					// CAPTURE THE RED PIECE
						kinged = move(currentBoard, jump, currentPiece, false);					// SET THE CURRENT PIECE ACROSS THE RED PIECE
						jump(player, currentBoard, currentPiece, jump, kinged);			// JUMP
					}
					
					else continue;
				}					
			}	
			
			return;
		}
	}
	
	public boolean move(ArrayList<CheckerPiece> board, int from, int to, boolean justKinged){
		
//		if(board.get(from).isKing()) System.out.println("I'm already a king?");
		
		
		/* THESE ARE FOR WHEN WE'RE MOVING INTO A FINAL SQUARE AND WE'RE NOT YET A KING */
		if(board.get(from).isRed() 
				&& to<4 
				&& !board.get(from).isKing()){ // DOESN'T MATTER IF WE RE-KING
	
			System.out.println("Moving from " + from + " to " + to);
			System.out.println("SHOULD KING!");
			
			board.get(from).setEmpty();
			board.get(to).setRed();
			board.get(to).setKing();
		
			return true;
		}
		
		else if(board.get(from).isBlack() 
				&& to>27 
				&& !board.get(from).isKing()){ // DOESN'T MATTER IF WE RE-KING
						
			System.out.println("Moving from " + from + " to " + to);
			System.out.println("SHOULD KING!");
			
			board.get(from).setEmpty();
			board.get(to).setBlack();
			board.get(to).setKing();
			
			return true;
		}
		
		/* THESE ARE FOR WHEN WE'RE MOVING OUT OF A FINAL SQUARE AND WE'RE A KING */
		else if(board.get(from).isRed() 
				&& to>3 
				&& board.get(from).isKing()){ 
			
			if(justKinged){
				
				System.out.println("Moving from " + from + " to " + to);
				System.out.println("RETURNING TO NORMAL!");
				
				board.get(from).setEmpty();
				board.get(to).setRed();
				board.get(to).unKing();
			}
			
			else {
				
				System.out.println("Moving from " + from + " to " + to);
				System.out.println("STAYING A KING");
				
				board.get(from).setEmpty();
				board.get(to).setRed();
				board.get(to).setKing();
			}
		
			return false;
		}
		
		else if(board.get(from).isBlack() 
				&& to<27 
				&& board.get(from).isKing()){ 
			
			if(justKinged){
				
				System.out.println("Moving from " + from + " to " + to);
				System.out.println("RETURNING TO NORMAL!");
				
				board.get(from).setEmpty();
				board.get(to).setBlack();
				board.get(to).unKing();
			}
			
			else {
				
				System.out.println("Moving from " + from + " to " + to);
				System.out.println("STAYING A KING!");
				
				board.get(from).setEmpty();
				board.get(to).setBlack();
				board.get(to).setKing();
			}
			
			return false;
		}
		
		/* THESE ARE FOR WHEN WERE MOVING TO A MIDDLE SQUARE AND WE'RE NOT A KING */
		else if(board.get(from).isRed() 
				&& to>3 
				&& !board.get(from).isKing()){ 
			
			System.out.println("Moving from " + from + " to " + to);
			System.out.println("SHOULDN'T KING!");
			
			board.get(from).setEmpty();
			board.get(to).setRed();

			return false;
		}
		
		else if(board.get(from).isBlack() 
				&& to<28 
				&& !board.get(from).isKing()){ 
			
			System.out.println("Moving from " + from + " to " + to);
			System.out.println("SHOULDN'T KING!");
			
			board.get(from).setEmpty();
			board.get(to).setBlack();

			return false;
		}
		
		/* THESE ARE FOR WHEN WE'RE MOVING TO A MIDDLE SQUARE AND WE ARE A KING */
		else if(board.get(from).isRed() 
				&& to>3 
				&& board.get(from).isKing()){ 
			
			System.out.println("Moving from " + from + " to " + to);
			System.out.println("ALREADY A KING!");
			
			board.get(from).setEmpty();
			board.get(to).setRed();
			board.get(to).setKing();

			return false;
		}
		
		else if(board.get(from).isBlack() 
			 	&& to<28 
			 	&& board.get(from).isKing()){ 
			
			System.out.println("Moving from " + from + " to " + to);
			System.out.println("ALREADY A KINGERRR!");
			
			board.get(from).setEmpty();
			board.get(to).setBlack();
			board.get(to).setKing();

			return false;
		}
	
		System.out.println("WE'RE MISSING A CASE");
		return false;
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
	
	public void printBoard(int turn, int number){
		
		if(turn%2==0) System.out.println("\n       Black ");
		if(turn%2==1) System.out.println("\n       Red ");
		System.out.println("       Move " + number);
		System.out.println("       Turn " + turn);
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
