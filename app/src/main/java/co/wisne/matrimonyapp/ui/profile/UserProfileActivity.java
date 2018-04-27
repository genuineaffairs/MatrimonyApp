package co.wisne.matrimonyapp.ui.profile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bumptech.glide.Glide;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.ActivityUserProfileBinding;
import co.wisne.matrimonyapp.ui.profile.adapter.UserProfileTabAdapter;
import co.wisne.matrimonyapp.ui.profile.dialogs.BookmarkPromptDialogFragment;

public class UserProfileActivity extends AppCompatActivity implements BookmarkPromptDialogFragment.BookmarkUserPrompt {

    ActivityUserProfileBinding binding;

    UserProfileActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_profile);

        viewModel = ViewModelProviders.of(this).get(UserProfileActivityViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        Toolbar toolbar = binding.toolbar;

        setSupportActionBar(toolbar);

        String userUID = getIntent().getExtras().getString("profileUUID");

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


        binding.buttonBookmark.setOnClickListener((view)->{
            BookmarkPromptDialogFragment bookmarkPromptDialogFragment = new BookmarkPromptDialogFragment();
            bookmarkPromptDialogFragment.show(getSupportFragmentManager(),"bookmark");
        });

    }

    @Override
    public void bookmarkCurrentUser() {
        Log.d("D", "bookmarkCurrentUser: bookmarking "+viewModel.getBasicProfile().getFullName().getValue());
    }
}
