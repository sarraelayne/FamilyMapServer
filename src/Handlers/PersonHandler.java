package Handlers;

import Results.PersonIDResult;
import Results.PersonResult;
import Services.PersonIDService;
import Services.PersonService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class PersonHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
            Headers reqHeader = httpExchange.getRequestHeaders();
            if (reqHeader.containsKey("Authorization")) {
                String authToken = reqHeader.getFirst("Authorization");
                String path = httpExchange.getRequestURI().getPath();
                PersonIDResult result;
                boolean idCheck;
                String[] params = path.split("/");
                idCheck = params.length == 2;
                try {
                    if (idCheck) { //PersonHandler
                        PersonResult res = new PersonService().getPeople(authToken);
                        nonSerialWrite(res, httpExchange);
                    } else { //PersonIDHandler
                        String id = path.substring(7);
                        result = new PersonIDService().getPerson(id, authToken);
                        nonSerialWrite(result, httpExchange);
                    }
                    success = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    result = new PersonIDResult("Error: " + e.getMessage());
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
