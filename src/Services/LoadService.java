package Services;

import DAO.*;
import Model.Event;
import Model.Person;
import Model.User;
import Requests.LoadRequest;
import Results.LoadResult;

import java.sql.Connection;

/**
 * returns all the loaded information
 */

public class LoadService {
    public LoadService() {}

    /**
     *Loads a new family tree for the user
     * @param loadReq the load request with the data to load
     * @return result
     */
    public LoadResult load(LoadRequest loadReq) throws Exception {
        Database db = new Database();
        LoadResult s;
        User[] users = loadReq.getUsers();
        Event[] events = loadReq.getEvents();
        Person[] persons = loadReq.getPeoples();
        try {
            Connection conn = db.openConnection();
            new ClearService().clear();

            loadUsers(users, conn);
            loadPeople(persons, conn);
            loadEvents(events, conn);
            s = new LoadResult("Successfully added " + loadReq.getUsers().length +
                    " users, " + loadReq.getPeoples().length +
                    " persons, and " + loadReq.getEvents().length +
                    " events to the database.");

            db.closeConnection(true);
        }
        catch (DatabaseAccessException e)
        {
            e.printStackTrace();
            s = new LoadResult(e.getMessage());
            db.closeConnection(false);
        }
        return s;
    }

    private void loadUsers(User[] users, Connection conn) throws DatabaseAccessException {
        if (users == null) {
            return;
        }
        UsersDAO uDAO = new UsersDAO(conn);

        for (User user : users) {
            uDAO.addUser(user);
        }
    }
    private void loadPeople(Person[] people, Connection conn) throws DatabaseAccessException {
        if (people == null) {
            return;
        }
        PersonDAO pDAO = new PersonDAO(conn);

        for (Person person : people) {
            pDAO.addPerson(person);
        }
    }
    private void loadEvents(Event[] events, Connection conn) throws DatabaseAccessException {
        if (events == null) {
            return;
        }
        EventDAO eDAO = new EventDAO(conn);

        for (Event event : events) {
            eDAO.insert(event);
        }
    }
}
