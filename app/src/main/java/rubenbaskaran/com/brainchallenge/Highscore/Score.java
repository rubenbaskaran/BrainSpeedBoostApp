package rubenbaskaran.com.brainchallenge.Highscore;

import java.io.Serializable;

/**
 * Created by Ruben on 17-01-2018.
 */

public class Score implements Serializable
{
    public int LevelOneQuestionsAnswered;
    public int LevelOneQuestionsAnsweredCorrectly;
    public double LevelOnePercentage;

    public int LevelTwoQuestionsAnswered;
    public int LevelTwoQuestionsAnsweredCorrectly;
    public double LevelTwoPercentage;

    public int LevelThreeQuestionsAnswered;
    public int LevelThreeQuestionsAnsweredCorrectly;
    public double LevelThreePercentage;

    public Score()
    {
    }

    public Score(int levelOneQuestionsAnswered, int levelOneQuestionsAnsweredCorrectly, double levelOnePercentage, int levelTwoQuestionsAnswered, int levelTwoQuestionsAnsweredCorrectly, double levelTwoPercentage, int levelThreeQuestionsAnswered, int levelThreeQuestionsAnsweredCorrectly, double levelThreePercentage)
    {
        LevelOneQuestionsAnswered = levelOneQuestionsAnswered;
        LevelOneQuestionsAnsweredCorrectly = levelOneQuestionsAnsweredCorrectly;
        LevelOnePercentage = levelOnePercentage;
        LevelTwoQuestionsAnswered = levelTwoQuestionsAnswered;
        LevelTwoQuestionsAnsweredCorrectly = levelTwoQuestionsAnsweredCorrectly;
        LevelTwoPercentage = levelTwoPercentage;
        LevelThreeQuestionsAnswered = levelThreeQuestionsAnswered;
        LevelThreeQuestionsAnsweredCorrectly = levelThreeQuestionsAnsweredCorrectly;
        LevelThreePercentage = levelThreePercentage;
    }
}
