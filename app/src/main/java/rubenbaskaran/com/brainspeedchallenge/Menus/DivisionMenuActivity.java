package rubenbaskaran.com.brainspeedchallenge.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.Games.NumbersGameActivity;
import rubenbaskaran.com.brainspeedchallenge.Highscores.HighscoreActivity;
import rubenbaskaran.com.brainspeedchallenge.R;

public class DivisionMenuActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division_menu);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener()
        {
            @Override
            public void onAdFailedToLoad(int i)
            {
                super.onAdFailedToLoad(i);
                Log.e("DivisionAd", "FAILED");
            }

            @Override
            public void onAdLoaded()
            {
                super.onAdLoaded();
                Log.e("DivisionAd", "LOADED");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.about_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.about)
        {
            Toast.makeText(getApplicationContext(), "Made by Ruben Baskaran", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void StartDivisionGame(View view)
    {
        Intent i = new Intent(getApplicationContext(), NumbersGameActivity.class);
        i.putExtra("gametype", GameTypes.Division);
        startActivity(i);
    }

    public void ShowDivisionHighscores(View view)
    {
        Intent i = new Intent(getApplicationContext(), HighscoreActivity.class);
        i.putExtra("gametype", GameTypes.Division);
        startActivity(i);
    }
}