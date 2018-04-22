package co.wisne.matrimonyapp.ui.main;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.FragmentBookmarksBinding;
import co.wisne.matrimonyapp.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarksFragment extends Fragment {


    public BookmarksFragment() {
        // Required empty public constructor
    }

    MainActivityViewModel viewModel;


    FragmentBookmarksBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);

        binding = FragmentBookmarksBinding.inflate(inflater,container,false);

        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

}
