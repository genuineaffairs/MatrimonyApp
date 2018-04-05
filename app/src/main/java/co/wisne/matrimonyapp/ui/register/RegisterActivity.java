package co.wisne.matrimonyapp.ui.register;

import android.app.DatePickerDialog;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.ActivityRegisterBinding;
import co.wisne.matrimonyapp.interfaces.ISetDate;
import co.wisne.matrimonyapp.pickers.DatePickerFragment;
import co.wisne.matrimonyapp.ui.phoneVerification.PhoneVerificationActivity;

public class RegisterActivity extends AppCompatActivity implements ISetDate {

    static final int VERIFY_PHONE_NUMBER = 13;

    RegisterViewModel viewModel;

    ActivityRegisterBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        setTitle("Register");

        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

    }

    public void ShowDatePicker(View view){

        DatePickerFragment datePicker = new DatePickerFragment();

        datePicker.show(getSupportFragmentManager(),"datepicker");

    }

    @Override
    public void setDate(int date,int month, int year) {

        Log.d("D", "setDate: Set Date"+date+" "+month+" "+year);

        viewModel.setDateOfBirth(date+"/"+month+"/"+year);
    }

    public void startPhoneNumberVerification(View view){

        Intent verifyPhoneIntent = new Intent(this, PhoneVerificationActivity.class);
        startActivityForResult(verifyPhoneIntent,VERIFY_PHONE_NUMBER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode ==  VERIFY_PHONE_NUMBER){

            if(resultCode == RESULT_OK){

                Toast.makeText(this,"Phone verification completed successfully.",Toast.LENGTH_SHORT).show();
                String phoneNumber = data.getStringExtra("phone");
                viewModel.setPhoneNumber(phoneNumber);
            }
            else {
                Toast.makeText(this,"Phone verification failed, please try again.",Toast.LENGTH_SHORT).show();
            }

        }

    }
}
