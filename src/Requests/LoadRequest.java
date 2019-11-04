package Requests;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * represents a request to load arrays of users, people and events
 */

public class LoadRequest {
    private User[] users;
    private Person[] persons;
    private Event[] events;

    public void setUsers(User[] users) {
        this.users = users;
    }
//    public void addUser(User user) {
//        this.user;
//    }
    public User[] getUsers() {
        return users;
    }
    public void setPeoples(Person[] persons) {
        this.persons = persons;
    }
//    public void addPeople(Person person) {
//        this.people.add(person);
//    }
    public Person[] getPeoples() {
        return persons;
    }
    public void setEvents(Event[] events) {
        this.events = events;
    }
//    public void addEvent(Event event) {
//        this.events.add(event);
//    }
    public Event[] getEvents() {
        return events;
    }

}
