package chess;

import java.util.ArrayList;

public class Knight extends ChessPiece{
	public Knight(ChessModel m, PlayerColour pc, Pair<Integer, Integer> pos){
		super(m, pc, pos, ChessPieceType.KNIGHT);
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
						1 <= x + xdir && x + xdir <= BOARD_SIZE &&
						1 <= y + ydir && y + ydir <= BOARD_SIZE){
					ChessPiece position = super.getModel().getBoard().getPiece(new Pair<Integer,Integer>(x + xdir, y + ydir));
					if (null == position ||
							super.getPlayerColour() != position.getPlayerColour()){
						moves.add(new Pair<Integer,Integer>(x + xdir, y + ydir));
					}
				}
			}
		}
		return moves;
	}
}
