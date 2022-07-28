package model.chess;

import model.Event;
import model.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// a chessBoard with side-length 8, a list of moves, a list of captured pieces for each color, and a turn
public class ChessBoard implements Writable {
    private static final int SIZE = 8;
    private Piece[][] gameBoard = new Piece[SIZE][SIZE];
    private boolean gameOver = false;
    private ArrayList<String> whitePiecesCaptured = new ArrayList<>();
    private ArrayList<String> blackPiecesCaptured = new ArrayList<>();
    private ArrayList<String> allPiecesCaptured = new ArrayList<>();
    private ArrayList<String> moves = new ArrayList<>();
    private boolean whiteTurn = true;

    // EFFECTS: creates a chess board with pieces and pawns, first turn is white, and empty lists for captured and moves
    public ChessBoard() {
        initPieces();
        initPawns();
    }

    // EFFECTS: creates a chess board with given board, turn, and lists for captured and moves
    public ChessBoard(String[] board, ArrayList<String> whiteCapturedList, 
                      ArrayList<String> blackCapturedList, ArrayList<String> moves, boolean whiteTurn) {
        gameBoard = loadBoard(board);
        whitePiecesCaptured = whiteCapturedList;
        blackPiecesCaptured = blackCapturedList;
        this.moves = moves;
        this.whiteTurn = whiteTurn;
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: returns a board with given board string
    private Piece[][] loadBoard(String[] board) {
        Piece[][] loadedBoard = new Piece[8][8];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int pos = row * 8 + col;
                if (board[pos].equals("wP")) {
                    loadedBoard[col][row] = new Pawn(true);
                } else if (board[pos].equals("bP")) {
                    loadedBoard[col][row] = new Pawn(false);
                } else if (board[pos].equals("wK")) {
                    loadedBoard[col][row] = new King(true);
                } else if (board[pos].equals("bK")) {
                    loadedBoard[col][row] = new King(false);
                } else if (board[pos].equals("wB")) {
                    loadedBoard[col][row] = new Bishop(true);
                } else if (board[pos].equals("bB")) {
                    loadedBoard[col][row] = new Bishop(false);
                } else if (board[pos].equals("wk")) {
                    loadedBoard[col][row] = new Knight(true);
                } else if (board[pos].equals("bk")) {
                    loadedBoard[col][row] = new Knight(false);
                } else if (board[pos].equals("wR")) {
                    loadedBoard[col][row] = new Rook(true);
                } else if (board[pos].equals("bR")) {
                    loadedBoard[col][row] = new Rook(false);
                } else if (board[pos].equals("wQ")) {
                    loadedBoard[col][row] = new Queen(true);
                } else if (board[pos].equals("bQ")) {
                    loadedBoard[col][row] = new Queen(false);
                }
            }
        }
        return  loadedBoard;
    }

    // MODIFIES: this
    // EFFECTS: creates different pieces objects on their corresponding positions
    public void initPieces() {
        gameBoard[0][0] = new Rook(false);
        gameBoard[1][0] = new Knight(false);
        gameBoard[2][0] = new Bishop(false);
        gameBoard[3][0] = new Queen(false);
        gameBoard[4][0] = new King(false);
        gameBoard[5][0] = new Bishop(false);
        gameBoard[6][0] = new Knight(false);
        gameBoard[7][0] = new Rook(false);
        gameBoard[0][7] = new Rook(true);
        gameBoard[1][7] = new Knight(true);
        gameBoard[2][7] = new Bishop(true);
        gameBoard[3][7] = new Queen(true);
        gameBoard[4][7] = new King(true);
        gameBoard[5][7] = new Bishop(true);
        gameBoard[6][7] = new Knight(true);
        gameBoard[7][7] = new Rook(true);
    }

    // MODIFIES: this
    // EFFECTS: creates pawn objects on their corresponding position
    public void initPawns() {
        for (int col = 0; col < SIZE; col++) {
            gameBoard[col][1] = new Pawn(false);
        }

        for (int col = 0; col < SIZE; col++) {
            gameBoard[col][6] = new Pawn(true);
        }
    }

    // EFFECTS: return true if the inputs is a valid move, false otherwise
    public boolean checkValidMove(int[] inputs) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        boolean notOutOfBoard = isNotOutOfBoard(inputs);
        boolean isSamePlace = (pieceX == inputs[2] && pieceY == inputs[3]);
        boolean notPiece = (gameBoard[pieceX][pieceY] == null);
        if (! notOutOfBoard || notPiece || isSamePlace || isTargetSameColor(inputs)) {
            return false;
        }
        Piece pieceToMove = gameBoard[pieceX][pieceY];
        boolean correctTurn = isCorrectTurn(pieceToMove);
        boolean notBlocked = pieceToMove.moveNotBlocked(inputs, this);
        boolean correctMove = pieceToMove.validMove(inputs, this);
        if (! correctTurn) {
            return false;
        } else if (! notBlocked) {
            return false;
        } else {
            return correctMove;
        }
    }

    // EFFECTS: returns true if the target piece is same color of the moving piece, false otherwise
    public boolean isTargetSameColor(int[] inputs) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        int targetX = inputs[2];
        int targetY = inputs[3];
        if (gameBoard[targetX][targetY] == null) {
            return false;
        } else {
            return gameBoard[pieceX][pieceY].isWhite() == gameBoard[targetX][targetY].isWhite();
        }
    }

    // EFFECTS: returns true if the inputs are not out of the board, false otherwise
    public  boolean isNotOutOfBoard(int[] inputs) {
        for (int i : inputs) {
            if (i < 0 || i > 7) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: return true if the moving piece is corresponding to the turn, false otherwise
    public boolean isCorrectTurn(Piece piece) {
        return (piece.isWhite() == whiteTurn);
    }

    // MODIFIES: this
    // EFFECTS: moves the piece to target square, captures the piece on the target square if there is one,
    // add the captured piece to arraylist of captured, game over if captured piece is king, change turn, add move to
    // moves arraylist
    public void movePiece(int[] inputs) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        int targetX = inputs[2];
        int targetY = inputs[3];
        Piece pieceToMove = gameBoard[pieceX][pieceY];
        gameBoard[pieceX][pieceY] = null;
        if (gameBoard[targetX][targetY] != null) {
            Piece capturedPiece = gameBoard[targetX][targetY];
            if (capturedPiece.isWhite()) {
                whitePiecesCaptured.add(capturedPiece.content());
                EventLog.getInstance().logEvent(
                        new Event(capturedPiece.content() + " added to white and all captured"));
            } else {
                blackPiecesCaptured.add(capturedPiece.content());
                EventLog.getInstance().logEvent(
                        new Event(capturedPiece.content() + " added to black and all captured"));
            }
            allPiecesCaptured.add(capturedPiece.content());
            gameOver = checkKing(capturedPiece);
        }
        addMove(inputs);
        gameBoard[targetX][targetY] = pieceToMove;
        whiteTurn = !whiteTurn;
    }

    // MODIFIES: this
    // EFFECTS: add the move performed to moves arraylist
    public void addMove(int[] inputs) {
        if (isWhiteTurn()) {
            moves.add("W: (" + inputs[0] + "," + inputs[1] + "), (" + inputs[2] + "," + inputs[3] + ")");
        } else {
            moves.add("B: (" + inputs[0] + "," + inputs[1] + "), (" + inputs[2] + "," + inputs[3] + ")");
        }
        EventLog.getInstance().logEvent(new Event("new move added to moves"));
    }

    // EFFECTS: return true if piece is King, false otherwise
    public boolean checkKing(Piece piece) {
        return (piece instanceof King);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Piece[][] getGameBoard() {
        return gameBoard;
    }

    public ArrayList<String> getWhitePiecesCaptured() {
        EventLog.getInstance().logEvent(new Event("loaded White Pieces Captured"));
        return whitePiecesCaptured;
    }

    public ArrayList<String> getBlackPiecesCaptured() {
        EventLog.getInstance().logEvent(new Event("loaded Black Pieces Captured"));
        return blackPiecesCaptured;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public ArrayList<String> getMoves() {
        return moves;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }

    public ArrayList<String> getAllPiecesCaptured() {
        return allPiecesCaptured;
    }

    // EFFECTS: writes the contents of the board into JSON
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONArray moveArray = new JSONArray(moves);
        JSONArray blackCapturedArray = new JSONArray(blackPiecesCaptured);
        JSONArray whiteCapturedArray = new JSONArray(whitePiecesCaptured);
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (gameBoard[col][row] != null) {
                    jsonArray.put(gameBoard[col][row].content());
                } else {
                    jsonArray.put("__");
                }
            }
        }
        json.put("board", jsonArray);
        json.put("moves", moveArray);
        json.put("bcaptured", blackCapturedArray);
        json.put("wcaptured", whiteCapturedArray);
        json.put("whiteTurn", whiteTurn);
        return json;
    }
}
