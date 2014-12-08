package edu.cuny.qc.cs363;

public abstract class MiniMax {

	public static CheckerBoard minimax_decision(CheckerBoard input){
		
		CheckerBoard temp = input;
		int iteration =0;
		int value = max_value(input, iteration);

		return temp;		
	}
	
	public static int max_value(CheckerBoard input,int iteration){
		
		int value =-100;
		if (input.board.isEmpty() || iteration >=7) // terminal _test ???
			return input.evaluate(); //not sure the Uttility(state)
		else {
			
			input.getChildren(0);
			//------------------???---------------------------------
			// got throught each possible black piece move
			
				value=Math.max(value, min_value(input, iteration++));				
		}
		
		return value;
		
	}
	public static int min_value(CheckerBoard input,int iteration){
		int value = -100;
		// cannot be winning board 
		if (input.possibleMoves==0)// no move move for red piece
			return input.evaluate();
		else {
			input.getChildren(0);
			for (int i=0; i<32 ;i++){ // got throught each possible black piece move
				
				value=Math.max(value, min_value(input, iteration++));		
		     }
		}
		return value;
	}

	private int Utility(CheckerBoard input) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
