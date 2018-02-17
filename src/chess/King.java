package chess;

import java.util.ArrayList;

public class King extends ChessPiece{
	public King(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.KING);
	}
	
	public ArrayList<Position> getPossibleMoves(){

		int x = super.getPosition().getFirst();
		int y = super.getPosition().getSecond();

		ArrayList<Position> moves = new ArrayList<Position>();
		
		// King can move 1 cell in each direction
		for (int xdir = -1; xdir <= 1; xdir++){
			for (int ydir = -1; ydir <= 1; ydir++){
				if ((xdir != 0 || ydir != 0) &&
						this.getModel().inBoard(x + xdir) && this.getModel().inBoard(y + ydir)){
					Position newPos = new Position(x + xdir, y + ydir);
					ChessPiece target = super.getModel().getBoard().getPiece(newPos);
					
					if ((null == target || super.getPlayerColour() != target.getPlayerColour()) &&
							!this.tryMoveCheck(newPos)){
						moves.add(new Position(x + xdir, y + ydir));
					}
				}
			}
		}
		return moves;
	}
}
