package co.wisne.matrimonyapp.ui.search.model;

public class SearchResult {
    String UUID;
    String firstName;
    String lastName;
    String heightFeet;
    String heightInch;
    String salary;
    String maritalStatus;
    String age;

    public SearchResult(){}

    public SearchResult(String UUID,String firstName, String lastName, String heightFeet, String heighInch, String salary, String maritalStatus, String age) {
        this.UUID = UUID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.heightFeet = heightFeet;
        this.heightInch = heighInch;
        this.salary = salary;
        this.maritalStatus = maritalStatus;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(String heightFeet) {
        this.heightFeet = heightFeet;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeightInch() {
        return heightInch;
    }

    public void setHeightInch(String heightInch) {
        this.heightInch = heightInch;
    }

    public String getHeight(){
        return this.heightFeet+"' "+this.heightInch+"''";
    }
}
