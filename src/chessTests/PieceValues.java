package chessTests;

import static org.junit.Assert.*;

import chess.*;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Test;

//Tests whether the pieces' values are calculated correctly
public class PieceValues extends ChessTestBase{

	public PieceValues(){
		super();
	}
	@Test
	public void test() {
		System.out.println("--Piece Value Tests: Start--");
		System.out.println();
		
		this.testPawn();
		this.testKnight();
		this.testBishop();
		this.testRook();
		this.testQueen();
		this.testKing();
		
		System.out.println();
		System.out.println("--Piece Value Tests: Done--");
	}
	
	//Tests all Pawn values
	public void testPawn(){
		System.out.println("Pawn: Start");
		this.reset();
		Pawn testPawn = new Pawn(this.model, PlayerColour.WHITE, new Position(1,1));
		
		this.model.addPiece(testPawn);
		double [][] arr = testPawn.getPositionValues();
		
		for (int i = 0; i < arr.length; i++){
			for (int j = 0; j < arr[0].length; j++){
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
		Assert.assertEquals(15.0, testPawn.getPieceValue());
		System.out.println("Pawn: Done");
	}
	
	//Tests all Knight values
	public void testKnight(){
		System.out.println("Knight: Start");
		this.reset();
		
		Knight testKnight = new Knight(this.model, PlayerColour.WHITE, new Position(1,1));
		this.model.addPiece(testKnight);
		
		Assert.assertEquals(28.0, testKnight.getPieceValue());
		System.out.println("Knight: Done");
	}

	//Tests all Bishop values
	public void testBishop(){
		System.out.println("Bishop: Start");
		this.reset();
		
		Bishop testBishop = new Bishop(this.model, PlayerColour.WHITE, new Position(1,1));
		this.model.addPiece(testBishop);
		
		Assert.assertEquals(30.0, testBishop.getPieceValue());
		System.out.println("Bishop: Done");
	}
	
	//Tests all Rook values
	public void testRook(){
		System.out.println("Rook: Start");
		this.reset();
		
		Rook testRook = new Rook(this.model, PlayerColour.WHITE, new Position(1,1));
		this.model.addPiece(testRook);
		
		Assert.assertEquals(51.0, testRook.getPieceValue());;
		System.out.println("Rook: Done");
	}
	
	//Tests all Queen values
	public void testQueen(){
		System.out.println("Queen: Start");
		this.reset();
		
		Queen testQueen = new Queen(this.model, PlayerColour.WHITE, new Position(1,1));
		this.model.addPiece(testQueen);
		
		Assert.assertEquals(90.0, testQueen.getPieceValue());
		System.out.println("Queen: Done");
	}
	
	//Tests all King values
	public void testKing(){
		System.out.println("King: Start");
		this.reset();
		
		this.model.removePiece(this.whiteKing);
		King testKing = new King(this.model, PlayerColour.WHITE, new Position(1,1));
		this.model.addPiece(testKing);
		
		Assert.assertEquals(896.0, testKing.getPieceValue());;
		System.out.println("King: Done");
	}
}
