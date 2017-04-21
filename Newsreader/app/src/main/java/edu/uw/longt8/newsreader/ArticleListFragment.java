package edu.uw.longt8.newsreader;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


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

    private static final String TAG = "ArticleListFragment";

    private static final String IMAGE_BASE_URL = "https://static01.nyt.com/";
    private static final String SEARCH_TERM = "searchTerm";
    private static final String BEGIN_DATE = "beginDate";
    private static final String END_DATE = "endDate";

//    private static final String RECENT_LIST = "recentList";
//    private static final String SEARCH_LIST = "searchList";

    // TODO: Rename and change types of parameters
    private String searchTerm;
    private String beginDate;
    private String endDate;

    private boolean hasSearchQueries = false;

    protected ArrayList<Article> articles;


    private OnListInteractionListener mListener;

    protected ArrayAdapter<Article> adapter;

    public ArticleListFragment() {
        articles = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param searchTerm Parameter 1.
     * @param beginDate  Parameter 2.
     * @param endDate    Parameter 3.
     * @return A new instance of fragment ArtitleListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleListFragment newInstance(String searchTerm, String beginDate, String endDate) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_TERM, searchTerm);
        args.putString(BEGIN_DATE, beginDate);
        args.putString(END_DATE, endDate);
        fragment.setArguments(args);
        return fragment;
    }

    public static ArticleListFragment newInstance() {
        ArticleListFragment fragment = new ArticleListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.article_list_fragment, container, false);
        //controller
        adapter = new ArrayAdapter<>(this.getActivity(),
                R.layout.list_item, R.id.txtItem, new ArrayList<Article>());

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = (Article) parent.getItemAtPosition(position);
                mListener.onItemClick(article.headline, article.imgUrl, article.webUrl, article.snippet);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = (Article) parent.getItemAtPosition(position);
                mListener.onItemLongPress(article.webUrl);
                return true;
            }
        });

        TextView listTitle = (TextView) rootView.findViewById(R.id.listTitle);
        if (getArguments() != null) {
            searchTerm = getArguments().getString(SEARCH_TERM);
            beginDate = getArguments().getString(BEGIN_DATE);
            endDate = getArguments().getString(END_DATE);
            hasSearchQueries = true;
            listTitle.setText("Search Results");
            fetchArticleList(searchTerm, beginDate, endDate);

        } else {
            hasSearchQueries = false;
            listTitle.setText("Recent Top Stories");
            fetchArticleList(null, null, null);
        }

        Log.v(TAG, articles.toString());

        return rootView;
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
     **/
    public interface OnListInteractionListener {
        // TODO: Update argument type and name
        void onItemClick(String title, String imgUrl, String webUrl, String snippet);
        void onItemLongPress(String article);
    }


    // fetch the article data from the NYT API
    public void fetchArticleList(String searchTerm, String beginDate, String endDate) {
        Log.v(TAG, "Downloading data for " + searchTerm + " beginDate: " +
                beginDate + " endDate " + endDate);


        //construct the url for the NYT APi
        Uri.Builder apiBuilder = new Uri.Builder();
        String apiKey = getActivity().getString(R.string.NEW_YORK_TIMES_API_KEY);

        apiBuilder.scheme("https")
                .authority("api.nytimes.com")
                .appendPath("svc");
        if (hasSearchQueries)
            apiBuilder.appendPath("search");
        else
            apiBuilder.appendPath("topstories");
        apiBuilder.appendPath("v2");
        if (hasSearchQueries)
            apiBuilder.appendPath("articlesearch.json");
        else
            apiBuilder.appendPath("home.json");
        apiBuilder.appendQueryParameter("api-key", apiKey);
        if (hasSearchQueries) {
            apiBuilder
                    .appendQueryParameter("q", searchTerm)
                    .appendQueryParameter("begin_date", beginDate)
                    .appendQueryParameter("end_date", endDate);
        }

        String urlString = apiBuilder.build().toString();
        Log.v(TAG, "API Request url: " + urlString);

        //the request queue
        RequestQueue queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        //build a request
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, urlString, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, "API Response: " + response.toString());
                        try {
                            articles.clear();
                            if (hasSearchQueries) {
                                JSONArray docs = response.getJSONObject("response").getJSONArray("docs");
                                String imgUrl = IMAGE_BASE_URL;
                                for (int i = 0; i < docs.length(); i++) {
                                    JSONObject doc = docs.getJSONObject(i);
                                    String webUrl = doc.getString("web_url");
                                    String snippet = doc.getString("snippet");
                                    String headline = doc.getJSONObject("headline").getString("main");
                                    String pubDate = doc.getString("pub_date");
                                    // image url assertion
                                    if (doc.getJSONArray("multimedia") != null &&
                                            doc.getJSONArray("multimedia").length() > 0 &&
                                            doc.getJSONArray("multimedia").getJSONObject(1).getString("url") != null) {
                                        imgUrl += doc.getJSONArray("multimedia").getJSONObject(1).getString("url");
                                    } else
                                        imgUrl = null;
                                    articles.add(new Article(snippet, imgUrl, webUrl, headline, pubDate));
                                    imgUrl = IMAGE_BASE_URL;
                                }
                            } else {
                                JSONArray docs = response.getJSONArray("results");
                                for (int i = 0; i < docs.length(); i++) {
                                    JSONObject doc = docs.getJSONObject(i);
                                    String webUrl = doc.getString("url");
                                    String snippet = doc.getString("abstract");
                                    String headline = doc.getString("title");
                                    String pubDate = doc.getString("published_date");
                                    String imgUrl;
                                    // image url assertion
                                    if (doc.getJSONArray("multimedia") != null &&
                                            doc.getJSONArray("multimedia").length() > 0 &&
                                            doc.getJSONArray("multimedia").getJSONObject(1).getString("url") != null) {
                                        imgUrl = doc.getJSONArray("multimedia").getJSONObject(1).getString("url");
                                    } else
                                        imgUrl = null;
                                    articles.add(new Article(snippet, imgUrl, webUrl, headline, pubDate));
                                }
                            }
                            adapter.clear();
                            for (Article article : articles) {
                                adapter.add(article);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Response Error" + error.toString());
            }
        });

        //add the request to the queue to send the HTTP Request
        queue.add(jsonRequest);
    }
}
