package edu.cuny.qc.cs363;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("GUI.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public static void main(String[] args) {
		//launch(args);
											//01234567890123456789012345678901	
		//CheckerBoard test = new CheckerBoard("    OO      X O  O        OO    ");
		CheckerBoard test = new CheckerBoard("OOOOOOOOOOOO        XXXXXXXXXXXX");
		//CheckerBoard test = new CheckerBoard("KKKKKKKKKKKKK       QQQQQQQQQQQQ");
		//printPositions();
		//CheckerBoard test = new CheckerBoard("    XXXX                OOOO    ");
		//CheckerBoard test = new CheckerBoard("K                   OO    K     ");
		//CheckerBoard test = new CheckerBoard("XX OO XX OO XX OO XX OO XX OO XX");
		test.printBoard(0,0);
		//test.printPositions();
		//test.getChildren(0);
	}
	
	public static void printPositions(){
		
		int k = 0;
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(i%2 == j%2){ 
					
					System.out.print(k++ + " ");
				}
				else System.out.print("  ");
			}
			
			System.out.println();
		}
	}
	
	public static class globals{
		
		static final int[][] BLACK_ADJACENCE = new int[][]{
		
		/* 00 */{-1,4,-1,-1},
		/* 01 */{4,5,-1,-1},
		/* 02 */{5,6,-1,-1},
		/* 03 */{6,7,-1,-1},
		/* 04 */{8,9,0,1},
		/* 05 */{9,10,1,2},
		/* 06 */{10,11,2,3},
		/* 07 */{11,-1,3,-1},
		/* 08 */{-1,12,-1,4},
		/* 09 */{12,13,4,5},
		/* 10 */{13,14,5,6},
		/* 11 */{14,15,6,7},
		/* 12 */{16,17,8,9},
		/* 13 */{17,18,9,10},
		/* 14 */{18,19,10,11},
		/* 15 */{19,-1,11,-1},
		/* 16 */{-1,20,-1,12},
		/* 17 */{20,21,12,13},
		/* 18 */{21,22,13,14},
		/* 19 */{22,23,14,15},
		/* 20 */{24,25,16,17},
		/* 21 */{25,26,17,18},
		/* 22 */{26,27,18,19},
		/* 23 */{27,-1,19,-1},
		/* 24 */{-1,28,-1,20},
		/* 25 */{28,29,20,21},
		/* 26 */{29,30,21,22},
		/* 27 */{30,31,22,23},
		/* 28 */{-1,-1,24,25},
		/* 29 */{-1,-1,25,26},
		/* 30 */{-1,-1,26,27},
		/* 31 */{-1,-1,27,-1}};		
	
	static final int[][] RED_ADJACENCE = new int[][]{
		
		//CHECKED
		
		/* 00 */{-1,-1,-1,4},
		/* 01 */{-1,-1,4,5},
		/* 02 */{-1,-1,5,6},
		/* 03 */{-1,-1,6,7},
		/* 04 */{0,1,8,9},
		/* 05 */{1,2,9,10},
		/* 06 */{2,3,10,11},
		/* 07 */{3,-1,11,-1},
		/* 08 */{-1,4,-1,12},
		/* 09 */{4,5,12,13},
		/* 10 */{5,6,13,14},
		/* 11 */{6,7,14,15},
		/* 12 */{8,9,16,17},
		/* 13 */{9,10,17,18},
		/* 14 */{10,11,18,19},
		/* 15 */{11,-1,19,-1},
		/* 16 */{-1,12,-1,20},
		/* 17 */{12,13,20,21},
		/* 18 */{13,14,21,22},
		/* 19 */{14,15,22,23},
		/* 20 */{16,17,24,25},
		/* 21 */{17,18,25,26},
		/* 22 */{18,19,26,27},
		/* 23 */{19,-1,27,-1},
		/* 24 */{-1,20,-1,28},
		/* 25 */{20,21,28,29},
		/* 26 */{21,22,29,30},
		/* 27 */{22,23,30,31},
		/* 28 */{24,25,-1,-1},
		/* 29 */{25,26,-1,-1},
		/* 30 */{26,27,-1,-1},
		/* 31 */{27,-1,-1,-1}};					
	}
}
