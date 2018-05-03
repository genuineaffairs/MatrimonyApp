package co.wisne.matrimonyapp.ui.chat;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {


    ActivityChatBinding binding;

    ChatActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ChatActivityViewModel.class);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat);

        binding.setLifecycleOwner(this);

        if(getIntent().getExtras().get("profileUUID") != null){

            viewModel.setUserUUID(getIntent().getExtras().get("profileUUID").toString());

            loadChatFragment();
        }


    }

    private void loadChatFragment(){
        if(getSupportFragmentManager().findFragmentByTag("chatWindow")!= null){
           getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("chatWindow")).commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().add(
                    R.id.chatFrame,new ChatWindowFragment(),"chatWindow"
            )
                    .commit();
        }


    }
}
