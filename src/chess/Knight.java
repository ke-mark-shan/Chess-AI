package chess;

import java.util.ArrayList;

public class Knight extends ChessPiece{
	public Knight(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.KNIGHT);
	}
	
	public ArrayList<Position> getPossibleMoves(){

		int col = this.getPosition().getFirst();
		int row = this.getPosition().getSecond();

		ArrayList<Position> moves = new ArrayList<Position>();
		
		int[] moveOne = new int[]{-1,1};
		int[] moveTwo = new int[]{-2,2};
		
		for (int dCol : moveOne){
			for (int dRow : moveTwo){
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
		
		for (int dCol : moveTwo){
			for (int dRow : moveOne){
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
