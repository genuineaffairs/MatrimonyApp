package co.wisne.matrimonyapp.ui.profile;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.FragmentUserReligionBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserReligionFragment extends Fragment {


    public UserReligionFragment() {
        // Required empty public constructor
    }

    FragmentUserReligionBinding binding;

    UserProfileActivityViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentUserReligionBinding.inflate(inflater,container,false);

        binding.setLifecycleOwner(this);

        viewModel = ViewModelProviders.of(getActivity()).get(UserProfileActivityViewModel.class);

        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

}
