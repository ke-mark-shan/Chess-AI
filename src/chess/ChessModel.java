package chess;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.undo.*;

public class ChessModel extends Observable{
	
	private UndoManager undoManager;
	private Player playerWhite;
	private Player playerBlack;
	private ChessBoard chessBoard;
	private PlayerColour turn;
	private Position selectedPos;
	private ArrayList<Position> highlightedPos;	
	
	public ChessModel(Player p1, Player p2){
		this.initializeBoard(p1, p2);
	}
	
	// Getters and Setters
	public Player getPlayer(PlayerColour pc){
		
		switch (pc){
			case WHITE:
				return this.playerWhite;
			case BLACK:
				return this.playerBlack;
			default:
				System.err.println("Unexpected PlayerColour: " + pc);
				return null;
		}
	}
	
	public ChessBoard getBoard(){
		return this.chessBoard;
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
				break;
			case BLACK:
				this.turn = PlayerColour.WHITE;
				break;
		}
		
		Player currPlayer = this.getPlayer(this.turn);

		if (this.inCheck(this.turn)){
			System.out.println("Check");
			this.chessBoard.setState(currPlayer.king.getPosition(), BoardCellState.INCHECK);
			
			if (!this.hasPossibleMoves(this.turn)){
				System.out.println("Checkmate");
				this.chessBoard.setState(currPlayer.king.getPosition(), BoardCellState.INCHECKMATE);
				return;
			}
		}
		else{
			this.chessBoard.setState(currPlayer.king.getPosition(), BoardCellState.DEFAULT);
			
			if (!this.hasPossibleMoves(this.turn)){
				System.out.println("Stalemate");
				return;
			}
		}
		
