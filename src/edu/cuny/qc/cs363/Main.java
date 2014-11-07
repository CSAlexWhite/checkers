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
		launch(args);
	}
	
	public static class globals{
		
		static final int[][] NORMAL_ADJACENCE = new int[][]{

		/* 00 */{4,-1},
		/* 01 */{4,5,-1},
		/* 02 */{5,6,-1},
		/* 03 */{6,7,-1},
		/* 04 */{8,9,-1},
		/* 05 */{9,10,-1},
		/* 06 */{10,11,-1},
		/* 07 */{11,-1},
		/* 08 */{12,-1},
		/* 09 */{12,13,-1},
		/* 00 */{13,14,-1},
		/* 11 */{14,15,-1},
		/* 12 */{16,17,-1},
		/* 13 */{17,18,-1},
		/* 14 */{18,19,-1},
		/* 15 */{19,-1},
		/* 16 */{20,-1},
		/* 17 */{20,21,-1},
		/* 18 */{21,22,-1},
		/* 19 */{22,23,-1},
		/* 20 */{24,25,-1},
		/* 21 */{25,26,-1},
		/* 22 */{26,27,-1},
		/* 23 */{27,-1},
		/* 24 */{28,-1},
		/* 25 */{28,29,-1},
		/* 26 */{29,30,-1},
		/* 27 */{30,31,-1},
		/* 28 */{31,-1},
		/* 29 */{-1},
		/* 30 */{-1},
		/* 31 */{-1}};
		
		static final int[][] KING_ADJACENCE = new int[][]{
			
		/* 00 */{4,-1},				// DOES THIS ORDER MATTER?
		/* 01 */{4,5,-1},
		/* 02 */{5,6,-1},
		/* 03 */{6,7,-1},
		/* 04 */{0,1,8,9,-1},
		/* 05 */{1,2,9,10,-1},
		/* 06 */{2,3,10,11,-1},
		/* 07 */{3,11,-1},
		/* 08 */{4,12,-1},
		/* 09 */{4,5,12,13,-1},
		/* 00 */{5,6,13,14,-1},
		/* 11 */{6,7,14,15,-1},
		/* 12 */{8,9,16,17,-1},
		/* 13 */{9,10,17,18,-1},
		/* 14 */{10,11,18,19,-1},
		/* 15 */{11,19,-1},
		/* 16 */{12,20,-1},
		/* 17 */{12,13,20,21,-1},
		/* 18 */{13,14,21,22,-1},
		/* 19 */{14,15,22,23,-1},
		/* 20 */{16,17,24,25,-1},
		/* 21 */{17,18,25,26,-1},
		/* 22 */{18,19,26,27,-1},
		/* 23 */{19,27,-1},
		/* 24 */{20,28,-1},
		/* 25 */{20,21,28,29,-1},
		/* 26 */{21,22,29,30,-1},
		/* 27 */{22,23,30,31,-1},
		/* 28 */{24,25,-1},
		/* 29 */{25,26,-1},
		/* 30 */{26,27,-1},
		/* 31 */{27,-1}};			
	}
	
	// MARGINAL CASES FOR THE BOARD (CAN ONLY MOVE RIGHT OR LEFT)
	static final int[] LEFTIES = new int[]{0,8,16,24};	
	static final int[] RIGHTIES = new int[]{7,15,23,31};
	
	static final int[][] START_BOARD = new int[][]{
		
				
	
}
