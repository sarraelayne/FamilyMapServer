package Serialize;

import com.google.gson.Gson;

import java.io.Reader;

public class Deserializer {
    public Object deserialize(Reader json, Class cls) {
        Gson gson = new Gson();
        return gson.fromJson(json, cls);
    }
}
