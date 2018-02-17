package chess;

import java.util.ArrayList;

public class King extends ChessPiece{
	
	public King(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.KING);
	}
	
	public ArrayList<Position> getPossibleMoves(){

		int col = this.getPosition().getFirst();
		int row = this.getPosition().getSecond();

		ArrayList<Position> moves = new ArrayList<Position>();
		
		int[] moveCol = new int[]{-1,1};
		int[] moveRow = new int[]{-1,1};
		
		for (int dCol : moveCol){
			for (int dRow : moveRow){
				if (this.getModel().inBoard(col + dCol) && this.getModel().inBoard(row + dRow)){
					Position newPos = new Position(col + dCol, row + dRow);
					ChessPiece target = this.getModel().getBoard().getPiece(newPos);
					
					if ((null == target || this.getPlayerColour() != target.getPlayerColour()) &&
							!this.tryMoveCheck(newPos)){
						moves.add(new Position(col + dCol, row + dRow));
					}
				}
			}
		}
		return moves;
	}
}
