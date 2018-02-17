package chessTests;

import chess.*;

import static org.junit.Assert.fail;

import java.util.ArrayList;

public class ChessTestBase {

	private ChessModel model;
		
	protected ChessTestBase(){
		this.reset();
	}
	
	//Getters and Setters
	protected ChessModel getModel(){
		return this.model;
	}
	
	protected void reset(){
		this.model = new ChessModel(new Player(PlayerColour.WHITE), new Player(PlayerColour.BLACK));
		this.clearBoard();
		this.placeKings();
	}
	
	protected void clearBoard(){
		int BOARD_SIZE = this.model.getBoard().getBoardSize();
		
		for (int i = 0; i < BOARD_SIZE; i++){
			for (int j = 0; j < BOARD_SIZE; j++){
				this.model.removePiece(this.model.getBoard().getPiece(new Position(i,j)));
			}
		}
	}
	
	protected void placeKings(){
		this.model.addPiece(
			new King(this.model, PlayerColour.WHITE, new Position(7,0))
		);
			
		this.model.addPiece(
			new King(this.model, PlayerColour.BLACK, new Position(7,7))
		);
	}
	
	protected void compareMoves(ArrayList<Position> expected, ArrayList<Position> received){
		System.out.println("Comparing...");
		if (received.size() != expected.size()) {
			
			boolean notEqual = false;
			
			for (Position e : expected){
				if (received.indexOf(e) < 0){
					notEqual = true;
					break;
				}
			}
			System.out.println("Expected " + expected.size() + " Moves");
			System.out.println("Got " + received.size() + " Moves:");
			for (Position p : received){
				System.out.println("(" + p.getFirst() + ", " + p.getSecond() + ")");
			}
			
			fail("Expected moves not being generated");
		}
	}
}
