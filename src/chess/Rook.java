package chess;

import java.util.ArrayList;

public class Rook extends ChessPiece{
	
	private static double myValue = 50;
																
	public Rook(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.ROOK, Rook.myValue, new double[][]	{	
																	new double[] {  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
																    new double[] {  0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5},
																    new double[] { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
																    new double[] { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
																    new double[] { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
																    new double[] { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
																    new double[] { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
																    new double[] {  0.0,   0.0, 0.0,  0.5,  0.5,  0.0,  0.0,  0.0}
																});
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
