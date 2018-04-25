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



    public void setMarriageStatus(String value) {
        if(value==null)
            return;
        Log.d(TAG, "setMarriageStatus: "+value);
        getMarriageStatus().setValue(value);
    }

    public void setHeightFeet(String value) {
        if(value==null)
            return;
        Log.d(TAG, "setHeightFeet: "+value);
        getHeightFeet().setValue(Integer.parseInt(value));
    }

    public void setHeightInch(String value) {
        if(value==null)
            return;
        Log.d(TAG, "setHeightInch: "+value);
        getHeightInch().setValue(Integer.parseInt(value));
    }

    public void setFamilyType(String value) {
        if(value==null)
            return;
        Log.d(TAG, "setFamilyType: "+value);
        getFamilyType().setValue(value);
    }

    public void setSpeciallyEnabled(Boolean speciallyEnabled) {

        getSpeciallyEnabled().setValue(speciallyEnabled);
    }
}