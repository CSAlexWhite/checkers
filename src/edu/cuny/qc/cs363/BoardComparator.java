package edu.cuny.qc.cs363;

import java.util.Comparator;

public class BoardComparator implements Comparator<CheckerBoard> {

	@Override
	public int compare(CheckerBoard board1, CheckerBoard board2) {
	
		if ( board1.evaluate() > board2.evaluate()) return -1;
		if ( board2.evaluate() > board1.evaluate()) return 1;
			
		return 0;
	}

}
