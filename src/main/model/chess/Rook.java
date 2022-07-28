package model.chess;

import javax.swing.*;

// Rook piece on a chessboard with a color
public class Rook extends Piece {

    // EFFECTS: constructs a rook object with given isWhite color
    public Rook(boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite) {
            icon = new ImageIcon("./data/chessPiecesImages/Chess_rlt60.png");
        } else {
            icon = new ImageIcon("./data/chessPiecesImages/Chess_rdt60.png");
        }
    }

    // EFFECTS: return true if the move is valid for the piece according to rules of chess, false otherwise
    @Override
    public boolean validMove(int[] inputs, ChessBoard board) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        int targetX = inputs[2];
        int targetY = inputs[3];
        return pieceX == targetX || pieceY == targetY;
    }

    // EFFECTS: return true if the path from the moving piece to target square is not blocked, false otherwise
    @Override
    public boolean moveNotBlocked(int[] inputs, ChessBoard board) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        int targetX = inputs[2];
        int targetY = inputs[3];
        Piece[][] gameBoard = board.getGameBoard();
        if (pieceX == targetX) {
            int diffY = targetY - pieceY;
            int dirY = diffY / Math.abs(diffY);
            for (int i = 1; i < Math.abs(diffY); i++) {
                if (gameBoard[pieceX][pieceY + dirY * i] != null) {
                    return false;
                }
            }
        } else {
            int diffX = targetX - pieceX;
            int dirX = diffX / Math.abs(diffX);
            for (int i = 1; i < Math.abs(diffX); i++) {
                if (gameBoard[pieceX + dirX * i][pieceY] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    // EFFECTS: return a string of white rook "wR" OR black rook "bR"
    @Override
    public String content() {
        if (isWhite) {
            return "wR";
        }
        return "bR";
    }
}
