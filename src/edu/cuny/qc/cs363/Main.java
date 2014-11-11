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
		
		static final int[][] ADJACENCE = new int[][]{
		
		/* 00 */{-1,4,-1,-1},
		/* 01 */{4,5,-1,-1},
		/* 02 */{5,6,-1,-1},
		/* 03 */{6,7,-1,-1},
		/* 04 */{8,9,1,0},
		/* 05 */{9,10,2,1},
		/* 06 */{10,11,3,2},
		/* 07 */{11,-1,-1,3},
		/* 08 */{-1,12,4,-1},
		/* 09 */{12,13,5,4},
		/* 10 */{13,14,6,5},
		/* 11 */{14,15,7,6},
		/* 12 */{16,17,9,8},
		/* 13 */{17,18,10,9},
		/* 14 */{18,19,11,10},
		/* 15 */{19,-1,-1,11},
		/* 16 */{-1,20,12,-1},
		/* 17 */{20,21,13,12},
		/* 18 */{21,22,14,13},
		/* 19 */{22,23,15,14},
		/* 20 */{24,25,17,16},
		/* 21 */{25,26,18,17},
		/* 22 */{26,27,19,18},
		/* 23 */{27,-1,-1,19},
		/* 24 */{-1,28,20,-1},
		/* 25 */{28,29,21,20},
		/* 26 */{29,30,22,21},
		/* 27 */{30,31,23,22},
		/* 28 */{-1,-1,25,24},
		/* 29 */{-1,-1,26,25},
		/* 30 */{-1,-1,27,26},
		/* 31 */{-1,-1,-1,27}};	
	}			
	
}
