package persistence;

import model.ticktacktoe.TickTackToeBoard;
import org.junit.jupiter.api.Test;
import persistence.ticktacktoe.JsonReaderTic;
import persistence.ticktacktoe.JsonWriterTic;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTicTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TickTackToeBoard tickTackToeBoard = new TickTackToeBoard();
            JsonWriterTic writer = new JsonWriterTic("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBoard() {
        try {
            TickTackToeBoard tickTackToeBoard = new TickTackToeBoard();
            JsonWriterTic writer = new JsonWriterTic("./data/testWriterEmptyTic.json");
            writer.open();
            writer.write(tickTackToeBoard);
            writer.close();

            JsonReaderTic reader = new JsonReaderTic("./data/testWriterEmptyTic.json");
            tickTackToeBoard = reader.read();
            assertTrue(tickTackToeBoard.isXturn());
            assertEquals("---------", tickTackToeBoard.getBoard());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterMovedBoard() {
        try {
            TickTackToeBoard tickTackToeBoard = new TickTackToeBoard("--XOOX-OX", true);
            JsonWriterTic writer = new JsonWriterTic("./data/testWriterEmptyTic.json");
            writer.open();
            writer.write(tickTackToeBoard);
            writer.close();

            JsonReaderTic reader = new JsonReaderTic("./data/testWriterEmptyTic.json");
            tickTackToeBoard = reader.read();
            assertTrue(tickTackToeBoard.isXturn());
            assertEquals("--XOOX-OX", tickTackToeBoard.getBoard());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
