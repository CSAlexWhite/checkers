package edu.cuny.qc.cs363;

public class CheckerPiece {
	
	private boolean empty; 
	private boolean black;  
	private boolean king;   
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
		king = false;
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
	}
	
	public void unKing(){
		
		king = false;
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
	
	public boolean isRedKing(){
		
		return !empty && !black && king;
	}
	
	public boolean isBlackKing(){
		
		return !empty && black && king;
	}
	
	public int numNeighbors(){
		
		if(isKing()) return 4;
		else return 2;
	}
	
	public void print(){
		
		System.out.println(type);
	}
}
