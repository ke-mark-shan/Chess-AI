package chess;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Observable;
public class ChessModel extends Observable{
	private Player playerWhite;
	private Player playerBlack;
	private ChessBoard chessBoard;
	private Rectangle2D boardDimensions;
	private PlayerColour turn;
	private Position selectedPos;
	private ArrayList<Position> highlightedPos;	//Highlighted positions
	
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
		return (0 <= i && i < this.getBoard().getBoardSize());
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
		
		Player owner = this.playerWhite;
		
		if (pc == PlayerColour.BLACK){
			owner = this.playerBlack;
		}
		
		ChessPiece toAdd = new Rook(this, pc, new Position(0, row));
		owner.rooks.add((Rook) toAdd);
		this.chessBoard.setPiece(new Position(0, row), toAdd);
		
		toAdd = new Knight(this, pc, new Position(1, row));
		owner.knights.add((Knight) toAdd);
		this.chessBoard.setPiece(new Position(1, row), toAdd);
		
		toAdd = new Bishop(this, pc, new Position(2, row));
		owner.bishops.add((Bishop) toAdd);
		this.chessBoard.setPiece(new Position(2, row), toAdd);
		
		toAdd = new Queen(this, pc, new Position(3, row));
		owner.queen = (Queen) toAdd;
		this.chessBoard.setPiece(new Position(3, row), toAdd);
		
		toAdd = new King(this, pc, new Position(4, row));
		owner.king = (King) toAdd;
		this.chessBoard.setPiece(new Position(4, row), toAdd);
		
		toAdd = new Bishop(this, pc, new Position(5, row));
		owner.bishops.add((Bishop) toAdd);
		this.chessBoard.setPiece(new Position(5, row), toAdd);
		
		toAdd = new Knight(this, pc, new Position(6, row));
		owner.knights.add((Knight) toAdd);
		this.chessBoard.setPiece(new Position(6, row), toAdd);
		
