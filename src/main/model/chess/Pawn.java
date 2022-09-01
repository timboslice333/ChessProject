package model.chess;

import javax.swing.*;

// Pawn piece on a chessboard with a color
public class Pawn extends Piece {

    // EFFECTS: constructs a pawn object with given isWhite color
    public Pawn(boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite) {
            icon = new ImageIcon("./ChessProject-main/data/chessPiecesImages/Chess_plt60.png");
        } else {
            icon = new ImageIcon("./ChessProject-main/data/chessPiecesImages/Chess_pdt60.png");
        }
    }

    // EFFECTS: return true if the move is valid for the piece according to rules of chess, false otherwise
    @Override
    public boolean validMove(int[] inputs, ChessBoard board) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        int targetX = inputs[2];
        int targetY = inputs[3];
        int diffY = targetY - pieceY;
        if (pieceX == targetX) {
            if (isWhite) {
                if (pieceY == 6 && diffY >= -2 && diffY < 0) {
                    return true;
                } else {
                    return (diffY == -1);
                }
            } else {
                if (pieceY == 1 && diffY <= 2 && diffY > 0) {
                    return true;
                } else {
                    return (diffY == 1);
                }
            }
        }
        return isPawnCapture(inputs, board);
    }

    // EFFECT: return true if the inputs is a move of valid pawn capture, false otherwise
    public boolean isPawnCapture(int[] inputs, ChessBoard board) {
        int pieceX = inputs[0];
        int pieceY = inputs[1];
        int targetX = inputs[2];
        int targetY = inputs[3];
        int absDiffX = Math.abs(pieceX - targetX);
        int diffY = targetY - pieceY;
        Piece targetSpace = board.getGameBoard()[targetX][targetY];
        if (isWhite) {
            return absDiffX == 1 && diffY == -1 && targetSpace != null && !targetSpace.isWhite();
        } else {
            return absDiffX == 1 && diffY == 1 && targetSpace != null && targetSpace.isWhite();
        }
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
            if ((Math.abs(targetY - pieceY) == 1)) {
                return gameBoard[targetX][targetY] == null;
            } else {
                return checkMiddle(inputs, board);
            }
        }
        return true;
    }

    // EFFECTS: returns true if there is no pieces blocking the two square movement of pawn, false otherwise
    public boolean checkMiddle(int[] inputs, ChessBoard board) {
        int targetX = inputs[2];
        int targetY = inputs[3];
        Piece[][] gameBoard = board.getGameBoard();
        if (isWhite) {
            return (gameBoard[targetX][targetY] == null) && (gameBoard[targetX][targetY + 1] == null);
        }
        return (gameBoard[targetX][targetY] == null) && (gameBoard[targetX][targetY - 1] == null);
    }

    // EFFECTS: return a string of white pawn "wP" OR black pawn "bP"
    @Override
    public String content() {
        if (isWhite) {
            return "wP";
        }
        return "bP";
    }
}