		currPlayer.startTurn();
		
	}

	private void setupMainPieces(int row, PlayerColour pc){
		
		Player owner = this.getPlayer(pc);
		
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
		Player owner = this.getPlayer(pc);
		
		for (int column = 0; column < BOARD_SIZE; column++){
			toAdd = new Pawn(this, pc, new Position(column, row));
			owner.pawns.add(toAdd);
			this.chessBoard.setPiece(new Position(column, row), toAdd);
		}
	}
	
	public void initializeBoard(Player p1, Player p2){
		
		this.undoManager = new UndoManager();
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
		
		p1.setModel(this);
		p2.setModel(this);
		
		setChangedAndNotify();
	}

	// Probably should be using contains, seems like I gotta do hashcode as well tho
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
		
		Player currPlayer = this.getPlayer(this.turn);
		
		if (currPlayer.getPlayerType() == PlayerType.HUMAN){
			
			ChessPiece piece = this.chessBoard.getPiece(pos);
		
			if (null != this.selectedPos){
				
				if (this.selectedPos.equals(pos)){
					
					System.out.println("Unselecting Position");
					this.unselectPosition();
					this.setChangedAndNotify();
					return;
				}
				else if(this.isHighlightedPos(pos)){
					
					currPlayer.makeMove(this.selectedPos, pos);
					this.unselectPosition();
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
			this.chessBoard.setState(pos, BoardCellState.SELECTED);
			
			this.highlightedPos = piece.getPossibleMoves();
			for (Position hpos : this.highlightedPos){
				this.chessBoard.setState(hpos, BoardCellState.HIGHLIGHTED);
			}
			this.setChangedAndNotify();
		}
		
	}
	
	// Returns the first ChessPiece of PlayerColour pc from start going in the direction of (dCol, dRow)
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
	
	// Returns whether piece at (myCol, myRow) can be attacked by a pawn on column col
	public boolean canBeAttackedByPawn(Player opponent, int myCol, int myRow, int col){
		
		if (this.inBoard(col) && this.inBoard(myRow - opponent.king.getDirectionMultiplier())){
			ChessPiece checkPiece = this.chessBoard.getPiece(new Position(col, myRow - opponent.king.getDirectionMultiplier()));
			
			if (null != checkPiece && 
				checkPiece.getPlayerColour() == opponent.getPlayerColour() &&
				checkPiece.getType() == ChessPieceType.PAWN){
				return true;
			}
		}
		return false;
	}
	
	// Returns whether a position can be attacked by any enemy piece
	public boolean canBeAttackeByEnemy(PlayerColour opponentColour, Position pos){
		Player opponent = this.getPlayer(opponentColour);
		
		int col = pos.getFirst();
		int row = pos.getSecond();
		
		// Attacks in 'cardinal directions'
		if (opponent.queen != null || opponent.rooks.size() > 0){
			ChessPiece firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), col, row, 0, 1);
			if (null != firstInDir &&(firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), col, row, 1, 0);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), col, row, 0, -1);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), col, row, -1, 0);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.ROOK)){
				return true;
			}			
		}

		// Diagonal attacks
		if (opponent.queen != null || opponent.bishops.size() > 0){
			ChessPiece firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), col, row, 1, 1);
			if (null != firstInDir &&(firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.BISHOP)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), col, row, 1, -1);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.BISHOP)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), col, row, -1, 1);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.BISHOP)){
				return true;
			}
			
			firstInDir = this.getFirstInDirection(opponent.getPlayerColour(), col, row, -1, -1);
			if (null != firstInDir && (firstInDir.getType() == ChessPieceType.QUEEN || firstInDir.getType() == ChessPieceType.BISHOP)){
				return true;
			}
		}
		
		if (opponent.knights.size() > 0){
			for (Knight k : opponent.knights){
				int knightCol = k.getPosition().getFirst();
				int knightRow = k.getPosition().getSecond();
				
				if ((Math.abs(knightCol - col) == 1 && Math.abs(knightRow - row) == 2) ||
					(Math.abs(knightCol - col) == 2 && Math.abs(knightRow - row) == 1)){
					return true;
				}
			}
		}
		
		if (opponent.pawns.size() > 0){
			if (this.canBeAttackedByPawn(opponent, col, row, col - 1) ||
				this.canBeAttackedByPawn(opponent, col, row, col + 1)){
				return true;
			}
		}
		
		int enecol = opponent.king.getPosition().getFirst();
		int enerow = opponent.king.getPosition().getSecond();
		
		if (Math.abs(enecol - col) <= 1 && Math.abs(enerow - row) <= 1){
			return true;
		}
		
		return false;
	}
	// Returns whether the player with colour pc is in check
	public boolean inCheck(PlayerColour pc){
		
		King myKing = this.playerWhite.king;
		PlayerColour opponentColour = PlayerColour.BLACK;
		
		if (pc == PlayerColour.BLACK)
		{
			myKing = this.playerBlack.king;
			opponentColour = PlayerColour.WHITE;
		}
				
		return this.canBeAttackeByEnemy(opponentColour, myKing.getPosition());
	}
	
	// Returns whether player pc has any moves
	public boolean hasPossibleMoves(PlayerColour pc){
		
		Player player = this.getPlayer(pc);
		
		// King
		if (null != player.king && player.king.getPossibleMoves().size() > 0){
			return true;
		}
		
		// Queen
		if (null != player.queen && player.queen.getPossibleMoves().size() > 0){
			return true;
		}
		
		// Rooks
		for (Rook r : player.rooks){
			if (r.getPossibleMoves().size() > 0){
				return true;
			}
		}
		
		// Bishops
		for (Bishop b : player.bishops){
			if (b.getPossibleMoves().size() > 0){
				return true;
			}
		}
		
		// Knights
		for (Knight k : player.knights){
			if (k.getPossibleMoves().size() > 0){
				return true;
			}
		}
		
		// Pawns
		for (Pawn p : player.pawns){
			if (p.getPossibleMoves().size() > 0){
				return true;
			}
		}
		
		return false;
	}
	
	// Returns whether moving ChessPiece p to position newPos will put its owner in check
	public boolean tryMoveCheck(ChessPiece p, Position newPos){
		
		boolean inCheck = false;
		Position oldPos = p.getPosition();
//		ChessPiece removedPiece = this.getBoard().getPiece(newPos);
		
		this.movePiece(oldPos, newPos, false);
		inCheck =  this.inCheck(p.getPlayerColour());
		
//		// Restore previous board state
//		this.movePiece(newPos, oldPos, false);
//		if (removedPiece != null){
//			this.addPiece(removedPiece);
//		}
		this.undoManager.undo();
		return inCheck;
	}
	
	// Moves piece at start to end
	public void movePiece(Position start, Position end, boolean actualMove){
		
		ChessPiece startPiece = this.chessBoard.getPiece(start);
		ChessPiece endPiece = this.chessBoard.getPiece(end);
		UndoableEdit undoableEdit = new AbstractUndoableEdit() {
			
			public void redo() throws CannotRedoException {
				super.redo();
				
				if (null != endPiece){
					removePiece(endPiece);
				}
				
				System.out.println("Redo: " + start.toString() + end.toString());
				startPiece.setPosition(end, actualMove);
				chessBoard.setPiece(end, startPiece);
				chessBoard.setPiece(start, null);
				setChangedAndNotify();
			}
			public void undo() throws CannotUndoException {
				super.undo();
				
				System.out.println("Undo: " + end.toString() + start.toString());
				startPiece.setPosition(start, actualMove);
				chessBoard.setPiece(start, startPiece);
				chessBoard.setPiece(end, null);
				if (endPiece != null){
					addPiece(endPiece);
				}
				setChangedAndNotify();
			}
		};
		
		if (null != endPiece){
			this.removePiece(endPiece);
		}
		
		if (actualMove){
			System.out.println("MovePiece: " + start.toString() + end.toString());
		}
		
		if (startPiece.getType() == ChessPieceType.KING && !startPiece.getMadeMove()){
			// Castling
			if (end.getFirst() - start.getFirst() == 2){
				// Castle to right
				Position rookStart = new Position(7, startPiece.getFirstRank());
				Position rookEnd = new Position(5, startPiece.getFirstRank());
				ChessPiece castlingRook = this.chessBoard.getPiece(rookStart);

				castlingRook.setPosition(rookEnd, actualMove);
				this.chessBoard.setPiece(rookEnd, castlingRook);
				this.chessBoard.setPiece(rookStart, null);
				
				undoableEdit = new AbstractUndoableEdit() {
			
					public void redo() throws CannotRedoException {
						super.redo();
						
						// King
						startPiece.setPosition(end, actualMove);
						chessBoard.setPiece(end, startPiece);
						chessBoard.setPiece(start, null);
						
						//Rook
						castlingRook.setPosition(rookEnd, actualMove);
						chessBoard.setPiece(rookEnd, castlingRook);
						chessBoard.setPiece(rookStart, null);
						setChangedAndNotify();
					}
					public void undo() throws CannotUndoException {
						super.undo();
						
						// King
						startPiece.setPosition(start, actualMove);
						chessBoard.setPiece(start, startPiece);
						chessBoard.setPiece(end, null);
						
						//Rook
						castlingRook.setPosition(rookStart, actualMove);
						chessBoard.setPiece(rookStart, castlingRook);
						chessBoard.setPiece(rookEnd, null);
						setChangedAndNotify();
						setChangedAndNotify();
					}
				};
			}
			else if (end.getFirst() - start.getFirst() == -2){
				Position rookStart = new Position(0, startPiece.getFirstRank());
				Position rookEnd = new Position(3, startPiece.getFirstRank());
				ChessPiece castlingRook = this.chessBoard.getPiece(rookStart);
				
				castlingRook.setPosition(rookEnd, actualMove);
				this.chessBoard.setPiece(rookEnd, castlingRook);
				this.chessBoard.setPiece(rookStart, null);
				
				undoableEdit = new AbstractUndoableEdit() {

					public void redo() throws CannotRedoException {
						super.redo();
						
						// King
						startPiece.setPosition(end, actualMove);
						chessBoard.setPiece(end, startPiece);
						chessBoard.setPiece(start, null);
						
						//Rook
						castlingRook.setPosition(rookEnd, actualMove);
						chessBoard.setPiece(rookEnd, castlingRook);
						chessBoard.setPiece(rookStart, null);
						setChangedAndNotify();
					}
					public void undo() throws CannotUndoException {
						super.undo();
						
						// King
						startPiece.setPosition(start, actualMove);
						chessBoard.setPiece(start, startPiece);
						chessBoard.setPiece(end, null);
						
						//Rook
						castlingRook.setPosition(rookStart, actualMove);
						chessBoard.setPiece(rookStart, castlingRook);
						chessBoard.setPiece(rookEnd, null);
						setChangedAndNotify();
					}
				};
			}

		}

		startPiece.setPosition(end, actualMove);
		this.chessBoard.setPiece(end, startPiece);
		this.chessBoard.setPiece(start, null);

		this.undoManager.addEdit(undoableEdit);
		this.setChangedAndNotify();
	}
	
	public void addPiece(ChessPiece p){
		
		this.chessBoard.setPiece(p.getPosition(),p);
		ChessPieceType type = p.getType();
		
		Player pieceOwner = this.getPlayer(p.getPlayerColour());

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
		
		Player pieceOwner = this.getPlayer(p.getPlayerColour());
		
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
		
	//Undo functions
    public void undoMove() {
		if (this.canUndoMove())
			undoManager.undo();
	}
	public void redoMove() {
		if (this.canRedoMove())
			undoManager.redo();
	}
	public boolean canUndoMove() {
		return undoManager.canUndo();
	}
	public boolean canRedoMove() {
		return undoManager.canRedo();
	}
	
	// Notify observers of changes
    public void setChangedAndNotify() {
    	
        setChanged();
		notifyObservers();
	}
}
