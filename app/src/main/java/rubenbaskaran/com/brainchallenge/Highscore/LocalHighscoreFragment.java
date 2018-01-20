package rubenbaskaran.com.brainchallenge.Highscore;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rubenbaskaran.com.brainchallenge.Data.Managers.LocalDatabaseManager;
import rubenbaskaran.com.brainchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainchallenge.Models.Score;
import rubenbaskaran.com.brainchallenge.R;

public class LocalHighscoreFragment extends Fragment
{
    TextView LevelOneFirstScore;
    TextView LevelTwoFirstScore;
    TextView LevelThreeFirstScore;

    public void setGameType(GameTypes gameType)
    {
        this.gameType = gameType;
    }

    GameTypes gameType;

    public LocalHighscoreFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_local_highscore, container, false);

        LevelOneFirstScore = view.findViewById(R.id.LevelOneFirstScore);
        LevelTwoFirstScore = view.findViewById(R.id.LevelTwoFirstScore);
        LevelThreeFirstScore = view.findViewById(R.id.LevelThreeFirstScore);

        LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager(HighscoreActivity.context);
        ShowScore(localDatabaseManager.GetLocalHighscores(gameType));

        return view;
    }

    public void ShowScore(ArrayList<Score> highscores)
    {
        Score score = highscores.get(0);

        String output1 = score.AnsweredCorrectly + " out of " + score.Answered;
        LevelOneFirstScore.setText(output1);
    }
}
