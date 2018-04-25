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
	public static double evaluateBoardValue(Player owner){
		
		double totalValue = 0;

		Player opponent = owner.getOpponent();
		
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
	
	private PossibleMove basicBestMove() {
		ArrayList<PossibleMove> possibleMoves = this.getAllPossibleMoves();
		
		System.out.println(possibleMoves.size() + " possible moves");
		
		double bestMoveValue = -1 * Double.MAX_VALUE;
		PossibleMove bestMoveSoFar = null;
		
		for (PossibleMove pm : possibleMoves){
			System.out.println(pm.from.toString() + " - " + pm.to.toString());
			
			this.myModel.movePiece(pm.from, pm.to, false);
			
			double thisBoardValue = this.evaluateBoardValue(this);
			
			if (thisBoardValue > bestMoveValue){
				bestMoveSoFar = pm;
				bestMoveValue = thisBoardValue;
			}
			this.myModel.undoMove();
		}
		return bestMoveSoFar;
	}
	
	@Override
	public void startTurn() {
		
		PossibleMove bestMove = this.startMinimax(3);
		
		// Find the best move 
		
		this.makeMove(bestMove.from, bestMove.to);
	}
	
	private PossibleMove startMinimax(int depth) {
		
		Player current = this;
		Player other = this.getOpponent();
		
		PossibleMove bestMoveSoFar = null;
		
		MinimaxConfig minimaxConfig = new MinimaxConfig(false);
		
		ArrayList<PossibleMove> possibleMoves = current.getAllPossibleMoves();
		double bestValueSoFar = minimaxConfig.getStartingValue();
		
		for (PossibleMove pm : possibleMoves){
			current.getModel().movePiece(pm.from, pm.to, false);
			
			double minimaxResult = PlayerComputer.minimax(depth - 1, other, current, true);
			if (minimaxResult > bestValueSoFar) {
				bestValueSoFar = minimaxResult;
				bestMoveSoFar =  pm;
			}
			
			current.getModel().undoMove();
		}
		return bestMoveSoFar;
	}
	
	// Could have went for NegaMax, but minimax is more 'general'
	private static double minimax(int depth, Player current, Player other, boolean minimizing) {
		if (0 >= depth) return -1 * PlayerComputer.evaluateBoardValue(current);
		
		MinimaxConfig minimaxConfig = new MinimaxConfig(minimizing);
		
		ArrayList<PossibleMove> possibleMoves = current.getAllPossibleMoves();
		double bestValueSoFar = minimaxConfig.getStartingValue();
		
		for (PossibleMove pm : possibleMoves){
			current.getModel().movePiece(pm.from, pm.to, false);
			bestValueSoFar = minimaxConfig.compare(bestValueSoFar, PlayerComputer.minimax(depth - 1, other, current, ! minimizing));
			current.getModel().undoMove();
		}
		return bestValueSoFar;
	}
}

// Factoring out the two cases of minimax
class MinimaxConfig {
	private final double MIN_START = Double.MAX_VALUE;
	private final double MAX_START = -1 * Double.MAX_VALUE;
	
	private boolean minimizing;
	private double startingValue;
	
	MinimaxConfig(boolean minimizing) {
		this.minimizing = minimizing;
		this.startingValue = this.MAX_START;
		
		if (minimizing) {
			this.startingValue = this.MIN_START;
		}
	}
	
	public double getStartingValue() {
		return this.startingValue;
	}
	
	// Compares the two parameters based on whether we are minimizing/maximizing
	public double compare(double leftVal, double rightval) {
		if (this.minimizing) {
			return Math.min(leftVal, rightval);
		}
		return Math.max(leftVal, rightval);
	}
}
