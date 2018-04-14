package co.wisne.matrimonyapp.ui.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.wisne.matrimonyapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemoFragment extends Fragment {

    public static final String ARG_OBJECT = "object";



    public DemoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_demo, container, false);

        Bundle args = getArguments();
        TextView text = view.findViewById(R.id.text1);
        text.setText(Integer.toString(args.getInt(ARG_OBJECT)));


        return view;
    }

}
