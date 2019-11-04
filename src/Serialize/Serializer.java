package Serialize;

import com.google.gson.Gson;

public class Serializer {
    public String serialize(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }
}
