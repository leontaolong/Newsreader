package edu.uw.longt8.newsreader;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

//    private SearchFragment onDateSelectedlistener;
    private OnDateSelectedListener mListener;


    public DatePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDateSelectedListener) {
            mListener = (OnDateSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param listener a parent SearchFragment listener
     * @return A new instance of fragment DatePickerFragment.
     */
    public static DatePickerFragment newInstance(SearchFragment listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
//        fragment.setListener(listener);
        fragment.setArguments(args);
        return fragment;
    }

//    public void setListener(SearchFragment listener) {
//        this.onDateSelectedlistener = listener;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mListener.onDateSelected(year, month, dayOfMonth);
//        (SearchFragment.OnDateSelectedListener) getActivity()
    }

    public interface OnDateSelectedListener {
        void onDateSelected(int year, int month, int dayOfMonth);
    }
}
