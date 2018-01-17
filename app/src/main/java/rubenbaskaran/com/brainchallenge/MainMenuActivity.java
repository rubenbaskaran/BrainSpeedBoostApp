package rubenbaskaran.com.brainchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rubenbaskaran.com.brainchallenge.Highscore.HighscoreActivity;

public class MainMenuActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
    }

    public void StartGame(View view)
    {
        Intent i = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(i);
    }

    public void ShowHighscores(View view)
    {
        Intent i = new Intent(getApplicationContext(), HighscoreActivity.class);
        startActivity(i);
    }
}
