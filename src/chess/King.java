package chess;

import java.util.ArrayList;

public class King extends ChessPiece{
	
	private static double myValue = 900;
	
	public King(ChessModel m, PlayerColour pc, Position pos){
		super(m, pc, pos, ChessPieceType.KING, King.myValue, new double[][] {	
																new double[] {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
																new double[] {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
																new double[] {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
																new double[] {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
																new double[] {-2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
																new double[] {-1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
																new double[] { 2.0,  2.0,  0.0,  0.0,  0.0,  0.0,  2.0,  2.0},
																new double[] { 2.0,  3.0,  1.0,  0.0,  0.0,  1.0,  3.0,  2.0}
															});
	}
	
	// Castling Rules (Wikipedia):
	//	1. The king and the chosen rook are on the player's first rank.[3]
	//	2. Neither the king nor the chosen rook has previously moved.
	//	3. There are no pieces between the king and the chosen rook.
	//	4. The king is not currently in check.
	//	5. The king does not pass through a square that is attacked by an enemy piece.[4]
	//	6. The king does not end up in check. (True of any legal move.)
	private boolean canCastleMove(PlayerColour opponentColour, int kingRow, Position potentialRookPos, Position[] betweenPositions, Position[] kingMoves,
									Position newKingPos, Position newRookPos){
		
		ChessModel model = this.getModel();
		ChessPiece potentialRook = model.getBoard().getPiece(potentialRookPos);
		
		if (kingRow == this.getFirstRank() &&														// 1
			!this.getMadeMove() &&																	// 2
			!model.inCheck(this.getPlayerColour()) &&												// 4
			ChessPieceType.ROOK == potentialRook.getType() && !potentialRook.getMadeMove()){		// 2
			
			for (int i = 0; i < betweenPositions.length; i++){
				if (null != model.getBoard().getPiece(betweenPositions[i])){ 						// 3
					return false;
				}
			}
			for (int i = 0; i < kingMoves.length; i++){
				if (model.canBeAttackeByEnemy(opponentColour, kingMoves[i])){						// 5
					return false;
				}
			}
			Position oldKingPos = this.getPosition();
			Position oldRookPos = potentialRookPos;
			
			model.movePiece(oldKingPos, newKingPos, false);
			
			boolean inCheck = model.inCheck(this.getPlayerColour());								// 6
			
			// Restore previous board state
			model.undoMove();
			
			return !inCheck;
		}

		return false;
	}
	
	public ArrayList<Position> getPossibleMoves(){

		int col = this.getPosition().getFirst();
		int row = this.getPosition().getSecond();

		ArrayList<Position> moves = new ArrayList<Position>();

		for (int dCol = -1; dCol <= 1; dCol++){
			for (int dRow = -1; dRow <= 1; dRow++){
				if (dCol == 0 && dRow == 0) continue;
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
		
		// Castling
		PlayerColour opponentColour = PlayerColour.BLACK;
		
		if (this.getPlayerColour() == PlayerColour.BLACK){
			opponentColour = PlayerColour.WHITE;
		}
		// Left Rook
		Position[] betweenPositions = new Position[] { new Position(1, row), new Position (2, row), new Position(3, row) };
		Position[] kingMoves = new Position[] { new Position (2, row), new Position(3, row) };
		Position newKingPos = new Position(2, row);
		
		if (this.canCastleMove(opponentColour, row, new Position(0, row), betweenPositions, kingMoves, newKingPos, new Position(3, row))){
			moves.add(newKingPos);
		}
		// Right Rook
		betweenPositions = new Position[] { new Position(5, row), new Position (6, row)};
		kingMoves = new Position[] { new Position (5, row), new Position(6, row) };
		newKingPos = new Position(6, row);
		
		if (this.canCastleMove(opponentColour, row, new Position(0, row), betweenPositions, kingMoves, newKingPos, new Position(5, row))){
			moves.add(newKingPos);
		}
		
		return moves;
	}

	
}
