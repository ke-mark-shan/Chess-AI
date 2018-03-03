package chess;

import java.util.ArrayList;

public abstract class Player {
	
	
	private PlayerColour myColour;
	private PlayerType myType;
	
	protected ChessModel myModel;
	
	public King king;
	public Queen queen;
	public ArrayList<Rook> rooks;
	public ArrayList<Bishop> bishops;
	public ArrayList<Knight> knights;
	public ArrayList<Pawn> pawns;
	
	//Model has to be set later because Player and Model rely on each other. TODO.
	public Player(PlayerColour pc, PlayerType pt){
		this.myColour = pc;
		this.myType = pt;
		
		this.king = null;
		this.queen = null;
		this.rooks = new ArrayList<Rook>();
		this.bishops = new ArrayList<Bishop>();
		this.knights = new ArrayList<Knight>();
		this.pawns = new ArrayList<Pawn>();
	}
	
	//Getters and Setters	
	public void setModel(ChessModel m){
		this.myModel = m;
	}
	
	public PlayerColour getPlayerColour(){
		return this.myColour;
	}
	
	public PlayerType getPlayerType(){
		return this.myType;
	}

	//Indicates beginning of this player's turn
	public abstract void startTurn();
	
	public void makeMove(Position start, Position end) {
		this.myModel.movePiece(start, end, true);
		this.myModel.swapTurns();
	}
	
	protected ArrayList<ChessPiece> getAllPieces(){
		
		ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
		
		// King
		if (null != this.king){
			pieces.add(this.king);
		}
		
		// Queen
		if (null != this.queen){
			pieces.add(this.queen);
		}
		
		// Rooks
		for (Rook r : this.rooks){
			pieces.add(r);
		}
		
		// Bishops
		for (Bishop b : this.bishops){
			pieces.add(b);
		}
		
		// Knights
		for (Knight k : this.knights){
			pieces.add(k);
		}
		
		// Pawns
		for (Pawn p : this.pawns){
			pieces.add(p);
		}
		
		return pieces;
	}
	
}
