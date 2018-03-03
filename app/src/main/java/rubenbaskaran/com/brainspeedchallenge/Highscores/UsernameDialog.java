package rubenbaskaran.com.brainspeedchallenge.Highscores;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import rubenbaskaran.com.brainspeedchallenge.Databases.Managers.LocalDatabaseManager;
import rubenbaskaran.com.brainspeedchallenge.Databases.Managers.OnlineDatabaseManager;
import rubenbaskaran.com.brainspeedchallenge.Models.Score;
import rubenbaskaran.com.brainspeedchallenge.R;

/**
 * Created by Ruben on 27-02-2018.
 */

public class UsernameDialog extends DialogFragment
{
    //region Fields
    SharedPreferences sharedPreferences;
    private InterstitialAd mInterstitialAd;
    AppCompatActivity numbersGameActivity;
    ArrayList<Score> highscoreList;
    public Context context;
    public Score score;
    public boolean listIsFull;
    //endregion

    //region Properties
    public void setNumbersGameActivity(AppCompatActivity numbersGameActivity)
    {
        this.numbersGameActivity = numbersGameActivity;
    }

    public void setHighscoreList(ArrayList<Score> highscoreList)
    {
        this.highscoreList = highscoreList;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public void setScore(Score score)
    {
        this.score = score;
    }

    public void setListIsFull(boolean listIsFull)
    {
        this.listIsFull = listIsFull;
    }
    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_username, container, false);

        mInterstitialAd = new InterstitialAd(context);
//        mInterstitialAd.setAdUnitId("ca-app-pub-4429595719358536/3669794038"); // REAL ID
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); // TEST ID
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdFailedToLoad(int i)
            {
                super.onAdFailedToLoad(i);
                Log.e("InterstitialAd", "FAILED");
            }

            @Override
            public void onAdLoaded()
            {
                super.onAdLoaded();
                Log.e("InterstitialAd", "LOADED");
            }
        });

        final Button submitButton = view.findViewById(R.id.submitButton);
        final EditText usernameEditText = view.findViewById(R.id.usernameEditText);

        sharedPreferences = context.getSharedPreferences("rubenbaskaran.com.brainspeedchallenge", Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");

        if (!savedUsername.isEmpty())
        {
            usernameEditText.setText(savedUsername);
        }
        else
        {
            submitButton.setEnabled(false);
        }

        usernameEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (!usernameEditText.getText().toString().isEmpty())
                {
                    submitButton.setEnabled(true);
                }
                else
                {
                    submitButton.setEnabled(false);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String retrievedUsername = usernameEditText.getText().toString();
                sharedPreferences.edit().putString("username", retrievedUsername).apply();
                score.setUsername(retrievedUsername);

                LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager(context);
                localDatabaseManager.SaveNewScore(score, listIsFull, highscoreList);

                OnlineDatabaseManager onlineDatabaseManager = new OnlineDatabaseManager(context);
                onlineDatabaseManager.SaveNewScoreOnline(score);

                if (mInterstitialAd.isLoaded())
                {
                    Log.e("UsernameDialog", "Showing interstitial ad");
                    mInterstitialAd.show();
                }
                else
                {
                    Log.e("UsernameDialog", "The interstitial wasn't loaded yet");
                    numbersGameActivity.finish();
                    Intent i = new Intent(context, HighscoreActivity.class);
                    i.putExtra("gametype", score.getGameType());
                    startActivity(i);
                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdClosed()
            {
                numbersGameActivity.finish();
                Intent i = new Intent(context, HighscoreActivity.class);
                i.putExtra("gametype", score.getGameType());
                startActivity(i);
            }
        });

        Button cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                numbersGameActivity.finish();
                Intent i = new Intent(context, HighscoreActivity.class);
                i.putExtra("gametype", score.getGameType());
                startActivity(i);
            }
        });

        return view;
    }
}
