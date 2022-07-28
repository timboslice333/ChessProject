package persistence.chess;

import model.chess.ChessBoard;
import org.json.JSONObject;
import persistence.JsonWriter;

// Represents a writer that writes JSON representation of chess to file
public class JsonWriterChess extends JsonWriter {

    // EFFECTS: constructs writer to write to chess destination file
    public JsonWriterChess(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of ticktacktoe board to file
    public void write(ChessBoard chessBoard) {
        JSONObject json = chessBoard.toJson();
        saveToFile(json.toString(TAB));
    }
}

