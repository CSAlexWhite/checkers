package edu.cuny.qc.cs363;

import java.util.ArrayList;
import java.util.Vector;

public class CheckerBoard { 
	
	/************** STATIC VARIABLES *****************/
	
	static int[][] black_adjacence;
	static int[][] red_adjacence;
	static int[] center;
	static int[] edge;
	
	ArrayList<CheckerPiece> board; 
	
	int possibleMoves, myValue, boardPlayer, turn, jumpFrom;
	boolean capture;
	
	int redKings = 0, blackKings = 0, redPieces = 0, blackPieces = 0;
	int boardValue;
	
	String moveFromLast, tempMove;
	
	/*********** BOARD VALUE VARIABLES **************/
	
	int toMove, fromMove;
	
	/**
	 * @param key
	 */
	public CheckerBoard(String key){
		
		boardValue = 0;
		printPositions();
		capture = false;
		moveFromLast = "Opening Board";
		turn = -1;
		board = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++)
			board.add(0, new CheckerPiece(key.charAt(i)));
		
		black_adjacence = Main.globals.BLACK_ADJACENCE;
		red_adjacence = Main.globals.RED_ADJACENCE;
		center = Main.globals.CENTER;
		edge = Main.globals.EDGE;
	}
	
	public CheckerBoard(ArrayList<CheckerPiece> inputBoard, 
						int turn, String move, boolean capture,
						int from, int to, int player, int value){
		
		boardValue = value;
		fromMove = from; toMove = to;
		boardPlayer = player;
		this.turn = turn+1; this.capture = capture;
		moveFromLast = move;
		board = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++) board.add(new CheckerPiece(inputBoard.get(i)));
		
		black_adjacence = Main.globals.BLACK_ADJACENCE;
		red_adjacence = Main.globals.RED_ADJACENCE;	
		center = Main.globals.CENTER;
		edge = Main.globals.EDGE;
	}
			
	public Vector<CheckerBoard> getChildren(int player){
		
		Vector<CheckerBoard> movesList = new Vector<CheckerBoard>();		
		if(player == 0){
			
			CheckerPiece targetSquare = null;			
			for(int currentPiece=0; currentPiece<32; currentPiece++){			// FOR EACH SQUARE ON THE BOARD
				
				if(board.get(currentPiece).isEmpty()) continue; 				// IF THE SPACE IS EMPTY, KEEP GOING
				
				ArrayList<CheckerPiece> newBoard = new ArrayList<CheckerPiece>(32);	// CLONE THE CURRENT BOARD				
				for(int square=0; square<32; square++) newBoard.add(new CheckerPiece(board.get(square)));
				
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
							tempMove = "Black moved from " + currentPiece + " to " + target1;
							
							kinged = move(newBoard, currentPiece, target1, false);				// SWAP PIECES
							movesList.add(new CheckerBoard(newBoard, turn, tempMove, false, currentPiece, target1, player, evaluate())); 	// RECORD THE MOVE
							
//							System.out.println("Moving Back");
							move(newBoard, target1, currentPiece, kinged);				// SWAP THEM BACK						
							continue;
						}
																 
						if(targetSquare.isRed()){								// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
	
							int target2 = black_adjacence[black_adjacence[currentPiece][neighbor]][neighbor];
							if(target2 != -1){ 
								
								int jump = target2;
								if(newBoard.get(jump).isEmpty()){				// AND THE PIECE ACROSS IS EMPTY
									
									jumpFrom = currentPiece;
									boolean kinged = false;
									kinged = move(newBoard, currentPiece, jump, false);
									//move(newBoard, jump, currentPiece, kinged);
									tempMove = "Black captured " + target1;
									newBoard.get(target1).setEmpty();			// REMOVE RED PIECE, TRACK CAPTURED PIECES?
									jump(player, newBoard, movesList, currentPiece, jump, kinged);	// JUMP!
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
				for(int square=0; square<32; square++) newBoard.add(new CheckerPiece(board.get(square)));
				
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
							movesList.add(new CheckerBoard(newBoard, turn, tempMove, false, currentPiece, target1, player, evaluate())); 	// RECORD THE MOVE							
		//					System.out.println("Moving Back");
							move(newBoard, target1, currentPiece, kinged);				// SWAP THEM BACK						
							continue;
						}
																 
						if(targetSquare.isBlack()){								// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
		
							int target2 = red_adjacence[red_adjacence[currentPiece][neighbor]][neighbor];
							if(target2 != -1){
														
								int jump = target2;			
								if(newBoard.get(jump).isEmpty()){				// AND THE PIECE ACROSS IS EMPTY
									
									jumpFrom = currentPiece;
									boolean kinged = false;
									kinged = move(newBoard, currentPiece, jump, false);
									//move(newBoard, jump, currentPiece, kinged);
									tempMove = "Red captured " + target1;
									newBoard.get(target1).setEmpty();			// REMOVE RED PIECE, TRACK CAPTURED PIECES									
									jump(player, newBoard, movesList, currentPiece, jump, kinged);				// JUMP!
								}
							}
							
							else continue;
						}
					} // FOR NEIGHBORS		
				} // IF OUR PIECE 
			} // FOR ALL SQUARES
		}	
		
		possibleMoves = movesList.size();
		return movesList;
	}
	
	public void jump(int player, ArrayList<CheckerPiece> newBoard, Vector<CheckerBoard> movesList, int previousPiece, int currentPiece, boolean justKinged){ 
		
		//if(justKinged) return;
		
//		System.out.println("Jumping!");
		if(player == 0){

			int target1, target2;		
			CheckerPiece jumpingSquare, targetSquare;
			
			ArrayList<CheckerPiece> currentBoard = new ArrayList<CheckerPiece>(32);	// CLONE THE CURRENT BOARD				
			for(int square=0; square<32; square++) currentBoard.add(new CheckerPiece(newBoard.get(square)));

			tempMove += " by jumping from " + jumpFrom + " to " + currentPiece;
			
			for(int neighbor=0; neighbor<currentBoard.get(currentPiece).numNeighbors(); neighbor++){
				
				target1 = black_adjacence[currentPiece][neighbor];				// TARGET1 IS THE IMMEDIATE NEIGHBOR
				if(target1 == -1) continue;	
				
				target2 = black_adjacence[target1][neighbor];					// TARGET2 IS THE NEIGHBORS'S NEIGHBOR			
				if(target2 == -1) continue;										// KEEP GOING WHEN YOU CAN'T MOVE THERE
				
				jumpingSquare = currentBoard.get(target1);
				targetSquare = currentBoard.get(target2);						// LOOK AT AN AVAILABLE MOVE
				
				if(!targetSquare.isEmpty()) continue;							// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
				
				if(jumpingSquare.isBlack()) continue;							// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
			
				if(jumpingSquare.isRed()){										// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE

					int jump = target2;										
					tempMove += " then captured " + target1;
					currentBoard.get(target1).setEmpty();					// CAPTURE THE RED PIECE
					justKinged = move(currentBoard, currentPiece, target2, justKinged);													// SET THE CURRENT PIECE ACROSS THE RED PIECE
					jump(player, currentBoard, movesList, currentPiece, jump, justKinged);			// JUMP
				}					
			}	
			movesList.add(new CheckerBoard(currentBoard, turn, tempMove, true, previousPiece, currentPiece, player, evaluate()));				// BOARD WITH THIS CAPTURE GOES ON THE LIST			
			move(currentBoard, currentPiece, previousPiece, justKinged);
			
			return;
		}
		
		if(player == 1){
			
			int target1, target2;			
			CheckerPiece jumpingSquare, targetSquare;
			
			ArrayList<CheckerPiece> currentBoard = new ArrayList<CheckerPiece>(32);	// CLONE THE CURRENT BOARD				
			for(int square=0; square<32; square++) currentBoard.add(new CheckerPiece(newBoard.get(square)));
			
			tempMove += " by jumping from " + jumpFrom + " to " + currentPiece;
			movesList.add(new CheckerBoard(currentBoard, turn, tempMove, true, previousPiece, currentPiece, player, evaluate()));				// BOARD WITH THIS CAPTURE GOES ON THE LIST
			
			for(int neighbor=0; neighbor<currentBoard.get(currentPiece).numNeighbors(); neighbor++){
				
				target1 = red_adjacence[currentPiece][neighbor];				// TARGET1 IS THE IMMEDIATE NEIGHBOR
				if(target1 == -1) continue;	
				
				target2 = red_adjacence[target1][neighbor];						// TARGET2 IS THE NEIGHBORS'S NEIGHBOR	
				
				if(target2 == -1) continue;										// KEEP GOING WHEN YOU CAN'T MOVE THERE
				
				jumpingSquare = currentBoard.get(target1);
				targetSquare = currentBoard.get(target2);						// LOOK AT AN AVAILABLE MOVE
				
				if(!targetSquare.isEmpty()) continue;							// IF OUR PIECE IS IN THE WAY																				// IGNORE THIS SPACE KEEP GOING
				
				if(jumpingSquare.isRed()) continue;								// IF SPACE IS EMPTY AND WE'RE NOT JUMPING
										
				if(jumpingSquare.isBlack()){									// IF THE TARGET SQUARE IS OCCUPIED BY A RED PIECE
					
					int jump = target2;					
					//TODO Jumping through the king zone not working
					tempMove += " then captured " + target1;
					currentBoard.get(target1).setEmpty();					// CAPTURE THE RED PIECE
					justKinged = move(currentBoard, currentPiece, target2, justKinged);																	// SET THE CURRENT PIECE ACROSS THE RED PIECE
					jump(player, currentBoard, movesList, currentPiece, jump, justKinged);	// JUMP
				}					
			}	
			
			move(currentBoard, currentPiece, previousPiece, justKinged);
			
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
	
	public boolean gameOver(){
		
		int red = 0, black = 0;
		/* COUNT THE REDS AND WHITES*/
		for(int i=0; i<32; i++){
			
			if(board.get(i).isRed()) red++;
			if(board.get(i).isBlack()) black++;
			if(board.get(i).isEmpty()) continue;
		}
		
		if(red == 0){
			//System.out.println("BLACK WINS");
			return true;
		}
		
		if(black == 0){
			//System.out.println("RED WINS");
			return true;
		}
		
		return false;
	}
	
	public void printArray(ArrayList<CheckerPiece> input){
		
		for(int i=0; i<32; i++) System.out.println(i + "\t" + input.get(i).type);
		System.out.println();
	}
	
	public void printPositions(){
		
//		int k = 0;
//		for(int i=0; i<8; i++){
//			for(int j=0; j<8; j++){
//				if(i%2 == j%2){ 
//					
//					System.out.print(k++ + " ");
//				}
//				else System.out.print("  ");
//			}
//			
//			System.out.println();
//		}
	}
	
	public int evaluate(){
		
		int kingValue = 10;
		int pawnValue = 2;
		int centeringMove = 10;
		int backRow = 10;
		
		int value = 0;
		
		/********************* PIECE INFORMATION ******************************/
		
		int redValue = 0, blackValue = 0;
		
		for(int i=0; i<32; i++){
			
			if(board.get(i).isBlack() && !board.get(i).isKing()) blackPieces += 1;							
			if(board.get(i).isBlack() && board.get(i).isKing())	blackKings += 1;
			
			if(board.get(i).isRed() && !board.get(i).isKing()) redPieces += 1;				
			if(board.get(i).isRed() && board.get(i).isKing()) redKings += 1;		
		}
				
		try{
			
			blackValue = (blackKings*kingValue)/(blackKings+blackPieces+1) + (blackPieces*pawnValue)/(blackKings+blackPieces+1);
			redValue = (redKings*kingValue)/(redKings+blackPieces+1) + (redPieces*pawnValue)/(redKings+redPieces+1);
			
			if(boardPlayer == 0) value = redValue - blackValue;
			if(boardPlayer == 1) value = blackValue - redValue;
		}
		
		catch(ArithmeticException ae){}
		
					
		/********************* POSITION INFORMATION ***************************/
		
		/* MOVING TOWARD THE CENTER IS GOOD, OUT OF THE CENTER BAD */
		for(int i=0; i<8; i++){ 
			
			if(center[i] != fromMove && center[i] == toMove)
				value += centeringMove;
			
			if(center[i] == fromMove && center[i] != toMove)
				value -= centeringMove;
		}
		
		/* TOWARD THE EDGE, BAD */
		for(int i=0; i<14; i++){
			
			if(edge[i] == toMove) value -= centeringMove;
		}
		
		/* LEAVING THE BACK ROW IS BAD */
		int count = 0;
		for(int i=28; i<32; i++){
						
			if(boardPlayer == 0) if(board.get(i).isEmpty()) count++;
			if(boardPlayer == 1) if(board.get(i-28).isEmpty()) count++;
		}
		
		value -= count * backRow;
		
		/* MOVING FORWARD IS GOOD OTHERWISE */
		
		if(boardPlayer == 0) value += toMove - fromMove;
		if(boardPlayer == 1) value += fromMove - toMove;
		
		/********************* EXTENUATING CIRCUMSTANCES **********************/
		
		/* ADD 1000 AND ENTER A DIFFERENT CLASS OF INSTRUCTIONS */
		
		/* TRYING TO DRAW */		
		
		/* TRYING TO CORNER OPPONENT'S LAST PIECE*/
				
		/* BLOCKING IN IF POSSIBLE */
		
		/* MOVING TOWARD THE CENTER IN GROUPS WHEN FEW PIECES ARE LEFT */

		return value;
	}
	
	public void printBoard(int turn){//, int number){

		System.out.println("\nTurn " + turn);//  + " Choice " + number);
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
