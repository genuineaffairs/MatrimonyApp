package co.wisne.matrimonyapp.models;

import java.util.HashMap;
import java.util.Map;

public class BasicProfile{

    String relation;
    Map<String, String> name = new HashMap<>();
    String birthDate;
    String phoneNumber;
    String sex;
    String profilePictureName;
    String idProofPictureName;


    public BasicProfile(){

    }

    public  BasicProfile(String Relation, String FirstName, String LastName, String BirthDate, String PhoneNumber, String Sex){
        relation = Relation;
        name.put("first",FirstName);
        name.put("last",LastName);
        birthDate = BirthDate;
        phoneNumber = PhoneNumber;
        sex = Sex;
    }

    public  BasicProfile(String Relation, String FirstName, String LastName, String BirthDate, String PhoneNumber, String Sex, String ProfilePictureName, String IDProofPictureName){
        relation = Relation;
        name.put("first",FirstName);
        name.put("last",LastName);
        birthDate = BirthDate;
        phoneNumber = PhoneNumber;
        sex = Sex;
        profilePictureName = ProfilePictureName;
        this.idProofPictureName = IDProofPictureName;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setFirstName(String firstName) {
        this.name.put("first", firstName);
    }

    public void setLastName(String lastName) {
        this.name.put("last", lastName);
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public void setProfilePictureName(String profilePictureName) {
        this.profilePictureName = profilePictureName;
    }

    public void setIdProofPictureName(String idProofPictureName) {
        this.idProofPictureName = idProofPictureName;
    }

    public String getRelation() {
        return relation;
    }

    public Map<String, String> getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public String getProfilePictureName() {
        return profilePictureName;
    }

    public String getIdProofPictureName() {
        return idProofPictureName;
    }
}
