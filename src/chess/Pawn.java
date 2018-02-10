package chess;

import java.util.ArrayList;

public class Pawn extends ChessPiece {
	
	private Boolean madeMove;		//Whether the piece has made a prior move
	
	public Pawn(ChessModel m, PlayerColour pc, Pair<Integer, Integer> pos){
		super(m, pc, pos, ChessPieceType.PAWN);

		this.madeMove = false;
	}
		
	@Override
	public void setPosition(Pair<Integer,Integer> p){
		super.setPosition(p);
		this.madeMove = true;
	}
	public ArrayList<Pair<Integer,Integer>> getPossibleMoves(){

		final int BOARD_SIZE = super.getModel().getBoard().BOARD_SIZE;
		int direction = this.getDirectionMultiplier();
		int x = super.getPosition().getFirst();
		int y = super.getPosition().getSecond();

		ArrayList<Pair<Integer,Integer>> moves = new ArrayList<Pair<Integer,Integer>>();
		
		if (this.getModel().inBoard(y + direction)){
			// Movement forward
			if (null == super.getModel().getBoard().getPiece(new Pair<Integer, Integer>(x, y + direction))){
				// Can move one cell forward
				moves.add(new Pair<Integer,Integer>(x,y + direction));
				
				if (!this.madeMove && null == super.getModel().getBoard().getPiece(new Pair<Integer, Integer>(x, y + 2 * direction))){
					// Can move 2 cells forward. Will not be out of bounds
					moves.add(new Pair<Integer,Integer>(x,y + 2* direction));
				}
			}
			
			//Diagonal attacks
			if (this.getModel().inBoard(x - 1) &&
					null != super.getModel().getBoard().getPiece(new Pair<Integer, Integer> (x - 1, y + direction)) &&
					super.getPlayerColour() != super.getModel().getBoard().getPiece(new Pair<Integer, Integer>(x - 1, y + direction)).getPlayerColour()){
				moves.add(new Pair<Integer,Integer>(x - 1,y + direction));
				
			}
			
			if (this.getModel().inBoard(x + 1) &&
					null != super.getModel().getBoard().getPiece(new Pair<Integer, Integer>(x + 1, y + direction)) &&
					super.getPlayerColour() != super.getModel().getBoard().getPiece(new Pair<Integer, Integer>(x + 1, y + direction)).getPlayerColour()){
				moves.add(new Pair<Integer,Integer>(x + 1,y + direction));
				
			}
		}
		
		return moves;
	}
}
