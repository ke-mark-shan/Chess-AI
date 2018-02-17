package chess;

import java.util.ArrayList;

public class Bishop extends ChessPiece{
	public Bishop(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.BISHOP);
	}
	
	@Override
	public ArrayList<Position> getPossibleMoves(){

		ArrayList<Position> moves = new ArrayList<Position>();
		
		moves.addAll(this.getValidMovesInDirection(1, 1));
		moves.addAll(this.getValidMovesInDirection(-1, 1));
		moves.addAll(this.getValidMovesInDirection(1, -1));
		moves.addAll(this.getValidMovesInDirection(-1, -1));
		
		return moves;
	}
}
