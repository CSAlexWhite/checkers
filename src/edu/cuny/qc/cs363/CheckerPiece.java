package edu.cuny.qc.cs363;

public class CheckerPiece {
	
	private boolean empty; // whether there is a piece
	private boolean black;  // whether this is my piece or the other's
	private boolean king;   // whether it is a king
	public char type;  
	public int position;
	
	public CheckerPiece(char type) {
		
		king = false;
		
		this.type = type;
		if(type == ' ') setEmpty();
		
		if(type == 'X') setBlack();
		if(type == 'K'){ setBlack(); setKing();}
		
		if(type == 'O') setRed();
		if(type == 'Q'){ setRed(); setKing();}
	}
	
	public CheckerPiece(CheckerPiece input){
		
		this.empty = input.empty;
		this.black = input.black;
		this.king = input.king;
		this.type = input.type;
	}
	
	public void setEmpty(){
		
		type = ' ';
		empty = true;
	}
	
	public void setBlack(){
		
		type = 'X';
		empty = false;
		black = true;
	}
	
	public void setRed(){
		
		type = 'O';
		empty = false;
		black = false;	
	}
	
	public void setKing(){
		
		if(isRed()){ 
			
			king = true;
			type = 'Q';
		}
		
		if(isBlack()){ 
			
			king = true;
			type = 'K';
		}
		
		//else System.out.println("WRONGG!!!");
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
	
	public void print(){
		
		System.out.println(type);
	}
}
