package chessTests;

import static org.junit.Assert.*;

import chess.*;
import java.util.ArrayList;
import org.junit.Test;

//Tests Rook.getPossibleMoves
public class RookGetMoves extends ChessTestBase {

	public RookGetMoves(){
		super();
	}
	
	@Test
	public void test() {
		System.out.println("--Rook Tests: Start--");
		System.out.println();
		
		this.allMovements();
		this.moveAndAttacks();
		this.moveAndInCheck();
		this.moveStopCheck();
		
		System.out.println();
		System.out.println("--Rook Tests: Done--");
	}

	//Tests all rook moves without attacks
	public void allMovements(){
		System.out.println("allMovements: Start");
		super.reset();
		
		Rook testRook = new Rook(super.getModel(), PlayerColour.WHITE, new Position(2,4));
		ArrayList<Position> testMoves;
		super.getModel().addPiece(testRook);
		testMoves = testRook.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		//Left
		expectedMoves.add(new Position(1,4));
		expectedMoves.add(new Position(0,4));
		//Up
		expectedMoves.add(new Position(2,5));
		expectedMoves.add(new Position(2,6));
		expectedMoves.add(new Position(2,7));
		//Right
		expectedMoves.add(new Position(3,4));
		expectedMoves.add(new Position(4,4));
		expectedMoves.add(new Position(5,4));
		expectedMoves.add(new Position(6,4));
		expectedMoves.add(new Position(7,4));
		//Down
		expectedMoves.add(new Position(2,3));
		expectedMoves.add(new Position(2,2));
		expectedMoves.add(new Position(2,1));
		expectedMoves.add(new Position(2,0));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("allMovemovements: Done");
	}

	//Tests all rook moves and attacks
	public void moveAndAttacks(){
		System.out.println("moveAndAttacks: Start");
		super.reset();
			
		Rook testRook = new Rook(super.getModel(), PlayerColour.WHITE, new Position(2,4));
		ArrayList<Position> testMoves;
		super.getModel().addPiece(testRook);
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();

		//Left
		Pawn testBlocker1 = new Pawn(super.getModel(), PlayerColour.WHITE, new Position(1,4));
		//Up
		expectedMoves.add(new Position(2,5));
		Pawn testTarget1 = new Pawn(super.getModel(), PlayerColour.BLACK, new Position(2,5));

		//Right
		expectedMoves.add(new Position(3,4));
		Pawn testTarget2 = new Pawn(super.getModel(), PlayerColour.BLACK, new Position(3,4));
		//Down
		expectedMoves.add(new Position(2,3));
		expectedMoves.add(new Position(2,2));
		expectedMoves.add(new Position(2,1));
		expectedMoves.add(new Position(2,0));
		
		this.getModel().addPiece(testTarget1);
		this.getModel().addPiece(testTarget2);
		this.getModel().addPiece(testBlocker1);
		testMoves = testRook.getPossibleMoves();
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveAndAttacks: Done");
	}

	//Tests when rook moving may put its king in check
	public void moveAndInCheck(){
		System.out.println("moveAndInCheck: Start");
		this.reset();
			
		Rook testRook = new Rook(this.getModel(), PlayerColour.WHITE, new Position(2,0));
		
		Rook checkRook = new Rook(this.getModel(), PlayerColour.BLACK, new Position(0,0));
		ArrayList<Position> testMoves;
		
		this.getModel().addPiece(testRook);
		this.getModel().addPiece(checkRook);
		
		testMoves = testRook.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		//All movements on its row is valid
		for (int c = 0; c <= 6; c++){
			if (c == 2) continue;
			expectedMoves.add(new Position(c,0));
		}
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveAndInCheck: Done");
	}
	
	//Tests when Rook can move to stop the king from being in check
	public void moveStopCheck(){
		System.out.println("moveStopCheck: Start");
		this.reset();
			
		Rook testRook = new Rook(this.getModel(), PlayerColour.WHITE, new Position(2,1));
		
		Queen checkQueen = new Queen(this.getModel(), PlayerColour.BLACK, new Position(0,0));
		ArrayList<Position> testMoves;
		
		this.getModel().addPiece(testRook);
		this.getModel().addPiece(checkQueen);
		
		testMoves = testRook.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();	
		expectedMoves.add(new Position(2,0));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveStopCheck: Done");
	}
}
