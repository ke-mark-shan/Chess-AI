package chess;

import java.util.ArrayList;

public class Pawn extends ChessPiece {
	
	private Boolean madeMove;		//Whether the piece has made a prior move
	private static double myValue = 10;
	
	public Pawn(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.PAWN, Pawn.myValue, new double[][]	{	
																new double[] {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
														        new double[] {5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0},
														        new double[] {1.0,  1.0,  2.0,  3.0,  3.0,  2.0,  1.0,  1.0},
														        new double[] {0.5,  0.5,  1.0,  2.5,  2.5,  1.0,  0.5,  0.5},
														        new double[] {0.0,  0.0,  0.0,  2.0,  2.0,  0.0,  0.0,  0.0},
														        new double[] {0.5, -0.5, -1.0,  0.0,  0.0, -1.0, -0.5,  0.5},
														        new double[] {0.5,  1.0, 1.0,  -2.0, -2.0,  1.0,  1.0,  0.5},
														        new double[] {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}
															});
		this.madeMove = false;
	}
		
	@Override
	public void setPosition(Position p, boolean actualMove){
		
		super.setPosition(p, actualMove);

		if (actualMove){
			this.madeMove = true;
		}
		
	}
	
	// Returns whether this piece can make a diagonal attack on cell (col, row)
	private boolean canDiagonalAttack(int col, int row){
		
		return (this.getModel().inBoard(col) &&
				null != this.getModel().getBoard().getPiece(new Position (col, row)) &&
				this.getPlayerColour() != this.getModel().getBoard().getPiece(new Position(col, row)).getPlayerColour() &&
				!this.tryMoveCheck(new Position(col, row)));
	}
	
	@Override
	public ArrayList<Position> getPossibleMoves(){

		int direction = this.getDirectionMultiplier();
		int col = this.getPosition().getFirst();
		int row = this.getPosition().getSecond();

		ArrayList<Position> moves = new ArrayList<Position>();
		
		if (this.getModel().inBoard(row + direction)){
			// Movement forward
			if (null == this.getModel().getBoard().getPiece(new Position(col, row + direction))){
				// Can move one cell forward
				if (!this.tryMoveCheck(new Position(col, row + direction))){
					moves.add(new Position(col, row + direction));
				}
				if (!this.madeMove &&
					null == this.getModel().getBoard().getPiece(new Position(col, row + 2 * direction)) &&
					!this.tryMoveCheck(new Position(col, row + 2 * direction))){
					// Can move 2 cells forward. Will not be out of bounds
					moves.add(new Position(col, row + 2 * direction));
				}
			}
			
			//Diagonal attacks
			if (this.canDiagonalAttack(col - 1, row + direction)){
				moves.add(new Position(col - 1, row + direction));
			}
			
			if (this.canDiagonalAttack(col + 1, row + direction)){
				moves.add(new Position(col + 1, row + direction));
			}
		}
		
		return moves;
	}

	
}
