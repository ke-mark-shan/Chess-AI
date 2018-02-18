package chessTests;

import static org.junit.Assert.*;
import chess.*;
import java.util.ArrayList;
import org.junit.Test;

//Tests Pawn.getPossibleMoves
public class PawnGetMoves extends ChessTestBase{

	public PawnGetMoves(){
		super();
	}
	
	@Test
	public void test() {
		System.out.println("--Pawn Tests: Start--");
		System.out.println();
		this.direction();
		this.firstAndNextMove();
		this.firstMoveBlocked();
		this.attacks();
		this.endOfBoard();
		this.moveAndInCheck();
		this.moveStopCheck();
		System.out.println();
		System.out.println("--Pawn Tests: Done--");
	}
	
	//Tests whether the direction of the Pawn is set correctly
	public void direction(){
		System.out.println("direction: Start");
		this.reset();
		Pawn testPawnWhite = new Pawn(this.model, PlayerColour.WHITE, new Position(1,1));
		Pawn testPawnBlack = new Pawn(this.model, PlayerColour.BLACK, new Position(6,1));
		
		if (testPawnWhite.getDirectionMultiplier() != 1 || testPawnBlack.getDirectionMultiplier() != -1){
			fail("Bad direction multiplier");
		}
		System.out.println("direction: Done");
	}

	//Tests whether the first move allows the piece to move 2 positions and only one on the next
	public void firstAndNextMove(){
		System.out.println("firstAndNextMove: Start");
		this.reset();
		
		Pawn testPawn = new Pawn(this.model, PlayerColour.WHITE, new Position(1,1));
		ArrayList<Position> testMoves;
		this.model.addPiece(testPawn);

		testMoves = testPawn.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		expectedMoves.add(new Position(1,2));
		expectedMoves.add(new Position(1,3));
		
		this.compareMoves(expectedMoves, testMoves);
		
		//Next Move
		this.model.movePiece(testPawn.getPosition(), new Position(1,2) , true);
		testMoves = testPawn.getPossibleMoves();
		expectedMoves = new ArrayList<Position>();
		expectedMoves.add(new Position(1,3));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("firstAndNextMove: Done");
	}
	
	//Tests when Pawn is blocked on first move
	public void firstMoveBlocked(){
		System.out.println("firstMoveBlocked: Start");
		this.reset();
				
		Pawn testPawn = new Pawn(this.model, PlayerColour.WHITE, new Position(1,1));
		Pawn blockPawn = new Pawn(this.model, PlayerColour.BLACK, new Position(1,2));
		ArrayList<Position> testMoves;
		
		this.model.addPiece(testPawn);
		this.model.addPiece(blockPawn);
		
		testMoves = testPawn.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("firstMoveBlocked: Done");
	}

	//Tests when Pawn can attack
	public void attacks(){
		System.out.println("attacks: Start");
		this.reset();
		
		Pawn testPawn = new Pawn(this.model, PlayerColour.WHITE, new Position(1,1));
		Pawn blockPawn = new Pawn(this.model, PlayerColour.BLACK, new Position(1,2));
		Pawn attackedPawn1 = new Pawn(this.model, PlayerColour.BLACK, new Position(0,2));
		Pawn attackedPawn2 = new Pawn(this.model, PlayerColour.BLACK, new Position(2,2));
		ArrayList<Position> testMoves;
		
		this.model.addPiece(testPawn);
		this.model.addPiece(blockPawn);
		this.model.addPiece(attackedPawn1);
		this.model.addPiece(attackedPawn2);
		
		testMoves = testPawn.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		expectedMoves.add(new Position(0,2));
		expectedMoves.add(new Position(2,2));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("attacks: Done");
	}

	//Test when Pawn reaches end of board
	public void endOfBoard(){
		System.out.println("endOfBoard: Start");
		this.reset();
		
		Pawn testPawn = new Pawn(this.model, PlayerColour.WHITE, new Position(1,7));

		ArrayList<Position> testMoves;
		
		this.model.addPiece(testPawn);
		
		testMoves = testPawn.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();

		this.compareMoves(expectedMoves, testMoves);
		System.out.println("endOfBoard: Done");
	}

	//Tests when Pawn moving would put its king in check
	public void moveAndInCheck(){
		System.out.println("moveAndInCheck: Start");
		this.reset();
				
		Pawn testPawn = new Pawn(this.model, PlayerColour.WHITE, new Position(2,0));
		Queen checkQueen = new Queen(this.model, PlayerColour.BLACK, new Position(0,0));
		ArrayList<Position> testMoves;
		
		this.model.addPiece(testPawn);
		this.model.addPiece(checkQueen);
		
		testMoves = testPawn.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveAndInCheck: Done");
	}
	
	//Tests when Pawn can move to stop the king from being in check
	public void moveStopCheck(){
		System.out.println("moveStopCheck: Start");
		this.reset();
			
		Pawn testPawn = new Pawn(this.model, PlayerColour.WHITE, new Position(6,1));
		
		Queen checkQueen = new Queen(this.model, PlayerColour.BLACK, new Position(7,2));
		ArrayList<Position> testMoves;
		
		this.model.addPiece(testPawn);
		this.model.addPiece(checkQueen);
		
		testMoves = testPawn.getPossibleMoves();
		
		ArrayList<Position> expectedMoves = new ArrayList<Position>();
			
		expectedMoves.add(new Position(7,2));
		
		this.compareMoves(expectedMoves, testMoves);
		System.out.println("moveStopCheck: Done");
	}
}
