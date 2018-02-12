package chess;

import java.util.*;

//pair of elements
class Pair<A,B>{
	private A first;
	private B second;
	
	public Pair(A first, B second){
		this.first = first;
		this.second = second;
	}
	
	public A getFirst(){
		return this.first;
	}
	public B getSecond(){
		return this.second;
	}
	
	public void setFirst(A a){
		first = a;
	}
	public void setSecond(B b){
		second = b;
	}
	
	public boolean equals(Pair<A,B> other){
		return (this.first == other.getFirst() && this.second == other.getSecond());
	}
}

public abstract class ChessPiece {
	private ChessModel model;
	private PlayerColour myColour;
	private Pair<Integer, Integer> myPosition;							//(Column,Row)
	private int myDirection;											//Multiplier for forward

	private ChessPieceType myType;
	public ChessPiece(ChessModel m, PlayerColour pc, Pair<Integer, Integer> pos, ChessPieceType t){
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
	
	public Pair<Integer,Integer> getPosition(){
		return this.myPosition;
	}
	
	public void setPosition(Pair<Integer,Integer> p, boolean actualMove){
		this.myPosition = p;
	}
	
	public int getDirectionMultiplier(){
		return this.myDirection;
	}
	public ChessPieceType getType(){
		return this.myType;
	}
	// Get all valid moves for this piece
	abstract public ArrayList<Pair<Integer,Integer>> getPossibleMoves();
	
	//Try moving the piece to new position and see if the owner is in check
	protected boolean tryMoveCheck(Pair<Integer,Integer> newPos){
		
		return this.model.tryMoveCheck(this, newPos);
	}
	
	protected ArrayList<Pair<Integer,Integer>> getValidMovesInDirection(int dCol, int dRow){
		
		int currCol = this.myPosition.getFirst();
		int currRow = this.myPosition.getSecond();
		PlayerColour myColour = this.getPlayerColour();
		ArrayList<Pair<Integer,Integer>> moves = new ArrayList<Pair<Integer,Integer>>();
		
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
				ChessPiece piece = this.model.getBoard().getPiece(new Pair<Integer, Integer>(currCol, currRow));
				Pair<Integer, Integer> position = new Pair<Integer, Integer>(currCol, currRow);
				
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
