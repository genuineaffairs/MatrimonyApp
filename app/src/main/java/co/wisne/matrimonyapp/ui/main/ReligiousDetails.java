package co.wisne.matrimonyapp.ui.main;

import android.arch.lifecycle.MutableLiveData;

public class ReligiousDetails{

    MutableLiveData<String> religion;
    MutableLiveData<String> caste;
    MutableLiveData<String> subCaste;
    MutableLiveData<String> timeOfBirth;

    MutableLiveData<Boolean> updateSubCaste;

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

    public MutableLiveData<Boolean> getUpdateSubCaste() {
        if(updateSubCaste == null){
            updateSubCaste = new MutableLiveData<>();
        }
        return updateSubCaste;
    }

    public void setSubCaste(String value) {
        getSubCaste().setValue(value);
        getUpdateSubCaste().setValue(true);
    }
}