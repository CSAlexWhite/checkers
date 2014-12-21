package edu.cuny.qc.cs363;

import java.util.ArrayList;
import java.util.Vector;

public class CheckerBoard { 
	
	/************** STATIC VARIABLES *****************/
	
	static int[][] 	black_adjacence,
	 				red_adjacence;
	static int[] center,
				 edge,
				 inneredge,
				 blackPosition,
				 redPosition,
				 boardTopography;
	
	
	static final int[] BLACKPOSITION = new int[] {	6, 6, 6, 6,
													6, 6, 6, 6,
													8, 2, 2, 10,
													12,2, 2, 10,
													12,2, 2, 14,
													16,2, 2, 14,
													16,14,14,18,
													20,20,20,20};
	static final int[] REDPOSITION = new int[] {	
													20,20,20,20,
													18,14,14,16,
													14,2, 2, 16,
													14,2, 2, 12,
													10,2, 2, 12,
													10,2, 2, 8,
													6, 6, 6, 6,
													6, 6, 6, 6};	
	ArrayList<CheckerPiece> board; 
	
	int possibleMoves, 
	boardPlayer, 
	turn, 
	captures,
	miniMax,
	jumpStart;

	boolean capture;
	
	/*********** BOARD VALUE VARIABLES **************/
	
	int toMove, fromMove, jumped;
	Vector<Integer> jumpedPieces;
		
	public CheckerBoard(String inputBoard){
		
		capture = false; 
		captures = 0;
		turn = -1;
		jumped = -1;
		jumpedPieces = new Vector<Integer>();
		board = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++)
			board.add(0, new CheckerPiece(inputBoard.charAt(i)));
		
		black_adjacence = Main.globals.BLACK_ADJACENCE;
		red_adjacence = Main.globals.RED_ADJACENCE;
		center = Main.globals.CENTER;
		edge = Main.globals.EDGE;
		inneredge = Main.globals.INNEREDGE;
		
