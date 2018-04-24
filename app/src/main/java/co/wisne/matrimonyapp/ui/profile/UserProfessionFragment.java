package co.wisne.matrimonyapp.ui.profile;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.FragmentUserProfessionBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfessionFragment extends Fragment {


    public UserProfessionFragment() {
        // Required empty public constructor
    }

    UserProfileActivityViewModel viewModel;

    FragmentUserProfessionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentUserProfessionBinding.inflate(inflater,container,false);
        binding.setLifecycleOwner(this);

        viewModel = ViewModelProviders.of(getActivity()).get(UserProfileActivityViewModel.class);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

}
