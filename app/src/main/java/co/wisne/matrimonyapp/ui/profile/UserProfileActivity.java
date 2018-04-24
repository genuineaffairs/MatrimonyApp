package co.wisne.matrimonyapp.ui.profile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.ActivityUserProfileBinding;
import co.wisne.matrimonyapp.ui.profile.adapter.UserProfileTabAdapter;

public class UserProfileActivity extends AppCompatActivity {

    ActivityUserProfileBinding binding;

    UserProfileActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

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


    }
}
