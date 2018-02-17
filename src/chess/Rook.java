package chess;

import java.util.ArrayList;

public class Rook extends ChessPiece{
	public Rook(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.ROOK);
	}
	
	@Override
	public ArrayList<Position> getPossibleMoves(){

		ArrayList<Position> moves = new ArrayList<Position>();
		
		moves.addAll(this.getValidMovesInDirection(1, 0));
		moves.addAll(this.getValidMovesInDirection(-1, 0));
		moves.addAll(this.getValidMovesInDirection(0, 1));
		moves.addAll(this.getValidMovesInDirection(0, -1));
		
		return moves;
	}
}
