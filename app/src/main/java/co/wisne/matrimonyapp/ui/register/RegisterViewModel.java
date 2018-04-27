package co.wisne.matrimonyapp.ui.register;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import co.wisne.matrimonyapp.models.BasicProfile;

import static android.content.ContentValues.TAG;

public class RegisterViewModel extends ViewModel{

    //boolean visibility

    private MutableLiveData<Boolean> formVisibility;

    private MutableLiveData<Boolean> progressBarVisibility;


    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    StorageReference mainReference = firebaseStorage.getReference();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private MutableLiveData<String> relation;
    private MutableLiveData<String> firstName;
    private MutableLiveData<String> lastName;
    private MutableLiveData<String> dateOfBirth;
    private MutableLiveData<String> phoneNumber;

    private MutableLiveData<Uri> profilePictureURI;
    private MutableLiveData<Uri> IDProofURI;

    private MutableLiveData<String> snackBarEvent;

    private String profilePictureFileName;
    private String IDProofFileName;

    public final int SEX_MALE = 2000;
    public final int SEX_FEMALE = 2200;

    private int SEX = -1;

    private MutableLiveData<Boolean> RegistrationSuccess;


    //bitmaps
    Bitmap profilePictureBitmap;

    Bitmap idProofBitmap;




    public RegisterViewModel(){

        getRelation().setValue("self");

        getRegistrationSuccess().setValue(false);

        setStatusReady();

    }

    public void setStatusLoading(){
        getFormVisibility().setValue(false);
        getProgressBarVisibility().setValue(true);
    }

    public void setStatusReady(){
        getFormVisibility().setValue(true);
        getProgressBarVisibility().setValue(false);
    }


    public MutableLiveData<Boolean> getFormVisibility() {
        if(formVisibility == null){
            formVisibility = new MutableLiveData<>();
        }
        return formVisibility;
    }

    public MutableLiveData<Boolean> getProgressBarVisibility() {
        if(progressBarVisibility == null){
            progressBarVisibility = new MutableLiveData<>();
        }
        return progressBarVisibility;
    }

    public MutableLiveData<String> getSnackBarEvent() {
        if(snackBarEvent == null)
        {
            snackBarEvent = new MutableLiveData<>();
        }
        return snackBarEvent;
    }

    public void setSnackBarEvent(MutableLiveData<String> snackBarEvent) {
        this.snackBarEvent = snackBarEvent;
    }

    public MutableLiveData<String> getRelation() {
        if(relation == null){
            relation = new MutableLiveData<>();
        }
        return relation;
    }

