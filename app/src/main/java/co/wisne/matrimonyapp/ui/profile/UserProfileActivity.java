package co.wisne.matrimonyapp.ui.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
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

        Toolbar toolbar = binding.toolbar;

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getIntent().getExtras().getString("profileUUID"));

        Log.d("D", "onCreate: profile opened for "+getIntent().getExtras().getString("profileUUID"));

        binding.viewPager.setAdapter(new UserProfileTabAdapter(getSupportFragmentManager()));

        binding.tabLayoutUserProfile.setupWithViewPager(binding.viewPager);
    }
}
