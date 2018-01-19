package rubenbaskaran.com.brainchallenge.Highscore;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import rubenbaskaran.com.brainchallenge.Data.LocalDatabaseManager;
import rubenbaskaran.com.brainchallenge.R;

public class HighscoreActivity extends AppCompatActivity implements Serializable
{
    Button ShowLocalHighscoreButton;
    Button ShowGlobalHighscoreButton;
    FragmentManager fragmentManager;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        context = getApplicationContext();
        Score score = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            score = (Score) bundle.get("Score");
            LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager(getApplicationContext(), "", null, 0);
            localDatabaseManager.SaveNewScore(score);
        }

        ShowLocalHighscoreButton = findViewById(R.id.ShowLocalHighscoreButton);
        ShowGlobalHighscoreButton = findViewById(R.id.ShowGlobalHighscoreButton);

        ShowGlobalHighscoreButton.setEnabled(true);
        ShowLocalHighscoreButton.setEnabled(false);

        LocalHighscoreFragment localHighscoreFragment = new LocalHighscoreFragment();
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, localHighscoreFragment, null);
        fragmentTransaction.commit();
    }


    public void ShowLocalHighscore(View view)
    {
        ShowLocalHighscoreButton.setEnabled(false);
        ShowGlobalHighscoreButton.setEnabled(true);
        LocalHighscoreFragment localHighscoreFragment = new LocalHighscoreFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, localHighscoreFragment, null);
        fragmentTransaction.commit();
    }

    public void ShowGlobalHighscore(View view)
    {
        ShowGlobalHighscoreButton.setEnabled(false);
        ShowLocalHighscoreButton.setEnabled(true);
        GlobalHighscoreFragment globalHighscoreFragment = new GlobalHighscoreFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, globalHighscoreFragment, null);
        fragmentTransaction.commit();
    }
}
