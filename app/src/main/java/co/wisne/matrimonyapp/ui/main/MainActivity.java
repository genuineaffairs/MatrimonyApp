package co.wisne.matrimonyapp.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;


import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.ActivityMainBinding;
import co.wisne.matrimonyapp.interfaces.ILogin;
import co.wisne.matrimonyapp.models.BasicProfile;
import co.wisne.matrimonyapp.ui.register.RegisterActivity;
import co.wisne.matrimonyapp.ui.search.SearchActivity;

public class MainActivity extends AppCompatActivity implements ILogin{

    FirebaseAuth auth;

    private static final int RC_SIGN_IN = 123;

    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;

    DrawerLayout mDrawerLayout;

    MainActivityViewModel viewModel;

    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main );

        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(this);

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();

        mDrawerLayout = binding.mainLayout;
                //findViewById(R.id.mainLayout);

        NavigationView navigationView = binding.navView;
                //findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        //menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        if(menuItem.getItemId() == R.id.main_menu_search){
                            Log.d("D", "onNavigationItemSelected: search");
                            startSearchIntent();
                        }

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        Toolbar toolbar = binding.toolbar;
                //findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();

        actionbar.setDisplayHomeAsUpEnabled(true);

        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);



        //get firebase auth
        auth = FirebaseAuth.getInstance();

        //if user is not signed in
        if(auth.getCurrentUser() == null){

            //set up Welcome screen

            loadWelcomeScreen();


            ShowLoginUI();


        }else {

            UserProfileCheck();
        }

        ///observer for changes

        viewModel.getUpdateProfile().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean b) {
                loadHomeFragment();
            }
        });


    }

    //load Welcome screen
    public void loadWelcomeScreen(){

        fragmentTransaction.add(R.id.mainFrame,new Welcome(),"Welcome");

        fragmentTransaction.commit();

    }

    //remove login screen()

    @Override
    public void RemoveLoginUI() {

        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragmentManager.findFragmentByTag("Welcome"));

        fragmentTransaction.commit();

    }

    //login screen
    @Override
    public void ShowLoginUI() {
        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                        new AuthUI.IdpConfig.GoogleBuilder().build()
                                )
                        )
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in

                RemoveLoginUI();

                UserProfileCheck();
                // ...
            } else {
                // Sign in failed, check response for error code
                // ...
                displaySnackBar(getResources().getString(R.string.login_error));
            }
        }
    }

    private void loadHomeFragment(){
        Log.d("D", "loadHomeFragment: HomeFragment initiated");

        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.mainFrame,new HomeFragment(),"home");

        fragmentTransaction.commit();

    }

    public void UserProfileCheck(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (user != null) {

            db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful()){

                        //if this is users first time login
                        if(!task.getResult().exists()) {

                            startRegisterIntent();

                        }
                        else{

                           viewModel.loadUserData();

                        }
                    }
                    else {
                        displaySnackBar("Failed to load user data.");
                    }


                }
            });
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private void displaySnackBar(String s){
        Snackbar.make(findViewById(R.id.mainCoordinatorLayout),s,Snackbar.LENGTH_LONG).show();
    }

    //show first time registration form
    private void startRegisterIntent(){

            Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);

            startActivity(registerIntent);

            finish();

    }

    //start search intent
    private void startSearchIntent(){

        Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);

        searchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(searchIntent);

    }




}
