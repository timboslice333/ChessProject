package model.ticktacktoe;

import org.json.JSONObject;
import persistence.Writable;

// a ticktacktoe game with side-length of 3, a X or O turn and a string representing the board
public class TickTackToeBoard implements Writable {
    private boolean gameOver;
    private static final int SIZE = 3;
    private boolean xturn = true;
    private String board = "---------";

    // EFFECTS: creates a ticktacktoe board with given board and turn
    public TickTackToeBoard(String board, boolean xturn) {
        this.board = board;
        this.xturn = xturn;
    }

    // EFFECTS: creates a brand new ticktacktoe game
    public TickTackToeBoard() {}

    // EFFECTS: checks if the move is valid, return true if valid, false otherwise
    public boolean checkValidMove(int row, int col) {
        return checkInBound(row, col) && checkEmpty(row, col);
    }

    // EFFECTS: returns the string index of the given row col position
    public int getStringIndex(int row, int col) {
        return row * 3 + col;
    }

    // EFFECTS: return true if the input is in the board, false otherwise
    public boolean checkInBound(int row, int col) {
        return (row < 3 && row >= 0 && col < 3 && col >= 0);
    }

    // EFFECTS: return true if the intended square is empty, false otherwise
    public boolean checkEmpty(int row, int col) {
        int stringIndex = getStringIndex(row, col);
        return board.charAt(stringIndex) == '-';
    }

    public boolean isGameOver() {
        return gameOver;
    }

    // MODIFIES: this
    // EFFECTS: make the intended move, then check if the game is over
    public void addMove(int row, int col) {
        int stringIndex = getStringIndex(row, col);
        String firstPart = board.substring(0, stringIndex);
        String secondPart = board.substring(stringIndex + 1);
        if (xturn) {
            board = firstPart + "X" + secondPart;
        } else {
            board = firstPart + "O" + secondPart;
        }
        xturn = !xturn;
        checkGameOver();
    }

    // EFFECTS: change the gameOver if there is continuous three X or O.
    public void checkGameOver() {
        if (checkRow() || checkCol() || checkDiagonal()) {
            gameOver = true;
        }
    }

    // EFFECTS: checks if there is 3 continuous X or O on the diagonals
    public boolean checkDiagonal() {
        String winX = "XXX";
        String winO = "OOO";
        StringBuilder checked = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            int stringIndex = getStringIndex(i, i);
            checked.append(board.charAt(stringIndex));
        }
        if (checked.toString().equals(winX) || checked.toString().equals(winO)) {
            return true;
        } else {
            checked = new StringBuilder();
            for (int i = 0; i < SIZE; i++) {
                int stringIndex = getStringIndex(SIZE - i - 1, i);
                checked.append(board.charAt(stringIndex));
            }
            return checked.toString().equals(winX) || checked.toString().equals(winO);
        }
    }

    // EFFECTS: checks if there is 3 continuous X or O on the same row
    public boolean checkRow() {
        String winX = "XXX";
        String winO = "OOO";
        StringBuilder checked = new StringBuilder();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int stringIndex = getStringIndex(row, col);
                checked.append(board.charAt(stringIndex));
            }
            if (checked.toString().equals(winX) || checked.toString().equals(winO)) {
                return true;
            }
            checked = new StringBuilder();
        }
        return false;
    }

    // EFFECTS: // EFFECTS: checks if there is 3 continuous X or O in the same column
    public boolean checkCol() {
        String winX = "XXX";
        String winO = "OOO";
        StringBuilder checked = new StringBuilder();
        for (int col = 0; col < SIZE; col++) {
            for (int row = 0; row < SIZE; row++) {
                int stringIndex = getStringIndex(row, col);
                checked.append(board.charAt(stringIndex));
            }
            if (checked.toString().equals(winX) || checked.toString().equals(winO)) {
                return true;
            }
            checked = new StringBuilder();
        }
        return false;
    }

    public boolean isXturn() {
        return xturn;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    // EFFECTS: writes the contents to JSON
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("board", board);
        json.put("xturn", xturn);
        return json;
    }
}
