package chess;

import java.util.ArrayList;

public class Player {
	
	private PlayerColour myColour;
	public King king;
	public Queen queen;
	public ArrayList<Rook> rooks = new ArrayList<Rook>();
	public ArrayList<Bishop> bishops = new ArrayList<Bishop>();
	public ArrayList<Knight> knights = new ArrayList<Knight>();
	public ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	
	public Player(PlayerColour pc){
		this.myColour = pc;
	}
	
	public PlayerColour getPlayerColour(){
		return this.myColour;
	}
}
