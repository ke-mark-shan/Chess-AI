package chessTests;

import static org.junit.Assert.*;
import chess.*;
import java.util.ArrayList;
import org.junit.Test;

//Tests Bishop.getPossibleMoves
public class BishopGetMoves extends ChessTestBase {

	public BishopGetMoves(){
		super();
	}
	
	@Test
	public void test() {
		System.out.println("--Bishop Tests: Start--");
		System.out.println();
		
		this.allMovements();
		this.moveAndAttacks();
		this.moveAndInCheck();
		this.moveStopCheck();
		
		System.out.println();
		System.out.println("--Bishop Tests: Done--");
	}

	//Tests all Bishop moves without attacks
	public void allMovements(){
		System.out.println("allMovements: Start");
		this.reset();
		
		Bishop testBishop = new Bishop(this.model, PlayerColour.WHITE, new Position(2,4));
		ArrayList<Position> testMoves;
		this.model.addPiece(testBishop);
		testMoves = testBishop.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		//Down-Left
		expectedMoves.add(new Position(1,3));
		expectedMoves.add(new Position(0,2));
		//Up-Left
		expectedMoves.add(new Position(1,5));
		expectedMoves.add(new Position(0,6));
		//Up-Right
		expectedMoves.add(new Position(3,5));
		expectedMoves.add(new Position(4,6));
		expectedMoves.add(new Position(5,7));
		//Down-Right
		expectedMoves.add(new Position(3,3));
		expectedMoves.add(new Position(4,2));
		expectedMoves.add(new Position(5,1));
		expectedMoves.add(new Position(6,0));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("allMovemovements: Done");
	}

	//Tests all Bishop moves and attacks
	public void moveAndAttacks(){
		System.out.println("moveAndAttacks: Start");
		this.reset();
			
		Bishop testBishop = new Bishop(this.model, PlayerColour.WHITE, new Position(2,4));
		ArrayList<Position> testMoves;
		this.model.addPiece(testBishop);
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		//Down-Left
		expectedMoves.add(new Position(1,3));
		expectedMoves.add(new Position(0,2));
		Pawn testTarget1 = new Pawn(this.model, PlayerColour.BLACK, new Position(0,2));

		//Up-Left
		Pawn testBlocker1 = new Pawn(this.model, PlayerColour.WHITE, new Position(1,5));

		//Up-Right
		expectedMoves.add(new Position(3,5));
		expectedMoves.add(new Position(4,6));
		expectedMoves.add(new Position(5,7));
		
		//Down-Right
		expectedMoves.add(new Position(3,3));
		Pawn testTarget2 = new Pawn(this.model, PlayerColour.BLACK, new Position(3,3));
		
		this.model.addPiece(testTarget1);
		this.model.addPiece(testTarget2);
		this.model.addPiece(testBlocker1);
		testMoves = testBishop.getPossibleMoves();
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveAndAttacks: Done");
	}

	//Tests when Bishop moving may put its king in check
	public void moveAndInCheck(){
		System.out.println("moveAndInCheck: Start");
		this.reset();
			
		Bishop testBishop = new Bishop(this.model, PlayerColour.WHITE, new Position(2,0));
		
		Queen checkQueen = new Queen(this.model, PlayerColour.BLACK, new Position(0,0));
		ArrayList<Position> testMoves;
		
		this.model.addPiece(testBishop);
		this.model.addPiece(checkQueen);
		
		testMoves = testBishop.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveAndInCheck: Done");
	}
	
	//Tests when Bishop can move to stop the king from being in check
	public void moveStopCheck(){
		System.out.println("moveStopCheck: Start");
		this.reset();
			
		Bishop testBishop = new Bishop(this.model, PlayerColour.WHITE, new Position(2,1));
		
		Queen checkQueen = new Queen(this.model, PlayerColour.BLACK, new Position(0,0));
		ArrayList<Position> testMoves;
		
		this.model.addPiece(testBishop);
		this.model.addPiece(checkQueen);
		
		testMoves = testBishop.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
			
		expectedMoves.add(new Position(1,0));
		expectedMoves.add(new Position(3,0));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveStopCheck: Done");
	}
}
