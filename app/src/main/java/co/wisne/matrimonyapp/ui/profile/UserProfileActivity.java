package co.wisne.matrimonyapp.ui.profile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.UUID;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.ActivityUserProfileBinding;
import co.wisne.matrimonyapp.ui.chat.ChatActivity;
import co.wisne.matrimonyapp.ui.profile.adapter.UserProfileTabAdapter;
import co.wisne.matrimonyapp.ui.profile.dialogs.BookmarkPromptDialogFragment;

public class UserProfileActivity extends AppCompatActivity implements BookmarkPromptDialogFragment.BookmarkUserPrompt {

    ActivityUserProfileBinding binding;

    UserProfileActivityViewModel viewModel;

    String userUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_profile);

        viewModel = ViewModelProviders.of(this).get(UserProfileActivityViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        Toolbar toolbar = binding.toolbar;

        setSupportActionBar(toolbar);

        userUID = getIntent().getExtras().getString("profileUUID");

        getSupportActionBar().setTitle(null);

        getSupportActionBar().setTitle(userUID);

        viewModel.loadUserInfo(userUID);

        Log.d("D", "onCreate: profile opened for "+getIntent().getExtras().getString("profileUUID"));

        binding.viewPager.setAdapter(new UserProfileTabAdapter(getSupportFragmentManager()));

        binding.tabLayoutUserProfile.setupWithViewPager(binding.viewPager);

        viewModel.getUserProfileUri().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(@Nullable Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(binding.appBarImage);
            }
        });

        viewModel.getToastEvent().observe(this, (message)->{
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        });


        binding.buttonBookmark.setOnClickListener((view)->{
            BookmarkPromptDialogFragment bookmarkPromptDialogFragment = new BookmarkPromptDialogFragment();
            bookmarkPromptDialogFragment.show(getSupportFragmentManager(),"bookmark");
        });

        binding.buttonChat.setOnClickListener((view)->{
            Intent chatIntent = new Intent(this, ChatActivity.class);
            chatIntent.putExtra("profileUUID", userUID);
            startActivity(chatIntent);
        });

    }

    @Override
    public void bookmarkCurrentUser() {
        viewModel.bookmarkThisUser();
    }
}
