package Handlers;

import Requests.LoadRequest;
import Results.LoadResult;
import Serialize.Deserializer;
import Serialize.Serializer;
import Services.LoadService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class LoadHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
            Reader reader = new InputStreamReader(httpExchange.getRequestBody());
            LoadRequest req = (LoadRequest) new Deserializer().deserialize(reader, LoadRequest.class);
            String response = "";
            try {
                LoadResult result = new LoadService().load(req);
                response = new Serializer().serialize(result);
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
