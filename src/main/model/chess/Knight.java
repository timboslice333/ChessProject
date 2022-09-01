package model.chess;

import javax.swing.*;

// Knight piece on a chessboard with a color
public class Knight extends Piece {

    // EFFECTS: constructs a knight object with given isWhite color
    public Knight(boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite) {
            icon = new ImageIcon("./ChessProject-main/data/chessPiecesImages/Chess_nlt60.png");
        } else {
            icon = new ImageIcon("./ChessProject-main/data/chessPiecesImages/Chess_ndt60.png");
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
        return (absDiffX < 3) && (absDiffY < 3) && (absDiffX + absDiffY == 3);
    }

    // EFFECTS: return a string of white knight "wk" OR black knight "bk"
    @Override
    public String content() {
        if (isWhite) {
            return "wk";
        }
        return "bk";
    }
}
