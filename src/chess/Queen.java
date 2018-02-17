package chess;

import java.util.ArrayList;

public class Queen extends ChessPiece{
	public Queen(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.QUEEN);
	}
	
	@Override
	public ArrayList<Position> getPossibleMoves(){

		ArrayList<Position> moves = new ArrayList<Position>();
		
		for (int dCol = -1; dCol <= 1; dCol ++){
			for (int dRow = -1; dRow <= 1; dRow ++){
				if (dCol != 0 || dRow != 0){
					moves.addAll(
						this.getValidMovesInDirection(dCol, dRow)
					);
				}
			}
		}
		
		return moves;
	}
}
