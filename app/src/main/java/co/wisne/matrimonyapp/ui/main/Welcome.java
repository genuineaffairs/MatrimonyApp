package co.wisne.matrimonyapp.ui.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.interfaces.ILogin;

/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome extends Fragment {

    @BindView(R.id.welcome_login_btn)
    Button LoginButton;

    public Welcome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_welcome, container, false);

        ButterKnife.bind(this,view);

        LoginButton.setOnClickListener(View -> initLogin());

        return view;
    }

    public void initLogin(){
        ((ILogin)getActivity()).ShowLoginUI();
    }

}
