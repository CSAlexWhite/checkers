package edu.cuny.qc.cs363;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GUIController {
	
	@FXML Button s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16;
	@FXML Button s17,s18,s19,s20,s21,s22,s23,s24,s25,s26,s27,s28,s29,s30,s31;
	@FXML Button[] squares;
	Image redPiece;
	Image blackPiece;
	Image wood;
	ImageView redView;
	ImageView blackView;
	ImageView woodView;
	
	public GUIController(){
		
		newButtons();	
		squares = new Button[32];
		
		squares[0] = s0;
		squares[1] = s1;
		squares[2] = s2;
		
		redPiece = new Image(getClass().getResourceAsStream("redpiece2.png"));
		blackPiece = new Image(getClass().getResourceAsStream("blackpiece2.png"));
		wood = new Image(getClass().getResourceAsStream("darksquare.png"));
		
		redView = new ImageView(redPiece);
		woodView = new ImageView(wood);
		blackView = new ImageView(blackPiece);
		
		setPieces();
		
		s0.setGraphic(redView);
		
		System.out.println("Done Instantiating");
	}
	
	public void setPieces(){
		
		System.out.println("Done setting");
		s0.setGraphic(redView);
	}
	
	int count =0;
	
	@FXML public void set1(){
		
		count++;
		
		System.out.println("PRESSED");
		
		//for(int i=0; i<32; i++) squares[i].setGraphic(woodView);
		
		squares[2].setGraphic(woodView);
		
		if(count%3==2) s0.setGraphic(woodView);
		if(count%3==0) s0.setGraphic(redView);
		if(count%3==1) s0.setGraphic(blackView);		
	}
	
	public void newButtons(){
		
		s0 = new Button();
		s1 = new Button();
		s2 = new Button();
		s3 = new Button();
		s4 = new Button();
		s5 = new Button();
		s6 = new Button();
		s7 = new Button();
		s8 = new Button();
		s9 = new Button();
		s10 = new Button();
		s11 = new Button();
		s12 = new Button();
		s13 = new Button();
		s14 = new Button();
		s15 = new Button();
		s16 = new Button();
		s17 = new Button();
		s18 = new Button();
		s19 = new Button();
		s20 = new Button();
		s21 = new Button();
		s22 = new Button();
		s23 = new Button();
		s24 = new Button();
		s25 = new Button();
		s26 = new Button();
		s27 = new Button();
		s28 = new Button();
		s29 = new Button();
		s30 = new Button();	
		s31 = new Button();
	}
}
