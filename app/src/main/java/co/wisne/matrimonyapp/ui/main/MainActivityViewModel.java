package co.wisne.matrimonyapp.ui.main;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import co.wisne.matrimonyapp.models.BasicProfile;
import co.wisne.matrimonyapp.ui.main.model.Bookmarks;
import co.wisne.matrimonyapp.ui.main.model.PersonalDetails;
import co.wisne.matrimonyapp.ui.main.model.ProfessionalDetails;
import co.wisne.matrimonyapp.ui.main.model.ReligiousDetails;

public class MainActivityViewModel extends ViewModel {

    FirebaseUser user ;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MutableLiveData<Boolean> UpdateProfile;

    PersonalDetails personalDetails;

    ReligiousDetails religiousDetails;

    BasicProfile userBasicProfile;

    ProfessionalDetails professionalDetails;

    MediatorLiveData<String> fullName;

    MutableLiveData<String> firstName;
    MutableLiveData<String> lastName;
    MutableLiveData<String> dateOfBirth;
    MutableLiveData<String> gender;
    MutableLiveData<String> mobileNumber;
    MutableLiveData<String> relation;

    MutableLiveData<String> snackBarEvent;

    MutableLiveData<Boolean> profileCompleted;

    StorageReference profilePictureRef;


    //bookmarks
    ArrayList<Bookmarks> userBookmarks;
    MutableLiveData<Boolean> updateBookmarks;

    public  MainActivityViewModel(){

        user = FirebaseAuth.getInstance().getCurrentUser();

        userBasicProfile = new BasicProfile();
        personalDetails = new PersonalDetails();
        religiousDetails = new ReligiousDetails();
        professionalDetails = new ProfessionalDetails();

        getFullName().addSource(getFirstName(), (s)->{
            getFullName().postValue(s + " "+ getLastName().getValue());
        });

        getFullName().addSource(getLastName(), (s)->{
            getFullName().postValue(getFirstName().getValue() + " "+ s );
        });
    }


    //for snackbar


    public MutableLiveData<String> getSnackBarEvent() {
        if(snackBarEvent == null){
            snackBarEvent = new MutableLiveData<>();
        }
        return snackBarEvent;
    }

    public MediatorLiveData<String> getFullName() {
        if(firstName == null){
            fullName = new MediatorLiveData<>();
        }
        return fullName;
    }

