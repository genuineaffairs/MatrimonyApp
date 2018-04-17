package co.wisne.matrimonyapp.ui.search;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SearchActivityViewModel extends ViewModel {

    public SearchActivityViewModel(){

    }

    private MutableLiveData<String> fromAge;

    public MutableLiveData<String> getFromAge() {
        if(fromAge == null){
            fromAge = new MutableLiveData<>();
        }
        return fromAge;
    }
}
