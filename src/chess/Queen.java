package chess;

import java.util.ArrayList;

public class Queen extends ChessPiece{
	
	private static double myValue = 90;
	
	public Queen(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.QUEEN, Queen.myValue, new double[][] {	
																	new double[] { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0},
																    new double[] { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
																    new double[] { -1.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
																    new double[] { -0.5,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
																    new double[] {  0.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
																    new double[] { -1.0,  0.5,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
																    new double[] { -1.0,  0.0,  0.5,  0.0,  0.0,  0.0,  0.0, -1.0},
																    new double[] { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0}
																});
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
