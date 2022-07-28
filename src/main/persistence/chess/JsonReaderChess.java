package persistence.chess;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import model.chess.ChessBoard;
import org.json.*;

// Represents a reader that reads chess game from JSON data stored in file
public class JsonReaderChess {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderChess(String source) {
        this.source = source;
    }

    // EFFECTS: reads chessgame from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ChessBoard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseChessBoard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses chess game from JSON object and returns it
    private ChessBoard parseChessBoard(JSONObject jsonObject) {
        ArrayList<String> moves = new ArrayList<String>();
        JSONArray jsonMoves = jsonObject.getJSONArray("moves");
        for (int i = 0; i < jsonMoves.length(); i++) {
            moves.add(jsonMoves.getString(i));
        }
        ArrayList<String> blackCapturedList = new ArrayList<String>();
        JSONArray jsonBlackCaptured = jsonObject.getJSONArray("bcaptured");
        for (int i = 0; i < jsonBlackCaptured.length(); i++) {
            blackCapturedList.add(jsonBlackCaptured.getString(i));
        }
        ArrayList<String> whiteCapturedList = new ArrayList<String>();
        JSONArray jsonWhiteCaptured = jsonObject.getJSONArray("wcaptured");
        for (int i = 0; i < jsonWhiteCaptured.length(); i++) {
            whiteCapturedList.add(jsonWhiteCaptured.getString(i));
        }
        boolean whiteTurn = jsonObject.getBoolean("whiteTurn");
        String[] board = new String[64];
        JSONArray jsonBoard = jsonObject.getJSONArray("board");
        for (int i = 0; i < 64; i++) {
            board[i] = jsonBoard.getString(i);
        }
        return new ChessBoard(board, whiteCapturedList, blackCapturedList, moves, whiteTurn);
    }
}