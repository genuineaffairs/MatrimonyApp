package co.wisne.matrimonyapp.ui.phoneVerification;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class PhoneVerificationViewModel extends ViewModel {

    MutableLiveData<Boolean> showEntranceWindow;

    MutableLiveData<Boolean> showExitWindow;

    MutableLiveData<String> PhoneNumber;

    MutableLiveData<String> Pin;

    public PhoneVerificationViewModel(){
        getShowEntranceWindow().setValue(true);
    }

    public MutableLiveData<Boolean> getShowEntranceWindow() {
        if(showEntranceWindow == null){
            showEntranceWindow = new MutableLiveData<>();
        }
        return showEntranceWindow;
    }

    public void setShowEntranceWindow(MutableLiveData<Boolean> showEntranceWindow) {
        this.showEntranceWindow = showEntranceWindow;
    }

    public MutableLiveData<Boolean> getShowExitWindow() {
        if(showExitWindow == null){
            showExitWindow = new MutableLiveData<>();
        }
        return showExitWindow;
    }

    public void setShowExitWindow(MutableLiveData<Boolean> showExitWindow) {
        this.showExitWindow = showExitWindow;
    }

    public void showExitWindow(){
        Log.d(TAG, "showExitWindow: "+getPhoneNumber().getValue());
        getShowEntranceWindow().setValue(false);
        getShowExitWindow().setValue(true);
    }

    public MutableLiveData<String> getPhoneNumber() {
        if(PhoneNumber == null){
            PhoneNumber = new MutableLiveData<>();
        }
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        getPhoneNumber().setValue(phoneNumber);
    }

    public MutableLiveData<String> getPin() {
        if(Pin == null){
            Pin = new MutableLiveData<>();
        }
        return Pin;
    }

    public void setPin(String pin) {
        getPin().setValue(pin);
    }
}
