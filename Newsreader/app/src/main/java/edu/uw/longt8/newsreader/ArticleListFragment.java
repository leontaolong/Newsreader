package edu.uw.longt8.newsreader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArticleListFragment.OnListInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private static final String RECENT_LIST = "recentList";
    private static final String SEARCH_LIST = "searchList";

    // TODO: Rename and change types of parameters
    private String searchTerm;
    private String beginDate;
    private String endDate;

    private String currentListType;

    private OnListInteractionListener mListener;

    public ArticleListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param searchTerm Parameter 1.
     * @param beginDate Parameter 2.
     * @param endDate Parameter 3.
     * @return A new instance of fragment ArtitleListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleListFragment newInstance(String searchTerm, String beginDate, String endDate) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, searchTerm);
        args.putString(ARG_PARAM2, beginDate);
        args.putString(ARG_PARAM3, endDate);
        fragment.setArguments(args);
        return fragment;
    }

    public static ArticleListFragment newInstance() {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentListType = SEARCH_LIST;
            searchTerm = getArguments().getString(ARG_PARAM1);
            beginDate = getArguments().getString(ARG_PARAM2);
            endDate = getArguments().getString(ARG_PARAM2);
        } else
            currentListType = RECENT_LIST;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.article_list_fragment, container, false);

//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String article) {
        if (mListener != null) {
            mListener.onItemClicked(article);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListInteractionListener) {
            mListener = (OnListInteractionListener) context;
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListInteractionListener {
        // TODO: Update argument type and name
        void onItemClicked(String article);
        void onItemLongPressed(String article);
    }
}
