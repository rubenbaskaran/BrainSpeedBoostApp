package rubenbaskaran.com.brainchallenge.GameCategories;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import rubenbaskaran.com.brainchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainchallenge.Highscore.HighscoreActivity;
import rubenbaskaran.com.brainchallenge.R;

public class DivisionActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division);
    }

    public void StartGame(View view)
    {
        Intent i = new Intent(getApplicationContext(), NumbersGameActivity.class);
        i.putExtra("gametype", GameTypes.Division);
        startActivity(i);
    }

    public void ShowHighscore(View view)
    {
        Intent i = new Intent(getApplicationContext(), HighscoreActivity.class);
        i.putExtra("gametype", GameTypes.Division);
        startActivity(i);
    }
}
