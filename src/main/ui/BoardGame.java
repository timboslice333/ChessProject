package ui;

import persistence.chess.JsonReaderChess;
import persistence.ticktacktoe.JsonReaderTic;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// a general gameboard to play chess or ticktacktoe
public class BoardGame extends JFrame {
    private static final String JSON_STORE_CHESS = "./ChessProject-main/data/chessBoard.json";
    private static final String JSON_STORE_TIC = "./ChessProject-main/data/ticBoard.json";
    private JsonReaderChess jsonReaderChess;
    private JsonReaderTic jsonReaderTic;
    private JPanel mainPanel;
    private JButton newChess;
    private JButton newTickTackToe;
    private JButton loadChess;
    private JButton loadTickTackToe;
    private JPanel informationPanel;
    private JLabel information;

    // EFFECTS: constructs a board game object
    public BoardGame() {
        super("Main Game");
        mainPanel = new JPanel(new GridLayout(2,2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(300, 150));
        setBackground(Color.GRAY);
        information = new JLabel("Welcome to my game");
        initButtons();
        jsonReaderChess = new JsonReaderChess(JSON_STORE_CHESS);
        jsonReaderTic = new JsonReaderTic(JSON_STORE_TIC);
        informationPanel = new JPanel();
        informationPanel.add(information);
        add(mainPanel, BorderLayout.SOUTH);
        add(informationPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initiates the buttons and add them to the panel
    private void initButtons() {
        newChess = new JButton("New Chess Game");
        newTickTackToe = new JButton("New TickTackToe Game");
        loadChess = new JButton("Load Chess Game");
        loadTickTackToe = new JButton("Load TickTackToe Game");
        addNewChessAction();
        addNewTickTackToeAction();
        addLoadChessAction();
        addLoadTicAction();
        mainPanel.add(newChess);
        mainPanel.add(newTickTackToe);
        mainPanel.add(loadTickTackToe);
        mainPanel.add(loadChess);
    }

    // MODIFIES: this
    // EFFECTS: add button action to load ticktacktoe game button
    private void addLoadTicAction() {
        loadTickTackToe.addActionListener(e -> loadTickTackToeGame());
    }

    // MODIFIES: this
    // EFFECTS: add button action to load chess game button
    private void addLoadChessAction() {
        loadChess.addActionListener(e -> loadChessGame());
    }

    // MODIFIES: this
    // EFFECTS: add button action to new ticktacktoe game button
    private void addNewTickTackToeAction() {
        newTickTackToe.addActionListener(e -> {
            setVisible(false);
            new TickTackToeGame();
        });
    }

    // MODIFIES: this
    // EFFECTS: add button action to new chess game button
    private void addNewChessAction() {
        newChess.addActionListener(e -> {
            setVisible(false);
            new ChessGame();
        });
    }

    // MODIFIES: this
    // EFFECTS: loads ticktacktoe game from file
    private void loadTickTackToeGame() {
        try {
            new TickTackToeGame(jsonReaderTic.read());
            setVisible(false);
        } catch (IOException e) {
            information.setText("Unable to read from file: " + JSON_STORE_TIC);
            add(information);
            validate();
            repaint();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads chess game from file
    private void loadChessGame() {
        try {
            new ChessGame(jsonReaderChess.read());
            setVisible(false);
        } catch (IOException e) {
            information.setText("Unable to read from file: " + JSON_STORE_CHESS);
            add(information);
            validate();
            repaint();
        }
    }
}

