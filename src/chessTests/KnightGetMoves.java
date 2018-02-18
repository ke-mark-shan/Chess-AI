package chessTests;

import static org.junit.Assert.*;
import chess.*;
import java.util.ArrayList;
import org.junit.Test;

//Tests Knight.getPossibleMoves
public class KnightGetMoves extends ChessTestBase {

	public KnightGetMoves(){
		super();
	}
	
	@Test
	public void test() {
		System.out.println("--Knight Tests: Start--");
		System.out.println();
		
		this.allMovements();
		this.boundaries();
		this.moveAndAttacks();
		this.moveAndInCheck();
		this.moveStopCheck();
		
		System.out.println();
		System.out.println("--Knight Tests: Done--");
	}

	//Tests all Knight blocked moves without attacks
	public void allMovements(){
		System.out.println("allMovements: Start");
		this.reset();
		
		Knight testKnight = new Knight(this.model, PlayerColour.WHITE, new Position(3,4));
		ArrayList<Position> testMoves;
		this.model.addPiece(testKnight);
		testMoves = testKnight.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		expectedMoves.add(new Position(2,6));
		expectedMoves.add(new Position(4,6));
		expectedMoves.add(new Position(5,5));
		expectedMoves.add(new Position(1,5));
		
		expectedMoves.add(new Position(5,3));
		expectedMoves.add(new Position(1,3));
		expectedMoves.add(new Position(2,2));
		expectedMoves.add(new Position(4,2));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("allMovemovements: Done");
	}
	
	//Tests Knight moves that may be outside the board
	public void boundaries(){
		System.out.println("boundaries: Start");
		this.reset();
		
		Knight testKnight = new Knight(this.model, PlayerColour.WHITE, new Position(0,0));
		ArrayList<Position> testMoves;
		this.model.addPiece(testKnight);
		testMoves = testKnight.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		expectedMoves.add(new Position(1,2));
		expectedMoves.add(new Position(2,1));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("boundaries: Done");
	}

	//Tests Knight moves and attacks
	public void moveAndAttacks(){
		System.out.println("moveAndAttacks: Start");
		this.reset();
			
		Knight testKnight = new Knight(this.model, PlayerColour.WHITE, new Position(3,4));
		ArrayList<Position> testMoves;
		this.model.addPiece(testKnight);
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		expectedMoves.add(new Position(2,6));
		expectedMoves.add(new Position(4,6));
		expectedMoves.add(new Position(5,5));
		expectedMoves.add(new Position(1,5));
		
		expectedMoves.add(new Position(1,3));
		expectedMoves.add(new Position(2,2));
		expectedMoves.add(new Position(4,2));
		
		Pawn testTarget1 = new Pawn(this.model, PlayerColour.BLACK, new Position(2,6));

		Pawn testBlocker1 = new Pawn(this.model, PlayerColour.WHITE, new Position(5,3));
		
		Pawn testTarget2 = new Pawn(this.model, PlayerColour.BLACK, new Position(1,3));
				
		this.model.addPiece(testTarget1);
		this.model.addPiece(testTarget2);
		this.model.addPiece(testBlocker1);
		testMoves = testKnight.getPossibleMoves();
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveAndAttacks: Done");
	}

	//Tests when Knight moving may put its king in check
	public void moveAndInCheck(){
		System.out.println("moveAndInCheck: Start");
		this.reset();
			
		Knight testKnight = new Knight(this.model, PlayerColour.WHITE, new Position(2,0));
		
		Queen checkQueen = new Queen(this.model, PlayerColour.BLACK, new Position(0,0));
		ArrayList<Position> testMoves;
		
		this.model.addPiece(testKnight);
		this.model.addPiece(checkQueen);
		
		testMoves = testKnight.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
				
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveAndInCheck: Done");
	}
	
	//Tests when Knight can move to stop the king from being in check. 
	public void moveStopCheck(){
		System.out.println("moveStopCheck: Start");
		this.reset();
			
		Knight testKnight = new Knight(this.model, PlayerColour.WHITE, new Position(2,1));
		
		Queen checkQueen = new Queen(this.model, PlayerColour.BLACK, new Position(0,0));
		ArrayList<Position> testMoves;
		
		this.model.addPiece(testKnight);
		this.model.addPiece(checkQueen);
		
		testMoves = testKnight.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		expectedMoves.add(new Position(0,0));
		expectedMoves.add(new Position(4,0));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveStopCheck: Done");
	}
}
