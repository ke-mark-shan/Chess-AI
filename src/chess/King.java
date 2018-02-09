package chess;

import java.util.ArrayList;

public class King extends ChessPiece{
	public King(ChessModel m, PlayerColour pc, Pair<Integer, Integer> pos){
		super(m, pc, pos, ChessPieceType.KING);
	}
	
	public ArrayList<Pair<Integer,Integer>> getPossibleMoves(){

		final int BOARD_SIZE = super.getModel().getBoard().BOARD_SIZE;
		
		int x = super.getPosition().getFirst();
		int y = super.getPosition().getSecond();

		ArrayList<Pair<Integer,Integer>> moves = new ArrayList<Pair<Integer,Integer>>();
		
		// King can move 1 cell in each direction
		for (int xdir = -1; xdir <= 1; xdir++){
			for (int ydir = -1; ydir <= 1; ydir++){
				if ((xdir != 0 || ydir != 0) &&
						this.getModel().inBoard(x + xdir) && this.getModel().inBoard(y + ydir)){
					Pair<Integer,Integer> newPos = new Pair<Integer,Integer>(x + xdir, y + ydir);
					ChessPiece target = super.getModel().getBoard().getPiece(newPos);
					
					if ((null == target || super.getPlayerColour() != target.getPlayerColour()) &&
							!this.tryMoveCheck(newPos)){
						moves.add(new Pair<Integer,Integer>(x + xdir, y + ydir));
					}
				}
			}
		}
		return moves;
	}
}
