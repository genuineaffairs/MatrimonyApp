package co.wisne.matrimonyapp.ui.main;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    MainActivityViewModel viewModel;

    FragmentProfileBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this.getActivity()).get(MainActivityViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater,container,false);


        return binding.getRoot();
    }

}
