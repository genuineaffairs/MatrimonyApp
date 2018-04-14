package co.wisne.matrimonyapp.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

import co.wisne.matrimonyapp.interfaces.ISetDate;

import static android.content.ContentValues.TAG;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    ISetDate mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mActivity = (ISetDate)context;
        }catch (Exception err){
            Log.d(TAG, "onAttach: interface not implemented");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        mActivity.setDate(day,month,year);
    }



}
