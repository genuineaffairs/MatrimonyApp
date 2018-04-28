package co.wisne.matrimonyapp.ui.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.FragmentWelcomeBinding;
import co.wisne.matrimonyapp.interfaces.ILogin;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {


    public WelcomeFragment() {
        // Required empty public constructor
    }

    FragmentWelcomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentWelcomeBinding.inflate(inflater,container,false);

        binding.welcomeLoginBtn.setOnClickListener(View -> initLogin());

        return binding.getRoot();
    }

    public void initLogin(){
        ((ILogin)getActivity()).ShowLoginUI();
    }

}
