package chess;

import java.util.ArrayList;

public class Bishop extends ChessPiece{
	
	private static double myValue = 30;
																	
	public Bishop(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.BISHOP, Bishop.myValue, new double[][] {	
																	new double[] {-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
																	new double[] {-1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
																	new double[] {-1.0,  0.0,  0.5,  1.0,  1.0,  0.5,  0.0, -1.0},
																	new double[] {-1.0,  0.5,  0.5,  1.0,  1.0,  0.5,  0.5, -1.0},
																	new double[] {-1.0,  0.0,  1.0,  1.0,  1.0,  1.0,  0.0, -1.0},
																	new double[] {-1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0, -1.0},
																	new double[] {-1.0,  0.5,  0.0,  0.0,  0.0,  0.0,  0.5, -1.0},
																	new double[] {-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}
																});
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
