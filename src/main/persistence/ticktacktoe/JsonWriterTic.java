package persistence.ticktacktoe;

import model.ticktacktoe.TickTackToeBoard;
import org.json.JSONObject;
import persistence.JsonWriter;

// Represents a writer that writes JSON representation of ticktacktoe to file
public class JsonWriterTic extends JsonWriter {

    // EFFECTS: constructs writer to write to ticktacktoe destination file
    public JsonWriterTic(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of ticktacktoe board to file
    public void write(TickTackToeBoard tickTackToeBoard) {
        JSONObject json = tickTackToeBoard.toJson();
        saveToFile(json.toString(TAB));
    }
}
