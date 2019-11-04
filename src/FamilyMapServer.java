import DAO.Database;
import DAO.DatabaseAccessException;
import Handlers.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;


public class FamilyMapServer {

    public static void main(String[] args) throws DatabaseAccessException {
        Database db = new Database();
        try {
//            db.openConnection();
//            db.createTables();
//            db.closeConnection(true);
            runServer();
        }
        catch (IOException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
    }
    private static void runServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 10);

        server.createContext("/", new Handler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/load", new LoadHandler());

        server.setExecutor(null);
        server.start();
    }

}
