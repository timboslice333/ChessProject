package persistence;

import model.chess.ChessBoard;
import model.chess.Pawn;
import model.chess.Piece;
import org.junit.jupiter.api.Test;
import persistence.chess.JsonReaderChess;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderChessTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReaderChess reader = new JsonReaderChess("./data/noSuchFile.json");
        try {
            ChessBoard chessBoard = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBoard() {
        JsonReaderChess reader = new JsonReaderChess("./data/emptyChess.json");
        try {
            ChessBoard chessBoard = reader.read();
            assertTrue(chessBoard.isWhiteTurn());
            assertFalse(chessBoard.isGameOver());
            assertTrue(chessBoard.getBlackPiecesCaptured().isEmpty());
            assertTrue(chessBoard.getWhitePiecesCaptured().isEmpty());
            assertTrue(chessBoard.getMoves().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderMovedBoard() {
        JsonReaderChess reader = new JsonReaderChess("./data/movedChess1.json");
        try {
            ChessBoard chessBoard = reader.read();
            Piece[][] gameBoard = chessBoard.getGameBoard();
            assertFalse(chessBoard.isWhiteTurn());
            assertFalse(chessBoard.isGameOver());
            assertEquals(2, chessBoard.getBlackPiecesCaptured().size());
            assertTrue(chessBoard.getWhitePiecesCaptured().isEmpty());
            assertEquals(5, chessBoard.getMoves().size());
            assertTrue(gameBoard[3][2] instanceof Pawn);
            assertTrue(gameBoard[3][2].isWhite());
            assertTrue(gameBoard[3][1] == null);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}