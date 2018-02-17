package chessTests;

import chess.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

//Tests Pawn.getPossibleMoves
public class PawnGetMoves extends ChessTestBase{

	public PawnGetMoves(){
		super();
	}
	
	@Test
	public void test() {
		System.out.println("Pawn Tests: Start");
		System.out.println();
		this.firstMove();
		this.firstMoveBlocked();
		this.attacks();
		this.endOfBoard();
		System.out.println();
		System.out.println("Pawn Tests: Done");
	}

	//Tests whether the first move allows the piece to move 2 positions
	public void firstMove(){
		System.out.println("firstMove: Start");
		super.reset();
		
		final Position oneStep = new Position(1,2);
		final Position twoStep = new Position(1,3);
		
		Pawn testPawn = new Pawn(super.getModel(), PlayerColour.WHITE, new Position(1,1));
		ArrayList<Position> testMoves;
		
		super.getModel().addPiece(testPawn);
		
		testMoves = testPawn.getPossibleMoves();
		
		if (testMoves.size() != 2 ||
			(!oneStep.equals(testMoves.get(0)) && !twoStep.equals(testMoves.get(0)))||
			(!oneStep.equals(testMoves.get(1)) && !twoStep.equals(testMoves.get(1)))){
			
			System.out.println("Got Moves:");
			for (Position p : testMoves){
				System.out.println("(" + p.getFirst() + ", " + p.getSecond() + ")");
			}
			fail("PawnGetMoves: Expected two possible moves on first move");
		}
		System.out.println("firstMove: Done");
	}
	
	//Tests when pawn is blocked on first move
	public void firstMoveBlocked(){
		System.out.println("firstMoveBlocked: Start");
		super.reset();
				
		Pawn testPawn = new Pawn(super.getModel(), PlayerColour.WHITE, new Position(1,1));
		Pawn blockPawn = new Pawn(super.getModel(), PlayerColour.BLACK, new Position(1,2));
		ArrayList<Position> testMoves;
		
		super.getModel().addPiece(testPawn);
		super.getModel().addPiece(blockPawn);
		
		testMoves = testPawn.getPossibleMoves();
		
		if (testMoves.size() != 0) {
			
			System.out.println("Got Moves:");
			for (Position p : testMoves){
				System.out.println("(" + p.getFirst() + ", " + p.getSecond() + ")");
			}
			fail("PawnGetMoves: Expected no possible moves when blocked");
		}
		System.out.println("firstMoveBlocked: Done");
	}

	//Tests when pawn can attack
	public void attacks(){
		System.out.println("attacks: Start");
		super.reset();
		
		Pawn testPawn = new Pawn(super.getModel(), PlayerColour.WHITE, new Position(1,1));
		Pawn blockPawn = new Pawn(super.getModel(), PlayerColour.BLACK, new Position(1,2));
		Pawn attackedPawn1 = new Pawn(super.getModel(), PlayerColour.BLACK, new Position(0,2));
		Pawn attackedPawn2 = new Pawn(super.getModel(), PlayerColour.BLACK, new Position(2,2));
		ArrayList<Position> testMoves;
		
		super.getModel().addPiece(testPawn);
		super.getModel().addPiece(blockPawn);
		super.getModel().addPiece(attackedPawn1);
		super.getModel().addPiece(attackedPawn2);
		
		testMoves = testPawn.getPossibleMoves();
		
		if (testMoves.size() != 2 ||
			(!attackedPawn1.getPosition().equals(testMoves.get(0)) && !attackedPawn2.getPosition().equals(testMoves.get(0)))||
			(!attackedPawn1.getPosition().equals(testMoves.get(1)) && !attackedPawn2.getPosition().equals(testMoves.get(1)))){
			
			System.out.println(testMoves.size() + " Moves:");
			for (Position p : testMoves){
				System.out.println("(" + p.getFirst() + ", " + p.getSecond() + ")");
			}
			fail("PawnGetMoves: Expected to only be able to attack two pawns");
		}
		System.out.println("attacks: Done");
	}

	//Test when pawn reaches end of board
	public void endOfBoard(){
		System.out.println("endOfBoard: Start");
		super.reset();
		
		Pawn testPawn = new Pawn(super.getModel(), PlayerColour.WHITE, new Position(1,7));

		ArrayList<Position> testMoves;
		
		super.getModel().addPiece(testPawn);
		
		testMoves = testPawn.getPossibleMoves();
		
		if (testMoves.size() != 0) {
			
			System.out.println("Got Moves:");
			for (Position p : testMoves){
				System.out.println("(" + p.getFirst() + ", " + p.getSecond() + ")");
			}
			fail("PawnGetMoves: Expected no possible moves when reached end of board");
		}
		System.out.println("endOfBoard: Done");
	}
}
