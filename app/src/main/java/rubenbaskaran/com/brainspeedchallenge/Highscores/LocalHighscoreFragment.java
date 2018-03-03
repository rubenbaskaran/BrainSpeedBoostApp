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
    //region Fields
    TextView firstPlaceName;
    TextView firstPlaceScore;
    TextView firstPlacePercentage;
    TextView secondPlaceName;
    TextView secondPlaceScore;
    TextView secondPlacePercentage;
    TextView thirdPlaceName;
    TextView thirdPlaceScore;
    TextView thirdPlacePercentage;
    TextView fourthPlaceName;
    TextView fourthPlaceScore;
    TextView fourthPlacePercentage;
    TextView fifthPlaceName;
    TextView fifthPlaceScore;
    TextView fifthPlacePercentage;
    GameTypes gameType;
    //endregion

    //region Properties
    public void setGameType(GameTypes gameType)
    {
        this.gameType = gameType;
    }
    //endregion

    public LocalHighscoreFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_local_highscore, container, false);

        firstPlaceName = view.findViewById(R.id.FirstPlaceName);
        firstPlaceScore = view.findViewById(R.id.FirstPlaceScore);
        firstPlacePercentage = view.findViewById(R.id.FirstPlacePercentage);
        secondPlaceName = view.findViewById(R.id.SecondPlaceName);
        secondPlaceScore = view.findViewById(R.id.SecondPlaceScore);
        secondPlacePercentage = view.findViewById(R.id.SecondPlacePercentage);
        thirdPlaceName = view.findViewById(R.id.ThirdPlaceName);
        thirdPlaceScore = view.findViewById(R.id.ThirdPlaceScore);
        thirdPlacePercentage = view.findViewById(R.id.ThirdPlacePercentage);
        fourthPlaceName = view.findViewById(R.id.FourthPlaceName);
        fourthPlaceScore = view.findViewById(R.id.FourthPlaceScore);
        fourthPlacePercentage = view.findViewById(R.id.FourthPlacePercentage);
        fifthPlaceName = view.findViewById(R.id.FifthPlaceName);
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
            Toast.makeText(HighscoreActivity.context, "No local highscores", Toast.LENGTH_SHORT).show();
            return;
        }

        if (highscores.size() > 0)
        {
            Score score = highscores.get(0);
            firstPlaceName.setText(score.getUsername());
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            firstPlaceScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            firstPlacePercentage.setText(percentage);
        }
        if (highscores.size() > 1)
        {
            Score score = highscores.get(1);
            secondPlaceName.setText(score.getUsername());
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            secondPlaceScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            secondPlacePercentage.setText(percentage);
        }
        if (highscores.size() > 2)
        {
            Score score = highscores.get(2);
            thirdPlaceName.setText(score.getUsername());
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            thirdPlaceScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            thirdPlacePercentage.setText(percentage);
        }
        if (highscores.size() > 3)
        {
            Score score = highscores.get(3);
            fourthPlaceName.setText(score.getUsername());
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            fourthPlaceScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            fourthPlacePercentage.setText(percentage);
        }
        if (highscores.size() == 5)
        {
            Score score = highscores.get(4);
            fifthPlaceName.setText(score.getUsername());
            String text = score.getAnsweredCorrectly() + " out of " + score.getAnswered();
            fifthPlaceScore.setText(text);
            String percentage = String.valueOf(score.getPercentage()) + "%";
            fifthPlacePercentage.setText(percentage);
        }
    }
}
