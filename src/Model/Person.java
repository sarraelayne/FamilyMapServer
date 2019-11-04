package Model;

/**
 * keeps track of user's people
 */
public class Person {
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String motherID;
    private String fatherID;
    private String spouseID;


    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender,
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


////////////////////////////////////////////////////
             //GETTERS AND SETTERS//
////////////////////////////////////////////////////
    public String getPersonID() {
        return personID;
    }
    public String getUserName() {
        return associatedUsername;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getGender() {
        return gender;
    }
    public String getMomID() {
        return motherID;
    }
    public String getDadID() {
        return fatherID;
    }
    public String getSpouseID() {
        return spouseID;
    }
    public void setPersonID(String personID) {
        this.personID = personID;
    }
    public void setUserName(String userName) {
        this.associatedUsername = userName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setMomID(String momID) {
        this.motherID = momID;
    }
    public void setDadID(String dadID) {
        this.fatherID = dadID;
    }
    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Person) {
            Person oPerson = (Person) o;
            return oPerson.getUserName().equals(getUserName()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender().equals(getGender()) &&
                    oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getMomID().equals(getMomID()) &&
                    oPerson.getDadID().equals(getDadID()) &&
                    oPerson.getSpouseID().equals(getSpouseID());
        } else {
            return false;
        }
    }
}
