package chessTests;

import static org.junit.Assert.*;
import chess.*;
import java.util.ArrayList;
import org.junit.Test;

//Tests Queen.getPossibleMoves
public class QueenGetMoves extends ChessTestBase {

	public QueenGetMoves(){
		super();
	}
	
	@Test
	public void test() {
		System.out.println("--Queen Tests: Start--");
		System.out.println();
		
		this.allMovements();
		this.moveAndAttacks();
		this.moveAndInCheck();
		this.moveStopCheck();
		
		System.out.println();
		System.out.println("--Queen Tests: Done--");
	}

	//Tests all queen moves without attacks
	public void allMovements(){
		System.out.println("allMovements: Start");
		super.reset();
		
		Queen testQueen = new Queen(super.getModel(), PlayerColour.WHITE, new Position(2,4));
		ArrayList<Position> testMoves;
		super.getModel().addPiece(testQueen);
		testMoves = testQueen.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		//Down-Left
		expectedMoves.add(new Position(1,3));
		expectedMoves.add(new Position(0,2));
		//Left
		expectedMoves.add(new Position(1,4));
		expectedMoves.add(new Position(0,4));
		//Up-Left
		expectedMoves.add(new Position(1,5));
		expectedMoves.add(new Position(0,6));
		//Up
		expectedMoves.add(new Position(2,5));
		expectedMoves.add(new Position(2,6));
		expectedMoves.add(new Position(2,7));
		//Up-Right
		expectedMoves.add(new Position(3,5));
		expectedMoves.add(new Position(4,6));
		expectedMoves.add(new Position(5,7));
		//Right
		expectedMoves.add(new Position(3,4));
		expectedMoves.add(new Position(4,4));
		expectedMoves.add(new Position(5,4));
		expectedMoves.add(new Position(6,4));
		expectedMoves.add(new Position(7,4));
		//Down-Right
		expectedMoves.add(new Position(3,3));
		expectedMoves.add(new Position(4,2));
		expectedMoves.add(new Position(5,1));
		expectedMoves.add(new Position(6,0));
		//Down
		expectedMoves.add(new Position(2,3));
		expectedMoves.add(new Position(2,2));
		expectedMoves.add(new Position(2,1));
		expectedMoves.add(new Position(2,0));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("allMovemovements: Done");
	}

	//Tests all queen moves and attacks
	public void moveAndAttacks(){
		System.out.println("moveAndAttacks: Start");
		super.reset();
			
		Queen testQueen = new Queen(super.getModel(), PlayerColour.WHITE, new Position(2,4));
		ArrayList<Position> testMoves;
		super.getModel().addPiece(testQueen);
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		//Down-Left
		expectedMoves.add(new Position(1,3));
		expectedMoves.add(new Position(0,2));
		Pawn testTarget1 = new Pawn(super.getModel(), PlayerColour.BLACK, new Position(0,2));

		//Left
		Pawn testBlocker1 = new Pawn(super.getModel(), PlayerColour.WHITE, new Position(1,4));
		//Up-Left
		expectedMoves.add(new Position(1,5));
		expectedMoves.add(new Position(0,6));
		//Up
		expectedMoves.add(new Position(2,5));
		expectedMoves.add(new Position(2,6));
		expectedMoves.add(new Position(2,7));
		//Up-Right
		expectedMoves.add(new Position(3,5));
		expectedMoves.add(new Position(4,6));
		expectedMoves.add(new Position(5,7));
		//Right
		expectedMoves.add(new Position(3,4));
		Pawn testTarget2 = new Pawn(super.getModel(), PlayerColour.BLACK, new Position(3,4));
		//Down-Right
		expectedMoves.add(new Position(3,3));
		expectedMoves.add(new Position(4,2));
		expectedMoves.add(new Position(5,1));
		expectedMoves.add(new Position(6,0));
		//Down
		expectedMoves.add(new Position(2,3));
		expectedMoves.add(new Position(2,2));
		expectedMoves.add(new Position(2,1));
		expectedMoves.add(new Position(2,0));
		
		this.getModel().addPiece(testTarget1);
		this.getModel().addPiece(testTarget2);
		this.getModel().addPiece(testBlocker1);
		testMoves = testQueen.getPossibleMoves();
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveAndAttacks: Done");
	}

	//Tests when queen moving may put its king in check
	public void moveAndInCheck(){
		System.out.println("moveAndInCheck: Start");
		this.reset();
			
		Queen testQueen = new Queen(this.getModel(), PlayerColour.WHITE, new Position(2,0));
		
		Queen checkQueen = new Queen(this.getModel(), PlayerColour.BLACK, new Position(0,0));
		ArrayList<Position> testMoves;
		
		this.getModel().addPiece(testQueen);
		this.getModel().addPiece(checkQueen);
		
		testMoves = testQueen.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		//All movements on its row is valid
		for (int c = 0; c <= 6; c++){
			if (c == 2) continue;
			expectedMoves.add(new Position(c,0));
		}
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveAndInCheck: Done");
	}
	
	//Tests when Queen can move to stop the king from being in check
	public void moveStopCheck(){
		System.out.println("moveStopCheck: Start");
		this.reset();
			
		Queen testQueen = new Queen(this.getModel(), PlayerColour.WHITE, new Position(2,1));
		
		Queen checkQueen = new Queen(this.getModel(), PlayerColour.BLACK, new Position(0,0));
		ArrayList<Position> testMoves;
		
		this.getModel().addPiece(testQueen);
		this.getModel().addPiece(checkQueen);
		
		testMoves = testQueen.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		for (int c = 1; c <= 3; c++){	
			expectedMoves.add(new Position(c,1));
		}
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveStopCheck: Done");
	}
}
