package persistence;

import model.chess.ChessBoard;
import model.chess.Pawn;
import model.chess.Piece;
import org.junit.jupiter.api.Test;
import persistence.chess.JsonReaderChess;
import persistence.chess.JsonWriterChess;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonWriterChessTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ChessBoard chessBoard = new ChessBoard();
            JsonWriterChess writer = new JsonWriterChess("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBoard() {
        try {
            ChessBoard chessBoard = new ChessBoard();
            JsonWriterChess writer = new JsonWriterChess("./data/testWriterEmptyChess.json");
            writer.open();
            writer.write(chessBoard);
            writer.close();

            JsonReaderChess reader = new JsonReaderChess("./data/testWriterEmptyChess.json");
            chessBoard = reader.read();
            assertTrue(chessBoard.isWhiteTurn());
            assertFalse(chessBoard.isGameOver());
            assertTrue(chessBoard.getBlackPiecesCaptured().isEmpty());
            assertTrue(chessBoard.getWhitePiecesCaptured().isEmpty());
            assertTrue(chessBoard.getMoves().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterMovedBoard1() {
        try {
            JsonReaderChess readerFromData = new JsonReaderChess("./data/movedChess1.json");
            ChessBoard chessBoard = readerFromData.read();
            JsonWriterChess writer = new JsonWriterChess("./data/testWriterMovedChess.json");
            writer.open();
            writer.write(chessBoard);
            writer.close();

            JsonReaderChess readerFromWritten = new JsonReaderChess("./data/testWriterMovedChess.json");
            chessBoard = readerFromWritten.read();
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
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterMovedBoard2() {
        try {
            JsonReaderChess readerFromData = new JsonReaderChess("./data/movedChess2.json");
            ChessBoard chessBoard = readerFromData.read();
            JsonWriterChess writer = new JsonWriterChess("./data/testWriterMovedChess.json");
            writer.open();
            writer.write(chessBoard);
            writer.close();

            JsonReaderChess readerFromWritten = new JsonReaderChess("./data/testWriterMovedChess.json");
            chessBoard = readerFromWritten.read();
            Piece[][] gameBoard = chessBoard.getGameBoard();
            assertTrue(chessBoard.isWhiteTurn());
            assertFalse(chessBoard.isGameOver());
            assertEquals(2, chessBoard.getWhitePiecesCaptured().size());
            assertTrue(chessBoard.getBlackPiecesCaptured().isEmpty());
            assertEquals(6, chessBoard.getMoves().size());
            assertTrue(gameBoard[5][4] instanceof Pawn);
            assertTrue(gameBoard[3][5] instanceof Pawn);
            assertFalse(gameBoard[3][5].isWhite());
            assertTrue(gameBoard[3][1] == null);
            assertTrue(gameBoard[3][6] == null);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