    public MutableLiveData<String> getFirstName() {

        if(firstName == null)
        {
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

    public void setLastName(MutableLiveData<String> lastName) {
        this.lastName = lastName;
    }

    public MutableLiveData<String> getDateOfBirth() {
        if(dateOfBirth==null){
            dateOfBirth = new MutableLiveData<>();
        }
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.getDateOfBirth().postValue(dateOfBirth);
    }

    public MutableLiveData<String> getPhoneNumber() {
        if(phoneNumber == null){
            phoneNumber = new MutableLiveData<>();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber.postValue(phoneNumber);
    }

    public MutableLiveData<Uri> getProfilePictureURI() {
        if(profilePictureURI == null){
            profilePictureURI = new MutableLiveData<>();
        }
        return profilePictureURI;
    }

    public void setProfilePictureURI(Uri profilePictureURI) {
        this.getProfilePictureURI().postValue(profilePictureURI);
    }

    public MutableLiveData<Uri> getIDProofURI() {
        if(IDProofURI == null){
            IDProofURI = new MutableLiveData<>();
        }
        return IDProofURI;
    }

    public void setIDProofURI(Uri IDProofURI) {
        this.getIDProofURI().postValue(IDProofURI);
    }


    public void sexChanged(int sex){

        setSexSet(true);

        if(sex == SEX_FEMALE){
            SEX = SEX_FEMALE;
        }else {
            SEX = SEX_MALE;
        }
        Log.d(TAG, "register: "+sex);
    }

    public MutableLiveData<Boolean> getRegistrationSuccess() {
        if(RegistrationSuccess == null){
            RegistrationSuccess = new MutableLiveData<>();
        }
        return RegistrationSuccess;
    }


    //validation
    private boolean firstNameValid;
    private boolean lastNameValid;
    private boolean dateOfBirthSet;
    private boolean dateOfBirthValid;
    private boolean phoneNumberValid;
    private boolean sexSet;
    private boolean profilePictureSet;
    private boolean IDProofSet;

    public boolean isFirstNameValid() {
        return firstNameValid;
    }

    public boolean isLastNameValid() {
        return lastNameValid;
    }

    public boolean isDateOfBirthSet() {
        return dateOfBirthSet;
    }

    public boolean isDateOfBirthValid() {
        return dateOfBirthValid;
    }

    public boolean isPhoneNumberValid() {
        return phoneNumberValid;
    }

    public boolean isSexSet() {
        return sexSet;
    }

    public boolean isProfilePictureSet() {
        return profilePictureSet;
    }

    public boolean isIDProofSet() {
        return IDProofSet;
    }

    public void setFirstNameValid(boolean firstNameValid) {
        this.firstNameValid = firstNameValid;
    }

    public void setLastNameValid(boolean lastNameValid) {
        this.lastNameValid = lastNameValid;
    }

    public void setDateOfBirthSet(boolean dateOfBirthSet) {
        this.dateOfBirthSet = dateOfBirthSet;
    }

    public void setDateOfBirthValid(boolean dateOfBirthValid) {
        this.dateOfBirthValid = dateOfBirthValid;
    }

    public void setPhoneNumberValid(boolean phoneNumberValid) {
        this.phoneNumberValid = phoneNumberValid;
    }

    public void setSexSet(boolean sexSet) {
        this.sexSet = sexSet;
    }

    public void setProfilePictureSet(boolean profilePictureSet) {
        this.profilePictureSet = profilePictureSet;
    }

    public void setIDProofSet(boolean IDProofSet) {
        this.IDProofSet = IDProofSet;
    }


    //functions
    public void registerUser(){

//        if(!isFirstNameValid()){
//            getSnackBarEvent().setValue("Please fill in first name properly.");
//            return;
//        }else if(!isLastNameValid()){
//            getSnackBarEvent().setValue("Please fill in last name properly");
//            return;
//        }else if(!isDateOfBirthValid()){
//            getSnackBarEvent().setValue("Please select a proper date of birth.");
//            return;
//        }else if(!isPhoneNumberValid()){
//            //getSnackBarEvent().setValue("Please validate your phone number.");
//            //return;
//        }else if(!isSexSet()){
//            getSnackBarEvent().setValue("Please choose sex of the user, cannot be changed later.");
//            return;
//        }else if(!isProfilePictureSet()){
//            getSnackBarEvent().setValue("Please select a profile picture to update.");
//            return;
//        }else if(!isIDProofSet()){
//            getSnackBarEvent().setValue("Please choose aadhaar card image to submit.");
//            return;
//        }


        setStatusLoading();

        uploadProfilePicture();


    }

    public void uploadProfilePicture(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        profilePictureBitmap.compress(Bitmap.CompressFormat.JPEG,40,byteArrayOutputStream);

        byte[] data = byteArrayOutputStream.toByteArray();

        StorageReference imagesRef = mainReference.child(user.getUid()+"/"+"images/profile.jpg");

        UploadTask uploadTask = imagesRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d(TAG, "onSuccess: uploaded profile picture "+downloadUrl);
                uploadIDproof();
            }
        });
    }

    public void uploadIDproof(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        idProofBitmap.compress(Bitmap.CompressFormat.JPEG,35,byteArrayOutputStream);

        byte[] data = byteArrayOutputStream.toByteArray();

        StorageReference imagesRef = mainReference.child(user.getUid()+"/"+"images/idProof.jpg");

        UploadTask uploadTask = imagesRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d(TAG, "onSuccess: uploaded idproof "+downloadUrl);
                insertIntoDatabase();
            }
        });

    }

    public void insertIntoDatabase(){

        String relation = getRelation().getValue();
        String firstName = getFirstName().getValue();
        String lastName = getLastName().getValue();
        String birthDate = getDateOfBirth().getValue();
        String phoneNumber = getPhoneNumber().getValue();
        String sex = SEX == SEX_FEMALE ? "female" : "male";


        DocumentReference userDocumentRef = db.collection("users").document(user.getUid());

        BasicProfile userProfile = new BasicProfile();
        userProfile.setRelation(relation);
        userProfile.getName().put("first",firstName);
        userProfile.getName().put("last",lastName);
        try {
            Date dateFormat = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
            userProfile.setBirthDate(dateFormat);
        }catch (ParseException e){
            Log.e(TAG, "insertIntoDatabase: Invalid Date form ",e);
        }
        userProfile.setPhoneNumber(phoneNumber);
        userProfile.setSex(sex);
        userProfile.setProfilePictureName("profile.jpg");
        userProfile.setIdProofPictureName("idProof.jpg");

        userDocumentRef.set(userProfile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: Inserted Data into database");
                        setStatusReady();
                        getRegistrationSuccess().postValue(true);
                    }
                });

    }




}
