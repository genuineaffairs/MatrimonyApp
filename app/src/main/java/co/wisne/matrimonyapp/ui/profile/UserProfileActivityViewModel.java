package co.wisne.matrimonyapp.ui.profile;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.net.Uri;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserProfileActivityViewModel extends ViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    BasicProfile basicProfile;

    UserReligion userReligion;

    UserProfession userProfession;

    MutableLiveData<Uri> userProfileUri;

    public UserProfileActivityViewModel(){
        basicProfile = new BasicProfile();
        userReligion = new UserReligion();
        userProfession = new UserProfession();
    }

    public BasicProfile getBasicProfile() {
        return basicProfile;
    }

    public UserReligion getUserReligion() {
        return userReligion;
    }

    public UserProfession getUserProfession() {
        return userProfession;
    }

    public MutableLiveData<Uri> getUserProfileUri() {
        if(userProfileUri == null){
            userProfileUri = new MutableLiveData<>();
        }
        return userProfileUri;
    }

    @WorkerThread
    public void loadUserInfo(String userUID){

        db.collection("users").document(userUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String firstName = documentSnapshot.getString("name.first");

                String lastName = documentSnapshot.getString("name.last");


                getBasicProfile().getFullName().postValue(firstName + " " + lastName);

                //display date of birth
                Date date = documentSnapshot.getDate("birthDate");

                Date currentDate = new Date();

                long diffTime = currentDate.getTime() - date.getTime();

                long diffYears = (diffTime / (24 * 60 * 60 * 1000))/365;

                DateFormat format = SimpleDateFormat.getDateInstance();

                getBasicProfile().getDateOfBirth().postValue(format.format(date)+" (Age "+diffYears+" )");


                //set Height

                if(documentSnapshot.getDouble("personalDetails.height.feet")!=null){

                    Integer heightFeet = (int)documentSnapshot.getDouble("personalDetails.height.feet").doubleValue();

                    Integer heightInch = (int)documentSnapshot.getDouble("personalDetails.height.inch").doubleValue();

                    getBasicProfile().getHeight().postValue(heightFeet + "' "+heightInch + "\" ");

                }



                //set marital status

                getBasicProfile().getMaritalStatus().postValue(documentSnapshot.getString("personalDetails.maritalStatus"));

                //specially enabled

                Boolean speciallyEnabled = documentSnapshot.getBoolean("personalDetails.speciallyEnabled");

                if(speciallyEnabled != null){

                   if(speciallyEnabled) {

                       getBasicProfile().getSpeciallyEnabled().postValue("Yes");
                   }

                   else getBasicProfile().getSpeciallyEnabled().postValue("No");

                }else {

                    getBasicProfile().getSpeciallyEnabled().postValue("Unavailable");

                }

                //family status

                String familyStatus = documentSnapshot.getString("personalDetails.familyStatus");

                if(familyStatus != null){
                    getBasicProfile().getFamilyStatus().postValue(familyStatus);
                }else {
                    getBasicProfile().getFamilyStatus().postValue("Unavailable");
                }

                //family Type

                String familyType = documentSnapshot.getString("personalDetails.familyType");

                if(familyType != null ){
                    getBasicProfile().getFamilyType().postValue(familyType);
                }else {
                    getBasicProfile().getFamilyType().postValue("Unavailable");
                }

                //number of people

                Double numberOfPeople = documentSnapshot.getDouble("personalDetails.numberOfPeople");

                if(numberOfPeople != null){
                    getBasicProfile().getNumberOfPeople().postValue(String.valueOf(numberOfPeople.intValue()));
                }else {
                    getBasicProfile().getNumberOfPeople().postValue("Unavailable");
                }


                //religion

                String religion = documentSnapshot.getString("religiousDetails.religion");

                if(religion!=null){
                    getUserReligion().getReligion().postValue(religion);
                }else {
                    getUserReligion().getReligion().postValue("Unavailable");
                }

                String caste = documentSnapshot.getString("religiousDetails.caste");

                if(caste!=null){
                    getUserReligion().getCaste().postValue(caste);
                }else {
                    getUserReligion().getCaste().postValue("Unavailable");
                }

                String subCaste = documentSnapshot.getString("religiousDetails.subCaste");

                if(subCaste!=null){
                    getUserReligion().getSubCaste().postValue(subCaste);
                }else {
                    getUserReligion().getSubCaste().postValue("Unavailable");
                }

                //professional details

                String education = documentSnapshot.getString("professionalDetails.highestEducation");

                if(education!= null){
                    getUserProfession().getEducation().postValue(education);
                }else {
                    getUserProfession().getEducation().postValue("Unavailable");
                }

                //employement

                String employement = documentSnapshot.getString("professionalDetails.employementStatus");

                if(employement != null){
                    getUserProfession().getEmployement().postValue(employement);
                }else {
                    getUserProfession().getEmployement().postValue("Unavailable");
                }

                //occupation

                String occupation = documentSnapshot.getString("professionalDetails.occupationDetails");

                if (occupation != null){
                    getUserProfession().getOccupation().postValue(occupation);
                }else {
                    getUserProfession().getOccupation().postValue("Unavailable");
                }

                //income

                String income = documentSnapshot.getString("professionalDetails.income");

                if(income != null){
                    getUserProfession().getSalary().postValue(income);
                }else {
                    getUserProfession().getSalary().postValue("Unavailable");
                }


            }
        });

        //load profile picture
        loadProfileUri(userUID);
    }

     private void loadProfileUri(String UUID){

        FirebaseStorage.getInstance()
                .getReference()
                .child(UUID+"/images/profile.jpg")
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        getUserProfileUri().postValue(uri);
                    }
                });
    }



    public class BasicProfile{

        MutableLiveData<String> fullName;

        MutableLiveData<String> dateOfBirth;

        MutableLiveData<String> height;

        MutableLiveData<String> maritalStatus;

        MutableLiveData<String> speciallyEnabled;

        MutableLiveData<String> familyStatus;

        MutableLiveData<String> familyType;

        MutableLiveData<String> numberOfPeople;

        public MutableLiveData<String> getFullName() {
            if(fullName == null){
                fullName = new MutableLiveData<>();
            }
            return fullName;
        }

        public MutableLiveData<String> getDateOfBirth() {
            if(dateOfBirth == null){
                dateOfBirth = new MutableLiveData<>();
            }
            return dateOfBirth;
        }

        public MutableLiveData<String> getHeight() {
            if(height == null){
                height = new MutableLiveData<>();
            }
            return height;
        }

        public MutableLiveData<String> getMaritalStatus() {
            if (maritalStatus == null) {
                maritalStatus = new MutableLiveData<>();
            }
            return maritalStatus;
        }

        public MutableLiveData<String> getSpeciallyEnabled() {
            if (speciallyEnabled == null)
            {
                speciallyEnabled = new MutableLiveData<>();
            }
            return speciallyEnabled;
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

        public MutableLiveData<String> getNumberOfPeople() {
            if(numberOfPeople == null){
                numberOfPeople = new MutableLiveData<>();
            }
            return numberOfPeople;
        }
    }

    public class UserReligion{

        private MutableLiveData<String> religion;

        private MutableLiveData<String> caste;

        private MutableLiveData<String> subCaste;

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
    }

    public class UserProfession{

        private MutableLiveData<String> education;

        private MutableLiveData<String> employement;

        private MutableLiveData<String> occupation;

        private MutableLiveData<String> salary;

        public MutableLiveData<String> getEducation() {
            if(education == null){
                education = new MutableLiveData<>();
            }
            return education;
        }

        public MutableLiveData<String> getEmployement() {
            if(employement == null){
                employement = new MutableLiveData<>();
            }
            return employement;
        }

        public MutableLiveData<String> getOccupation() {
            if(occupation == null){
                occupation = new MutableLiveData<>();
            }
            return occupation;
        }

        public MutableLiveData<String> getSalary() {
            if(salary == null){
                salary = new MutableLiveData<>();
            }
            return salary;
        }
    }
}
