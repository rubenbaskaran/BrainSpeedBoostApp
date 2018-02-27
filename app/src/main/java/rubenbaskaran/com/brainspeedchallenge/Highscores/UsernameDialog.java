package rubenbaskaran.com.brainspeedchallenge.Highscores;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_username, container, false);

        final Button submitButton = view.findViewById(R.id.submitButton);
        submitButton.setEnabled(false);

        final EditText usernameEditText = view.findViewById(R.id.usernameEditText);
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
                // TODO: Step 2
                score.setUsername(usernameEditText.getText().toString());

                LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager(context);
                localDatabaseManager.SaveNewScore(score, listIsFull, highscoreList);

                OnlineDatabaseManager onlineDatabaseManager = new OnlineDatabaseManager();
                onlineDatabaseManager.SaveNewScoreOnline(score);

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

    public void setNumbersGameActivity(AppCompatActivity numbersGameActivity)
    {
        this.numbersGameActivity = numbersGameActivity;
    }

    AppCompatActivity numbersGameActivity;

    public void setHighscoreList(ArrayList<Score> highscoreList)
    {
        this.highscoreList = highscoreList;
    }

    ArrayList<Score> highscoreList;

    public void setContext(Context context)
    {
        this.context = context;
    }

    public Context context;

    public void setScore(Score score)
    {
        this.score = score;
    }

    public Score score;

    public void setListIsFull(boolean listIsFull)
    {
        this.listIsFull = listIsFull;
    }

    public boolean listIsFull;
}
