package model;

import model.chess.ChessBoard;
import model.chess.Knight;
import model.chess.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {

    ChessBoard board;
    Knight knight;

    @BeforeEach
    void runBefore() {
        board = new ChessBoard();
        knight = new Knight(true);
    }

    @Test
    void testConstructor() {
        Piece[][] gameBoard = board.getGameBoard();
        assertTrue(gameBoard[1][7] instanceof Knight);
        assertTrue(gameBoard[6][7] instanceof Knight);
        assertTrue(gameBoard[1][0] instanceof Knight);
        assertTrue(gameBoard[6][0] instanceof Knight);
        assertTrue(gameBoard[1][7].isWhite());
        assertTrue(gameBoard[6][7].isWhite());
        assertFalse(gameBoard[1][0].isWhite());
        assertFalse(gameBoard[6][0].isWhite());
    }

    @Test
    void testValidMove() {
        int[] validMove = new int[]{1,7,2,5};
        int[] invalidMove = new int[]{1,7,3,7};
        int[] invalidMove2 = new int[]{1,7,1,4};
        int[] invalidMove3 = new int[]{1,7,4,7};
        assertTrue(knight.validMove(validMove, board));
        assertFalse(knight.validMove(invalidMove, board));
        assertFalse(knight.validMove(invalidMove2, board));
        assertFalse(knight.validMove(invalidMove3, board));
    }

    @Test
    void testContent() {
        Knight whiteKnight = new Knight(true);
        Knight blackKnight = new Knight(false);
        assertEquals("wk", whiteKnight.content());
        assertEquals("bk", blackKnight.content());
    }
}
