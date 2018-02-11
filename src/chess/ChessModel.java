package chess;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Observable;
public class ChessModel extends Observable{
	private Player player1;										//White
	private Player player2;										//Black
	private ChessBoard chessBoard;
	private Rectangle2D boardDimensions;
	private PlayerColour turn;
	private Pair<Integer, Integer> selectedPos;
	private ArrayList<Pair<Integer,Integer>> highlightedPos;	//Highlighted positions
	
	public ChessModel(Player p1, Player p2){
		boardDimensions = new Rectangle2D.Double(0,0,0,0);
		this.initializeBoard(p1, p2);
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
	
	public PlayerColour getTurn(){
		return this.turn;
	}
	
	// Returns whether row/column i is inside the board
	public boolean inBoard(int i){
		return (0 <= i && i < this.getBoard().BOARD_SIZE);
	}
	
	public void swapTurns(){
		switch (this.turn){
			case WHITE:
				this.turn = PlayerColour.BLACK;
				return;
			case BLACK:
				this.turn = PlayerColour.WHITE;
				return;
		}
	}

	private void setupMainPieces(int row, PlayerColour pc){
		this.chessBoard.setPiece(new Pair<Integer,Integer>(0, row), new Rook(this, pc, new Pair<Integer,Integer>(0, row)));
		this.chessBoard.setPiece(new Pair<Integer,Integer>(1, row), new Knight(this, pc, new Pair<Integer,Integer>(1, row)));
		this.chessBoard.setPiece(new Pair<Integer,Integer>(2, row), new Bishop(this, pc, new Pair<Integer,Integer>(2, row)));
		this.chessBoard.setPiece(new Pair<Integer,Integer>(3, row), new Queen(this, pc, new Pair<Integer,Integer>(3, row)));
		this.chessBoard.setPiece(new Pair<Integer,Integer>(4, row), new King(this, pc, new Pair<Integer,Integer>(4, row)));
		this.chessBoard.setPiece(new Pair<Integer,Integer>(5, row), new Bishop(this, pc, new Pair<Integer,Integer>(5, row)));
		this.chessBoard.setPiece(new Pair<Integer,Integer>(6, row), new Knight(this, pc, new Pair<Integer,Integer>(6, row)));
		this.chessBoard.setPiece(new Pair<Integer,Integer>(7, row), new Rook(this, pc, new Pair<Integer,Integer>(7, row)));
	}
	
	private void setupPawns(int row, PlayerColour pc){
		int BOARD_SIZE = this.getBoard().BOARD_SIZE;
		for (int column = 0; column < BOARD_SIZE; column++){
			this.chessBoard.setPiece(new Pair<Integer,Integer>(column, row), new Pawn(this, pc, new Pair<Integer,Integer>(column, row)));
		}
	}
	
	public void initializeBoard(Player p1, Player p2){
		this.chessBoard = new ChessBoard();
		this.turn = PlayerColour.WHITE;
		this.selectedPos = null;
		this.highlightedPos = null;
		this.player1 = p1;
		this.player2 = p2;
		
		final int BOARD_SIZE = this.chessBoard.BOARD_SIZE;
		
		for (int column = 0; column < BOARD_SIZE; column++){
			for (int row = 0; row < BOARD_SIZE; row++){
				this.chessBoard.setPiece(new Pair<Integer, Integer>(column, row), null);
			}
		}
		
		this.setupMainPieces(0, PlayerColour.WHITE);
		this.setupPawns(1, PlayerColour.WHITE);
		this.setupMainPieces(7, PlayerColour.BLACK);
		this.setupPawns(6, PlayerColour.BLACK);
		
		this.turn = PlayerColour.WHITE;
		this.selectedPos = null;
		
		System.out.println("Chess Board Initialized");
		setChangedAndNotify();
	}

	//Probably should be using contains, seems like I gotta do hashcode as well tho
	private boolean isHighlightedPos(Pair<Integer, Integer> pos){
		if (this.highlightedPos == null){
			return true;
		}
		for (Pair<Integer, Integer> hpos : this.highlightedPos){
			if (pos.equals(hpos)){
				return true;
			}
		}
		return false;
	}
	
	private void unselectPosition(){
		for (Pair<Integer, Integer> hpos : this.highlightedPos){
			this.chessBoard.setState(hpos, BoardCellState.DEFAULT);
		}
		this.chessBoard.setState(selectedPos, BoardCellState.DEFAULT);
		this.selectedPos = null;
		this.setChangedAndNotify();
	}
	
	public void selectPosition(Pair<Integer, Integer> pos){
		
		ChessPiece piece = this.chessBoard.getPiece(pos);
		
		
		if (null != this.selectedPos){
			if (this.selectedPos.equals(pos)){
				System.out.println("Unselecting Position");
				this.unselectPosition();
				return;
			}
			else if(this.isHighlightedPos(pos)){
				this.movePiece(this.selectedPos,pos);
				this.unselectPosition();
				this.swapTurns();
				System.out.println("Turn: " + this.turn);
				return;
			}
			this.unselectPosition();
		}
		
		if (null == piece){
			return;
		}
		if ( piece.getPlayerColour() != this.turn){
			System.out.println("Not their turn");
			return;
		}
		this.selectedPos = pos;
		this.chessBoard.setState(selectedPos, BoardCellState.SELECTED);
		
		this.highlightedPos = piece.getPossibleMoves();
		for (Pair<Integer, Integer> hpos : this.highlightedPos){
			this.chessBoard.setState(hpos, BoardCellState.HIGHLIGHTED);
		}
		this.setChangedAndNotify();
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
		System.out.println("Moving Piece");
		ChessPiece startPiece = this.chessBoard.getPiece(start);
		ChessPiece endPiece = this.chessBoard.getPiece(end);
		
		if (null != endPiece){
			System.out.println("Captured piece :(" + end.getFirst() + ", " + end.getSecond() + ")");
			this.removePiece(endPiece);
		}
		
		startPiece.setPosition(end);
		this.chessBoard.setPiece(end, startPiece);
		this.chessBoard.setPiece(start, null);
		this.setChangedAndNotify();
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
				break;
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
				break;
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
