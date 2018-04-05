package co.wisne.matrimonyapp.ui.register;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class RegisterViewModel extends ViewModel{

    private MutableLiveData<String> firstName;
    private MutableLiveData<String> lastName;
    private MutableLiveData<String> dateOfBirth;
    private MutableLiveData<String> phoneNumber;



    public RegisterViewModel(){
    }

    public MutableLiveData<String> getFirstName() {
        if(firstName == null)
        {
            firstName = new MutableLiveData<>();
        }
        return firstName;
    }

    public void setFirstName(String firstName){

        getFirstName().postValue(firstName);
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


}
