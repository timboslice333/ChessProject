package persistence.ticktacktoe;


import model.ticktacktoe.TickTackToeBoard;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads ticktacktoe game from JSON data stored in file
public class JsonReaderTic {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderTic(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TickTackToeBoard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTicBoard(jsonObject);
    }

    // EFFECTS: parses ticktacktoe game from JSON object and returns it
    private TickTackToeBoard parseTicBoard(JSONObject jsonObject) {
        String board = jsonObject.getString("board");
        boolean turn = jsonObject.getBoolean("xturn");
        TickTackToeBoard loadTickBoard = new TickTackToeBoard(board, turn);
        return loadTickBoard;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
}
