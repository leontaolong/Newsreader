package edu.uw.longt8.newsreader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple SearchFragment subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnSearchListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private OnSearchListener mListener;
    private String beginDate;
    private String endDate;
    private String beginDateDisplay;
    private String endDateDisplay;
    private String currentDateSelector;

    private static final String TAG = "SearchFragment";

    private static final String BEGIN_DATE_SELECTOR = "beginDateSelector";
    private static final String END_DATE_SELECTOR = "endDateSelector";

    // end date: today
    private static final Date DEFAULT_END_DATE = new Date();
    private static final long DAY_IN_MS = 1000 * 60 * 60 * 24;
    // begin date: 7 days ago
    private static final Date DEFAULT_BEGIN_DATE = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));

    public SearchFragment() {
        beginDate = new SimpleDateFormat("yyyyMMdd").format(DEFAULT_BEGIN_DATE);
        endDate = new SimpleDateFormat("yyyyMMdd").format(DEFAULT_END_DATE);

        beginDateDisplay = new SimpleDateFormat("MM-dd-yyyy").format(DEFAULT_BEGIN_DATE);
        endDateDisplay = new SimpleDateFormat("MM-dd-yyyy").format(DEFAULT_END_DATE);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Search.
     */
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search_fragment, container, false);

        // display the default dates for article search
        TextView beginDateView = (TextView) rootView.findViewById(R.id.begin_date_text);
        TextView endDateView = (TextView) rootView.findViewById(R.id.end_date_text);
        beginDateView.setText(beginDateDisplay);
        endDateView.setText(endDateDisplay);

        Button searchButton = (Button) rootView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) rootView.findViewById(R.id.search_text);
                String searchTerm = text.getText().toString();
                mListener.onSearch(searchTerm, beginDate, endDate);
            }
        });

        Button beginDateButton = (Button) rootView.findViewById(R.id.begin_date);
        beginDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "Begin Date");
                currentDateSelector = BEGIN_DATE_SELECTOR;
            }
        });

        Button endDateButton = (Button) rootView.findViewById(R.id.end_date);
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "End Date");
                currentDateSelector = END_DATE_SELECTOR;
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchListener) {
            mListener = (OnSearchListener) context;
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


    public void onDateSelected(int year, int month, int dayOfMonth) {
        // format date strings for both displaying and searching
        String dateDisplay = (month + 1) + "-" + dayOfMonth + "-" + year;
        month += 1;
        String dateSelected = "" + year;
        if (month < 10)
            dateSelected += "" + 0;
        dateSelected += "" + month;
        if (dayOfMonth < 10) {
            dateSelected += "" + 0;
        }
        dateSelected += "" + dayOfMonth;

        if (currentDateSelector.equals(BEGIN_DATE_SELECTOR)) {
            beginDateDisplay = dateDisplay;
            beginDate = dateSelected;
            TextView dateView = (TextView) getActivity().findViewById(R.id.begin_date_text);
            dateView.setText(beginDateDisplay);
            Log.v(TAG, "Begin Date Selected: " + beginDate);
        } else {          // currentDateSelector.equals(END_DATE_SELECTOR)
            endDateDisplay = dateDisplay;
            endDate = dateSelected;
            TextView dateView = (TextView) getActivity().findViewById(R.id.end_date_text);
            dateView.setText(endDateDisplay);
            Log.v(TAG, "End Date Selected: " + endDate);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     */
    public interface OnSearchListener {
        void onSearch(String SearchTerm, String beginDate, String endDate);
    }
}
