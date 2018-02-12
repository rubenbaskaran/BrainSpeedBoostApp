package rubenbaskaran.com.brainspeedchallenge.Highscores;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import rubenbaskaran.com.brainspeedchallenge.Databases.Managers.OnlineDatabaseManager;
import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.Models.Score;
import rubenbaskaran.com.brainspeedchallenge.R;

public class GlobalHighscoreFragment extends Fragment
{
    TextView firstPlaceGlobalScore;
    TextView firstPlaceGlobalPercentage;
    TextView secondPlaceGlobalScore;
    TextView secondPlaceGlobalPercentage;
    TextView thirdPlaceGlobalScore;
    TextView thirdPlaceGlobalPercentage;
    TextView fourthPlaceGlobalScore;
    TextView fourthPlaceGlobalPercentage;
    TextView fifthPlaceGlobalScore;
    TextView fifthPlaceGlobalPercentage;
    GameTypes gameType;

    public GameTypes getGameType()
    {
        return gameType;
    }

    public void setGameType(GameTypes gameType)
    {
        this.gameType = gameType;
    }

    public GlobalHighscoreFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_global_highscore, container, false);

        firstPlaceGlobalScore = view.findViewById(R.id.FirstPlaceGlobalScore);
        firstPlaceGlobalPercentage = view.findViewById(R.id.FirstPlaceGlobalPercentage);
        secondPlaceGlobalScore = view.findViewById(R.id.SecondPlaceGlobalScore);
        secondPlaceGlobalPercentage = view.findViewById(R.id.SecondPlaceGlobalPercentage);
        thirdPlaceGlobalScore = view.findViewById(R.id.ThirdPlaceGlobalScore);
        thirdPlaceGlobalPercentage = view.findViewById(R.id.ThirdPlaceGlobalPercentage);
        fourthPlaceGlobalScore = view.findViewById(R.id.FourthPlaceGlobalScore);
        fourthPlaceGlobalPercentage = view.findViewById(R.id.FourthPlaceGlobalPercentage);
        fifthPlaceGlobalScore = view.findViewById(R.id.FifthPlaceGlobalScore);
        fifthPlaceGlobalPercentage = view.findViewById(R.id.FifthPlaceGlobalPercentage);

        OnlineDatabaseManager onlineDatabaseManager = new OnlineDatabaseManager();

        // TODO: Add loading animation
        onlineDatabaseManager.GetOnlineHighscores(this);
        // TODO: Remove loading animation

        return view;
    }

    public void ShowScore(ArrayList<Score> highscores)
    {
        if (highscores.size() == 0)
        {
            Log.e("GetOnlineHighscores", getGameType().toString() + " highscore list is empty");
            Toast.makeText(HighscoreActivity.context, "Highscores list is emtpy", Toast.LENGTH_SHORT).show();
            return;
        }

        Collections.reverse(highscores);
        if (highscores.size() > 0)
        {
            Score score = highscores.get(0);
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            firstPlaceGlobalScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            firstPlaceGlobalPercentage.setText(percentage);
        }
        if (highscores.size() > 1)
        {
            Score score = highscores.get(1);
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            secondPlaceGlobalScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            secondPlaceGlobalPercentage.setText(percentage);
        }
        if (highscores.size() > 2)
        {
            Score score = highscores.get(2);
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            thirdPlaceGlobalScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            thirdPlaceGlobalPercentage.setText(percentage);
        }
        if (highscores.size() > 3)
        {
            Score score = highscores.get(3);
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            fourthPlaceGlobalScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            fourthPlaceGlobalPercentage.setText(percentage);
        }
        if (highscores.size() == 5)
        {
            Score score = highscores.get(4);
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            fifthPlaceGlobalScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            fifthPlaceGlobalPercentage.setText(percentage);
        }
    }
}
