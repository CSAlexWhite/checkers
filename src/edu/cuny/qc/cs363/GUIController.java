package edu.cuny.qc.cs363;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * The GUI Controller just handles the initialization of the board given the FXML
 * graph that it finds according to what's specified in the Main class.
 */
public class GUIController implements Initializable{
	
	public static GUIController controller = new GUIController();
	
	@FXML Button s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16;
	@FXML Button s17,s18,s19,s20,s21,s22,s23,s24,s25,s26,s27,s28,s29,s30,s31;
	@FXML Button[] squares;
	Image redPiece, blackPiece, redKing, blackKing, wood;
	ImageView[] redView, blackView, rKingView, bKingView, woodView;
	
	int count =0;
	
	@FXML public void piecePress(){}	
	@FXML public void set1(){}
	
	public void setup(CheckerBoard currentBoard) throws NullPointerException{
		
		for(int i=0; i<32; i++){
			
			if(currentBoard.board.get(i).isBlack()) squares[i].setGraphic(blackView[i]);
			if(currentBoard.board.get(i).isRed()) squares[i].setGraphic(redView[i]);
			if(currentBoard.board.get(i).isEmpty()) squares[i].setGraphic(woodView[i]);
			if(currentBoard.board.get(i).isBlackKing()) squares[i].setGraphic(bKingView[i]);
			if(currentBoard.board.get(i).isRedKing()) squares[i].setGraphic(rKingView[i]);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		squares = new Button[]{s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,
				s15,s16,s17,s18,s19,s20,s21,s22,s23,s24,s25,s26,s27,s28,s29,s30,s31};
		
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
		
		System.out.println("Done Instantiating");
	}	
}
