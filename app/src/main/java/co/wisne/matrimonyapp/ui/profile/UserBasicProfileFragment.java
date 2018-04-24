package co.wisne.matrimonyapp.ui.profile;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.FragmentUserBasicProfileBinding;
import co.wisne.matrimonyapp.ui.search.SearchActivityViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserBasicProfileFragment extends Fragment {


    public UserBasicProfileFragment() {
        // Required empty public constructor
    }
    UserProfileActivityViewModel viewModel;
    FragmentUserBasicProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(getActivity()).get(UserProfileActivityViewModel.class);

        binding = FragmentUserBasicProfileBinding.inflate(inflater,container,false);

        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

}
