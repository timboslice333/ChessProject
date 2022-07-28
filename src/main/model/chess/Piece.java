package model.chess;

import javax.swing.*;
import java.awt.*;

// a piece with a white or black color
public abstract class Piece {
    protected boolean isWhite;
    protected ImageIcon icon;

    // EFFECTS: return true if the move is valid for the piece according to rules of chess, false otherwise
    protected abstract boolean validMove(int[] inputs, ChessBoard board);

    // EFFECTS: return true if the move is not blocked, false otherwise
    protected boolean moveNotBlocked(int[] inputs, ChessBoard board) {
        return true;
    }

    // EFFECTS: return the content of the piece as a string
    public abstract String content();
    
    public boolean isWhite() {
        return isWhite;
    }

    public Image getImage() {
        return icon.getImage();
    }
}
