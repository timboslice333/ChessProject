package model;

import model.chess.ChessBoard;
import model.chess.Pawn;
import model.chess.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {

    ChessBoard board;
    Pawn whitePawn;
    Pawn blackPawn;

    @BeforeEach
    void runBefore() {
        board = new ChessBoard();
        whitePawn = new Pawn(true);
        blackPawn = new Pawn(false);
    }

    @Test
    void testConstructor() {
        Piece[][] gameBoard = board.getGameBoard();
        for (int i = 0; i < 8; i++) {
            assertTrue(gameBoard[i][1] instanceof Pawn);
            assertTrue(gameBoard[i][6] instanceof Pawn);
            assertFalse(gameBoard[i][1].isWhite());
            assertTrue(gameBoard[i][6].isWhite());
        }
    }

    @Test
    void testValidMoveWhite() {
        int[] validMoveWhite = new int[]{2,6,2,4};
        int[] validMoveWhite2 = new int[]{2,5,2,4};
        int[] invalidMove = new int[]{2,5,3,7};
        int[] invalidMoveWhite1 = new int[]{2,5,2,3};
        int[] invalidMoveWhite2 = new int[]{2,6,2,3};
        int[] invalidMoveWhite3 = new int[]{2,6,2,7};
        assertTrue(whitePawn.validMove(validMoveWhite, board));
        assertTrue(whitePawn.validMove(validMoveWhite2, board));
        assertFalse(whitePawn.validMove(invalidMove, board));
        assertFalse(whitePawn.validMove(invalidMoveWhite1, board));
        assertFalse(whitePawn.validMove(invalidMoveWhite2, board));
        assertFalse(whitePawn.validMove(invalidMoveWhite3, board));
    }

    @Test
    void testValidMoveBlack() {
        int[] validMoveBlack = new int[]{2,1,2,3};
        int[] validMoveBlack2 = new int[]{2,2,2,3};
        int[] invalidMoveBlack1 = new int[]{2,1,2,4};
        int[] invalidMoveBlack2 = new int[]{2,2,2,4};
        int[] invalidMoveBlack3= new int[]{2,1,2,0};
        assertTrue(blackPawn.validMove(validMoveBlack, board));
        assertTrue(blackPawn.validMove(validMoveBlack2, board));
        assertFalse(blackPawn.validMove(invalidMoveBlack1, board));
        assertFalse(blackPawn.validMove(invalidMoveBlack2, board));
        assertFalse(blackPawn.validMove(invalidMoveBlack3, board));
    }

    @Test
    void testIsPawnCapture() {
        int[] invalidMoveWhite = new int[]{2,6,3,5};
        int[] invalidMoveWhite2 = new int[]{2,6,4,5};
        int[] invalidMoveWhite3 = new int[]{2,6,3,7};
        int[] invalidMoveBlack = new int[]{2,1,3,2};
        int[] invalidMoveBlack2 = new int[]{2,1,4,2};
        int[] invalidMoveBlack3 = new int[]{2,1,3,0};
        assertFalse(whitePawn.isPawnCapture(invalidMoveWhite, board));
        assertFalse(whitePawn.isPawnCapture(invalidMoveWhite2, board));
        assertFalse(whitePawn.isPawnCapture(invalidMoveWhite3, board));
        assertFalse(blackPawn.isPawnCapture(invalidMoveBlack, board));
        assertFalse(blackPawn.isPawnCapture(invalidMoveBlack2, board));
        assertFalse(blackPawn.isPawnCapture(invalidMoveBlack3, board));
    }

    @Test
    void testIsPawnCaptureInvalid() {
        int[] moveWhiteToWhitePawnCapture = new int[]{7,7,3,5};
        int[] moveBlackToBlackPawnCapture = new int[]{0,0,3,2};
        board.movePiece(moveBlackToBlackPawnCapture);
        board.movePiece(moveWhiteToWhitePawnCapture);
        int[] invalidMoveWhite4 = new int[]{2,6,3,5};
        int[] invalidMoveBlack4 = new int[]{2,1,3,2};
        assertFalse(whitePawn.isPawnCapture(invalidMoveWhite4, board));
        assertFalse(blackPawn.isPawnCapture(invalidMoveBlack4, board));
    }

    @Test
    void testIsPawnCaptureValid() {
        int[] moveWhiteToBlackPawnCapture = new int[]{7,0,3,5};
        int[] moveBlackToWhitePawnCapture = new int[]{0,7,3,2};
        board.movePiece(moveWhiteToBlackPawnCapture);
        board.movePiece(moveBlackToWhitePawnCapture);
        int[] validMoveWhite = new int[]{2,6,3,5};
        int[] validMoveBlack = new int[]{2,1,3,2};
        assertTrue(whitePawn.isPawnCapture(validMoveWhite, board));
        assertTrue(blackPawn.isPawnCapture(validMoveBlack, board));
    }

    @Test
    void testMoveNotBlocked() {
        int[] validMove = new int[]{2,6,2,4};
        assertTrue(whitePawn.moveNotBlocked(validMove, board));
        int[] validMove2 = new int[]{2,6,3,5};
        assertTrue(whitePawn.moveNotBlocked(validMove2, board));

        int[] movePawnToBlock = new int[]{2,1,2,4};
        int[] invalidMove = new int[]{2,6,2,4};
        int[] invalidMove2 = new int[]{2,5,2,4};
        board.movePiece(movePawnToBlock);
        assertFalse(whitePawn.moveNotBlocked(invalidMove, board));
        assertFalse(whitePawn.moveNotBlocked(invalidMove2, board));
    }

    @Test
    void testCheckMiddle() {
        int[] validMoveWhite = new int[]{2,6,2,4};
        assertTrue(whitePawn.checkMiddle(validMoveWhite, board));
        int[] validMoveBlack = new int[]{2,1,2,3};
        assertTrue(blackPawn.checkMiddle(validMoveBlack, board));

        int[] moveWhiteToTwoSquaresBlackPawn = new int[]{7,7,2,3};
        int[] moveBlackToTwoSquaresWhitePawn = new int[]{0,0,2,4};
        int[] invalidMoveWhite = new int[]{2,6,2,4};
        int[] invalidMoveBlack = new int[]{2,1,2,3};
        board.movePiece(moveBlackToTwoSquaresWhitePawn);
        board.movePiece(moveWhiteToTwoSquaresBlackPawn);
        assertFalse(whitePawn.checkMiddle(invalidMoveWhite, board));
        assertFalse(blackPawn.checkMiddle(invalidMoveBlack, board));

        int[] moveWhiteToOneSquaresBlackPawn = new int[]{2,3,2,2};
        int[] moveBlackToOneSquaresWhitePawn = new int[]{2,4,2,5};
        board.movePiece(moveBlackToOneSquaresWhitePawn);
        board.movePiece(moveWhiteToOneSquaresBlackPawn);
        assertFalse(whitePawn.checkMiddle(invalidMoveWhite, board));
        assertFalse(blackPawn.checkMiddle(invalidMoveBlack, board));
    }

    @Test
    void testContent() {
        assertEquals("wP", whitePawn.content());
        assertEquals("bP", blackPawn.content());
    }
}
