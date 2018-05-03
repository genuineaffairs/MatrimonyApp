package co.wisne.matrimonyapp.ui.chat;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import co.wisne.matrimonyapp.databinding.FragmentChatWindowBinding;
import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.ui.chat.adapter.MessageListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatWindowFragment extends Fragment {

    ChatActivityViewModel viewModel;

    FragmentChatWindowBinding binding;

    public ChatWindowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this.getActivity()).get(ChatActivityViewModel.class);
    }

    RecyclerView.LayoutManager layoutManager;

    MessageListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentChatWindowBinding.inflate(inflater,container,false);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        layoutManager = new LinearLayoutManager(this.getContext());

        listAdapter = new MessageListAdapter(viewModel);

        binding.recyclerMessagesList.setLayoutManager(layoutManager);

        binding.recyclerMessagesList.setAdapter(listAdapter);

        binding.recyclerMessagesList.setHasFixedSize(false);

        viewModel.getUpdateMessages().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.recyclerMessagesList.getAdapter().notifyDataSetChanged();
            }
        });

        viewModel.getAddedMessage().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.recyclerMessagesList.getAdapter().notifyItemInserted(viewModel.messages.size() - 1);
            }
        });

        return binding.getRoot();
    }

}
