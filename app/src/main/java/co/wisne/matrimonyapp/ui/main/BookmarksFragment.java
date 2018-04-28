package co.wisne.matrimonyapp.ui.main;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.FragmentBookmarksBinding;
import co.wisne.matrimonyapp.databinding.FragmentProfileBinding;
import co.wisne.matrimonyapp.ui.main.adapter.BookmarksAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarksFragment extends Fragment {


    public BookmarksFragment() {
        // Required empty public constructor
    }

    MainActivityViewModel viewModel;


    FragmentBookmarksBinding binding;

    RecyclerView.LayoutManager layoutManager;

    BookmarksAdapter bookmarksAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentBookmarksBinding.inflate(inflater,container,false);

        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(this);

        layoutManager = new LinearLayoutManager(this.getActivity());

        bookmarksAdapter = new BookmarksAdapter(viewModel.getUserBookmarks());

        binding.bookmarkRecyclerView.setLayoutManager(layoutManager);

        binding.bookmarkRecyclerView.setHasFixedSize(true);

        binding.bookmarkRecyclerView.setAdapter(bookmarksAdapter);

        viewModel.loadBookmarks();

        viewModel.getUpdateBookmarks().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.bookmarkRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        return binding.getRoot();
    }

}
