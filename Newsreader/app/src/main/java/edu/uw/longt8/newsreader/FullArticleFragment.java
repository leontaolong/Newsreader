package edu.uw.longt8.newsreader;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullArticleFragment extends DialogFragment {
    private static final String WEB_URL = "webUrl";

    // TODO: Rename and change types of parameters
    private String webUrl;


    public FullArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param webUrl Parameter 1.
     * @return A new instance of fragment FullArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FullArticleFragment newInstance(String webUrl) {
        FullArticleFragment fragment = new FullArticleFragment();
        Bundle args = new Bundle();
        args.putString(WEB_URL, webUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            webUrl = getArguments().getString(WEB_URL);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.full_article_fragment, container, false);
//        WebView myWebView = (WebView) rootView.findViewById(R.id.webView);
//        myWebView.loadUrl(webUrl);
//        myWebView.setWebViewClient(new WebViewClient());
//        return rootView;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.full_article_fragment, null);
        WebView myWebView = (WebView) rootView.findViewById(R.id.webView);
        myWebView.loadUrl(webUrl);
        myWebView.setWebViewClient(new WebViewClient());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(myWebView);


        AlertDialog dialog = builder.create();
        return dialog;
//        return super.onCreateDialog(savedInstanceState);
    }
}
