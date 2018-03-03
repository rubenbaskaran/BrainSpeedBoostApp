package rubenbaskaran.com.brainspeedchallenge.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.Games.NumbersGameActivity;
import rubenbaskaran.com.brainspeedchallenge.Highscores.HighscoreActivity;
import rubenbaskaran.com.brainspeedchallenge.R;

public class AdditionMenuActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition_menu);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener()
        {
            @Override
            public void onAdFailedToLoad(int i)
            {
                super.onAdFailedToLoad(i);
                Log.e("AdditionAd", "FAILED");
            }

            @Override
            public void onAdLoaded()
            {
                super.onAdLoaded();
                Log.e("AdditionAd", "LOADED");
            }
        });
    }

    public void StartAdditionGame(View view)
    {
        Intent i = new Intent(getApplicationContext(), NumbersGameActivity.class);
        i.putExtra("gametype", GameTypes.Addition);
        startActivity(i);
    }

    public void ShowAdditionHighscores(View view)
    {
        Intent i = new Intent(getApplicationContext(), HighscoreActivity.class);
        i.putExtra("gametype", GameTypes.Addition);
        startActivity(i);
    }
}
