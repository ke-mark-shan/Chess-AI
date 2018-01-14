package chess;

// Records pieces on the chess board
// Coordinates: (column, row) as described by Algebraic Notation
// A cell on the board is either a chess piece or null

public class ChessBoard {
	private static final int BOARD_SIZE = 8;
	
	private ChessPiece[][] board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
	
	//Initialize chess board with pieces in appropriate places
	public ChessBoard(){
		for (int column = 0; column < BOARD_SIZE; column++){
			for (int row = 0; row < BOARD_SIZE; row++){
				board[column][row] = null;
			}
		}
		
		// White Pieces
		for (int column = 0; column < BOARD_SIZE; column++){
			board[column][1] = new Pawn(PlayerColour.WHITE);
		}
		
		// Black Pieces
		for (int column = 0; column < BOARD_SIZE; column++){
			board[column][6] = new Pawn(PlayerColour.BLACK);
		}
	}
	public ChessPiece getPiece(int row, int column){
		return board[column - 1][row - 1];
	}
	public void setPiece(int row, int column, ChessPiece p){
		board[column - 1][row - 1] = p;
	}
}
