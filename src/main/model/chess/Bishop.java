package model.chess;

import javax.swing.*;

// Bishop piece on a chessboard with a color
public class Bishop extends Piece {

    // EFFECTS: constructs a bishop object with given isWhite color
    public Bishop(boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite) {
            icon = new ImageIcon("./data/chessPiecesImages/Chess_blt60.png");
        } else {
            icon = new ImageIcon("./data/chessPiecesImages/Chess_bdt60.png");
        }
    }

    // EFFECTS: return true if the move is valid for the piece according to rules of chess, false otherwise
    @Override
    public boolean validMove(int[] inputs, ChessBoard board) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        int targetX = inputs[2];
        int targetY = inputs[3];
        int absDiffX = Math.abs(pieceX - targetX);
        int absDiffY = Math.abs(pieceY - targetY);
        return absDiffX == absDiffY;
    }

    // EFFECTS: return true if the path from the moving piece to target square is not blocked, false otherwise
    @Override
    public boolean moveNotBlocked(int[] inputs, ChessBoard board) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        int targetX = inputs[2];
        int targetY = inputs[3];
        int diffX = targetX - pieceX;
        int diffY = targetY - pieceY;
        int dirX = diffX / Math.abs(diffX);
        int dirY = diffY / Math.abs(diffY);
        Piece[][] gameBoard = board.getGameBoard();
        for (int i = 1; i < Math.abs(diffX); i++) {
            if (gameBoard[pieceX + dirX * i][pieceY + dirY * i] != null) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: return a string of white bishop "wB" OR black bishop "bB"
    @Override
    public String content() {
        if (isWhite) {
            return "wB";
        }
        return "bB";
    }
}
