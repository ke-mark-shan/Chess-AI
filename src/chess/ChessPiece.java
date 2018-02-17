package chess;

import java.util.*;

public abstract class ChessPiece {
	private ChessModel model;
	private PlayerColour myColour;
	private Position myPosition;										//(Column,Row)
	private int myDirection;											//Multiplier for forward

	private ChessPieceType myType;
	public ChessPiece(ChessModel m, PlayerColour pc, Position pos, ChessPieceType t){
		this.model = m;
		this.myColour = pc;
		this.myPosition = pos;
		this.myDirection = 1;
		this.myType = t;
		if (this.myColour == PlayerColour.BLACK){
			this.myDirection = -1;
		}
	}

	//Getters and Setters
	public ChessModel getModel(){
		return this.model;
	}
	
	public PlayerColour getPlayerColour(){
		return this.myColour;
	}
	
	public Position getPosition(){
		return this.myPosition;
	}
	
	public void setPosition(Position p, boolean actualMove){
		this.myPosition = p;
	}
	
	public int getDirectionMultiplier(){
		return this.myDirection;
	}
	public ChessPieceType getType(){
		return this.myType;
	}
	// Get all valid moves for this piece
	abstract public ArrayList<Position> getPossibleMoves();
	
	//Try moving the piece to new position and see if the owner is in check
	protected boolean tryMoveCheck(Position newPos){
		
		return this.model.tryMoveCheck(this, newPos);
	}
	
	protected ArrayList<Position> getValidMovesInDirection(int dCol, int dRow){
		
		int currCol = this.myPosition.getFirst();
		int currRow = this.myPosition.getSecond();
		PlayerColour myColour = this.getPlayerColour();
		ArrayList<Position> moves = new ArrayList<Position>();
		
		if (dCol == 0 && dRow == 0){
			System.err.println("Both dcol,drow are 0");
			return moves;
		}
		
		while (true){
			currCol += dCol;
			currRow += dRow;
			
			if (!this.model.inBoard(currCol) || !this.model.inBoard(currRow)){
				return moves;
			}
			else{
				Position position = new Position(currCol, currRow);
				ChessPiece piece = this.model.getBoard().getPiece(position);
				
				if (null == piece){
					if (!this.tryMoveCheck(position)){
						moves.add(position);
					}
				}
				else{
					if (piece.getPlayerColour() != this.getPlayerColour() && !this.tryMoveCheck(position)){
						moves.add(position);
					}
					return moves;
				}
			}
		}				
	}
}
