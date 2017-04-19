package edu.uw.longt8.newsreader;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public FrameLayout panelLeft;
    public FrameLayout panelRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panelLeft = (FrameLayout)findViewById(R.id.left_panel);
        panelRight = (FrameLayout)findViewById(R.id.right_panel);
        FragmentManager fragmentManager = getFragmentManager();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
