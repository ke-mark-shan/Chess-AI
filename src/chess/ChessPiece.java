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
	
	public void setPosition(Pair<Integer,Integer> p){
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
	public boolean tryMoveCheck(Pair<Integer,Integer> newPos){
		
		final Pair<Integer,Integer> myPos = this.myPosition;
		
		return this.model.tryMoveCheck(this, newPos);
	}
}
