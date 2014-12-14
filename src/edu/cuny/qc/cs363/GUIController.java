package edu.cuny.qc.cs363;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GUIController implements Initializable, Runnable{
	
	public static GUIController controller = new GUIController();
	
	@FXML Button s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16;
	@FXML Button s17,s18,s19,s20,s21,s22,s23,s24,s25,s26,s27,s28,s29,s30,s31;
	@FXML Button[] squares;
	Image redPiece, blackPiece, redKing, blackKing, wood;
	ImageView[] redView, blackView, rKingView, bKingView, woodView;
	
	public GUIController(){
		

	}
	
	
	int count =0;
	
	@FXML public void piecePress(){
		
		System.out.println();
	}
	
	@FXML public void set1(){
		
		count++;
		
		System.out.println("PRESSED");
		
		//for(int i=0; i<32; i++) squares[i].setGraphic(woodView);
		
		//squares[2].setGraphic(woodView);
		
		if(count%3==2) squares[23].setGraphic(woodView[23]);
		if(count%3==0) squares[23].setGraphic(redView[23]);
		if(count%3==1) squares[23].setGraphic(blackView[23]);		
	}
	
	public void setup(CheckerBoard currentBoard) throws NullPointerException{
		
		for(int i=0; i<32; i++){
			
			if(currentBoard.board.get(i).isBlack()) squares[i].setGraphic(blackView[i]);
			if(currentBoard.board.get(i).isRed()) squares[i].setGraphic(redView[i]);
			if(currentBoard.board.get(i).isEmpty()) squares[i].setGraphic(woodView[i]);
			if(currentBoard.board.get(i).isBlackKing()) squares[i].setGraphic(bKingView[i]);
			if(currentBoard.board.get(i).isRedKing()) squares[i].setGraphic(rKingView[i]);
		}
		
//		for(int i=0; i<32; i++) squares[i].setGraphic(woodView[i]);
//		squares[2].setGraphic(redView[2]);
	}
	
//	public void begin(){
//		
//		Game checkerGame = new Game();
//		checkerGame.thisThread.start();
//	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//controller
		
		squares = new Button[]{s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21,s22,s23,s24,s25,s26,s27,s28,s29,s30,s31};
		
		//squares[0].setGraphic(woodView);
		redPiece = new Image(getClass().getResourceAsStream("redpiece2.png"));
		blackPiece = new Image(getClass().getResourceAsStream("blackpiece2.png"));
		wood = new Image(getClass().getResourceAsStream("darksquare.png"));
		redKing = new Image(getClass().getResourceAsStream("redking.png"));
		blackKing = new Image(getClass().getResourceAsStream("blackking.png"));
		
		redView = new ImageView[32];
		woodView = new ImageView[32];
		blackView = new ImageView[32];
		rKingView = new ImageView[32];
		bKingView = new ImageView[32];
		
		for(int i=0; i<32; i++){
			
			redView[i] = new ImageView(redPiece);
			blackView[i] = new ImageView(blackPiece);
			woodView[i] = new ImageView(wood);
			rKingView[i] = new ImageView(redKing);
			bKingView[i] = new ImageView(blackKing);
		}
		
		//for(int i=0; i<32; i++) squares[i].setGraphic(woodView);
		//setPieces();
		//squares[1].setGraphic(woodView);
		
		//s2 = new Button("", redView)
		//set1();
		//s0.setGraphic(redView);
		
		System.out.println("Done Instantiating");
		//s2 = new Button("", redView);
		//instance = this;		
	}

	@Override
	public void run() {
		
		
		
	}
	
	
}
