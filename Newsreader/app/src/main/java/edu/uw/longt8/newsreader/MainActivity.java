package edu.uw.longt8.newsreader;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnSearchListener,
        DatePickerFragment.OnDateSelectListener, ArticleListFragment.OnListInteractionListener,
        PreviewFragment.OnViewFullClickListener {

    private static final String TAG = "MainActivity";
    private FrameLayout panelLeft;
    private FrameLayout panelRight;
    private boolean isDualPanel;

    private SearchFragment searchFrag;
    private PreviewFragment previewFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panelLeft = (FrameLayout) findViewById(R.id.left_panel);
        panelRight = (FrameLayout) findViewById(R.id.right_panel);
        Log.v(TAG, "Visibility " + panelRight);

        isDualPanel = panelRight.getVisibility() == View.VISIBLE;

        showRecentListFragment();
        showSearchFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_top_stories:
                showRecentListFragment();
                if (searchFrag != null)
                    hideSearchFragment();
                if (previewFrag != null)
                    hidePreviewFragment();
                return true;
            case R.id.item_search:
                showSearchFragment();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showRecentListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.left_panel, new ArticleListFragment().newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void showSearchFragment() {
        searchFrag = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_panel, searchFrag)
                .addToBackStack(null)
                .commit();
    }

    private void hideSearchFragment() {
        getSupportFragmentManager().beginTransaction()
                .hide(searchFrag)
                .addToBackStack(null)
                .commit();
    }

    private void hidePreviewFragment() {
        getSupportFragmentManager().beginTransaction()
                .hide(previewFrag)
                .addToBackStack(null)
                .commit();
    }

    private void showSearchListFragment(String searchTerm, String beginDate, String endDate) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.left_panel, new ArticleListFragment().newInstance(searchTerm, beginDate, endDate))
                .addToBackStack(null)
                .commit();
    }

    private void showPreviewFragment(String title, String imgUrl, String webUrl, String snippet) {
        previewFrag = new PreviewFragment().newInstance(title, imgUrl, webUrl, snippet);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_panel, previewFrag)
                .addToBackStack(null)
                .commit();
    }

    private void showFullArticleFragment(String webUrl) {
        DialogFragment newFragment = FullArticleFragment.newInstance(webUrl);
        newFragment.show(getSupportFragmentManager().beginTransaction(), "");
    }


    @Override
    public void onSearch(String searchTerm, String beginDate, String endDate) {
        Log.v(TAG, "Article Search Queries: searchTerm: " + searchTerm + " beginDate: " +
                beginDate + " endDate " + endDate);
        showSearchListFragment(searchTerm, beginDate, endDate);
    }

    @Override
    public void onItemClick(String title, String imgUrl, String webUrl, String snippet) {
        showPreviewFragment(title, imgUrl, webUrl, snippet);

    }

    @Override
    public void onItemLongPress(String webUrl) {
        showFullArticleFragment(webUrl);
    }

    @Override
    public void onDateSelect(int year, int month, int dayOfMonth) {
        searchFrag.onDateSelected(year, month, dayOfMonth);
    }

    @Override
    public void onViewFullClick(String webUrl) {
        showFullArticleFragment(webUrl);
    }
}
