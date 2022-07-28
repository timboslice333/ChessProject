package ui;

import model.ticktacktoe.TickTackToeBoard;
import persistence.ticktacktoe.JsonWriterTic;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

// a ticktacktoe game to play the game
public class TickTackToeGame {
    private JsonWriterTic jsonWriter;
    private static final String JSON_STORE_TIC = "./data/ticBoard.json";
    private static final Dimension TICKTACKTOE_DIMENSION = new Dimension(350,300);
    private JFrame ticFrame;
    private JPanel boardPanel;
    private JPanel utilities;
    private JLabel information;
    private JButton save;
    private TickTackToeBoard bd;

    // EFFECTS: construct a TickTackToeGame object that runs a ticktacktoe game
    public TickTackToeGame() {
        bd = new TickTackToeBoard();
        jsonWriter = new JsonWriterTic(JSON_STORE_TIC);
        ticFrame = new JFrame("TickTackToe Game");
        ticFrame.setMinimumSize(TICKTACKTOE_DIMENSION);
        ticFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        utilities = new JPanel(new GridLayout(2, 0));
        boardPanel = new JPanel(new GridLayout(3, 3));
        initTiles();
        information = new JLabel("O Turn");
        save = new JButton("Save Game");
        addSaveAction();
        utilities.add(save);
        utilities.add(information);
        ticFrame.add(boardPanel, BorderLayout.WEST);
        ticFrame.add(utilities, BorderLayout.EAST);
        ticFrame.setVisible(true);
    }

    // EFFECTS: construct a ticktacktoegame object with given board
    public TickTackToeGame(TickTackToeBoard tickTackToeBoard) throws FileNotFoundException {
        bd = tickTackToeBoard;
        jsonWriter = new JsonWriterTic(JSON_STORE_TIC);
        ticFrame = new JFrame("TickTackToe Game");
        ticFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ticFrame.setMinimumSize(TICKTACKTOE_DIMENSION);
        utilities = new JPanel(new GridLayout(2, 0));
        boardPanel = new JPanel(new GridLayout(3, 3));
        initTiles();
        if (bd.isXturn()) {
            information = new JLabel("X Turn");
        } else {
            information = new JLabel("O Turn");
        }
        save = new JButton("Save Game");
        addSaveAction();
        utilities.add(save);
        utilities.add(information);
        ticFrame.add(boardPanel, BorderLayout.WEST);
        ticFrame.add(utilities, BorderLayout.EAST);
        ticFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add button action to save button
    private void addSaveAction() {
        save.addActionListener(e -> saveTickTackToeGame());
    }

    // MODIFIES: this
    // EFFECTS: initiates the tile panels and add them to the board panel
    private void initTiles() {
        String board = bd.getBoard();
        for (int i = 0; i < 9; i++) {
            String tileText = board.substring(i, i + 1);
            JButton tile = new JButton(tileText);
            addAction(tile, i);
            boardPanel.add(tile);
        }
    }

    // MODIFIES: this
    // EFFECTS: add action to the tile buttons
    private void addAction(JButton tile, int index) {
        tile.addActionListener(e -> {
            if (bd.checkValidMove(index / 3, index % 3)) {
                bd.addMove(index / 3, index % 3);
                if (bd.isXturn()) {
                    tile.setText("X");
                    information.setText("O Turn");
                } else {
                    tile.setText("O");
                    information.setText("X Turn");
                }
                if (bd.isGameOver()) {
                    ticFrame.setVisible(false);
                }
                ticFrame.validate();
                ticFrame.repaint();
            }
        });
    }

    // EFFECTS: saves the workroom to file
    private void saveTickTackToeGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(bd);
            jsonWriter.close();
            information.setText("Saved ticktacktoe game to " + JSON_STORE_TIC);
        } catch (FileNotFoundException e) {
            information.setText("Unable to write to file: " + JSON_STORE_TIC);
        }
    }
}
