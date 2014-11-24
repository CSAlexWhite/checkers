package edu.cuny.qc.cs363;

public class CheckerPiece {
	
	private boolean empty; // whether there is a piece
	private boolean black;  // whether this is my piece or the other's
	private boolean king;   // whether it is a king
	   
	public CheckerPiece(boolean empty, boolean black, boolean king) {
		   
	}
	
	public void setEmpty(){
		
		empty = true;
	}
	
	public void setBlack(){
		
		empty = false;
		black = true;
	}
	
	public void setRed(){
		
		empty = false;
		black = false;	
	}
	
	public void setKing(boolean value){
		
		king = true;
	}
	
	public boolean isKing(){
		
		return king;
	}
	
	public boolean isEmpty(){
		
		return empty;
	}
	
	public boolean isBlack(){
		
		return !empty && black;
	}
	
	public boolean isRed(){
		
		return !empty && !black;
	}
	
	public int numNeighbors(){
		
		if(isKing()) return 4;
		else return 2;
	}
}
