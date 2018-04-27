package co.wisne.matrimonyapp.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class ProfessionalDetails {

    MutableLiveData<String> highestEducation;
    MutableLiveData<String> employementStatus;
    MutableLiveData<String> occupationDetails;
    MutableLiveData<String> income;


    public MutableLiveData<String> getHighestEducation() {
        if (highestEducation == null) {
            highestEducation = new MutableLiveData<>();
        }
        return highestEducation;
    }

    public MutableLiveData<String> getEmployementStatus() {
        if (employementStatus == null) {
            employementStatus = new MutableLiveData<>();
        }
        return employementStatus;
    }

    public MutableLiveData<String> getOccupationDetails() {
        if (occupationDetails == null) {
            occupationDetails = new MutableLiveData<>();
        }
        return occupationDetails;
    }

    public MutableLiveData<String> getIncome() {
        if (income == null) {
            income = new MutableLiveData<>();
        }
        return income;
    }

    public void setHighestEducation(String value) {

        getHighestEducation().setValue(value);
    }

    public void setEmployementStatus(String employementStatus) {

        getEmployementStatus().setValue(employementStatus);
    }

    public void setIncome(String income) {

        getIncome().setValue(income);
    }
}