package co.wisne.matrimonyapp.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;

import java.util.Collection;

import co.wisne.matrimonyapp.models.BasicProfile;

import static android.content.ContentValues.TAG;

public class MainActivityViewModel extends ViewModel {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MutableLiveData<Boolean> UpdateProfile;

    public  MainActivityViewModel(){
    }

    public MutableLiveData<Boolean> getUpdateProfile() {
        if(UpdateProfile == null){
            UpdateProfile = new MutableLiveData<>();
        }
        return UpdateProfile;
    }

    public void loadUserData(){

        db.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "onSuccess: Updating Profile");
                getUpdateProfile().postValue(true);
            }
        });


    }

}
