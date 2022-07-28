package ui;

import model.Event;
import model.EventLog;
import model.chess.ChessBoard;
import model.chess.Piece;
import persistence.chess.JsonWriterChess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

// a chessgame to play the game
public class ChessGame extends JFrame {
    private static final String JSON_STORE_CHESS = "./data/chessBoard.json";
    private static final Dimension CHESS_MIN_DIMENSION = new Dimension(800,640);
    private JPanel informationPanel;
    private JsonWriterChess jsonWriter;
    private static JPanel boardPanel;
    private static ChessTiles[][] tilePanels;
    private JButton moves;
    private JButton capturedWhite;
    private JButton capturedBlack;
    private JButton save;
    private static JLabel allCaptured;
    private static JLabel information;
    private static int[] actions = new int[4];
    private static ChessBoard bd;
    private static boolean firstAction;

    // EFFECTS: construct a chessGame object that runs a chess game
    public ChessGame() {
        bd = new ChessBoard();
        firstAction = true;
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jsonWriter = new JsonWriterChess(JSON_STORE_CHESS);
        setMinimumSize(CHESS_MIN_DIMENSION);
        boardPanel = new JPanel(new GridLayout(8, 8));
        informationPanel = new JPanel(new GridLayout(5, 0));
        information = new JLabel();
        information.setMaximumSize(new Dimension(200, 160));
        allCaptured = new JLabel();
        tilePanels = new ChessTiles[8][8];
        initButtons();
        makeTilesPanel();
        informationPanel.add(information);
        add(boardPanel, BorderLayout.WEST);
        add(allCaptured, BorderLayout.SOUTH);
        setVisible(true);
    }

