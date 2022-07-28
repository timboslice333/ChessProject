package persistence;

import model.ticktacktoe.TickTackToeBoard;
import org.junit.jupiter.api.Test;
import persistence.ticktacktoe.JsonReaderTic;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTicTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReaderTic reader = new JsonReaderTic("./data/noSuchFile.json");
        try {
            TickTackToeBoard tickTackToeBoard = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBoard() {
        JsonReaderTic reader = new JsonReaderTic("./data/emptyTic.json");
        try {
            TickTackToeBoard tickTackToeBoard = reader.read();
            assertTrue(tickTackToeBoard.isXturn());
            assertEquals("---------", tickTackToeBoard.getBoard());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderMovedBoard() {
        JsonReaderTic reader = new JsonReaderTic("./data/movedTic.json");
        try {
            TickTackToeBoard tickTackToeBoard = reader.read();
            assertFalse(tickTackToeBoard.isXturn());
            assertEquals("-X--XX-OO", tickTackToeBoard.getBoard());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
