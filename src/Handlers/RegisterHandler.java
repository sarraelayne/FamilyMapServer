package Handlers;

import Requests.RegisterRequest;
import Results.RegisterResult;
import Serialize.Deserializer;
import Serialize.Serializer;
import Services.RegisterService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;


public class RegisterHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
            Reader reader = new InputStreamReader(httpExchange.getRequestBody());
            RegisterRequest r = (RegisterRequest) new Deserializer().deserialize(reader, RegisterRequest.class);
            String resp = "";
            try {
                RegisterResult result = new RegisterService().register(r);
                resp = new Serializer().serialize(result);
            } catch (Exception e) {
                e.printStackTrace();
                serialWrite(resp, httpExchange);
            }
            serialWrite(resp, httpExchange);
            success = true;
        }
        if (!success) {
            httpExchange.sendResponseHeaders(203, 0);
            httpExchange.getResponseBody().close();
        }
    }
}
