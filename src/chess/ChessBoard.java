package chess;

// Records pieces on the chess board
// Coordinates: (column, row) as described by Algebraic Notation
// A cell on the board is either a chess piece or null

public class ChessBoard {
	public static final int BOARD_SIZE = 8;
	
	private ChessPiece[][] board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
	private BoardCellState[][] states = new BoardCellState[BOARD_SIZE][BOARD_SIZE];
	
	public ChessBoard(){
		for (int column = 0; column < BOARD_SIZE; column++){
			for (int row = 0; row < BOARD_SIZE; row++){
				this.states[column][row] = BoardCellState.DEFAULT;
			}
		}
	}
	
	// Getters and  Setters
	public ChessPiece getPiece(Pair<Integer,Integer> pos){
		return board[pos.getFirst()][pos.getSecond()];
	}
	public void setPiece(Pair<Integer,Integer> pos, ChessPiece p){
		board[pos.getFirst()][pos.getSecond()] = p;
	}
	
	public BoardCellState getState(Pair<Integer, Integer> pos){
		return states[pos.getFirst()][pos.getSecond()];
	}
	
	public void setState(Pair<Integer,Integer> pos, BoardCellState s){
		states[pos.getFirst()][pos.getSecond()] = s;
	}
	
}
