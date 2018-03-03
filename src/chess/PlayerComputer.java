package chess;

import java.util.ArrayList;

class PossibleMove{
	public Position from;
	public Position to;
	
	public PossibleMove(Position from, Position to){
		this.from = from;
		this.to = to;
	}
	
	public static ArrayList<PossibleMove> convertToPossibleMoves(Position from, ArrayList<Position> tos){
		
		ArrayList<PossibleMove> moves = new ArrayList<PossibleMove>();
		
		for (Position to : tos){
			moves.add(new PossibleMove(from, to));
		}
		
		return moves;
	}
}

public class PlayerComputer extends Player {
	
	public PlayerComputer(PlayerColour pc) {
		super(pc, PlayerType.COMPUTER);
	}
	
	//Returns the total value of the board's layout for the owner
	private double evaluateBoardValue(Player owner){
		
		double totalValue = 0;
		PlayerColour opponentColour = PlayerColour.WHITE;
		
		if (owner.getPlayerColour() == PlayerColour.WHITE) {
			opponentColour = PlayerColour.BLACK;
		}
		
		Player opponent = this.myModel.getPlayer(opponentColour);
		
		ArrayList<ChessPiece> ownerPieces = owner.getAllPieces();
		ArrayList<ChessPiece> opponentPieces = opponent.getAllPieces();
		
		for (ChessPiece p : ownerPieces){
			totalValue += p.getPieceValue();
		}
		
		for (ChessPiece p : opponentPieces){
			totalValue -= p.getPieceValue();
		}
		
		return totalValue;
	}
	
	private ArrayList<PossibleMove> getAllPossibleMoves(){
		
		ArrayList<PossibleMove> moves = new ArrayList<PossibleMove>();
		ArrayList<ChessPiece> myPieces = this.getAllPieces();
		
		for (ChessPiece p : myPieces){
			moves.addAll(PossibleMove.convertToPossibleMoves(p.getPosition(), p.getPossibleMoves()));
		}
		
		return moves;
	}
	
	@Override
	public void startTurn() {
		ArrayList<PossibleMove> possibleMoves = this.getAllPossibleMoves();
		
		System.out.println(possibleMoves.size() + " possible moves");
		for (PossibleMove pm : possibleMoves){
			System.out.println(pm.from.toString() + " - " + pm.to.toString());
		}
		
		PossibleMove bestMove = possibleMoves.get(0);
		
		this.makeMove(bestMove.from, bestMove.to);
	}
}
