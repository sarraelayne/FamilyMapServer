package Services;

import DAO.*;
import Data.Locations;
import Data.fNames;
import Data.mNames;
import Data.sNames;
import Model.Event;
import Model.Person;
import Model.User;
import Results.FillResult;

import java.sql.Connection;
import java.util.Random;
import java.util.UUID;

/**
 * performs fill Request
 */

public class FillService {
    private int numPeople = 0;
    private int numEvents = 0;
    private int userYear = 1990;
    private int gap = 25;

    public FillService() {}

    public FillResult fill(String userName, int generations) throws Exception {
        String message = null;
        Database db = new Database();
        Connection connect = db.openConnection();
        User user;
        db.createTables();
        FillResult result;

        if (generations < 0) {
            throw new DatabaseAccessException( "Number of generations must be 0+");
        }

        try {
            UsersDAO uDAO = new UsersDAO(connect);
            EventDAO eDAO = new EventDAO(connect);
            PersonDAO pDAO = new PersonDAO(connect);
            user = uDAO.findUser(userName);

            if (user == null) {
                throw new DatabaseAccessException("Invalid username.");
            }
            eDAO.removeEvent(user.getUsername());
            pDAO.removePerson(user.getUsername());
            Person fillPerson = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(), user.getLastName(),
                    user.getGender(), null, null, null);
            pDAO.addPerson(fillPerson);
            numPeople++;
            buildFamily(fillPerson, generations, 1, user.getUsername(), db);
            result = new FillResult("Successfully added " + numPeople + " persons and " + numEvents + " events to the database.");
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            result = new FillResult("Error: " + e.getMessage());
            db.closeConnection(false);
        }
        return result;
    }
    private void buildFamily(Person person, int generations, int currGen, String userName, Database db) throws DatabaseAccessException {
        String momFirst = new fNames().getRandomName();
        String momLast = new sNames().randomSurname();
        String momID = UUID.randomUUID().toString();
        String dadID = UUID.randomUUID().toString();
        String dadFirst = new mNames().randomMName();

        person.setDadID(dadID);
        person.setMomID(momID);

        try {
            Connection connect = db.openConnection();
            PersonDAO pDAO = new PersonDAO(connect);
            createBirth(person, currGen - 1, db);
            numPeople++;
            pDAO.addPerson(person);
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }

        if (generations == 0) {
            return;
        }

        Person mom = new Person(momID, userName, momFirst, momLast, "f", null, null, dadID);
        Person dad = new Person(dadID, userName, dadFirst, momLast, "m", null, null, momID);
        Locations marriageLoc = new Locations();
        Locations.singleLoc loc =  marriageLoc.randomLoc();
        createMarriage(mom, loc, currGen, db);
        createMarriage(dad, loc, currGen, db);
        createEvents(mom, loc, currGen, db);
        createEvents(dad, loc, currGen, db);

        if (currGen != generations) { //for mom and dad
            buildFamily(mom, generations, currGen + 1, userName, db);
            buildFamily(dad, generations, currGen + 1, userName, db);
        }
        else {
            try {
                Connection connect = db.openConnection();
                PersonDAO pDAO = new PersonDAO(connect);

                numPeople += 2;
                createBirth(mom, currGen, db);
                createBirth(dad, currGen, db);
                pDAO.addPerson(mom);
                pDAO.addPerson(dad);
                db.closeConnection(true);
            }
            catch (DatabaseAccessException e) {
                e.printStackTrace();
                db.closeConnection(false);
            }
        }
    }


    private void createBirth(Person person, int generation, Database db) throws DatabaseAccessException {
        String birthID = UUID.randomUUID().toString();
        EventDAO eDAO = new EventDAO(db.openConnection());
        Locations loc = new Locations();
        Locations.singleLoc birthLoc = loc.randomLoc();
        db.closeConnection(true);

        Event birth = new Event(birthID, person.getUserName(), person.getPersonID(), Double.parseDouble(birthLoc.lat),
                Double.parseDouble(birthLoc.longitude), birthLoc.country, birthLoc.city, "Birth",
                userYear - generation * gap);
        eDAO.insert(birth);
        numEvents++;
    }
    private void createMarriage(Person person, Locations.singleLoc loc, int generation, Database db) throws DatabaseAccessException {
        String marriageID = UUID.randomUUID().toString();
        EventDAO eDAO = new EventDAO(db.openConnection());
        Event marriage = new Event(marriageID, person.getUserName(), person.getPersonID(), Double.parseDouble(loc.lat),
                Double.parseDouble(loc.longitude), loc.country, loc.city, "Marriage", userYear + 20 - generation + gap);
        eDAO.insert(marriage);
        numEvents++;
        db.closeConnection(true);
    }
    private void createEvents(Person person, Locations.singleLoc loc, int generation, Database db) throws DatabaseAccessException {
        String[] moreEvents = {"Death",
            "Got a new job",
            "Won an award",
            "Won a championship",
            "Moved houses"};
        String eventID = UUID.randomUUID().toString();
        Random generate = new Random();
        int i = generate.nextInt(moreEvents.length);
        String eventType = moreEvents[i];
        EventDAO eDAO = new EventDAO(db.openConnection());

        Event event = new Event(eventID, person.getUserName(), person.getPersonID(), Double.parseDouble(loc.lat),
                Double.parseDouble(loc.longitude), loc.country, loc.city, eventType, userYear + 30 - generation * gap);
        eDAO.insert(event);
        numEvents++;
        db.closeConnection(true);
    }

}
