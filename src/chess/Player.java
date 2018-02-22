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
	public ChessModel getModel(){
		return this.myModel;
	}
	
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
	
}
