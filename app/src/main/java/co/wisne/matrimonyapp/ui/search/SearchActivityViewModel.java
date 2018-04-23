package co.wisne.matrimonyapp.ui.search;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import co.wisne.matrimonyapp.models.BasicProfile;
import co.wisne.matrimonyapp.ui.search.model.SearchResult;

import static android.content.ContentValues.TAG;

public class SearchActivityViewModel extends ViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    CollectionReference usersRef = db.collection("users");

    String userSex;

    SearchPreference searchPreference;

    ArrayList<SearchResult> searchResults;

    //progress bar visibility
    MutableLiveData<Boolean> showProgressBar;

    MutableLiveData<Boolean> searchReady;

    public SearchActivityViewModel(){

        searchPreference = new SearchPreference();

        searchResults = new ArrayList<>();

        loadUser();

    }

    public MutableLiveData<Boolean> getSearchReady() {
        if(searchReady == null){
            searchReady = new MutableLiveData<>();
        }
        return searchReady;
    }

    public void loadUser(){

        getShowProgressBar().setValue(true);

        db.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                userSex = documentSnapshot.getString("sex");

                Log.d(TAG, "onSuccess: userData loaded successfully");

                getShowProgressBar().setValue(false);
            }
        });
    }

    public void query(){

        Log.d(TAG, "query: querying");
        
        Query query = usersRef.whereEqualTo("sex","female");

        searchResults.clear();

        showProgressBar.setValue(true);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot d: queryDocumentSnapshots) {

                    searchResults.add(new SearchResult(
                            d.getId(),
                            d.getString("name.first"),
                            d.getString("name.last"),
                            d.get("personalDetails.height.feet").toString(),
                            d.get("personalDetails.height.inch").toString(),
                            d.getString("professionalDetails.income"),
                            d.getString("personalDetails.maritalStatus"),
                            d.getString("age")
                    ));

                }
                getSearchReady().setValue(true);
                showProgressBar.setValue(false);
            }
        });

    }


    public MutableLiveData<Boolean> getShowProgressBar() {
        if(showProgressBar == null){
            showProgressBar = new MutableLiveData<>();
        }
        return showProgressBar;
    }

    public SearchPreference getSearchPreference() {
        return searchPreference;
    }

    public class SearchPreference{

        MutableLiveData<String> caste;

        MutableLiveData<String> fromAge;

        MutableLiveData<String> toAge;

        MutableLiveData<String> maritalStatus;

        MutableLiveData<String> fromHeightFeet;

        MutableLiveData<String> fromHeightInch;

        MutableLiveData<String> toHeightFeet;

        MutableLiveData<String> toHeightInch;

        MutableLiveData<String> salary;

        public MutableLiveData<String> getCaste() {
            if(caste==null){
                caste = new MutableLiveData<>();
            }
            return caste;
        }

        public MutableLiveData<String> getFromAge() {
            if(fromAge==null){
                fromAge= new MutableLiveData<>();
            }
            return fromAge;
        }

        public MutableLiveData<String> getToAge() {
            if(toAge==null)            {
                toAge = new MutableLiveData<>();
            }
            return toAge;
        }

        public MutableLiveData<String> getMaritalStatus() {
            if(maritalStatus == null){
                maritalStatus = new MutableLiveData<>();
            }
            return maritalStatus;
        }

        public MutableLiveData<String> getFromHeightFeet() {
            if(fromHeightFeet == null){
                fromHeightFeet = new MutableLiveData<>();
            }
            return fromHeightFeet;
        }

        public MutableLiveData<String> getFromHeightInch() {
            if(fromHeightInch == null){
                fromHeightInch = new MutableLiveData<>();
            }
            return fromHeightInch;
        }

        public MutableLiveData<String> getToHeightFeet() {
            if(toHeightFeet == null){
                toHeightFeet = new MutableLiveData<>();
            }
            return toHeightFeet;
        }

        public MutableLiveData<String> getToHeightInch() {
            if(toHeightInch == null){
                toHeightInch = new MutableLiveData<>();
            }
            return toHeightInch;
        }

        public MutableLiveData<String> getSalary() {
            if(salary == null){
                salary = new MutableLiveData<>();
            }
            return salary;
        }
    }
}



