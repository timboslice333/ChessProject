package model;

import model.chess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChessBoardTest {

    ChessBoard chessBoard;

    @BeforeEach
    void runBefore() {
        chessBoard = new ChessBoard();
    }

    @Test
    void testConstructor() {
        Piece[][] gameBoard = chessBoard.getGameBoard();
        assertEquals(8, gameBoard.length);
        assertEquals(8, gameBoard[0].length);
        assertTrue(chessBoard.isWhiteTurn());
        assertFalse(chessBoard.isGameOver());
        assertTrue(chessBoard.getBlackPiecesCaptured().isEmpty());
        assertTrue(chessBoard.getWhitePiecesCaptured().isEmpty());
        assertTrue(chessBoard.getMoves().isEmpty());
    }

    @Test
    void testInitPieces() {
        Piece[][] gameBoard = chessBoard.getGameBoard();
        assertTrue(gameBoard[7][7] instanceof Rook);
        assertTrue(gameBoard[3][7] instanceof Queen);
        assertTrue(gameBoard[0][0] instanceof Rook);
        assertTrue(gameBoard[5][0] instanceof Bishop);
        assertTrue(gameBoard[0][7].isWhite());
        assertTrue(gameBoard[4][7].isWhite());
        assertFalse(gameBoard[3][0].isWhite());
        assertFalse(gameBoard[6][0].isWhite());
    }

    @Test
    void testInitPawns() {
        Piece[][] gameBoard = chessBoard.getGameBoard();
        assertTrue(gameBoard[7][6] instanceof Pawn);
        assertTrue(gameBoard[3][6] instanceof Pawn);
        assertTrue(gameBoard[0][1] instanceof Pawn);
        assertTrue(gameBoard[5][1] instanceof Pawn);
        assertTrue(gameBoard[0][6].isWhite());
        assertTrue(gameBoard[4][6].isWhite());
        assertFalse(gameBoard[3][1].isWhite());
        assertFalse(gameBoard[6][1].isWhite());
    }

    @Test
    void testCheckValidMove() {
        int[] targetSameColor = new int[]{7,7,7,6};
        int[] notPiece = new int[]{5,5,3,5};
        int[] outOfBoard = new int[]{0,3,2,10};
        int[] isBlocked = new int[]{5,7,3,5};
        int[] notCorrectMove = new int[]{5,6,5,3};
        int[] notCorrectTurn = new int[]{0,1,0,2};
        int[] targetSamePlace = new int[]{6,6,6,6,};
        assertFalse(chessBoard.checkValidMove(targetSamePlace));
        assertFalse(chessBoard.checkValidMove(targetSameColor));
        assertFalse(chessBoard.checkValidMove(notPiece));
        assertFalse(chessBoard.checkValidMove(outOfBoard));
        assertFalse(chessBoard.checkValidMove(isBlocked));
        assertFalse(chessBoard.checkValidMove(notCorrectMove));
        assertFalse(chessBoard.checkValidMove(notCorrectTurn));
        int[] validMove = new int[]{6,7,5,5};
        assertTrue(chessBoard.checkValidMove(validMove));
    }

    @Test
    void testIsTargetSameColor() {
        int[] targetSameWhite = new int[]{7, 7, 7, 6};
        int[] targetSameBlack = new int[]{0, 0, 0, 1};
        int[] targetDiffWhite = new int[]{7, 7, 0, 0};
        int[] targetDiffBlack = new int[]{0, 0, 7, 7};
        int[] targetNull = new int[]{0, 0, 3, 3};
        assertTrue(chessBoard.isTargetSameColor(targetSameBlack));
        assertTrue(chessBoard.isTargetSameColor(targetSameWhite));
        assertFalse(chessBoard.isTargetSameColor(targetNull));
        assertFalse(chessBoard.isTargetSameColor(targetDiffBlack));
        assertFalse(chessBoard.isTargetSameColor(targetDiffWhite));
    }

    @Test
    void testIsOutOfBoard() {
        int[] outOfBoundsAbove = new int[]{1,3,2,10};
        int[] outOfBoundsBelow = new int[]{-4,3,2,3};
        int[] notOutOfBounds = new int[]{7,7,6,6,};
        assertFalse(chessBoard.isNotOutOfBoard(outOfBoundsAbove));
        assertFalse(chessBoard.isNotOutOfBoard(outOfBoundsBelow));
        assertTrue(chessBoard.isNotOutOfBoard(notOutOfBounds));
    }

    @Test
    void testIsCorrectTurn() {
        Piece whiteBishop = new Bishop(true);
        Piece blackBishop = new Bishop(false);
        assertTrue(chessBoard.isCorrectTurn(whiteBishop));
        assertFalse(chessBoard.isCorrectTurn(blackBishop));
        chessBoard.setWhiteTurn(false);
        assertFalse(chessBoard.isCorrectTurn(whiteBishop));
        assertTrue(chessBoard.isCorrectTurn(blackBishop));
    }

    @Test
    void testMovePiece() {
        int[] moveInputWithoutCapture = new int[]{3,6,3,4};
        Piece[][] gameBoard = chessBoard.getGameBoard();
        Piece pieceToMove = gameBoard[3][6];
        chessBoard.movePiece(moveInputWithoutCapture);
        assertNull(gameBoard[3][6]);
        assertSame(gameBoard[3][4], pieceToMove);
        assertTrue(chessBoard.getWhitePiecesCaptured().isEmpty());
        assertTrue(chessBoard.getBlackPiecesCaptured().isEmpty());
        assertEquals(1, chessBoard.getMoves().size());
        assertFalse(chessBoard.isGameOver());
    }

    @Test
    void testMovePieceCapture() {
        Piece[][] gameBoard = chessBoard.getGameBoard();
        int[] moveInputWithCapture = new int[]{0,0,7,7};
        Piece pieceToMove2 = gameBoard[0][0];
        Piece pieceCaptured = gameBoard[7][7];
        chessBoard.movePiece(moveInputWithCapture);
        assertNull(gameBoard[0][0]);
        assertSame(gameBoard[7][7], pieceToMove2);
        assertSame(chessBoard.getWhitePiecesCaptured().get(0), pieceCaptured.content());
        assertEquals(1, chessBoard.getWhitePiecesCaptured().size());
        assertTrue(chessBoard.getBlackPiecesCaptured().isEmpty());
        assertEquals(1, chessBoard.getMoves().size());
        assertFalse(chessBoard.isGameOver());
    }

    @Test
    void testMovePieceCaptureKing() {
        Piece[][] gameBoard = chessBoard.getGameBoard();
        int[] moveInputCaptureKing = new int[]{3,7,4,0};
        Piece pieceToMove3 = gameBoard[3][7];
        Piece pieceCaptured2 = gameBoard[4][0];
        chessBoard.movePiece(moveInputCaptureKing);
        assertNull(gameBoard[3][7]);
        assertSame(gameBoard[4][0], pieceToMove3);
        assertSame(chessBoard.getBlackPiecesCaptured().get(0), pieceCaptured2.content());
        assertEquals(1, chessBoard.getBlackPiecesCaptured().size());
        assertEquals(1, chessBoard.getMoves().size());
        assertTrue(chessBoard.isGameOver());
    }


    @Test
    void testAddMove() {
        int[] whiteMove = new int[]{7,7,0,0,};
        int[] blackMove = new int[]{1,1,3,3,};
        assertTrue(chessBoard.getMoves().isEmpty());
        chessBoard.addMove(whiteMove);
        assertEquals(1, chessBoard.getMoves().size());
        assertEquals("W: (7,7), (0,0)", chessBoard.getMoves().get(0));
        chessBoard.setWhiteTurn(false);
        chessBoard.addMove(blackMove);
        assertEquals(2, chessBoard.getMoves().size());
        assertEquals("B: (1,1), (3,3)", chessBoard.getMoves().get(1));
    }

    @Test
    void testCheckKing() {
        Piece bishop = new Bishop(true);
        Piece king = new King(true);
        assertTrue(chessBoard.checkKing(king));
        assertFalse(chessBoard.checkKing(bishop));
    }
}
