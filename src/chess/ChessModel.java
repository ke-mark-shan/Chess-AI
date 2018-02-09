package chess;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
public class ChessModel extends Observable{
	private Player player1;						//White
	private Player player2;						//Black
	private ChessBoard chessBoard;
	private Rectangle2D boardDimensions;
	private PlayerColour turn;
	
	public ChessModel(){
		boardDimensions = new Rectangle2D.Double(0,0,0,0);
		this.initializeBoard();
	}
	
	//Getters and Setters
	public ChessBoard getBoard(){
		return this.chessBoard;
	}
	
	public Rectangle2D getBoardSize(){
		return boardDimensions;
	}
	public void setBoardSize(double w, double h){
		//The game board will the largest square that could fit within the window
		double boardWidth = Math.min(w,h);
		boardDimensions = new Rectangle2D.Double(0, 0, boardWidth, boardWidth);
		setChangedAndNotify();
	}
	
	// Returns whether row/column i is inside the board
	public boolean inBoard(int i){
		return (0 <= i && i < this.getBoard().BOARD_SIZE);
	}
	
	public void initializeBoard(){
		chessBoard = new ChessBoard();
		turn = PlayerColour.WHITE;
		
		final int BOARD_SIZE = this.chessBoard.BOARD_SIZE;
		
		for (int column = 0; column < BOARD_SIZE; column++){
			for (int row = 0; row < BOARD_SIZE; row++){
				this.chessBoard.setPiece(new Pair<Integer, Integer>(column, row), null);
			}
		}
		
		// White Pieces
		for (int column = 0; column < BOARD_SIZE; column++){
			this.chessBoard.setPiece(new Pair<Integer,Integer>(column, 1), new Pawn(this, PlayerColour.WHITE, new Pair<Integer,Integer>(column, 1)));
		}
		
		// Black Pieces
		for (int column = 0; column < BOARD_SIZE; column++){
			this.chessBoard.setPiece(new Pair<Integer,Integer>(column, 6), new Pawn(this, PlayerColour.BLACK, new Pair<Integer,Integer>(column, 6))); 
		}
		
		System.out.println("Chess Board Initialized");
		setChangedAndNotify();
	}
	// Returns whether the player with colour pc is in check
	public boolean inCheck(PlayerColour pc){
		
		King myKing = this.player1.king;
		Player opponent = this.player2;
		
		if (pc == PlayerColour.BLACK)
		{
			myKing = this.player2.king;
			opponent = this.player1;
		}
		
		int myKingCol = myKing.getPosition().getFirst();
		int myKingRow = myKing.getPosition().getSecond();
		
		//Look for all ways pc's king could be attacked
		if (opponent.queen != null || opponent.rooks.size() > 0){
			
		}
		
		if (opponent.queen != null || opponent.bishops.size() > 0){
			
		}
		
		if (opponent.knights.size() > 0){
			
		}
		if (opponent.pawns.size() > 0){
			
		}
		return false;
	}
	
	// Returns whether moving ChessPiece p to position newPos will put its owner in check
	public boolean tryMoveCheck(ChessPiece p, Pair<Integer, Integer> newPos){
		
		boolean inCheck = false;
		Pair<Integer,Integer> oldPos = p.getPosition();
		ChessPiece removedPiece = this.getBoard().getPiece(newPos);
		
		this.movePiece(oldPos, newPos);
		inCheck =  this.inCheck(p.getPlayerColour());
		
		//Restore previous board state
		this.movePiece(newPos, oldPos);
		if (removedPiece != null){
			this.addPiece(removedPiece);
		}
		return inCheck;
	}
	// Moves piece at start to end
	public void movePiece(Pair<Integer,Integer> start, Pair<Integer,Integer> end){
		ChessPiece startPiece = chessBoard.getPiece(start);
		ChessPiece endPiece = chessBoard.getPiece(end);
		
		if (null != endPiece){
			this.removePiece(endPiece);
		}
		
		startPiece.setPosition(end);
		chessBoard.setPiece(end, startPiece);
	}
	
	public void addPiece(ChessPiece p){
		this.chessBoard.setPiece(p.getPosition(),p);
		ChessPieceType type = p.getType();
		
		Player pieceOwner = this.player1;
		if (p.getPlayerColour() == PlayerColour.BLACK){
			pieceOwner = this.player2;
		}
		switch(type){
			case KING:
				pieceOwner.king = (King)p;
				break;
			case QUEEN:
				pieceOwner.queen = (Queen)p;
				break;
			case BISHOP:
				pieceOwner.bishops.add((Bishop)p);
				break;
			case KNIGHT:
				pieceOwner.knights.add((Knight)p);
				break;
			case ROOK:
				pieceOwner.rooks.add((Rook)p);
			case PAWN:
				pieceOwner.pawns.add((Pawn)p);
				break;
		}
	}
	
	public void removePiece(ChessPiece p){
		this.chessBoard.setPiece(p.getPosition(),null);
		ChessPieceType type = p.getType();
		
		Player pieceOwner = this.player1;
		if (p.getPlayerColour() == PlayerColour.BLACK){
			pieceOwner = this.player2;
		}
		switch(type){
			case KING:
				pieceOwner.king = null;
				break;
			case QUEEN:
				pieceOwner.queen = null;
				break;
			case BISHOP:
				pieceOwner.bishops.remove((Bishop)p);
				break;
			case KNIGHT:
				pieceOwner.knights.remove((Knight)p);
				break;
			case ROOK:
				pieceOwner.rooks.remove((Rook)p);
			case PAWN:
				pieceOwner.pawns.remove((Pawn)p);
				break;
		}
	}
	
	//Notify observers of changes
    public void setChangedAndNotify() {
        setChanged();
		notifyObservers();
	}
}
