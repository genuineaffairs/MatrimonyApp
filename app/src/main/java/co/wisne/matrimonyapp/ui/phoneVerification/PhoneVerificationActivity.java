package co.wisne.matrimonyapp.ui.phoneVerification;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.ActivityPhoneVerificationBinding;
import co.wisne.matrimonyapp.ui.register.RegisterActivity;

public class PhoneVerificationActivity extends AppCompatActivity {

    PhoneVerificationViewModel viewModel;

    ActivityPhoneVerificationBinding binding;

    String mVerificationID;

    PhoneAuthProvider.ForceResendingToken mToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        viewModel = ViewModelProviders.of(this).get(PhoneVerificationViewModel.class);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_phone_verification);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);


    }

    public void beginPhoneAuth(View view){
        Log.d("D", "beginPhoneAuth: begin auth");
        successfulVerificationExit();

//        viewModel.showExitWindow();
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+91"+viewModel.PhoneNumber.getValue(),
//                60,
//                TimeUnit.SECONDS,
//                this,
//                mCallbacks
//        );
    }

    public void completeAuth(View view){

        Log.d("D", "completeAuth: Entered Pin = "+viewModel.getPin().getValue());

        PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(mVerificationID,viewModel.Pin.getValue());


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            user.linkWithCredential(authCredential).addOnCompleteListener(PhoneVerificationActivity.this,onAuthLinkCompleteListener);
        }

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("D", "onVerificationCompleted:" + credential);

            //begin linking phone with account
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                user.linkWithCredential(credential).addOnCompleteListener(PhoneVerificationActivity.this,onAuthLinkCompleteListener);
            }



        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("D", "onVerificationFailed", e);

            setResult(RESULT_CANCELED);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                binding.textInputPin.setError("Invalid phone number.");
                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...

            }

            // Show a message and update the UI
            // ...
        }

        @Override
        public void onCodeSent(String verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("D", "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later

            mVerificationID = verificationId;
            mToken = token;
            // ...
        }

    };

    //if verification has completed successfully.
    public void successfulVerificationExit(){

        Intent resultIntent = new Intent();
        resultIntent.putExtra("phone","+91"+viewModel.PhoneNumber.getValue());

        if (getParent() == null) {
            setResult(Activity.RESULT_OK,resultIntent);
        } else {
            getParent().setResult(Activity.RESULT_OK,resultIntent);
        }
        finish();
    }

    //if verification has failed.
    public void failedVerificationExit(){
        Intent Data = new Intent();
        Data.putExtra("phone","+91"+viewModel.getPhoneNumber().getValue());
        setResult(RESULT_OK,Data);
        finish();
    }


    //oncomplete listener for the auth linking
    OnCompleteListener<AuthResult> onAuthLinkCompleteListener = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {

                Log.d("D", "linkWithCredential:success");
                successfulVerificationExit();

            } else {
                Log.w("D", "linkWithCredential:failure", task.getException());
                Toast.makeText(getApplicationContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                failedVerificationExit();
            }

        }
    };
}
