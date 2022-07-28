package model;

import model.chess.ChessBoard;
import model.chess.King;
import model.chess.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KingTest {

    ChessBoard board;
    King king;

    @BeforeEach
    void runBefore() {
        board = new ChessBoard();
        king = new King(true);
    }

    @Test
    void testConstructor() {
        Piece[][] gameBoard = board.getGameBoard();
        assertTrue(gameBoard[4][7] instanceof King);
        assertTrue(gameBoard[4][0] instanceof King);
        assertTrue(gameBoard[4][7].isWhite());
        assertFalse(gameBoard[4][0].isWhite());
    }

    @Test
    void testValidMove() {
        int[] validMove = new int[]{4,7,5,6};
        int[] invalidMove = new int[]{4,7,2,7};
        int[] invalidMove2 = new int[]{4,7,4,5};
        int[] invalidMove3 = new int[]{4,7,2,5};
        assertTrue(king.validMove(validMove, board));
        assertFalse(king.validMove(invalidMove, board));
        assertFalse(king.validMove(invalidMove2, board));
        assertFalse(king.validMove(invalidMove3, board));
    }

    @Test
    void testContent() {
        King whiteKing = new King(true);
        King blackKing = new King(false);
        assertEquals("wK", whiteKing.content());
        assertEquals("bK", blackKing.content());
    }
}
