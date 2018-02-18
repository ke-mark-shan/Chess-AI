package chessTests;

import static org.junit.Assert.*;
import chess.*;
import java.util.ArrayList;
import org.junit.Test;

//Tests King.getPossibleMoves
public class KingGetMoves extends ChessTestBase {

	public KingGetMoves(){
		super();
	}
	
	@Test
	public void test() {
		System.out.println("--King Tests: Start--");
		System.out.println();
		
		this.allMovements();
		this.boundaries();
		this.moveAndAttacks();
		this.moveAndInCheck();
		this.moveStopCheck();
		
		System.out.println();
		System.out.println("--King Tests: Done--");
	}

	//Tests all King moves without attacks
	public void allMovements(){
		System.out.println("allMovements: Start");
		this.reset();
		this.model.removePiece(this.whiteKing);
		
		this.whiteKing = new King(this.model, PlayerColour.WHITE, new Position(3,4));
		
		ArrayList<Position> testMoves;
		this.model.addPiece(this.whiteKing);
		testMoves = this.whiteKing.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		int[] moveCol = new int[]{-1,1};
		int[] moveRow = new int[]{-1,1};
		
		for (int dCol : moveCol){
			for (int dRow : moveRow){
				expectedMoves.add(new Position(3 + dCol, 4 + dRow));
			}
		}
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("allMovemovements: Done");
	}
	
	//Tests King moves that may be outside the board
	public void boundaries(){
		System.out.println("boundaries: Start");
		this.reset();
		this.model.removePiece(this.whiteKing);
		
		this.whiteKing = new King(this.model, PlayerColour.WHITE, new Position(0,0));
		
		ArrayList<Position> testMoves;
		this.model.addPiece(this.whiteKing);
		testMoves = this.whiteKing.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		expectedMoves.add(new Position(1,0));
		expectedMoves.add(new Position(1,1));
		expectedMoves.add(new Position(0,1));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("boundaries: Done");
	}

	//Tests King blocked moves and attacks
	public void moveAndAttacks(){
		System.out.println("moveAndAttacks: Start");
		this.reset();
		this.model.removePiece(this.whiteKing);
		
		this.whiteKing = new King(this.model, PlayerColour.WHITE, new Position(3,4));
		
		ArrayList<Position> testMoves;
		this.model.addPiece(this.whiteKing);
		testMoves = this.whiteKing.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		expectedMoves.add(new Position(2,4));
		expectedMoves.add(new Position(2,5));
		expectedMoves.add(new Position(3,5));
		expectedMoves.add(new Position(4,5));
		expectedMoves.add(new Position(4,4));

		expectedMoves.add(new Position(3,3));
		expectedMoves.add(new Position(2,3));
		
		Pawn testTarget1 = new Pawn(this.model, PlayerColour.BLACK, new Position(2,5));

		Pawn testBlocker1 = new Pawn(this.model, PlayerColour.WHITE, new Position(4,3));
		
		Pawn testTarget2 = new Pawn(this.model, PlayerColour.BLACK, new Position(3,3));
				
		this.model.addPiece(testTarget1);
		this.model.addPiece(testTarget2);
		this.model.addPiece(testBlocker1);
		testMoves = this.whiteKing.getPossibleMoves();
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveAndAttacks: Done");
	}

	//Tests when King moving may put itself in check
	public void moveAndInCheck(){
		System.out.println("moveAndInCheck: Start");
		this.reset();
			
		this.reset();
		this.model.removePiece(this.whiteKing);
		
		this.whiteKing = new King(this.model, PlayerColour.WHITE, new Position(6,1));
		
		Queen checkQueen = new Queen(this.model, PlayerColour.BLACK, new Position(0,0));

		ArrayList<Position> testMoves;
		this.model.addPiece(this.whiteKing);
		this.model.addPiece(checkQueen);
		
		testMoves = this.whiteKing.getPossibleMoves();

		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		expectedMoves.add(new Position(5,1));
		expectedMoves.add(new Position(5,2));
		expectedMoves.add(new Position(6,2));
		expectedMoves.add(new Position(7,2));
		expectedMoves.add(new Position(7,1));
				
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveAndInCheck: Done");
	}
	
	//Tests when King can move to stop the itself from being in check. 
	public void moveStopCheck(){
		System.out.println("moveStopCheck: Start");
		this.reset();
		
		this.reset();
		this.model.removePiece(this.whiteKing);
		
		this.whiteKing = new King(this.model, PlayerColour.WHITE, new Position(6,0));
		
		Queen checkQueen = new Queen(this.model, PlayerColour.BLACK, new Position(0,0));

		ArrayList<Position> testMoves;
		this.model.addPiece(this.whiteKing);
		this.model.addPiece(checkQueen);
		
		testMoves = this.whiteKing.getPossibleMoves();

		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		expectedMoves.add(new Position(5,1));
		expectedMoves.add(new Position(6,1));
		expectedMoves.add(new Position(7,1));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveStopCheck: Done");
	}
}
