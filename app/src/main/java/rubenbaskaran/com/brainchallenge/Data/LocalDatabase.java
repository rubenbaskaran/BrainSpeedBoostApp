package rubenbaskaran.com.brainchallenge.Data;

import java.util.ArrayList;

import rubenbaskaran.com.brainchallenge.Highscore.Scores;

/**
 * Created by Ruben on 17-01-2018.
 */

public class LocalDatabase
{
    public LocalDatabase()
    {
    }

    public void SaveNewScore(Scores score)
    {
        // TODO: If less than 3 existing scores then save new score
        // TODO: If existing 3 scores have less than 100% percentage, then compare percentage, and if new score has higher percentage then save it
        // TODO: If existing 3 scores have 100% percentage, then compare number of correctly answered questions, and save new score if it has higher amount
    }

    public ArrayList<Scores> GetLocalHighscores()
    {
        // TODO: Get top 3 highscores from SQLite database

        Scores score = new Scores();
        score.LevelOneQuestionsAnsweredCorrectly = 10;
        score.LevelOneQuestionsAnswered = 20;
        score.LevelOnePercentage = 50;

        score.LevelTwoQuestionsAnsweredCorrectly = 5;
        score.LevelTwoQuestionsAnswered = 10;
        score.LevelTwoPercentage = 50;

        score.LevelThreeQuestionsAnsweredCorrectly = 2;
        score.LevelThreeQuestionsAnswered = 5;
        score.LevelThreePercentage = 50;

        ArrayList<Scores> scores = new ArrayList<Scores>();
        scores.add(score);
        return scores;
    }
}
