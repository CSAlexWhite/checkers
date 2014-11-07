package edu.cuny.qc.cs363;

public class CheckerMove {

	int from, to;
	boolean isJump;
	
	public CheckerMove(int from, int to, boolean isJump){
		
		this.from = from;
		this.to = to;
		this.isJump = isJump;
	}
}
