package model;

import model.chess.ChessBoard;
import model.chess.Piece;
import model.chess.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RookTest {

    ChessBoard board;
    Rook rook;

    @BeforeEach
    void runBefore() {
        board = new ChessBoard();
        rook = new Rook(true);
    }

    @Test
    void testConstructor() {
        Piece[][] gameBoard = board.getGameBoard();
        assertTrue(gameBoard[7][7] instanceof Rook);
        assertTrue(gameBoard[0][7] instanceof Rook);
        assertTrue(gameBoard[0][0] instanceof Rook);
        assertTrue(gameBoard[7][0] instanceof Rook);
        assertTrue(gameBoard[7][7].isWhite());
        assertTrue(gameBoard[0][7].isWhite());
        assertFalse(gameBoard[0][0].isWhite());
        assertFalse(gameBoard[7][0].isWhite());
    }

    @Test
    void testValidMove() {
        int[] validMoveX = new int[]{0,0,5,0};
        int[] validMoveY = new int[]{7,7,7,4};
        int[] invalidMove = new int[]{2,5,3,7};
        assertTrue(rook.validMove(validMoveX, board));
        assertTrue(rook.validMove(validMoveY, board));
        assertFalse(rook.validMove(invalidMove, board));
    }

    @Test
    void testMoveNotBlocked() {
        int[] blockedMoveY = new int[]{7,7,7,3};
        assertFalse(rook.moveNotBlocked(blockedMoveY, board));
        int[] blockedMoveX = new int[]{7,7,3,7};
        assertFalse(rook.moveNotBlocked(blockedMoveX, board));

        int[] movePawnOutOfWay = new int[]{7,6,7,4};
        board.movePiece(movePawnOutOfWay);
        int[] notBlockedMove = new int[]{7,7,7,5};
        assertTrue(rook.moveNotBlocked(notBlockedMove, board));

        int[] notBlockedMove2 = new int[]{7,5,2,5};
        board.movePiece(notBlockedMove);
        assertTrue(rook.moveNotBlocked(notBlockedMove2, board));
    }

    @Test
    void testContent() {
        Rook whiteRook = new Rook(true);
        Rook blackRook = new Rook(false);
        assertEquals("wR", whiteRook.content());
        assertEquals("bR", blackRook.content());
    }
}