    // EFFECTS: constructs a chessGame object with given chessboard
    public ChessGame(ChessBoard chessBoard) throws FileNotFoundException {
        bd = chessBoard;
        firstAction = true;
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jsonWriter = new JsonWriterChess(JSON_STORE_CHESS);
        setMinimumSize(CHESS_MIN_DIMENSION);
        boardPanel = new JPanel(new GridLayout(8, 8));
        informationPanel = new JPanel(new GridLayout(5, 0));
        information = new JLabel();
        information.setMaximumSize(new Dimension(200, 160));
        allCaptured = new JLabel();
        tilePanels = new ChessTiles[8][8];
        initButtons();
        makeTilesPanel();
        informationPanel.add(information);
        add(boardPanel, BorderLayout.WEST);
        add(allCaptured, BorderLayout.SOUTH);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initiates the buttons and add them to the panel
    private void initButtons() {
        moves = new JButton("Moves Performed");
        addMovesAction();
        capturedWhite = new JButton("White Pieces Captured");
        addCapWhiteAction();
        capturedBlack = new JButton("Black Pieces Captured");
        addCapBlackAction();
        save = new JButton("Save current game");
        addSaveAction();
        informationPanel.add(moves);
        informationPanel.add(capturedWhite);
        informationPanel.add(capturedBlack);
        informationPanel.add(save);
        add(informationPanel, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: add button action to save button
    private void addSaveAction() {
        save.addActionListener(e -> saveChessGame());
    }

    // MODIFIES: this
    // EFFECTS: add button action to display black pieces captured button
    private void addCapBlackAction() {
        capturedBlack.addActionListener(e -> information.setText(blackCaptured()));
    }

    // MODIFIES: this
    // EFFECTS: add button action to display white pieces captured button
    private void addCapWhiteAction() {
        capturedWhite.addActionListener(e -> information.setText(whiteCaptured()));
    }

    // MODIFIES: this
    // EFFECTS: add button action to display moves button
    private void addMovesAction() {
        moves.addActionListener(e -> information.setText(displayMoves()));
    }

    // MODIFIES: this
    // EFFECTS: moves the tile according to the action
    private static void moveTiles(int[] actions) {
        int pieceX = actions[0];
        int pieceY = actions[1];
        int targetX = actions[2];
        int targetY = actions[3];
        ChessTiles tileToMove = tilePanels[pieceX][pieceY];
        Icon iconToMove = tileToMove.getIcon();
        tileToMove.setIcon(null);
        ChessTiles targetTile = tilePanels[targetX][targetY];
        targetTile.setIcon(iconToMove);
    }

    // MODIFIES: this
    // EFFECTS: initiates the tile panels and add them to the board panel
    private void makeTilesPanel() {
        Piece[][] pieces = bd.getGameBoard();
        boolean light = true;
        for (int row = 0; row < 8; row++) {
            light = !light;
            for (int col = 0; col < 8; col++) {
                ChessTiles tile;
                if (pieces[col][row] == null) {
                    tile = new ChessTiles(light, col, row);
                } else {
                    tile = new ChessTiles(pieces[col][row], light, col, row);
                }
                tile.setOpaque(true);
                tile.setBorderPainted(false);
                tile.setVisible(true);
                light = !light;
                boardPanel.add(tile);
                tilePanels[col][row] = tile;
            }
        }
        boardPanel.setVisible(true);
        boardPanel.setSize(600,600);
        boardPanel.setBackground(Color.ORANGE);
    }

    // EFFECTS: prints the moves in moves arraylist
    public String displayMoves() {
        ArrayList<String> moves = bd.getMoves();
        StringBuilder returnString = new StringBuilder();
        for (String s : moves) {
            returnString.append(s).append(" ");
        }
        return returnString.toString();
    }

    // EFFECTS: returns the captured white pieces in the captured arraylists
    public String whiteCaptured() {
        StringBuilder returnString = new StringBuilder("White pieces captured: ");
        for (String piece : bd.getWhitePiecesCaptured()) {
            returnString.append(piece).append(", ");
        }
        return returnString.toString();
    }

    // EFFECTS: returns the captured white pieces in the captured arraylists
    public String blackCaptured() {
        StringBuilder returnString = new StringBuilder("Black pieces captured: ");
        for (String piece : bd.getBlackPiecesCaptured()) {
            returnString.append(piece).append(", ");
        }
        return returnString.toString();
    }

    // EFFECTS: saves the chess game to file
    private void saveChessGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(bd);
            jsonWriter.close();
            information.setText("Saved chess game to " + JSON_STORE_CHESS);
        } catch (FileNotFoundException e) {
            information.setText("Unable to write to file: " + JSON_STORE_CHESS);
        }
    }

    // a button of chess tile with positions
    private static class ChessTiles extends JButton {
        private int xcoord;
        private int ycoord;

        // EFFECTS: constructs a chesstile with position, color, and piece it represents
        public ChessTiles(Piece piece, boolean light, int xcoord, int ycoord) {
            if (light) {
                setBackground(Color.PINK);
            } else {
                setBackground(Color.ORANGE);
            }
            Image img = piece.getImage();
            Image newimg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(newimg));
            this.xcoord = xcoord;
            this.ycoord = ycoord;
            this.addActionListener(actionListener);
        }

        // EFFECTS: constructs a chesstile with position and color
        public ChessTiles(boolean light, int xcoord, int ycoord) {
            if (light) {
                setBackground(Color.PINK);
            } else {
                setBackground(Color.ORANGE);
            }
            this.xcoord = xcoord;
            this.ycoord = ycoord;
            this.addActionListener(actionListener);
        }

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (firstAction) {
                    information.setText("first input received");
                } else {
                    information.setText("move performed");
                }
                if (firstAction) {
                    actions[0] = xcoord;
                    actions[1] = ycoord;
                    firstAction = false;
                } else {
                    actions[2] = xcoord;
                    actions[3] = ycoord;
                    firstAction = true;
                    drawBoard();
                }
            }
        };

        // MODIFIES: this
        // EFFECTS: updates the board if the move is valid
        private void drawBoard() {
            if (bd.checkValidMove(actions)) {
                bd.movePiece(actions);
                moveTiles(actions);
                updateCaptured();
                if (bd.isGameOver()) {
                    for (Iterator<model.Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                        Event e = it.next();
                        System.out.println(e.toString());
                    }
                    System.exit(0);
                }
            }
            boardPanel.validate();
            boardPanel.repaint();
        }

        // MODIFIES: this
        // EFFECTS: updates the all captured jlabel
        private void updateCaptured() {
            StringBuilder returnString = new StringBuilder("All pieces captured: ");
            for (String piece : bd.getAllPiecesCaptured()) {
                returnString.append(piece).append(", ");
            }
            allCaptured.setText(returnString.toString());
        }
    }

    // EFFECTS: prints out all events when closing
    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            for (Iterator<model.Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                Event logE = it.next();
                System.out.println(logE.toString());
            }
            System.exit(0);
        }
    }
}