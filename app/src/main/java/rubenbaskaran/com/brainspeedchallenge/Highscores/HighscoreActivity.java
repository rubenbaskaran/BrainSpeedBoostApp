package rubenbaskaran.com.brainspeedchallenge.Highscores;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.R;

public class HighscoreActivity extends AppCompatActivity
{
    Button ShowLocalHighscoreButton;
    Button ShowGlobalHighscoreButton;
    FragmentManager fragmentManager;
    public static Context context;
    LocalHighscoreFragment localHighscoreFragment;
    GameTypes gameTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //TODO: Add gametype in front of Highscores title
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        context = getApplicationContext();

        Intent receivedIntent = getIntent();
        gameTypes = (GameTypes) receivedIntent.getSerializableExtra("gametype");
        Boolean newHighscore = receivedIntent.getBooleanExtra("newhighscore", false);

        ShowLocalHighscoreButton = findViewById(R.id.ShowLocalHighscoreButton);
        ShowGlobalHighscoreButton = findViewById(R.id.ShowGlobalHighscoreButton);

        ShowGlobalHighscoreButton.setEnabled(true);
        ShowLocalHighscoreButton.setEnabled(false);

        localHighscoreFragment = new LocalHighscoreFragment();
        localHighscoreFragment.setGameType(gameTypes);
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, localHighscoreFragment, null);
        fragmentTransaction.commit();

//        if (newHighscore)
//        {
//            Toast.makeText(getApplicationContext(), "Congratulations! New highscore!", Toast.LENGTH_LONG).show();
//        }
    }

    public void ShowLocalHighscore(View view)
    {
        ShowLocalHighscoreButton.setEnabled(false);
        ShowGlobalHighscoreButton.setEnabled(true);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, localHighscoreFragment, null);
        fragmentTransaction.commit();
    }

    public void ShowGlobalHighscore(View view)
    {
        ShowGlobalHighscoreButton.setEnabled(false);
        ShowLocalHighscoreButton.setEnabled(true);
        GlobalHighscoreFragment globalHighscoreFragment = new GlobalHighscoreFragment();
        globalHighscoreFragment.setGameType(gameTypes);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, globalHighscoreFragment, null);
        fragmentTransaction.commit();
    }
}
