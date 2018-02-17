package chessTests;

import chess.*;

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
}
