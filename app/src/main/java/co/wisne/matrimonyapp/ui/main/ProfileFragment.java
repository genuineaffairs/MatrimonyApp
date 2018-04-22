package co.wisne.matrimonyapp.ui.main;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.FragmentProfileBinding;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    MainActivityViewModel viewModel;

    FragmentProfileBinding binding;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this.getActivity()).get(MainActivityViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater,container,false);

        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(this);

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.saveProfile();
            }
        });

        InitFormData();

        InitSpinners();

        UpdateSpinners();

        return binding.getRoot();
    }



    public void InitFormData(){
        //init relation spinner

        String key = viewModel.getRelation().getValue().substring(0,1).toUpperCase()+viewModel.getRelation().getValue().substring(1);
        String[] arr =  getResources().getStringArray(R.array.register_spinner_profile_for);
        List<String> arrList = Arrays.asList(arr);
        binding.spinnerRelation.setSelection(arrList.indexOf(key));

    }


    public void InitSpinners(){

        binding.spinnerMaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                Log.d(TAG, "onItemSelected: "+adapterView.getItemAtPosition(pos).toString());
                viewModel.personalDetails.getMarriageStatus().setValue(adapterView.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                viewModel.personalDetails.getMarriageStatus().setValue(null);
            }
        });

        binding.spinnerFeets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                viewModel.personalDetails.getHeightFeet().setValue(
                        Integer.parseInt(adapterView.getItemAtPosition(pos).toString())
                );

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerInches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                viewModel.personalDetails.getHeightInch().setValue(
                        Integer.parseInt(adapterView.getItemAtPosition(pos).toString())
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerFamilyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                viewModel.getPersonalDetails().getFamilyType().setValue(
                        adapterView.getItemAtPosition(pos).toString()
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                viewModel.getReligiousDetails().getReligion().setValue(adapterView.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerCaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                viewModel.getReligiousDetails().getCaste().setValue(adapterView.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerSubCaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                viewModel.getReligiousDetails().getSubCaste().setValue(adapterView.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerHighestEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.getProfessionalDetails().getHighestEducation().setValue(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerEmployeementStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.getProfessionalDetails().getEmployementStatus().setValue(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.getProfessionalDetails().getIncome().setValue(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void UpdateSpinners(){

        //spinner for personal details
        viewModel.getPersonalDetails().getUpdateMarriageStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                String key = viewModel.getPersonalDetails().getMarriageStatus().getValue();
                String[] arr =  getResources().getStringArray(R.array.marital_status);
                List<String> arrList = Arrays.asList(arr);
                Log.d(TAG, "selected MaritalStatus: "+key);
                binding.spinnerMaritalStatus.setSelection(arrList.indexOf(key));
            }
        });

        viewModel.getPersonalDetails().getUpdateHeightFeet().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                String key = viewModel.getPersonalDetails().getHeightFeet().getValue().toString();
                String[] arr =  getResources().getStringArray(R.array.height_feet);
                Log.d(TAG, "onChanged: height = "+key);
                List<String> arrList = Arrays.asList(arr);
                binding.spinnerFeets.setSelection(arrList.indexOf(key));
            }
        });

        viewModel.getPersonalDetails().getUpdateHeightFeet().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                String key = viewModel.getPersonalDetails().getHeightInch().getValue().toString();
                String[] arr =  getResources().getStringArray(R.array.height_inch);
                List<String> arrList = Arrays.asList(arr);
                binding.spinnerInches.setSelection(arrList.indexOf(key));
            }
        });

        viewModel.getPersonalDetails().getUpdateFamilyType().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                String key = viewModel.getPersonalDetails().getFamilyType().getValue();
                String[] arr =  getResources().getStringArray(R.array.family_types);
                List<String> arrList = Arrays.asList(arr);
                binding.spinnerFamilyType.setSelection(arrList.indexOf(key));
            }
        });

        viewModel.getPersonalDetails().getUpdateSpeciallyEnabled().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean==null){
                    return;
                }

                if(aBoolean == true){
                    binding.speciallyEnabledTrue.setChecked(true);
                }else if(aBoolean == false) {
                    binding.speciallyEnabledFalse.setChecked(true);
                }

            }
        });


        //spinner for religious details
        viewModel.getReligiousDetails().getUpdateSubCaste().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                String key = viewModel.getReligiousDetails().getSubCaste().getValue();
                String[] arr =  getResources().getStringArray(R.array.sub_castes);
                List<String> arrList = Arrays.asList(arr);
                binding.spinnerSubCaste.setSelection(arrList.indexOf(key));
            }
        });
    }

}
