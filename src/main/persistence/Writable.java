package persistence;

import org.json.JSONObject;

// represents something that is writable to store to JSON
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}