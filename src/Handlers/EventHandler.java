package Handlers;

import Results.EventIDResult;
import Results.EventResult;
import Services.EventIDService;
import Services.EventService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class EventHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
            Headers reqHeader = httpExchange.getRequestHeaders();
            if (reqHeader.containsKey("Authorization")) {
                String authToken = reqHeader.getFirst("Authorization");
                String path = httpExchange.getRequestURI().getPath();
                EventIDResult result;
                boolean idCheck;
                String[] params = path.split("/");
                idCheck = params.length == 2;
                try {
                    if (idCheck) { //EventHandler
                        EventResult res = new EventService().getEventsForUser(authToken);
                        nonSerialWrite(res, httpExchange);
                    }
                    else { //EventIDHandler
                        String id = path.substring(7);
                        result = new EventIDService().getEvent(authToken, id);
                        nonSerialWrite(result, httpExchange);
                    }
                    success = true;
                }
                catch (Exception e) {
                    result = new EventIDResult("Error: " + e.getMessage());
                    nonSerialWrite(result, httpExchange);
                }
            }
            if (!success) {
                httpExchange.sendResponseHeaders(203, 0);
                httpExchange.getResponseBody().close();
            }
        }
    }
}
