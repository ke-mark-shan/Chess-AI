package chess;

import java.util.*;

public abstract class ChessPiece {
	
	private ChessModel model;
	private PlayerColour myColour;
	private Position myPosition;										//(Column,Row)
	private int myDirection;											//Multiplier for forward
	private ChessPieceType myType;
	
	private double myBaseValue;
	private double[][] positionValues;
	
	private static void reverse(double[][] arr){
        double[] temp;
        
        for (int i = 0; i < arr.length/2; i++){
            temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
    }
	
	public ChessPiece(ChessModel m, PlayerColour pc, Position pos, ChessPieceType t, double baseValue, double[][] positionValues){
		
		this.model = m;
		this.myColour = pc;
		this.myPosition = pos;
		this.myDirection = 1;
		this.positionValues = positionValues;
		if (this.myColour == PlayerColour.BLACK){			
			this.myDirection = -1;
			ChessPiece.reverse(this.positionValues);
		}
		this.myType = t;
		this.myBaseValue = baseValue;
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
	
	public double getBaseValue(){
		return this.myBaseValue;
	}
	
	public double[][] getPositionValues(){
		return this.positionValues;
	}
	
	//Get the value of the piece 
	public double getPieceValue(){
		return this.myBaseValue + this.positionValues[this.myPosition.getSecond()][this.myPosition.getFirst()];
	}
	
	//Get all valid moves for this piece
	abstract public ArrayList<Position> getPossibleMoves();
	
	//Try moving the piece to new position and see if the owner is in check
	protected boolean tryMoveCheck(Position newPos){
		return this.model.tryMoveCheck(this, newPos);
	}
	
	protected ArrayList<Position> getValidMovesInDirection(int dCol, int dRow){
		
		int currCol = this.myPosition.getFirst();
		int currRow = this.myPosition.getSecond();
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
