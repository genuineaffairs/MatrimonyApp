package co.wisne.matrimonyapp.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import co.wisne.matrimonyapp.models.BasicProfile;

import static android.content.ContentValues.TAG;

public class MainActivityViewModel extends ViewModel {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MutableLiveData<Boolean> UpdateProfile;

    PersonalDetails personalDetails;

    ReligiousDetails religiousDetails;

    BasicProfile userBasicProfile;

    ProfessionalDetails professionalDetails;

    MutableLiveData<String> firstName;
    MutableLiveData<String> lastName;
    MutableLiveData<String> dateOfBirth;
    MutableLiveData<String> gender;
    MutableLiveData<String> mobileNumber;
    MutableLiveData<String> relation;

    public  MainActivityViewModel(){
        userBasicProfile = new BasicProfile();
        personalDetails = new PersonalDetails();
        religiousDetails = new ReligiousDetails();
        professionalDetails = new ProfessionalDetails();
    }

    public ProfessionalDetails getProfessionalDetails() {
        return professionalDetails;
    }

    public ReligiousDetails getReligiousDetails() {
        return religiousDetails;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public MutableLiveData<String> getFirstName() {
        if(firstName == null){
            firstName = new MutableLiveData<>();
        }
        return firstName;
    }

    public MutableLiveData<String> getLastName() {
        if(lastName == null){
            lastName = new MutableLiveData<>();
        }
        return lastName;
    }

    public MutableLiveData<String> getRelation() {
        if(relation == null){
            relation = new MutableLiveData<>();
        }
        return relation;
    }

    public MutableLiveData<String> getMobileNumber() {
        if(mobileNumber == null){
            mobileNumber = new MutableLiveData<>();
        }
        return mobileNumber;
    }

    public MutableLiveData<Boolean> getUpdateProfile() {
        if(UpdateProfile == null){
            UpdateProfile = new MutableLiveData<>();
        }
        return UpdateProfile;
    }

    public MutableLiveData<String> getGender() {
        if(gender == null){
            gender = new MutableLiveData<>();
        }
        return gender;
    }

    public MutableLiveData<String> getDateOfBirth() {
        if(dateOfBirth == null){
            dateOfBirth = new MutableLiveData<>();
        }
        return dateOfBirth;
    }

    public void loadUserData(){

        db.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Log.d(TAG, "onSuccess: Updating Profile");

                userBasicProfile = documentSnapshot.toObject(BasicProfile.class);

                UpdateProfile.postValue(true);

                getFirstName().postValue(userBasicProfile.getFirstName());

                getLastName().postValue(userBasicProfile.getLastName());

                getDateOfBirth().postValue(userBasicProfile.getBirthDate());

                getMobileNumber().postValue(userBasicProfile.getPhoneNumber());

                getGender().postValue(userBasicProfile.getSex());

                getRelation().postValue(userBasicProfile.getRelation());

                Log.d(TAG, "onSuccess: updated Profile");
            }
        });
    }


    public void saveProfile() {
        Log.d(TAG, "saveProfile: firstname: " + getFirstName().getValue());
        Log.d(TAG, "saveProfile: lastnaem " + getLastName().getValue());
        Log.d(TAG, "saveProfile: date of birth " + getDateOfBirth().getValue());
        Log.d(TAG, "saveProfile: mobile " + getMobileNumber().getValue());
        Log.d(TAG, "saveProfile: gender " + getGender().getValue());
        Log.d(TAG, "saveProfile: relation " + getRelation().getValue());

        //personal Details
        Log.d(TAG, "saveProfile: marital status " + getPersonalDetails().getMarriageStatus().getValue());
        Log.d(TAG, "saveProfile: height feet " + getPersonalDetails().getHeightFeet().getValue());
        Log.d(TAG, "saveProfile: height inch " + getPersonalDetails().getHeightInch().getValue());
        Log.d(TAG, "saveProfile: family status " + getPersonalDetails().getFamilyType().getValue());
        Log.d(TAG, "saveProfile: number of people " + getPersonalDetails().getNumberOfFamilyMembers().getValue());
        Log.d(TAG, "saveProfile: specially enabled " + getPersonalDetails().getSpeciallyEnabled().getValue());

        Log.d(TAG, "saveProfile: religion " + getReligiousDetails().getReligion().getValue());
        Log.d(TAG, "saveProfile: caste " + getReligiousDetails().getCaste().getValue());
        Log.d(TAG, "saveProfile: sub caste" + getReligiousDetails().getSubCaste().getValue());
        Log.d(TAG, "saveProfile: time Of birth " + getReligiousDetails().getTimeOfBirth().getValue());


        Log.d(TAG, "saveProfile: Highest education " + getProfessionalDetails().getHighestEducation().getValue());
        Log.d(TAG, "saveProfile: Employement status" + getProfessionalDetails().getEmployementStatus().getValue());
        Log.d(TAG, "saveProfile: occupation details " + getProfessionalDetails().getOccupationDetails().getValue());
        Log.d(TAG, "saveProfile: Income " + getProfessionalDetails().getIncome().getValue());

        db.collection("users").document(user.getUid()).update(
                "name.first", getFirstName().getValue(),
                "name.last", getLastName().getValue()
        );

        db.collection("users").document(user.getUid()).update(
                "personalDetails.maritalStatus",getPersonalDetails().getMarriageStatus().getValue(),
                "personalDetails.height.feet",getPersonalDetails().getHeightFeet().getValue(),
                "personalDetails.height.inch",getPersonalDetails().getHeightInch().getValue(),
                "personalDetails.familyStatus",getPersonalDetails().getFamilyStatus().getValue(),
                "personalDetails.familyType",getPersonalDetails().getFamilyType().getValue(),
                "personalDetails.numberOfPeople",getPersonalDetails().getNumberOfFamilyMembers().getValue(),
                "personalDetails.speciallyEnabled",getPersonalDetails().getSpeciallyEnabled().getValue(),

                "religiousDetails.religion", getReligiousDetails().getReligion().getValue(),
                "religiousDetails.caste", getReligiousDetails().getCaste().getValue(),
                "religiousDetails.subCaste", getReligiousDetails().getSubCaste().getValue(),
                "religiousDetails.timeOfBirth", getReligiousDetails().getTimeOfBirth().getValue(),

                "professionalDetails.highestEducation", getProfessionalDetails().getHighestEducation().getValue(),
                "professionalDetails.employementStatus", getProfessionalDetails().getEmployementStatus().getValue(),
                "professionalDetails.occupationalDetails", getProfessionalDetails().getOccupationDetails().getValue(),
                "professionalDetails.income", getProfessionalDetails().getIncome().getValue()


        );
    }





    public class PersonalDetails{

        MutableLiveData <String> marriageStatus;
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
    }

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
    }

    public class ProfessionalDetails{

        MutableLiveData<String> highestEducation;
        MutableLiveData<String> employementStatus;
        MutableLiveData<String> occupationDetails;
        MutableLiveData<String> income;

        public MutableLiveData<String> getHighestEducation() {
            if(highestEducation == null){
                highestEducation = new MutableLiveData<>();
            }
            return highestEducation;
        }

        public MutableLiveData<String> getEmployementStatus() {
            if(employementStatus == null){
                employementStatus = new MutableLiveData<>();
            }
            return employementStatus;
        }

        public MutableLiveData<String> getOccupationDetails() {
            if(occupationDetails == null){
                occupationDetails = new MutableLiveData<>();
            }
            return occupationDetails;
        }

        public MutableLiveData<String> getIncome() {
            if(income == null){
                income = new MutableLiveData<>();
            }
            return income;
        }
    }

}