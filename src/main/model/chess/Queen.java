package model.chess;

import javax.swing.*;

// Queen piece on a chessboard with a color
public class Queen extends Piece {

    // EFFECTS: constructs a queen object with given isWhite color
    public Queen(boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite) {
            icon = new ImageIcon("./data/chessPiecesImages/Chess_qlt60.png");
        } else {
            icon = new ImageIcon("./data/chessPiecesImages/Chess_qdt60.png");
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
        return pieceX == targetX || pieceY == targetY || absDiffX == absDiffY;
    }

    // EFFECTS: return true if the path from the moving piece to target square is not blocked, false otherwise
    @Override
    public boolean moveNotBlocked(int[] inputs, ChessBoard board) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        int targetX = inputs[2];
        int targetY = inputs[3];
        Piece[][] gameBoard = board.getGameBoard();
        if (pieceX == targetX || pieceY == targetY) {
            return checkRook(inputs, gameBoard);
        } else {
            return checkBishop(inputs, gameBoard);
        }
    }

    // EFFECTS: return true if the move is a valid rook move, false otherwise
    public boolean checkRook(int[] inputs, Piece[][] gameBoard) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        int targetX = inputs[2];
        int targetY = inputs[3];
        if (pieceX == targetX) {
            int diffY = targetY - pieceY;
            int dirY = diffY / Math.abs(diffY);
            for (int i = 1; i < Math.abs(diffY); i++) {
                if (gameBoard[pieceX][pieceY + dirY * i] != null) {
                    return false;
                }
            }
            return true;
        } else {
            int diffX = targetX - pieceX;
            int dirX = diffX / Math.abs(diffX);
            for (int i = 1; i < Math.abs(diffX); i++) {
                if (gameBoard[pieceX][pieceY + dirX * i] != null) {
                    return false;
                }
            }
            return true;
        }
    }

    // EFFECTS: return true if inputs move is a valid Bishop move, false otherwise
    public boolean checkBishop(int[] inputs, Piece[][] gameBoard) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        int targetX = inputs[2];
        int targetY = inputs[3];
        int diffX = targetX - pieceX;
        int diffY = targetY - pieceY;
        int dirX = diffX / Math.abs(diffX);
        int dirY = diffY / Math.abs(diffY);
        for (int i = 1; i < Math.abs(diffX); i++) {
            if (gameBoard[pieceX + dirX * i][pieceY + dirY * i] != null) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: return a string of white queen "wQ" OR black queen "bQ"
    @Override
    public String content() {
        if (isWhite) {
            return "wQ";
        }
        return "bQ";
    }
}
