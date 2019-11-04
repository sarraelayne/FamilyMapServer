package Handlers;

import Results.FillResult;
import Services.FillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class FillHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        try {
            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                String id = httpExchange.getRequestURI().getPath();
                id = id.substring(6);
                String[] substrings = id.split("/");
                String username = substrings[0];
                int gens = 4;
                FillResult result;

                try {
                    if (substrings.length > 1) {
                        gens = Integer.parseInt(substrings[1]);
                    }
                }
                catch (NumberFormatException e) {
                    result = new FillResult("Generations must be numeric and above 0");
                    nonSerialWrite(result, httpExchange);
                }
                finally {
                    if (gens < 1) {
                        result = new FillResult("Generations must be numeric and above 0");
                        nonSerialWrite(result, httpExchange);
                    }
                    else {
                        result = new FillService().fill(username, gens);
                        nonSerialWrite(result, httpExchange);
                        success = true;
                    }
                }
            }
            if (!success) {
                httpExchange.sendResponseHeaders(203, 0);
                httpExchange.getResponseBody().close();
            }
        }
        catch (Exception e) {
            httpExchange.sendResponseHeaders(400, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
