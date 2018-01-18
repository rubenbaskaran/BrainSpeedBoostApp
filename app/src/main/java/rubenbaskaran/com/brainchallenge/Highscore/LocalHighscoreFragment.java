package rubenbaskaran.com.brainchallenge.Highscore;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rubenbaskaran.com.brainchallenge.Data.LocalDatabaseManager;
import rubenbaskaran.com.brainchallenge.R;

public class LocalHighscoreFragment extends Fragment
{
    TextView LevelOneFirstScore;
    TextView LevelTwoFirstScore;
    TextView LevelThreeFirstScore;

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

        LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager();
        ShowScore(localDatabaseManager.GetLocalHighscores());

        return view;
    }

    public void ShowScore(ArrayList<Scores> highscores)
    {
        Scores score = highscores.get(0);

        String output1 = score.LevelOneQuestionsAnsweredCorrectly + " out of " + score.LevelOneQuestionsAnswered;
        LevelOneFirstScore.setText(output1);

        String output2 = score.LevelTwoQuestionsAnsweredCorrectly + " out of " + score.LevelTwoQuestionsAnswered;
        LevelTwoFirstScore.setText(output2);

        String output3 = score.LevelThreeQuestionsAnsweredCorrectly + " out of " + score.LevelThreeQuestionsAnswered;
        LevelThreeFirstScore.setText(output3);
    }
}
