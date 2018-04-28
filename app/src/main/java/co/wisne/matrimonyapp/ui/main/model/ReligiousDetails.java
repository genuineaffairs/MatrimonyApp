package co.wisne.matrimonyapp.ui.main.model;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class ReligiousDetails{

    MutableLiveData<String> religion;
    MutableLiveData<String> caste;
    MutableLiveData<String> subCaste;
    MutableLiveData<String> timeOfBirth;


    public MutableLiveData<String> getReligion() {
        if(religion == null){
            religion = new MutableLiveData<>();
        }
        return religion;
    }

    public MutableLiveData<String> getCaste() {
        if(caste == null){
            caste = new MutableLiveData<>();
        }
        return caste;
    }

    public MutableLiveData<String> getSubCaste() {
        if(subCaste == null){
            subCaste = new MutableLiveData<>();
        }
        return subCaste;
    }

    public MutableLiveData<String> getTimeOfBirth() {
        if (timeOfBirth == null){
            timeOfBirth = new MutableLiveData<>();
        }
        return timeOfBirth;
    }

    public void setReligion(String religion) {
        if(religion == null){
            return;
        }

        getReligion().setValue(religion);
    }

    public void setCaste(String caste) {
        if(caste==null){
            return;
        }
        getCaste().setValue(caste);
    }

    public void setSubCaste(String value) {
        if(value == null){
            return;
        }

        getSubCaste().setValue(value);

    }
}