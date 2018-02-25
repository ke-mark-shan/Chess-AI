package chessTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BishopGetMoves.class, ChessPieceTests.class, KingGetMoves.class, KnightGetMoves.class,
		PawnGetMoves.class, PieceValues.class, QueenGetMoves.class, RookGetMoves.class })
public class AllTests {

}
