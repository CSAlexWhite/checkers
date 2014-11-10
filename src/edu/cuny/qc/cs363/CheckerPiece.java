package edu.cuny.qc.cs363;

public class CheckerPiece {
	
	private boolean empty; // whether there is a piece
	private boolean black;  // whether this is my piece or the other's
	private boolean king;   // whether it is a king
	   
	public CheckerPiece(boolean empty, boolean black, boolean king) {
		   
	}
	
	public void setEmpty(boolean value){
		
		empty = value;
	}
	
	public void setBlack(boolean value){
		
		black = value;
	}
	
	public void setKing(boolean value){
		
		king = value;
	}
	
	public boolean isKing(){
		
		return king;
	}
	
	public boolean isEmpty(){
		
		return empty;
	}
	
	public boolean isBlack(){
		
		return black;
	}
	
	public boolean isRed(){
		
		return !empty && !black;
	}
}
