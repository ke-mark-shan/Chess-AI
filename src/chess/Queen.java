package chess;

import java.util.ArrayList;

public class Queen extends ChessPiece{
	public Queen(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.QUEEN);
	}
	
	public ArrayList<Position> getPossibleMoves(){

		int x = super.getPosition().getFirst();
		int y = super.getPosition().getSecond();

		ArrayList<Position> moves = new ArrayList<Position>();
		
		
		return moves;
	}
}
