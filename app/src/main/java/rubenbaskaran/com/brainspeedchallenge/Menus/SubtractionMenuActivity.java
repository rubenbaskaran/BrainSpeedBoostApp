package rubenbaskaran.com.brainspeedchallenge.Menus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.Games.NumbersGameActivity;
import rubenbaskaran.com.brainspeedchallenge.Highscores.HighscoreActivity;
import rubenbaskaran.com.brainspeedchallenge.R;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SubtractionMenuActivity extends AppCompatActivity
{
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction_menu);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void StartSubtractionGame(View view)
    {
        Intent i = new Intent(getApplicationContext(), NumbersGameActivity.class);
        i.putExtra("gametype", GameTypes.Subtraction);
        startActivity(i);
    }

    public void ShowSubtractionHighscores(View view)
    {
        Intent i = new Intent(getApplicationContext(), HighscoreActivity.class);
        i.putExtra("gametype", GameTypes.Subtraction);
        startActivity(i);
    }
}
