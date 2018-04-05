package co.wisne.matrimonyapp.ui.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;


import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.interfaces.ILogin;
import co.wisne.matrimonyapp.ui.register.RegisterActivity;

public class MainActivity extends AppCompatActivity implements ILogin{

    FirebaseAuth auth;

    private static final int RC_SIGN_IN = 123;

    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();


        //get firebase auth
        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_main);



        //if user is not signed in
        if(auth.getCurrentUser() == null){

            //set up Welcome screen

            loadWelcomeScreen();

            ShowLoginUI();


        }else {

            UserProfileCheck();
        }


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
                Snackbar errorBar = Snackbar.make(findViewById(R.id.mainLayout),R.string.login_error,Snackbar.LENGTH_LONG);
            }
        }
    }

    public void UserProfileCheck(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();




        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                //if this is users first time login
                if(!task.getResult().exists()){

                    Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(registerIntent);
                    finish();

                }
            }
        });


    }





}
