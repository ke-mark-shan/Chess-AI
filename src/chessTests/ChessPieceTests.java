package chessTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BishopGetMoves.class, KingGetMoves.class, KnightGetMoves.class, PawnGetMoves.class, QueenGetMoves.class,
		RookGetMoves.class, PieceValues.class })
public class ChessPieceTests {

}
