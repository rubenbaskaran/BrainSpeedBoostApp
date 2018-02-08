package rubenbaskaran.com.brainspeedchallenge.Highscores;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import rubenbaskaran.com.brainspeedchallenge.Databases.Managers.LocalDatabaseManager;
import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.Models.Score;
import rubenbaskaran.com.brainspeedchallenge.R;

public class LocalHighscoreFragment extends Fragment
{
    TextView firstPlaceScore;
    TextView firstPlacePercentage;
    TextView secondPlaceScore;
    TextView secondPlacePercentage;
    TextView thirdPlaceScore;
    TextView thirdPlacePercentage;
    TextView fourthPlaceScore;
    TextView fourthPlacePercentage;
    TextView fifthPlaceScore;
    TextView fifthPlacePercentage;
    GameTypes gameType;

    public void setGameType(GameTypes gameType)
    {
        this.gameType = gameType;
    }

    public LocalHighscoreFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_local_highscore, container, false);

        firstPlaceScore = view.findViewById(R.id.FirstPlaceScore);
        firstPlacePercentage = view.findViewById(R.id.FirstPlacePercentage);
        secondPlaceScore = view.findViewById(R.id.SecondPlaceScore);
        secondPlacePercentage = view.findViewById(R.id.SecondPlacePercentage);
        thirdPlaceScore = view.findViewById(R.id.ThirdPlaceScore);
        thirdPlacePercentage = view.findViewById(R.id.ThirdPlacePercentage);
        fourthPlaceScore = view.findViewById(R.id.FourthPlaceScore);
        fourthPlacePercentage = view.findViewById(R.id.FourthPlacePercentage);
        fifthPlaceScore = view.findViewById(R.id.FifthPlaceScore);
        fifthPlacePercentage = view.findViewById(R.id.FifthPlacePercentage);

        LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager(HighscoreActivity.context);
        ShowScore(localDatabaseManager.GetLocalHighscores(gameType));

        return view;
    }

    public void ShowScore(ArrayList<Score> highscores)
    {
        if (highscores.size() == 0)
        {
            Toast.makeText(HighscoreActivity.context, "Highscores list is emtpy", Toast.LENGTH_SHORT).show();
            return;
        }

        if (highscores.size() > 0)
        {
            Score score = highscores.get(0);
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            firstPlaceScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            firstPlacePercentage.setText(percentage);
        }
        if (highscores.size() > 1)
        {
            Score score = highscores.get(1);
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            secondPlaceScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            secondPlacePercentage.setText(percentage);
        }
        if (highscores.size() > 2)
        {
            Score score = highscores.get(2);
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            thirdPlaceScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            thirdPlacePercentage.setText(percentage);
        }
        if (highscores.size() > 3)
        {
            Score score = highscores.get(3);
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            fourthPlaceScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            fourthPlacePercentage.setText(percentage);
        }
        if (highscores.size() == 5)
        {
            Score score = highscores.get(4);
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            fifthPlaceScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            fifthPlacePercentage.setText(percentage);
        }
    }
}
