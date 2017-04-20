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
        ArticleListFragment.OnListInteractionListener {

    private static final String TAG = "MainActivity";
    private FrameLayout panelLeft;
    private FrameLayout panelRight;
    private boolean isDualPanel;


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
                return true;
            case R.id.item_search:
                showSearchFragment();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showRecentListFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.left_panel, new SearchFragment())
                .commit();
    }

    private void showSearchFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_panel, new SearchFragment())
                .commit();
    }

    @Override
    public void onSearch(String searchTerm, String beginDate, String endDate) {
        Log.v(TAG, "Article Search Queries: searchTerm: " + searchTerm + " beginDate: "+
        beginDate + " endDate " + endDate);



    }

    @Override
    public void onItemClicked(String article) {

    }

    @Override
    public void onItemLongPressed(String article) {

    }
}
