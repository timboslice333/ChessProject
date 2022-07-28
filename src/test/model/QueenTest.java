package model;

import model.chess.ChessBoard;
import model.chess.Piece;
import model.chess.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {

    ChessBoard board;
    Queen queen;

    @BeforeEach
    void runBefore() {
        board = new ChessBoard();
        queen = new Queen(true);
    }

    @Test
    void testConstructor() {
        Piece[][] gameBoard = board.getGameBoard();
        assertTrue(gameBoard[3][7] instanceof Queen);
        assertTrue(gameBoard[3][0] instanceof Queen);
        assertTrue(gameBoard[3][7].isWhite());
        assertFalse(gameBoard[3][0].isWhite());
    }

    @Test
    void testValidMove() {
        int[] validMoveY = new int[]{3,7,3,2};
        int[] validMoveX = new int[]{3,7,5,7};
        int[] validMoveSlanted = new int[]{3,7,5,5};
        int[] invalidMove = new int[]{2,7,6,4};
        assertTrue(queen.validMove(validMoveY, board));
        assertTrue(queen.validMove(validMoveX, board));
        assertTrue(queen.validMove(validMoveSlanted, board));
        assertFalse(queen.validMove(invalidMove, board));
    }

    @Test
    void testMoveNotBlocked() {
        int[] blockedMove = new int[]{3,0,5,0};
        assertFalse(queen.moveNotBlocked(blockedMove, board));

        int[] movePawnOutOfWayY = new int[]{3,6,3,4};
        board.movePiece(movePawnOutOfWayY);
        int[] notBlockedMoveY = new int[]{3,7,3,4};
        assertTrue(queen.moveNotBlocked(notBlockedMoveY, board));
    }

    @Test
    void testCheckRook() {
        int[] blockedMove = new int[]{3,7,3,5};
        assertFalse(queen.moveNotBlocked(blockedMove, board));

        int[] movePawnOutOfWay = new int[]{3,6,3,4};
        board.movePiece(movePawnOutOfWay);
        int[] notBlockedMove = new int[]{3,7,3,5};
        assertTrue(queen.moveNotBlocked(notBlockedMove, board));

        int[] notBlockedMove2 = new int[]{3,5,6,5};
        board.movePiece(notBlockedMove);
        assertTrue(queen.moveNotBlocked(notBlockedMove2, board));
    }

    @Test
    void testCheckQueen() {
        int[] movePawnOutOfWay = new int[]{4,6,4,4};
        board.movePiece(movePawnOutOfWay);
        int[] notBlockedMove = new int[]{3,7,6,4};
        int[] blockedMove = new int[]{3,0,5,2};
        assertTrue(queen.moveNotBlocked(notBlockedMove, board));
        assertFalse(queen.moveNotBlocked(blockedMove, board));
    }

    @Test
    void testContent() {
        Queen whiteQueen = new Queen(true);
        Queen blackQueen = new Queen(false);
        assertEquals("wQ", whiteQueen.content());
        assertEquals("bQ", blackQueen.content());
    }
}
