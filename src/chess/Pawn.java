package chess;

public class Pawn implements ChessPiece {
	private ChessPieceType myType = ChessPieceType.PAWN;
	private PlayerColour myColour;
	
	public Pawn(PlayerColour pc){
		myColour = pc;
	}
	
	public ChessPieceType getType(){
		return myType;
	}
	
	public PlayerColour getColour(){
		return myColour;
	}
}
