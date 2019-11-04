package Handlers;

import Results.Result;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String url = httpExchange.getRequestURI().toString();

        if (url.isEmpty() || url.equals("/")) {
            url = "/index.html";
        }

        String filePath = Paths.get("").toAbsolutePath().toString() + "/web" + url;
        File file = new File(filePath);

        if(file.exists()) {
            httpExchange.sendResponseHeaders(200, 0);
            Path path = FileSystems.getDefault().getPath(filePath);
            System.out.println(path.toString());
            OutputStream os = httpExchange.getResponseBody();
            Files.copy(path, os);
            os.close();
        }
        else {
            httpExchange.sendResponseHeaders(203, 0);
        }
    }


    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    void nonSerialWrite(Result result, HttpExchange httpExchange) throws IOException {
        Gson gson = new Gson();
        String response = gson.toJson(result);
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream body = httpExchange.getResponseBody();
        writeString(response, body);
        httpExchange.getResponseBody().close();
    }
    void serialWrite(String response, HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream body = httpExchange.getResponseBody();
        writeString(response, body);
        httpExchange.getResponseBody().close();
    }

}