		boardTopography = new int[32];
	}
	
	public CheckerBoard(ArrayList<CheckerPiece> inputBoard, 
			int turn, boolean capture,
			int from, int to, int player, int value,
			int jumped, Vector<Integer> captures){

		fromMove = from; 
		toMove = to;
		boardPlayer = player;
		this.turn = turn+1; 
		this.capture = capture; 
		this.jumped = jumped;
		
		board = copy(inputBoard);
		
		jumpedPieces = new Vector<Integer>();
		for(int i=0; i<captures.size(); i++) jumpedPieces.add(captures.get(i));
		
		if(jumped != -1) jumpedPieces.add(jumped);
		
		black_adjacence = Main.globals.BLACK_ADJACENCE;
		red_adjacence = Main.globals.RED_ADJACENCE;	
		center = Main.globals.CENTER;
		edge = Main.globals.EDGE;
		inneredge = Main.globals.INNEREDGE;
		
		boardTopography = new int[32];
	}
			
	public Vector<CheckerBoard> getChildren(int player){
		
		Vector<CheckerBoard> movesList = new Vector<CheckerBoard>();		
		if(player == 0){
			
			CheckerPiece targetSquare = null;			
			for(int currentPiece=0; currentPiece<32; currentPiece++){			
				
				if(board.get(currentPiece).isEmpty()) continue; 				
				
				ArrayList<CheckerPiece> newBoard = copy(board);
				
				if(newBoard.get(currentPiece).isBlack()){			 				
										
					for(int neighbor = 0; 										
							neighbor<newBoard.get(currentPiece).numNeighbors(); 
							neighbor++)
					{  	
																										
						int target1 = black_adjacence[currentPiece][neighbor];			
						if(target1 == -1) continue;															
												
						targetSquare = newBoard.get(target1);					
						
						if(targetSquare.isBlack()) continue;																							

						if(targetSquare.isEmpty()){ 							
							
							boolean kinged = false;							
							kinged = move(newBoard, currentPiece, target1, kinged);				
							movesList.add(
									new CheckerBoard(
											newBoard, turn, false, currentPiece, 
											target1, player, evaluate(), -1, jumpedPieces)); 	

							move(newBoard, target1, currentPiece, kinged);							
							continue;
						}
						
						if(targetSquare.isRed()){								
							
							int target2 = 
									black_adjacence[
									black_adjacence[currentPiece][neighbor]][neighbor];
							
							if(target2 != -1){ 
								
								int jump = target2;
								if(newBoard.get(jump).isEmpty()){				
									
									jumpStart = currentPiece;
									boolean kinged = false;
									kinged = move(newBoard, currentPiece, jump, kinged);
									jumpedPieces.add(target1);	
									movesList.add(jump(player, copy(newBoard), movesList, currentPiece, 
											jump, target1, kinged));	
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
			for(int currentPiece=0; currentPiece<32; currentPiece++){			
				
				if(board.get(currentPiece).isEmpty()) continue; 				
				
				ArrayList<CheckerPiece> newBoard = copy(board);
				
				if(newBoard.get(currentPiece).isRed()){			 					
									
					for(int neighbor = 0; 										
							neighbor<newBoard.get(currentPiece).numNeighbors(); 
							neighbor++)
					{  															
						
						int target1 = red_adjacence[currentPiece][neighbor];		
						if(target1 == -1) continue;								
						
						targetSquare = newBoard.get(target1);					
						
						if(targetSquare.isRed()) continue;
						
						if(targetSquare.isEmpty()){ 							
							
							boolean kinged = false;
							kinged = move(newBoard, currentPiece, target1, kinged);				
							movesList.add(
									new CheckerBoard(
											newBoard, turn, false, currentPiece, 
											target1, player, evaluate(), -1, jumpedPieces)); 
							
							move(newBoard, target1, currentPiece, kinged);									
							continue;
						}
						
						if(targetSquare.isBlack()){								
							
							int target2 = red_adjacence[
							              red_adjacence[currentPiece][neighbor]][neighbor];
							
							if(target2 != -1){
														
								int jump = target2;			
								if(newBoard.get(jump).isEmpty()){				
									
									jumpStart = currentPiece;
									boolean kinged = false;
									kinged = move(newBoard, currentPiece, jump, kinged);
									jumpedPieces.add(target1);							
									movesList.add(jump(player, copy(newBoard), movesList, currentPiece, 
											jump, target1, kinged));											
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
	
	private CheckerBoard jump(int player, ArrayList<CheckerPiece> currentBoard, 
			Vector<CheckerBoard> movesList, int previousPiece, int currentPiece, 
			int jumpedSquare, boolean justKinged){ 
		
		int jumpEnd = currentPiece;
		
		currentBoard.get(jumpedSquare).setEmpty();

		if(player == 0){

			int target1, target2;		
			CheckerPiece jumpingSquare, targetSquare;
			
			for(int neighbor=0; 
					neighbor<currentBoard.get(currentPiece).numNeighbors(); 
					neighbor++){
				
				target1 = black_adjacence[currentPiece][neighbor];				
				if(target1 == -1) continue;	
				
				target2 = black_adjacence[target1][neighbor];							
				if(target2 == -1) continue;										
				
				jumpingSquare = currentBoard.get(target1);
				targetSquare = currentBoard.get(target2);						
				
				if(!targetSquare.isEmpty()) continue;																								
				
				if(jumpingSquare.isBlack()) continue;							
			
				if(jumpingSquare.isRed()){										

					int jumpTo = target2;										
					jumpedPieces.add(target1);
					jumpedSquare = target1;
					currentBoard.get(target1).setEmpty();					
					justKinged = move(currentBoard, currentPiece, target2, justKinged);	
					
					if(justKinged) 
						return new CheckerBoard(currentBoard, turn, true, 
							jumpStart, jumpTo, player, evaluate(), target1, jumpedPieces);
					
					jumpEnd = jumpTo;
					
					return(jump(player, currentBoard, movesList, 
							currentPiece, jumpTo, target1, false));					
				}					
			}	
		}
		
		if(player == 1){
			
			int target1, target2;			
			CheckerPiece jumpingSquare, targetSquare;
			
			for(int neighbor=0; 
					neighbor<currentBoard.get(currentPiece).numNeighbors(); 
					neighbor++){
				
				target1 = red_adjacence[currentPiece][neighbor];				
				if(target1 == -1) continue;	
				
				target2 = red_adjacence[target1][neighbor];							
				
				if(target2 == -1) continue;										
				
				jumpingSquare = currentBoard.get(target1);
				targetSquare = currentBoard.get(target2);						
				
				if(!targetSquare.isEmpty()) continue;																										
				
				if(jumpingSquare.isRed()) continue;								
										
				if(jumpingSquare.isBlack()){									
					
					int jumpTo = target2;					
					jumpedPieces.add(target1);
					jumpedSquare = target1;
					currentBoard.get(target1).setEmpty();					
					justKinged = move(currentBoard, currentPiece, target2, justKinged);	
				
					if(justKinged) 
						return new CheckerBoard(currentBoard, turn, true, 
							jumpStart, jumpTo, player, evaluate(), target1, jumpedPieces);

					jumpEnd = jumpTo;
					
					return(jump(player, currentBoard, movesList, 
							currentPiece, jumpTo, target1, false));	
				}					
			}		
		}
		
		return new CheckerBoard(currentBoard, turn, true, 
				jumpStart, jumpEnd, player, evaluate(), jumpedSquare, jumpedPieces);
	}
	
	private boolean move(
			ArrayList<CheckerPiece> board, int from, int to, boolean justKinged){
			
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
		for(int i=0; i<32; i++){
			
			if(board.get(i).isRed()) red++;
			if(board.get(i).isBlack()) black++;
			if(board.get(i).isEmpty()) continue;
		}
		
		if(red == 0) return true;	
		if(black == 0) return true;
		
		return false;
	}
	
	public void printArray(ArrayList<CheckerPiece> input){
		
		for(int i=0; i<32; i++) System.out.println(i + "\t" + input.get(i).type);
		System.out.println();
	}
		
	public int evaluate(){
		 
		int kingValue = 50,
		pawnValue = 10,
		positionWeight = 5,		// minimize the weights I have to come up with
		victory = 10000,
		
		redKings = 0, 
		blackKings = 0, 
		redPawns = 0, 
		blackPawns = 0,
		redPieces = 0,
		blackPieces = 0,
		totalPieces = 0;
		
		int value = 0;
		int[] zones = new int[32];
		
		/********************* PIECE INFORMATION ******************************/
		
		int redValue = 0, blackValue = 0;
		
		for(int i=0; i<32; i++){
					
			if(board.get(i).isBlack() && !board.get(i).isKing()) blackPawns++;							
			if(board.get(i).isBlack() && board.get(i).isKing())	blackKings++;
			if(board.get(i).isBlack()) blackPieces++;
			
			if(board.get(i).isRed() && !board.get(i).isKing()) redPawns++;				
			if(board.get(i).isRed() && board.get(i).isKing()) redKings++;;	
			if(board.get(i).isRed()) redPieces++;
		}
		
		totalPieces = blackPieces + redPieces;
				
		try{
			
			blackValue = (blackKings*kingValue)/(blackKings+blackPawns+1) + 
						 (blackPawns*pawnValue)/(blackKings+blackPawns+1);
			
			redValue = 	 (redKings*kingValue)/(redKings+blackPawns+1) + 
						 (redPawns*pawnValue)/(redKings+redPawns+1);
			
			if(blackValue == 0 && boardPlayer == 1) value += victory;
			if(redValue == 0 && boardPlayer == 0) value += victory;
			if(blackValue == 0 && boardPlayer == 0) value -= victory;
			if(redValue == 0 && boardPlayer == 1) value -= victory;
			
			if(boardPlayer == 0) value = redValue - blackValue;
			if(boardPlayer == 1) value = blackValue - redValue;
		}
		
		catch(ArithmeticException ae){}
		
						
		/********************* EXTENUATING CIRCUMSTANCES **********************/
				
		
		int adjustment = (int) (Math.abs(blackPieces - redPieces) * (5 - Math.log(totalPieces)));

		if(blackPieces > redPieces){
				
			for(int i=0; i<8; i++){
					
				BLACKPOSITION[center[i]] += adjustment;
				REDPOSITION[center[i]] -= adjustment;
			}
				
			for(int i=0; i<14; i++){
					
				BLACKPOSITION[edge[i]] -= adjustment;
				REDPOSITION[edge[i]] += adjustment;
			}
			
			for(int i=0; i<10; i++){
				
				BLACKPOSITION[inneredge[i]] -= adjustment;
				REDPOSITION[inneredge[i]] += adjustment;
			}
		}
			
		if(redPieces > blackPieces){
			
			for(int i=0; i<14; i++){
				
				BLACKPOSITION[edge[i]] -= adjustment;
				REDPOSITION[edge[i]] += adjustment;
			}
			
			for(int i=0; i<8; i++){
				
				BLACKPOSITION[center[i]] += adjustment;
				REDPOSITION[center[i]] -= adjustment;
			}
			
			for(int i=0; i<10; i++){
				
				BLACKPOSITION[inneredge[i]] -= adjustment;
				REDPOSITION[inneredge[i]] += adjustment;
			}
		}
			
		/************************** BOARD TOPOGRAPHY  *************************/
		
		try{
			
		if (this.boardPlayer == 0){
		
			for (int i = 0; i < 32; i++){
    	  
				if (board.get(i).isRed() && !board.get(i).isRedKing()) {
        	
					for (int j = 0; j < 2; j++) {
						
		        		if (red_adjacence[i][j] == -1) continue;
						if (red_adjacence[i][j] != -1) 
							zones[red_adjacence[i][j]] -= 2;
						
						if (red_adjacence[red_adjacence[i][j]][j] != -1){
			                zones[red_adjacence[red_adjacence[i][j]][j]] += 2;
			                zones[red_adjacence[red_adjacence[i][j]][((j + 1) % 4)]] += 2;
						}
					}
				}
			
		        if (board.get(i).isRedKing()) {
		        	
		        	for (int j = 0; j < 4; j++) {
		        		
		        		if (red_adjacence[i][j] == -1) continue;
		        		if (red_adjacence[i][j] != -1)		        			
		        			zones[red_adjacence[i][j]] -= 3;
		        		
		        		if (red_adjacence[red_adjacence[i][j]][j] != -1){
		        			
			                zones[red_adjacence[red_adjacence[i][j]][j]] += 3;
			                zones[red_adjacence[red_adjacence[i][j]][((j + 1) % 4)]] += 3;
			            }
		            }
		        }

		        if (board.get(i).isBlack() && !board.get(i).isBlackKing()) {
		        	
		        	for (int j = 0; j < 4; j++) {
		        		
		        		if (red_adjacence[i][j] == -1) continue;
			            if (red_adjacence[i][j] != -1) 
			            	zones[red_adjacence[i][j]] += 2;
			            
			            
			            if (red_adjacence[red_adjacence[i][j]][j] != -1){
		            	  
			            	zones[red_adjacence[red_adjacence[i][j]][j]] -= 2;
			            	zones[red_adjacence[red_adjacence[i][j]][((j + 1) % 4)]] -= 2;
			            }	
			        }
			    }
		        
		        if (board.get(i).isBlackKing()) {
		        	
		        	for (int j = 0; j < 4; j++) {
		        		
		        		if (red_adjacence[i][j] == -1) continue;
		        		if (red_adjacence[i][j] != -1)
		        			zones[red_adjacence[i][j]] += 3;
		        		
		        		if (red_adjacence[red_adjacence[i][j]][j] != -1){
		        			
			                zones[red_adjacence[red_adjacence[i][j]][j]] -= 3;
			                zones[red_adjacence[red_adjacence[i][j]][((j + 1) % 4)]] -= 3;
		        		}
		            }
		        }		        
			}
			
			for (int i = 0; i < 32; i++) zones[i] *= BLACKPOSITION[i];	      
	    }
		
	    if (this.boardPlayer == 1){
	    	
	    	for (int i = 0; i < 32; i++){
	    	  
	    		if (board.get(i).isBlack() && board.get(i).isBlackKing()) {
	    			
	    			for (int j = 0; j < 2; j++) {
	    				
	    				if (black_adjacence[i][j] == -1) continue;
	    				if (black_adjacence[i][j] != -1)
	    					zones[black_adjacence[i][j]] -= 2;
	    				
	    				if (black_adjacence[black_adjacence[i][j]][j] != -1){
	    					
			                zones[black_adjacence[black_adjacence[i][j]][j]] += 2;
			                zones[black_adjacence[black_adjacence[i][j]][((j + 1) % 4)]] += 2;
	    				}
	    			}
	    		}
	        	    	
		        if (board.get(i).isBlackKing()) {
		        	
		        	for (int j = 0; j < 4; j++) {
		        		
		        		if (black_adjacence[i][j] == -1) continue;
		        		if (black_adjacence[i][j] != -1)
		        			zones[black_adjacence[i][j]] -= 2;
		        		
		        		if (black_adjacence[black_adjacence[i][j]][j] != -1){
	            	  
			                zones[black_adjacence[black_adjacence[i][j]][j]] += 2;
			                zones[black_adjacence[black_adjacence[i][j]][((j + 1) % 4)]] += 2;
		        		}
		            }	          	
		        }
		        
		        if (board.get(i).isRed() && board.get(i).isRedKing()) {
		        	
		        	for (int j = 0; j < 4; j++) {
		        		
		        		if (black_adjacence[i][j] == -1) continue;
		        		if (black_adjacence[i][j] != -1)
		        			zones[black_adjacence[i][j]] += 2;
		        		
		        		if (black_adjacence[black_adjacence[i][j]][j] != -1){
		            	  
		        			zones[black_adjacence[black_adjacence[i][j]][j]] -= 2;
		        			zones[black_adjacence[black_adjacence[i][j]][((j + 1) % 4)]] -= 2;
		        		}
		            }
		        }
		        	    	
		        if (board.get(i).isRedKing()){
		        	
		        	for (int j = 0; j < 4; j++) {
		        		
		        		if (black_adjacence[i][j] == -1) continue;
		        		if (black_adjacence[i][j] != -1)
		        			zones[black_adjacence[i][j]] += 3;
		        		
		        		if (black_adjacence[black_adjacence[i][j]][j] != -1){
		            	  
			                zones[black_adjacence[black_adjacence[i][j]][j]] -= 3;
			                zones[black_adjacence[black_adjacence[i][j]][((j + 1) % 4)]] -= 3;
		        		}
		            }
		        }		        
		    }
	      
	    	for (int i = 0; i < 32; i++) zones[i] *= REDPOSITION[i];      
	    }}
		
		catch(IndexOutOfBoundsException ioobe){}
		catch(NullPointerException npe){}  
		
		boardTopography = zones;
	    
		/********************* POSITION INFORMATION ***************************/
		
		if(boardPlayer == 0) value += BLACKPOSITION[toMove] * positionWeight;
		if(boardPlayer == 1) value += REDPOSITION[toMove] * positionWeight;
	   
	        
	    value += zones[toMove] * positionWeight;	
		
		value += 100 * jumpedPieces.size();
		
		return value;
	}
	
	public void setMiniMax(int value){
		
		miniMax = value;
	}
	
	public ArrayList<CheckerPiece> copy(ArrayList<CheckerPiece> inBoard){
		
		ArrayList<CheckerPiece> toReturn = new ArrayList<CheckerPiece>();
		for(int i=0; i<32; i++) toReturn.add(new CheckerPiece(inBoard.get(i)));
		
		return toReturn;
	}
	
	public void printTopography(){
		
		evaluate();
		
		int position = 0;
		System.out.println(this.boardPlayer);
		
		for (int i = 0; i < 8; i++){
			
			for (int j = 0; j < 8; j++) {
				
				if (i % 2 != j % 2){ 
					
					System.out.print(boardTopography[(position++)]/100000 + ",");
				}
				
				else System.out.print("0,");
			}	System.out.println();
		}		System.out.println("\n");						
	}
	
	public void printBoard(){//, int number){

		System.out.println("  ---------------");
		int position = 0;
		for(int i=0; i<8; i++){
			
			System.out.print("| ");			
			for(int j=0; j<8; j++){
				
				if(i%2 != j%2){ 
					
					System.out.print(board.get(position++).type + " ");
				}	else System.out.print("  ");			
			}		System.out.println("|");
		}			System.out.println("  ---------------");
	}
}
