package model;

import model.chess.Bishop;
import model.chess.ChessBoard;
import model.chess.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {

    ChessBoard board;
    Bishop bishop;

    @BeforeEach
    void runBefore() {
        board = new ChessBoard();
        bishop = new Bishop(true);
    }

    @Test
    void testConstructor() {
        Piece[][] gameBoard = board.getGameBoard();
        assertTrue(gameBoard[2][7] instanceof Bishop);
        assertTrue(gameBoard[5][7] instanceof Bishop);
        assertTrue(gameBoard[2][0] instanceof Bishop);
        assertTrue(gameBoard[5][0] instanceof Bishop);
        assertTrue(gameBoard[2][7].isWhite());
        assertTrue(gameBoard[5][7].isWhite());
        assertFalse(gameBoard[2][0].isWhite());
        assertFalse(gameBoard[5][0].isWhite());
    }

    @Test
    void testValidMove() {
        int[] validMove = new int[]{2,7,5,4};
        int[] invalidMove = new int[]{2,7,3,7};
        assertTrue(bishop.validMove(validMove, board));
        assertFalse(bishop.validMove(invalidMove, board));
    }

    @Test
    void testMoveNotBlocked() {
        int[] movePawnOutOfWay = new int[]{3,6,3,4};
        board.movePiece(movePawnOutOfWay);
        int[] notBlockedMove = new int[]{2,7,5,4};
        int[] blockedMove = new int[]{2,0,5,3};
        assertTrue(bishop.moveNotBlocked(notBlockedMove, board));
        assertFalse(bishop.moveNotBlocked(blockedMove, board));
    }

    @Test
    void testContent() {
        Bishop whiteBishop = new Bishop(true);
        Bishop blackBishop = new Bishop(false);
        assertEquals("wB", whiteBishop.content());
        assertEquals("bB", blackBishop.content());
    }
}
