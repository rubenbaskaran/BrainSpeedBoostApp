package rubenbaskaran.com.brainchallenge.Highscore;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rubenbaskaran.com.brainchallenge.R;

public class LocalHighscoreFragment extends Fragment
{
    TextView LevelOneFirstScore;
    TextView LevelTwoFirstScore;
    TextView LevelThreeFirstScore;
    public Scores score = null;

    public void setScore(Scores score)
    {
        this.score = score;
    }

    public LocalHighscoreFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_local_highscore, container, false);

        LevelOneFirstScore = view.findViewById(R.id.LevelOneFirstScore);
        LevelTwoFirstScore = view.findViewById(R.id.LevelTwoFirstScore);
        LevelThreeFirstScore = view.findViewById(R.id.LevelThreeFirstScore);
        ShowScore();

        return view;
    }

    public void ShowScore()
    {
        if (score != null)
        {
            String output1 = score.LevelOneQuestionsAnsweredCorrectly + " out of " + score.LevelOneQuestionsAnswered;
            LevelOneFirstScore.setText(output1);

            String output2 = score.LevelTwoQuestionsAnsweredCorrectly + " out of " + score.LevelTwoQuestionsAnswered;
            LevelTwoFirstScore.setText(output2);

            String output3 = score.LevelThreeQuestionsAnsweredCorrectly + " out of " + score.LevelThreeQuestionsAnswered;
            LevelThreeFirstScore.setText(output3);
        }
    }
}
