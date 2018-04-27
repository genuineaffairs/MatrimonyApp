package co.wisne.matrimonyapp.ui.register;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.ActivityRegisterBinding;
import co.wisne.matrimonyapp.dialogs.SelectPictureDialogFragment;
import co.wisne.matrimonyapp.interfaces.ISetDate;
import co.wisne.matrimonyapp.dialogs.DatePickerFragment;
import co.wisne.matrimonyapp.ui.main.MainActivity;
import co.wisne.matrimonyapp.ui.phoneVerification.PhoneVerificationActivity;

public class RegisterActivity extends AppCompatActivity implements ISetDate,AdapterView.OnItemSelectedListener {



    static final int VERIFY_PHONE_NUMBER = 13;

    static final int CHOOSE_PICTURE_FROM_GALLERY = 125;

    static final int REQUEST_IMAGE_CAPTURE = 127;

    RegisterViewModel viewModel;

    ActivityRegisterBinding binding;

    SelectPictureDialogFragment selectPicture;


    //pictures

    byte PROFILE_PICTURE = 6;

    byte ID_PROOF = 5;

    byte CHOOSEN_PICTURE = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        setTitle("Register");

        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        binding.spinnerProfileFor.setOnItemSelectedListener(this);


        //inpur validations
        viewModel.getFirstName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                if(s.length() < 3){
                    binding.textInputLayoutFirstname.setErrorEnabled(true);
                    binding.textInputLayoutFirstname.setError(getResources().getString(R.string.register_firstname_error));
                    viewModel.setFirstNameValid(false);

                }else {
                    binding.textInputLayoutFirstname.setErrorEnabled(false);
                    binding.textInputLayoutFirstname.setError(null);
                    viewModel.setFirstNameValid(true);
                }

            }
        });

        viewModel.getLastName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(s.length() < 1){
                    binding.textInputLayoutLastName.setErrorEnabled(true);
                    binding.textInputLayoutLastName.setError(getResources().getString(R.string.register_lastname_error));
                    viewModel.setLastNameValid(false);

                }else {
                    binding.textInputLayoutLastName.setErrorEnabled(false);
                    binding.textInputLayoutLastName.setError(null);
                    viewModel.setLastNameValid(true);
                }
            }
        });

        viewModel.getDateOfBirth().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try {
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(s);

                    Calendar calendar = Calendar.getInstance();

                    calendar.add(Calendar.YEAR,-18);

                    if(date.after(calendar.getTime())){
                        binding.textInputLayoutDateOfBirth.setErrorEnabled(true);
                        binding.textInputLayoutDateOfBirth.setError(getResources().getString(R.string.register_dateofbirth_error));
                        viewModel.setDateOfBirthValid(false);
                    }else {
                        binding.textInputLayoutDateOfBirth.setErrorEnabled(false);
                        binding.textInputLayoutDateOfBirth.setError(null);
                        viewModel.setDateOfBirthValid(true);
                    }

                }catch (ParseException ex){
                    Log.d("D", "onChanged: Invalid date parse exception");
                }
            }
        });


        //for snackbars
        viewModel.getSnackBarEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Snackbar.make(findViewById(R.id.registerform),s,Snackbar.LENGTH_LONG).show();
            }
        });



        //request for permissions

        viewModel.getRegistrationSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean == true){
                    completeRegistration();
                }
            }
        });

    }




    //show date pickers
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


        //this branch is for code confirmation
        if(requestCode ==  VERIFY_PHONE_NUMBER){

            if(resultCode == RESULT_OK){
                //if all went right

                Toast.makeText(this,"Phone verification completed successfully.",Toast.LENGTH_SHORT).show();

                String phoneNumber = data.getStringExtra("phone");

                viewModel.setPhoneNumber(phoneNumber);

                viewModel.setPhoneNumberValid(true);
            }
            else {

                viewModel.setPhoneNumberValid(false);

                Toast.makeText(this,"Phone verification failed, please try again.",Toast.LENGTH_SHORT).show();

            }

        }else if(requestCode == CHOOSE_PICTURE_FROM_GALLERY  && resultCode == RESULT_OK){



            Uri fullPhotoUri = data.getData();

            Log.d("D", "onActivityResult: Choosen photo "+fullPhotoUri.getPath());

            try {

                InputStream inputStream = getContentResolver().openInputStream(fullPhotoUri);

                BitmapFactory.Options options = new BitmapFactory.Options();

                options.inMutable = true;

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);

                setThumbnail(bitmap);

                setPictureBitmap(bitmap);


            } catch (FileNotFoundException e) {

                Log.e("E", "onActivityResult: "+e);

            }



        }else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            if(selectPicture != null){

                Log.d("D", "onActivityResult: "+selectPicture.getCurrentPhotoPath());

                Uri photoPath = Uri.fromFile(new File(selectPicture.getCurrentPhotoPath()));

                try {

                    InputStream inputStream = getContentResolver().openInputStream(photoPath);

                    BitmapFactory.Options options = new BitmapFactory.Options();

                    options.inMutable = true;

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);

                    setThumbnail(bitmap);

                    setPictureBitmap(bitmap);

                } catch (FileNotFoundException e) {

                    Log.e("E", "onActivityResult: " + e);

                }
            }

        }

    }

    //clicked to select a profile picture
    public void selectProfilePicture(View view){

        CHOOSEN_PICTURE = PROFILE_PICTURE;

        Log.d("D", "selectProfilePicture: "+CHOOSEN_PICTURE);

        viewModel.setProfilePictureSet(true);

        selectPicture = new SelectPictureDialogFragment();

        selectPicture.show(getSupportFragmentManager(),"selectProfilePicture");

    }

    public void selectIDProof(View view){

        CHOOSEN_PICTURE = ID_PROOF;

        Log.d("D", "selectIDProof: "+CHOOSEN_PICTURE);

        viewModel.setIDProofSet(true);

        selectPicture = new SelectPictureDialogFragment();

        selectPicture.show(getSupportFragmentManager(),"selectIDProof");
    }

    public void setThumbnail(Bitmap bitmap){

        if(CHOOSEN_PICTURE == PROFILE_PICTURE){
            Log.d("D", "setThumbnail: for profile");

            // Get the dimensions of the View
            int targetW = binding.imageButtonProfilePicture.getWidth();
            int targetH =  binding.imageButtonProfilePicture.getHeight();


            Bitmap s = Bitmap.createScaledBitmap(bitmap,targetW ,targetH,false);
            binding.imageButtonProfilePicture.setImageBitmap(s);

        }else if (CHOOSEN_PICTURE == ID_PROOF){
            Log.d("D", "setThumbnail: for Id proof");

            // Get the dimensions of the View
            int targetW = binding.imageButtonIDProof.getWidth();
            int targetH =  binding.imageButtonIDProof.getHeight();


            Bitmap s = Bitmap.createScaledBitmap(bitmap,targetW ,targetH,false);

            binding.imageButtonIDProof.setImageBitmap(s);
        }
    }

    private void setPictureBitmap(Bitmap bitmap){
        if(CHOOSEN_PICTURE == PROFILE_PICTURE){
            viewModel.profilePictureBitmap = bitmap;
            Log.d("D", "setPictureURI: for profile ");
        }else if(CHOOSEN_PICTURE == ID_PROOF){
            viewModel.idProofBitmap = bitmap;
            Log.d("D", "setPictureURI: for id ");
        }
    }

    //item selection for spinner.

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        Log.d("D", "onItemSelected: "+adapterView.getItemAtPosition(pos));
        viewModel.getRelation().postValue(adapterView.getItemAtPosition(pos).toString().toLowerCase());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        viewModel.getRelation().postValue(null);
    }


    //
    public void completeRegistration(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }


}
