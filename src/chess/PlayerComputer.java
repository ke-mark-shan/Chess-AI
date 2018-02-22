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
	
	
	private ArrayList<PossibleMove> getAllPossibleMoves(){
		
		ArrayList<PossibleMove> moves = new ArrayList<PossibleMove>();
		
		// King
		if (null != this.king){
			moves.addAll(PossibleMove.convertToPossibleMoves(this.king.getPosition(), this.king.getPossibleMoves()));
		}
		
		// Queen
		if (null != this.queen){
			moves.addAll(PossibleMove.convertToPossibleMoves(this.queen.getPosition(), this.queen.getPossibleMoves()));
		}
		
		// Rooks
		for (Rook r : this.rooks){
			moves.addAll(PossibleMove.convertToPossibleMoves(r.getPosition(), r.getPossibleMoves()));
		}
		
		// Bishops
		for (Bishop b : this.bishops){
			moves.addAll(PossibleMove.convertToPossibleMoves(b.getPosition(), b.getPossibleMoves()));
		}
		
		// Knights
		for (Knight k : this.knights){
			moves.addAll(PossibleMove.convertToPossibleMoves(k.getPosition(), k.getPossibleMoves()));
		}
		
		// Pawns
		for (Pawn p : this.pawns){
			moves.addAll(PossibleMove.convertToPossibleMoves(p.getPosition(),p.getPossibleMoves()));
		}
		
		return moves;
	}
	
	@Override
	public void startTurn() {
		
		ArrayList<PossibleMove> possibleMoves = this.getAllPossibleMoves();
		
		for (PossibleMove pm : possibleMoves){
			System.out.println(pm.from.toString() + " - " + pm.to.toString());
		}
		
		PossibleMove bestMove = possibleMoves.get(0);
		
		this.makeMove(bestMove.from, bestMove.to);
	}
}
