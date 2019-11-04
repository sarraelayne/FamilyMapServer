package Handlers;

import Requests.LoginRequest;
import Results.LoginResult;
import Serialize.Deserializer;
import Serialize.Serializer;
import Services.LoginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class LoginHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
            Reader reader = new InputStreamReader(httpExchange.getRequestBody());
            LoginRequest r = (LoginRequest) new Deserializer().deserialize(reader, LoginRequest.class);
            String response = "";
            try {
                LoginResult result = new LoginService().login(r);
                response = new Serializer().serialize(result);
            }
            catch (Exception e) {
                serialWrite(response, httpExchange);
                e.printStackTrace();
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
