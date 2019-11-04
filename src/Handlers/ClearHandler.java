package Handlers;

import Results.ClearResult;
import Serialize.Serializer;
import Services.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ClearHandler extends Handler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
            ClearService cs = new ClearService();
            String response = "";
            try {
                ClearResult result = cs.clear();
                Serializer ser = new Serializer();
                response = ser.serialize(result);

            } catch (Exception e) {
                e.printStackTrace();
                serialWrite(response, httpExchange);
            }
            serialWrite(response, httpExchange);
            success = true;
        }
        if (!success) {
            httpExchange.sendResponseHeaders(203, 0);
            httpExchange.getResponseBody().close();
        }
    }
}
