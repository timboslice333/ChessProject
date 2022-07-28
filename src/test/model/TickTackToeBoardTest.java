package model;

import model.ticktacktoe.TickTackToeBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TickTackToeBoardTest {
    private TickTackToeBoard tickTackToeBoard;

    @BeforeEach
    void runBefore() {
        tickTackToeBoard = new TickTackToeBoard();
    }

    @Test
    void testCheckValidMove() {
        assertTrue(tickTackToeBoard.checkValidMove(0, 0));
        assertFalse(tickTackToeBoard.checkValidMove(3, 0));
        tickTackToeBoard.addMove(1, 1);
        assertFalse(tickTackToeBoard.checkValidMove(1, 1));
    }

    @Test
    void testGetStringIndex() {
        assertEquals(0, tickTackToeBoard.getStringIndex(0, 0));
        assertEquals(3, tickTackToeBoard.getStringIndex(1, 0));
    }

    @Test
    void testCheckInBound() {
        assertTrue(tickTackToeBoard.checkInBound(1, 2));
        assertFalse(tickTackToeBoard.checkInBound(-1, 2));
        assertFalse(tickTackToeBoard.checkInBound(4, 2));
        assertFalse(tickTackToeBoard.checkInBound(2, -1));
        assertFalse(tickTackToeBoard.checkInBound(2, 4));
    }

    @Test
    void testCheckEmpty() {
        assertTrue(tickTackToeBoard.checkEmpty(0, 0));
        tickTackToeBoard.addMove(1, 1);
        assertFalse(tickTackToeBoard.checkEmpty(1, 1));
    }

    @Test
    void testAddMove() {
        tickTackToeBoard.addMove(0, 0);
        assertFalse(tickTackToeBoard.isXturn());
        assertFalse(tickTackToeBoard.checkEmpty(0, 0));
        assertEquals("X--------", tickTackToeBoard.getBoard());

        tickTackToeBoard.addMove(2, 1);
        assertTrue(tickTackToeBoard.isXturn());
        assertFalse(tickTackToeBoard.checkEmpty(2, 1));
        assertEquals("X------O-", tickTackToeBoard.getBoard());
    }

    @Test
    void testCheckGameOver() {
        String diagonalOver = "X-O-XO--X";
        String rowOver = "XXXOXO---";
        String colOver = "XOXXO-X-0";
        String notOver = "X-X--X-O-";
        tickTackToeBoard.setBoard(diagonalOver);
        tickTackToeBoard.checkGameOver();
        assertTrue(tickTackToeBoard.isGameOver());
        tickTackToeBoard.setGameOver(false);
        tickTackToeBoard.setBoard(rowOver);
        tickTackToeBoard.checkGameOver();
        assertTrue(tickTackToeBoard.isGameOver());
        tickTackToeBoard.setGameOver(false);
        tickTackToeBoard.setBoard(colOver);
        tickTackToeBoard.checkGameOver();
        assertTrue(tickTackToeBoard.isGameOver());
        tickTackToeBoard.setGameOver(false);
        tickTackToeBoard.setBoard(notOver);
        tickTackToeBoard.checkGameOver();
        assertFalse(tickTackToeBoard.isGameOver());
    }

    @Test
    void testCheckDiagonal() {
        String downDiagonalOverX = "X-O-XO--X";
        tickTackToeBoard.setBoard(downDiagonalOverX);
        assertTrue(tickTackToeBoard.checkDiagonal());
        String downDiagonalOverO = "O-X-OX--O";
        tickTackToeBoard.setBoard(downDiagonalOverO);
        assertTrue(tickTackToeBoard.checkDiagonal());
        String upDiagonalOverX = "--XOXOX--";
        tickTackToeBoard.setBoard(upDiagonalOverX);
        assertTrue(tickTackToeBoard.checkDiagonal());
        String upDiagonalOverO = "--OXOXO--";
        tickTackToeBoard.setBoard(upDiagonalOverO);
        assertTrue(tickTackToeBoard.checkDiagonal());
    }
    @Test
    void testCheckRow() {
        String firstRowX = "XXX-O-O--";
        tickTackToeBoard.setBoard(firstRowX);
        assertTrue(tickTackToeBoard.checkRow());
        String firstRowO = "OOO-X-X-X";
        tickTackToeBoard.setBoard(firstRowO);
        assertTrue(tickTackToeBoard.checkRow());
        String lastRowX = "---OO-XXX";
        tickTackToeBoard.setBoard(lastRowX);
        assertTrue(tickTackToeBoard.checkRow());
        String lastRowO = "X-X-X-OOO";
        tickTackToeBoard.setBoard(lastRowO);
        assertTrue(tickTackToeBoard.checkRow());
    }

    @Test
    void testCheckCol() {
        String firstColX = "X--X-OXO-";
        tickTackToeBoard.setBoard(firstColX);
        assertTrue(tickTackToeBoard.checkCol());
        String firstColO = "OX-O-XOX";
        tickTackToeBoard.setBoard(firstColO);
        assertTrue(tickTackToeBoard.checkCol());
        String lastColX = "O-X--XO-X";
        tickTackToeBoard.setBoard(lastColX);
        assertTrue(tickTackToeBoard.checkCol());
        String lastColO = "X-O-XOX-O";
        tickTackToeBoard.setBoard(lastColO);
        assertTrue(tickTackToeBoard.checkCol());
    }
}
