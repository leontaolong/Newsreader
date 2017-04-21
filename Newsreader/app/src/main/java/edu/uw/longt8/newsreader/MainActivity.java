package edu.uw.longt8.newsreader;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnSearchListener,
        DatePickerFragment.OnDateSelectedListener, ArticleListFragment.OnListInteractionListener {

    private static final String TAG = "MainActivity";
    private FrameLayout panelLeft;
    private FrameLayout panelRight;
    private boolean isDualPanel;

    private SearchFragment searchFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();


        panelLeft = (FrameLayout) findViewById(R.id.left_panel);
        panelRight = (FrameLayout) findViewById(R.id.right_panel);
        Log.v(TAG, "Visibility " + panelRight);
        isDualPanel = panelRight.getVisibility() == View.VISIBLE;

        showRecentListFragment();
        showSearchFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.item_top_stories:
                showRecentListFragment();
                hideSearchFragment();
                return true;
            case R.id.item_search:
                showSearchFragment();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showRecentListFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.left_panel, new ArticleListFragment().newInstance())
                .commit();
    }

    private void showSearchFragment(){
        searchFrag = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_panel, searchFrag)
                .commit();
    }

    private void hideSearchFragment(){
        getSupportFragmentManager().beginTransaction()
                .hide(searchFrag)
                .addToBackStack(null)
                .commit();
    }

    private void showTopicListFragment(String searchTerm, String beginDate, String endDate) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.left_panel, new ArticleListFragment().newInstance(searchTerm, beginDate, endDate))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSearch(String searchTerm, String beginDate, String endDate) {
        Log.v(TAG, "Article Search Queries: searchTerm: " + searchTerm + " beginDate: "+
        beginDate + " endDate " + endDate);
        showTopicListFragment(searchTerm, beginDate, endDate);
    }

    @Override
    public void onItemClicked(String article) {

    }

    @Override
    public void onItemLongPressed(String article) {

    }

    @Override
    public void onDateSelected(int year, int month, int dayOfMonth) {
        searchFrag.onDateSelected(year, month, dayOfMonth);
    }
}
