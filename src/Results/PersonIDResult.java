package Results;

import Model.Person;

public class PersonIDResult extends Result {
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String motherID;
    private String fatherID;
    private String spouseID;
    private String message;

    public PersonIDResult(String personID, String associatedUsername, String firstName, String lastName, String gender,
                          String motherID, String fatherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.motherID = motherID;
        this.fatherID = fatherID;
        this.spouseID = spouseID;
    }
    public PersonIDResult(String message) {
        this.message = message;
    }

    public String getPersonID() {return personID;}
    public String getUserName() {return associatedUsername;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getGender() {return gender;}
    public String getMomID() {return motherID;}
    public String getDadID() {return fatherID;}
    public String getSpouseID() {return spouseID;}
}
