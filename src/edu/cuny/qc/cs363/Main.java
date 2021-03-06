package edu.cuny.qc.cs363;
	
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	static Player player1;
	static Player player2;
	static Game game;
	static GUIController gameBoard;
	static boolean gameOver;
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			/*
			 * This section instantiates the GUI and sets the correct variables.
			 */			
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("GUI.fxml"));
									
			GridPane root = fxmlloader.load();//(GridPane)FXMLLoader.load(getClass().getResource("GUI.fxml"));
			
			Scene scene = new Scene(root,620,620);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			game = new Game();
			gameBoard = (GUIController) fxmlloader.getController();
			gameBoard.setup(game.currentBoard);
			
			/*
			 * This section runs the game loop and synchronizes display with
			 * the GUI.
			 */
			Task task = new Task<Void>(){
				
				@Override
				public Void call() throws Exception {
					
					while(!gameOver){
						
						Platform.runLater(new Runnable(){
							
							@Override
							public void run(){
								
								try{gameBoard.setup(game.nextBoard());}
								catch(NullPointerException npe){
									
									setGameOver();
									System.out.println("GAME OVER");
									game.printHistory();
								}
								catch(IllegalArgumentException iae){
									
									setGameOver();
									System.out.println("GAME OVER");
									game.printHistory();
								}
								catch(IndexOutOfBoundsException ioobe){
									
									setGameOver();
									System.out.println("GAME OVER");
									game.printHistory();
								}
							}
						});

						Thread.sleep(500);
					}
					
					return null;		
				}
			};
			
			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();			

		} catch(Exception e) {e.printStackTrace();}
	}
		
	public static void setGameOver(){
		
		gameOver = true;
	}

	public static void main(String[] args) throws IOException {
		
		launch(args);	
	}
	
	/*
	 * The global variables are shared mainly with the CheckerBoard class.
	 */
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
	
		static final int[] CENTER = new int[] {9,10,13,14,17,18,21,22};
		static final int[] EDGE = new int[] {0,1,2,3,7,15,23,31,30,29,28,24,16,8};
		static final int[] INNEREDGE = new int[] {4,5,6,11,19,27,26,25,20,12};
	}	
}
