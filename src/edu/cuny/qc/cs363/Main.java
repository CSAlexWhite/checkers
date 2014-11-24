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
		
		/* 00 */{4,5,-1,-1},
		/* 01 */{5,6,-1,-1},
		/* 02 */{6,7,-1,-1},
		/* 03 */{7,-1,-1,-1},
		/* 04 */{-1,8,0,-1},
		/* 05 */{8,9,1,0},
		/* 06 */{9,10,2,1},
		/* 07 */{10,11,3,2},
		/* 08 */{12,13,5,4},
		/* 09 */{13,14,6,5},
		/* 10 */{14,15,7,6},
		/* 11 */{15,-1,-1,7},
		/* 12 */{-1,16,8,-1},
		/* 13 */{16,17,9,8},
		/* 14 */{17,18,10,9},
		/* 15 */{18,19,11,10},
		/* 16 */{20,21,13,12},
		/* 17 */{21,22,14,13},
		/* 18 */{22,23,15,14},
		/* 19 */{23,-1,-1,15},
		/* 20 */{-1,24,16,-1},
		/* 21 */{24,25,17,16},
		/* 22 */{25,26,18,17},
		/* 23 */{26,27,19,18},
		/* 24 */{28,29,21,20},
		/* 25 */{29,30,22,21},
		/* 26 */{30,31,23,22},
		/* 27 */{31,-1,-1,23},
		/* 28 */{-1,-1,24,-1},
		/* 29 */{-1,-1,25,24},
		/* 30 */{-1,-1,26,25},
		/* 31 */{-1,-1,27,26}};	
	}			
	
}
