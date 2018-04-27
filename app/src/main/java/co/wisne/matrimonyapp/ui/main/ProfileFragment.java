package co.wisne.matrimonyapp.ui.main;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.adapters.AdapterViewBindingAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;


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
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



            binding = FragmentProfileBinding.inflate(inflater,container,false);

            binding.setViewModel(viewModel);

            binding.setLifecycleOwner(this);

            binding.buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.saveProfile();
                }
            });

            return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();

        UpdateSpinners();

    }

    public void UpdateSpinners(){
        new SetSpinnersTask().execute();
    }


    class SetSpinnersTask extends AsyncTask<Void, Void, Void>{

        String keyRelation;
        String[] arrRelations;

        String keyMarriageStatus;
        String[] arrMaritalStatus;

        String keyFamilyType;
        String[] arrFamilyType;


        @Override
        protected Void doInBackground(Void... voids) {

            keyRelation = viewModel.getRelation().getValue();
            arrRelations = getResources().getStringArray(R.array.register_spinner_profile_for);

            keyMarriageStatus = viewModel.getPersonalDetails().getMarriageStatus().getValue();
            arrMaritalStatus =  getResources().getStringArray(R.array.marital_status);

            keyFamilyType = viewModel.getPersonalDetails().getFamilyType().getValue();
            arrFamilyType =  getResources().getStringArray(R.array.family_types);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(getActivity()== null){
                return;
            }

            //relation
            if(keyRelation!=null)
                binding.spinnerRelation.setSelection(Arrays.asList(arrRelations).indexOf(keyRelation));

            //maritalStatus
            if(keyMarriageStatus!=null)
                binding.spinnerMaritalStatus.setSelection(Arrays.asList(arrMaritalStatus).indexOf(keyMarriageStatus));

            if( viewModel.getPersonalDetails().getHeightFeet().getValue()!=null){

                //heightFeet
                String keyHeightFeet = viewModel.getPersonalDetails().getHeightFeet().getValue().toString();
                String[] arrHeightFeet =  getResources().getStringArray(R.array.height_feet);
                if(keyHeightFeet!=null)
                    binding.spinnerFeets.setSelection(Arrays.asList(arrHeightFeet).indexOf(keyHeightFeet));

                //heightInch
                String keyHeightInch = viewModel.getPersonalDetails().getHeightInch().getValue().toString();
                String[] arrHeightInch =  getResources().getStringArray(R.array.height_inch);
                if(keyHeightInch!=null)
                    binding.spinnerInches.setSelection(Arrays.asList(arrHeightInch).indexOf(keyHeightInch));
            }



            //familyType
            if(keyFamilyType!=null)
                binding.spinnerFamilyType.setSelection(Arrays.asList(arrFamilyType).indexOf(keyFamilyType));

            //specially enabled
            Boolean keySpeciallyEnabled = viewModel.personalDetails.getSpeciallyEnabled().getValue();
            if(keySpeciallyEnabled != null){
                if(keySpeciallyEnabled){
                    binding.speciallyEnabledTrue.setChecked(true);
                }else {
                    binding.speciallyEnabledFalse.setChecked(true);
                }
            }

            //subcaste
            String keySubCaste = viewModel.getReligiousDetails().getSubCaste().getValue();
            String[] arrSubCaste =  getResources().getStringArray(R.array.sub_castes);
            if(keySubCaste!=null)
                binding.spinnerSubCaste.setSelection(Arrays.asList(arrSubCaste).indexOf(keySubCaste));


            //education
            String keyEducation = viewModel.getProfessionalDetails().getHighestEducation().getValue();
            String[] arrEducation =  getResources().getStringArray(R.array.highest_education_list);
            if(keyEducation!=null)
                binding.spinnerHighestEducation.setSelection(Arrays.asList(arrEducation).indexOf(keyEducation));

            //employement
            String keyEmployement = viewModel.getProfessionalDetails().getEmployementStatus().getValue();
            String[] arrEmployement =  getResources().getStringArray(R.array.employement_status_list);
            if(keyEmployement!=null)
                binding.spinnerEmployeementStatus.setSelection(Arrays.asList(arrEmployement).indexOf(keyEmployement));

            //employement
            String keySalary = viewModel.getProfessionalDetails().getIncome().getValue();
            String[] arrSalary =  getResources().getStringArray(R.array.income_list);
            if(keySalary!=null)
                binding.spinnerIncome.setSelection(Arrays.asList(arrSalary).indexOf(keySalary));

        }
    }



}
