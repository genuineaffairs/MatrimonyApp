package co.wisne.matrimonyapp.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BasicProfile{

    String relation;
    Map<String, String> name = new HashMap<>();
    Date birthDate;
    String phoneNumber;
    String sex;
    String profilePictureName;
    String idProofPictureName;

    ReligiousDetails religiousDetails = new ReligiousDetails();
    PersonalDetails personalDetails = new PersonalDetails();
    ProfessionalDetails professionalDetails = new ProfessionalDetails();

    private Boolean profileCompleted;


    public BasicProfile(){

    }

    public  BasicProfile(String Relation, String FirstName, String LastName, Date BirthDate, String PhoneNumber, String Sex){
        relation = Relation;
        name.put("first",FirstName);
        name.put("last",LastName);
        birthDate = BirthDate;
        phoneNumber = PhoneNumber;
        sex = Sex;
    }

    public  BasicProfile(String Relation, String FirstName, String LastName, Date BirthDate, String PhoneNumber, String Sex, String ProfilePictureName, String IDProofPictureName){
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



    public void setBirthDate(Date birthDate) {
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


    public Date getBirthDate() {
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

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public ReligiousDetails getReligiousDetails() {
        return religiousDetails;
    }

    public void setReligiousDetails(ReligiousDetails religiousDetails) {
        this.religiousDetails = religiousDetails;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }


    public ProfessionalDetails getProfessionalDetails() {
        return professionalDetails;
    }

    public void setProfessionalDetails(ProfessionalDetails professionalDetails) {
        this.professionalDetails = professionalDetails;
    }

    public Boolean getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(Boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }


    //personal details

    public static class PersonalDetails{
        private String familyStatus;
        private String familyType;
        private String maritalStatus;
        private String numberOfPeople;
        private Boolean speciallyEnabled;
        private Map<String, Long> height;


        public String getFamilyStatus() {
            return familyStatus;
        }

        public void setFamilyStatus(String familyStatus) {
            this.familyStatus = familyStatus;
        }

        public String getFamilyType() {
            return familyType;
        }

        public void setFamilyType(String familyType) {
            this.familyType = familyType;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getNumberOfPeople() {
            return numberOfPeople;
        }

        public void setNumberOfPeople(String numberOfPeople) {
            this.numberOfPeople = numberOfPeople;
        }

        public Boolean getSpeciallyEnabled() {
            return speciallyEnabled;
        }

        public void setSpeciallyEnabled(Boolean speciallyEnabled) {
            this.speciallyEnabled = speciallyEnabled;
        }

        public Map<String, Long> getHeight() {
            return height;
        }

        public void setHeight(Map<String, Long> height) {
            this.height = height;
        }


    }

    public static class ReligiousDetails{
        private String caste;
        private String religion;
        private String subCaste;
        private String timeOfBirth;

        public ReligiousDetails() {

        }

        public String getCaste() {
            return caste;
        }

        public void setCaste(String caste) {
            this.caste = caste;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getSubCaste() {
            return subCaste;
        }

        public void setSubCaste(String subCaste) {
            this.subCaste = subCaste;
        }

        public String getTimeOfBirth() {
            return timeOfBirth;
        }

        public void setTimeOfBirth(String timeOfBirth) {
            this.timeOfBirth = timeOfBirth;
        }
    }

    public static class ProfessionalDetails{
        private String highestEducation;
        private String employementStatus;
        private String income;
        private String occupationalDetails;

        public String getHighestEducation() {
            return highestEducation;
        }

        public void setHighestEducation(String highestEducation) {
            this.highestEducation = highestEducation;
        }

        public String getEmployementStatus() {
            return employementStatus;
        }

        public void setEmployementStatus(String employementStatus) {
            this.employementStatus = employementStatus;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getOccupationalDetails() {
            return occupationalDetails;
        }

        public void setOccupationalDetails(String occupationalDetails) {
            this.occupationalDetails = occupationalDetails;
        }
    }

}
