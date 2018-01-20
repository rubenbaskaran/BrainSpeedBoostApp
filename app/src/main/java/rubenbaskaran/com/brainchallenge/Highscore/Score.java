package rubenbaskaran.com.brainchallenge.Highscore;

import java.io.Serializable;

/**
 * Created by Ruben on 17-01-2018.
 */

public class Score implements Serializable
{
    public String Table;
    public int Answered;
    public int AnsweredCorrectly;
    public double Percentage;

    public Score()
    {
    }

    public Score(String table, int answered, int answeredcorrectly, double percentage)
    {
        this.Table = table;
        this.Answered = answered;
        this.AnsweredCorrectly = answeredcorrectly;
        this.Percentage = percentage;
    }
}