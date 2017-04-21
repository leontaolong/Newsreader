package edu.uw.longt8.newsreader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreviewFragment.OnViewFullClickListener} interface
 * to handle interaction events.
 * Use the {@link PreviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviewFragment extends Fragment {
    private static final String TITLE = "title";
    private static final String IMG_URL = "imgUrl";
    private static final String WEB_URL = "webUrl";
    private static final String SNIPPET = "snippet";

    // TODO: Rename and change types of parameters
    private String title;
    private String imgUrl;
    private String webUrl;
    private String snippet;

    private OnViewFullClickListener mListener;

    public PreviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param title Parameter 1.
     * @param imgUrl Parameter 2.
     * @param webUrl Parameter 3.
     * @param snippet Parameter 4.
     * @return A new instance of fragment PreviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreviewFragment newInstance(String title, String imgUrl, String webUrl, String snippet) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(IMG_URL, imgUrl);
        args.putString(WEB_URL, webUrl);
        args.putString(SNIPPET, snippet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            imgUrl = getArguments().getString(IMG_URL);
            webUrl = getArguments().getString(WEB_URL);
            snippet = getArguments().getString(SNIPPET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.preview_fragment, container, false);
        TextView articleTitle = (TextView) rootView.findViewById(R.id.article_title);
        articleTitle.setText(title);

        TextView articleSnippet = (TextView) rootView.findViewById(R.id.article_snippet);
        articleSnippet.setText(snippet);

        ImageView imgView = (ImageView) rootView.findViewById(R.id.article_img);
        ImageLoader loader = VolleySingleton.getInstance(getActivity()).getImageLoader();
        //get the image from the loader
        loader.get(imgUrl, ImageLoader.getImageListener(imgView, 0, 0));

        Button viewFullButton = (Button) rootView.findViewById(R.id.view_full_article);
        viewFullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onViewFullClick(webUrl);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnViewFullClickListener) {
            mListener = (OnViewFullClickListener) context;
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
     */
    public interface OnViewFullClickListener {
        // TODO: Update argument type and name
        void onViewFullClick(String webUrl);
    }
}