    public MutableLiveData<Boolean> getProfileCompleted() {
        if(profileCompleted == null){
            profileCompleted = new MutableLiveData<>();
        }
        return profileCompleted;
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

    public void setRelation(String relation) {
        this.relation.setValue(relation);
    }

    public ArrayList<Bookmarks> getUserBookmarks() {
        if(userBookmarks == null){
            userBookmarks = new ArrayList<>();
        }
        return userBookmarks;
    }

    public void setUserBookmarks(ArrayList<Bookmarks> userBookmarks) {
        this.userBookmarks = userBookmarks;
    }

    public MutableLiveData<Boolean> getUpdateBookmarks() {
        if(updateBookmarks == null){
            updateBookmarks = new MutableLiveData<>();
        }
        return updateBookmarks;
    }

    public void loadUserData(){

        db.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                //set profile picture;

                profilePictureRef = FirebaseStorage.getInstance().getReference().child(user.getUid()+"/images/profile.jpg");

                userBasicProfile = documentSnapshot.toObject(BasicProfile.class);

                getFirstName().postValue(userBasicProfile.getName().get("first"));

                getLastName().postValue(userBasicProfile.getName().get("last"));



                getDateOfBirth().postValue(new SimpleDateFormat("dd/MM/yyy").format(userBasicProfile.getBirthDate()));

                getMobileNumber().postValue(userBasicProfile.getPhoneNumber());

                getGender().postValue(userBasicProfile.getSex());

                getRelation().postValue(userBasicProfile.getRelation());

                getProfileCompleted().postValue(userBasicProfile.getProfileCompleted());

                
                if(documentSnapshot.contains("personalDetails")){

                    //set personal details
                    if(userBasicProfile.getPersonalDetails()!=null){

                        BasicProfile.PersonalDetails personalDetails = userBasicProfile.getPersonalDetails();

                        if(personalDetails.getMaritalStatus()!=null){
                            getPersonalDetails().setMarriageStatus(personalDetails.getMaritalStatus());
                        }

                        if(personalDetails.getHeight()!=null){

                            if(personalDetails.getHeight().get("feet") != null){
                                getPersonalDetails().setHeightFeet(personalDetails.getHeight().get("feet").toString());
                            }

                            if(personalDetails.getHeight().get("inch") != null){
                                getPersonalDetails().setHeightInch(personalDetails.getHeight().get("inch").toString());
                            }

                        }


                        if(personalDetails.getFamilyType()!=null){
                            getPersonalDetails().setFamilyType(personalDetails.getFamilyType());
                        }

                        if(personalDetails.getSpeciallyEnabled()!=null){
                            getPersonalDetails().setSpeciallyEnabled(personalDetails.getSpeciallyEnabled());
                        }

                        if(personalDetails.getFamilyStatus()!=null){
                            getPersonalDetails().getFamilyStatus().setValue(personalDetails.getFamilyStatus());
                        }
                            if(personalDetails.getNumberOfPeople()!=null)
                            getPersonalDetails().getNumberOfFamilyMembers().setValue(String.valueOf(personalDetails.getNumberOfPeople().intValue()));

                    }

                }

                if(userBasicProfile.getReligiousDetails()!=null){
                    //set religious details
                    if(userBasicProfile.getReligiousDetails().getSubCaste()!= null){
                        getReligiousDetails().setSubCaste(userBasicProfile.getReligiousDetails().getSubCaste());
                    }
                }

                if(userBasicProfile.getProfessionalDetails()!=null){
                    BasicProfile.ProfessionalDetails professionalDetails = userBasicProfile.getProfessionalDetails();

                    if(professionalDetails.getHighestEducation()!=null){
                        getProfessionalDetails().setHighestEducation(professionalDetails.getHighestEducation());
                    }

                    if(professionalDetails.getEmployementStatus()!=null){
                        getProfessionalDetails().setEmployementStatus(professionalDetails.getEmployementStatus());
                    }

                    if(professionalDetails.getIncome()!=null){
                        getProfessionalDetails().setIncome(professionalDetails.getIncome());
                    }

                    if (professionalDetails.getOccupationalDetails()!=null){
                        getProfessionalDetails().getOccupationDetails().setValue(professionalDetails.getOccupationalDetails());

                    }

                }

                UpdateProfile.postValue(true);
            }
        });
    }
    

    public void saveProfile() {

        //save changes to user profile

        db.collection("users").document(user.getUid()).update(
                "name.first", getFirstName().getValue(),
                "name.last", getLastName().getValue(),
                "relation",getRelation().getValue()

        );

        db.collection("users")
                .document(user.getUid())
                .update(
                "personalDetails.maritalStatus",getPersonalDetails().getMarriageStatus().getValue(),
                "personalDetails.height.feet",getPersonalDetails().getHeightFeet().getValue(),
                "personalDetails.height.inch",getPersonalDetails().getHeightInch().getValue(),
                "personalDetails.familyStatus",getPersonalDetails().getFamilyStatus().getValue(),
                "personalDetails.familyType",getPersonalDetails().getFamilyType().getValue(),
                "personalDetails.numberOfPeople",Double.parseDouble(getPersonalDetails().getNumberOfFamilyMembers().getValue()),
                "personalDetails.speciallyEnabled",getPersonalDetails().getSpeciallyEnabled().getValue(),

                "religiousDetails.religion", getReligiousDetails().getReligion().getValue(),
                "religiousDetails.caste", getReligiousDetails().getCaste().getValue(),
                "religiousDetails.subCaste", getReligiousDetails().getSubCaste().getValue(),
                "religiousDetails.timeOfBirth", getReligiousDetails().getTimeOfBirth().getValue(),

                "professionalDetails.highestEducation", getProfessionalDetails().getHighestEducation().getValue(),
                "professionalDetails.employementStatus", getProfessionalDetails().getEmployementStatus().getValue(),
                "professionalDetails.occupationalDetails", getProfessionalDetails().getOccupationDetails().getValue(),
                "professionalDetails.income", getProfessionalDetails().getIncome().getValue(),

                 //profile completed
                 "profileCompleted",true


        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getProfileCompleted().postValue(true);
                getSnackBarEvent().postValue("Profile changes saved.");
            }
        });
    }

    @WorkerThread
    public void loadBookmarks(){

        getUserBookmarks().clear();

        db.collection("users")
                .document(user.getUid())
                .collection("bookmarks")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(DocumentSnapshot d: queryDocumentSnapshots){

                            getUserBookmarks().add(
                                    new Bookmarks(
                                            d.getId()
                                            )
                            );

                        }

                        //notify collection changed
                        getUpdateBookmarks().postValue(true);
                    }
                });

    }



    }


