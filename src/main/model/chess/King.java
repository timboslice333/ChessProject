package model.chess;

import javax.swing.*;

// King piece on a chessboard with a color
public class King extends Piece {

    // EFFECTS: constructs a king object with given isWhite color
    public King(boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite) {
            icon = new ImageIcon("./data/chessPiecesImages/Chess_klt60.png");
        } else {
            icon = new ImageIcon("./data/chessPiecesImages/Chess_kdt60.png");
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
        return (absDiffX <= 1) && (absDiffY <= 1);
    }

    // EFFECTS: return a string of white king "wK" OR black king "bK"
    @Override
    public String content() {
        if (isWhite) {
            return "wK";
        }
        return "bK";
    }
}
