package co.wisne.matrimonyapp.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class PersonalDetails{

    MutableLiveData<String> marriageStatus;

    MutableLiveData <Integer> heightFeet;

    MutableLiveData <Integer> heightInch;

    MutableLiveData <String> familyStatus;

    MutableLiveData <String> familyType;

    MutableLiveData <String> numberOfFamilyMembers;

    MutableLiveData <Boolean> speciallyEnabled;


    //update signal
    MutableLiveData <Boolean> updateMarriageStatus;

    MutableLiveData <Boolean> updateHeightFeet;

    MutableLiveData <Boolean> updateHeightInch;

    MutableLiveData <Boolean> updateFamilyType;

    MutableLiveData <Boolean> updateSpeciallyEnabled;

    public MutableLiveData<String> getMarriageStatus() {
        if(marriageStatus == null){
            marriageStatus = new MutableLiveData<>();
        }
        return marriageStatus;
    }

    public MutableLiveData<Integer> getHeightFeet() {
        if(heightFeet == null){
            heightFeet = new MutableLiveData<>();
        }
        return heightFeet;
    }

    public MutableLiveData<Integer> getHeightInch() {
        if(heightInch == null){
            heightInch = new MutableLiveData<>();
        }
        return heightInch;
    }

    public MutableLiveData<String> getFamilyStatus() {
        if(familyStatus == null){
            familyStatus = new MutableLiveData<>();
        }
        return familyStatus;
    }

    public MutableLiveData<String> getFamilyType() {
        if(familyType == null){
            familyType = new MutableLiveData<>();
        }
        return familyType;
    }

    public MutableLiveData<String> getNumberOfFamilyMembers() {
        if(numberOfFamilyMembers == null){
            numberOfFamilyMembers = new MutableLiveData<>();
        }
        return numberOfFamilyMembers;
    }

    public MutableLiveData<Boolean> getSpeciallyEnabled() {
        if(speciallyEnabled == null){
            speciallyEnabled = new MutableLiveData<>();
        }
        return speciallyEnabled;
    }

    public void speciallyEnabledChanged(boolean result){
        Log.d(TAG, "speciallyEnabledChanged: "+result);
        getSpeciallyEnabled().setValue(result);
    }

    public MutableLiveData<Boolean> getUpdateMarriageStatus() {
        if(updateMarriageStatus == null){
            updateMarriageStatus = new MutableLiveData<>();
        }
        return updateMarriageStatus;
    }

    public MutableLiveData<Boolean> getUpdateHeightFeet() {
        if(updateHeightFeet == null){
            updateHeightFeet = new MutableLiveData<>();
        }
        return updateHeightFeet;
    }

    public MutableLiveData<Boolean> getUpdateHeightInch() {
        if(updateHeightInch == null){
            updateHeightInch = new MutableLiveData<>();
        }
        return updateHeightInch;
    }

    public MutableLiveData<Boolean> getUpdateFamilyType() {
        if(updateFamilyType == null){
            updateFamilyType = new MutableLiveData<>();
        }
        return updateFamilyType;
    }

    public MutableLiveData<Boolean> getUpdateSpeciallyEnabled() {
        if(updateSpeciallyEnabled == null){
            updateSpeciallyEnabled = new MutableLiveData<>();
        }
        return updateSpeciallyEnabled;
    }

    public void setMarriageStatus(String value) {
        if(value==null)
            return;
        getMarriageStatus().setValue(value);
        getUpdateMarriageStatus().setValue(true);
    }

    public void setHeightFeet(String value) {
        if(value==null)
            return;
        getHeightFeet().setValue(Integer.parseInt(value));
        getUpdateHeightFeet().setValue(true);
    }

    public void setHeightInch(String value) {
        if(value==null)
            return;
        getHeightInch().setValue(Integer.parseInt(value));
        getUpdateHeightInch().setValue(true);
    }

    public void setFamilyType(String value) {
        if(value==null)
            return;
        getFamilyType().setValue(value);
        getUpdateFamilyType().setValue(true);
    }

    public void setSpeciallyEnabled(Boolean speciallyEnabled) {

        getSpeciallyEnabled().setValue(speciallyEnabled);
        getUpdateSpeciallyEnabled().setValue(speciallyEnabled);
    }
}