		toAdd =  new Rook(this, pc, new Position(7, row));
		owner.rooks.add((Rook) toAdd);
		this.chessBoard.setPiece(new Position(7, row), toAdd);
	}
	
	private void setupPawns(int row, PlayerColour pc){
		
		final int BOARD_SIZE = this.getBoard().getBoardSize();
		
		Pawn toAdd;
		Player owner = this.playerWhite;
		
		if (pc == PlayerColour.BLACK){
			owner = this.playerBlack;
		}
		
		for (int column = 0; column < BOARD_SIZE; column++){
			toAdd = new Pawn(this, pc, new Position(column, row));
			owner.pawns.add(toAdd);
			this.chessBoard.setPiece(new Position(column, row), toAdd);
		}
	}
	
	public void initializeBoard(Player p1, Player p2){
		
		this.chessBoard = new ChessBoard();
		this.turn = PlayerColour.WHITE;
		this.selectedPos = null;
		this.highlightedPos = null;
		this.playerWhite = p1;
		this.playerBlack = p2;
		
		final int BOARD_SIZE = this.chessBoard.getBoardSize();
		
		for (int column = 0; column < BOARD_SIZE; column++){
			for (int row = 0; row < BOARD_SIZE; row++){
				this.chessBoard.setPiece(new Position(column, row), null);
			}
		}
		
		this.setupMainPieces(0, PlayerColour.WHITE);
		this.setupPawns(1, PlayerColour.WHITE);
		this.setupMainPieces(7, PlayerColour.BLACK);
		this.setupPawns(6, PlayerColour.BLACK);
		
		this.turn = PlayerColour.WHITE;
		this.selectedPos = null;
		
		setChangedAndNotify();
	}

	//Probably should be using contains, seems like I gotta do hashcode as well tho
	private boolean isHighlightedPos(Position pos){
		if (this.highlightedPos == null){
			return true;
		}
		for (Position hpos : this.highlightedPos){
			if (pos.equals(hpos)){
				return true;
			}
		}
		return false;
	}
	
	private void unselectPosition(){
		for (Position hpos : this.highlightedPos){
			this.chessBoard.setState(hpos, BoardCellState.DEFAULT);
		}
		this.chessBoard.setState(selectedPos, BoardCellState.DEFAULT);
		this.selectedPos = null;
		this.setChangedAndNotify();
	}
	
	// Processes result of clicking a cell
	public void selectPosition(Position pos){
		
		ChessPiece piece = this.chessBoard.getPiece(pos);
		
		if (null != this.selectedPos){
			if (this.selectedPos.equals(pos)){
				System.out.println("Unselecting Position");
				this.unselectPosition();
				this.setChangedAndNotify();
				return;
			}
			else if(this.isHighlightedPos(pos)){
				this.movePiece(this.selectedPos, pos, true);
				this.unselectPosition();
				this.swapTurns();
				System.out.println("Turn: " + this.turn);
				this.setChangedAndNotify();
				return;
			}
			this.unselectPosition();
		}
		
		if (null == piece){
			return;
		}
		if (piece.getPlayerColour() != this.turn){
			System.out.println("Not their turn");
			return;
		}
		this.selectedPos = pos;
		this.chessBoard.setState(selectedPos, BoardCellState.SELECTED);
		
		this.highlightedPos = piece.getPossibleMoves();
		for (Position hpos : this.highlightedPos){
			this.chessBoard.setState(hpos, BoardCellState.HIGHLIGHTED);
		}
		this.setChangedAndNotify();
	}
	
	// returns the first ChessPiece of PlayerColour pc from start going in the direction of (dCol, dRow)
	private ChessPiece getFirstInDirection(PlayerColour pc, int startCol, int startRow, int dCol, int dRow){
		if (dCol == 0 && dRow == 0){
			System.err.println("Both dcol,drow are 0");
			return null;
		}
		
		int currCol = startCol;
		int currRow = startRow;
		
		while (true){
			currCol += dCol;
			currRow += dRow;
			
			if (!this.inBoard(currCol) || !this.inBoard(currRow)){
				return null;
			}
			else{
				ChessPiece piece = this.chessBoard.getPiece(new Position(currCol, currRow));
				
				if (null != piece){
					if (piece.getPlayerColour() == pc){
						return piece;
					}
					return null;
				}
				
			}
		}				
	}
	
	//Returns whether piece at (myCol, myRow) can be attacked by a pawn on column col
	public boolean canBeAttackedByPawn(PlayerColour opponent, int myCol, int myRow, int col, int direction){
		if (this.inBoard(col) && this.inBoard(myRow + direction)){
			ChessPiece checkPiece = this.chessBoard.getPiece(new Position(col, myRow + direction));
			
			if (null != checkPiece && 
				checkPiece.getPlayerColour() == opponent &&
				checkPiece.getType() == ChessPieceType.PAWN){
				return true;
			}
		}
		return false;
	}
	
	// Returns whether the player with colour pc is in check
	public boolean inCheck(PlayerColour pc){
		
		King myKing = this.playerWhite.king;
		Player opponent = this.playerBlack;
		
		if (pc == PlayerColour.BLACK)
		{
			myKing = this.playerBlack.king;
			opponent = this.playerWhite;
		}
		
		int myKingCol = myKing.getPosition().getFirst();
		int myKingRow = myKing.getPosition().getSecond();
		
		//Look for all ways pc's king could be attacked
		if (opponent.queen != null || opponent.rooks.size() > 0){
			ChessPiece firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), myKingCol, myKingRow, 0, 1);
			if (null != firstInDir &&(firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), myKingCol, myKingRow, 1, 0);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), myKingCol, myKingRow, 0, -1);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), myKingCol, myKingRow, -1, 0);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}
			
		}

		if (opponent.queen != null || opponent.bishops.size() > 0){
			ChessPiece firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), myKingCol, myKingRow, 1, 1);
			if (null != firstInDir &&(firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), myKingCol, myKingRow, 1, -1);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), myKingCol, myKingRow, -1, 1);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), myKingCol, myKingRow, -1, -1);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}
		}
		
		if (opponent.knights.size() > 0){
			for (Knight k : opponent.knights){
				int knightCol = k.getPosition().getFirst();
				int knightRow = k.getPosition().getSecond();
				
				if ((Math.abs(knightCol - myKingCol) == 1 && Math.abs(knightRow - myKingRow) == 2) ||
					(Math.abs(knightCol - myKingCol) == 2 && Math.abs(knightRow - myKingRow) == 1)){
					return true;
				}
			}
		}
		if (opponent.pawns.size() > 0){
			if (this.canBeAttackedByPawn(opponent.getPlayerColour(), myKingCol, myKingRow, myKingCol - 1, myKing.getDirectionMultiplier()) ||
				this.canBeAttackedByPawn(opponent.getPlayerColour(), myKingCol, myKingRow, myKingCol + 1, myKing.getDirectionMultiplier())){
				return true;
			}
		}
		
		return false;
	}
	
	// Returns whether moving ChessPiece p to position newPos will put its owner in check
	public boolean tryMoveCheck(ChessPiece p, Position newPos){
		
		boolean inCheck = false;
		Position oldPos = p.getPosition();
		ChessPiece removedPiece = this.getBoard().getPiece(newPos);
		
		this.movePiece(oldPos, newPos, false);
		inCheck =  this.inCheck(p.getPlayerColour());
		
		//Restore previous board state
		this.movePiece(newPos, oldPos, false);
		if (removedPiece != null){
			this.addPiece(removedPiece);
		}
		return inCheck;
	}
	
	// Moves piece at start to end
	public void movePiece(Position start, Position end, boolean actualMove){
		
		ChessPiece startPiece = this.chessBoard.getPiece(start);
		ChessPiece endPiece = this.chessBoard.getPiece(end);
		
		if (null != endPiece){
			this.removePiece(endPiece);
		}
		
		startPiece.setPosition(end, actualMove);
		this.chessBoard.setPiece(end, startPiece);
		this.chessBoard.setPiece(start, null);
	}
	
	public void addPiece(ChessPiece p){
		this.chessBoard.setPiece(p.getPosition(),p);
		ChessPieceType type = p.getType();
		
		Player pieceOwner = this.playerWhite;
		if (p.getPlayerColour() == PlayerColour.BLACK){
			pieceOwner = this.playerBlack;
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
		if (null == p) return;
		
		this.chessBoard.setPiece(p.getPosition(),null);
		ChessPieceType type = p.getType();
		
		Player pieceOwner = this.playerWhite;
		if (p.getPlayerColour() == PlayerColour.BLACK){
			pieceOwner = this.playerBlack;
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
