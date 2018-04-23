package co.wisne.matrimonyapp.ui.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.wisne.matrimonyapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserReligionFragment extends Fragment {


    public UserReligionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_religion, container, false);
    }

}
