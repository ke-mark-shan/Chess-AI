package chess;

import java.util.ArrayList;

public class Pawn extends ChessPiece {
	
	private Boolean madeMove;		//Whether the piece has made a prior move
	
	public Pawn(ChessModel m, PlayerColour pc, Pair<Integer, Integer> pos){
		super(m, pc, pos, ChessPieceType.PAWN);
		this.madeMove = false;
	}
		
	@Override
	public void setPosition(Pair<Integer,Integer> p, boolean actualMove){
		super.setPosition(p, actualMove);

		if (actualMove){
			this.madeMove = true;
		}
		
	}
	
	// Returns whether this piece can make a diagonal attack on cell (col, row)
	private boolean canDiagonalAttack(int col, int row){
		
		return (this.getModel().inBoard(col) &&
				null != super.getModel().getBoard().getPiece(new Pair<Integer, Integer> (col, row)) &&
				super.getPlayerColour() != super.getModel().getBoard().getPiece(new Pair<Integer, Integer>(col, row)).getPlayerColour() &&
				!this.tryMoveCheck(new Pair<Integer, Integer>(col, row)));
	}
	
	@Override
	public ArrayList<Pair<Integer,Integer>> getPossibleMoves(){

		final int BOARD_SIZE = super.getModel().getBoard().BOARD_SIZE;
		int direction = this.getDirectionMultiplier();
		int col = super.getPosition().getFirst();
		int row = super.getPosition().getSecond();

		ArrayList<Pair<Integer,Integer>> moves = new ArrayList<Pair<Integer,Integer>>();
		
		if (this.getModel().inBoard(row + direction)){
			// Movement forward
			if (null == super.getModel().getBoard().getPiece(new Pair<Integer, Integer>(col, row + direction))){
				// Can move one cell forward
				if (!this.tryMoveCheck(new Pair<Integer, Integer>(col, row + direction))){
					moves.add(new Pair<Integer,Integer>(col, row + direction));
				}
				if (!this.madeMove &&
					null == super.getModel().getBoard().getPiece(new Pair<Integer, Integer>(col, row + 2 * direction)) &&
					!this.tryMoveCheck(new Pair<Integer, Integer>(col, row + 2 * direction))){
					// Can move 2 cells forward. Will not be out of bounds
					moves.add(new Pair<Integer,Integer>(col, row + 2 * direction));
				}
			}
			
			//Diagonal attacks
			if (this.canDiagonalAttack(col - 1, row + direction)){
				moves.add(new Pair<Integer,Integer>(col - 1, row + direction));
			}
			
			if (this.canDiagonalAttack(col + 1, row + direction)){
				moves.add(new Pair<Integer,Integer>(col + 1, row + direction));
			}
		}
		
		return moves;
	}
}
