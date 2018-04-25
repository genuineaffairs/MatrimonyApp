package co.wisne.matrimonyapp.ui.main;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    MainActivityViewModel viewModel;

    FragmentHomeBinding binding;

    ProfilePagerAdapter profilePagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater,container,false);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        profilePagerAdapter = new ProfilePagerAdapter(getChildFragmentManager());

        binding.viewPager.setAdapter(profilePagerAdapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);

        return binding.getRoot();
    }

}
