package Results;

import Model.Person;

/**
 * handles request from personRequest
 */
public class PersonResult extends Result {
    private Person[] data;
    private String message;

    public PersonResult(Person[] data) {
        this.data = data;
    }
    public PersonResult(String message) {
        this.message = message;
    }

    public Person[] getPerson() {
        return data;
    }
}
