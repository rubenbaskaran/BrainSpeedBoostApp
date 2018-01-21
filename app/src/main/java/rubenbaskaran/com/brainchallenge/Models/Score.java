package rubenbaskaran.com.brainchallenge.Models;

import java.io.Serializable;

import rubenbaskaran.com.brainchallenge.Enums.GameTypes;

/**
 * Created by Ruben on 17-01-2018.
 */

public class Score implements Serializable
{
    public Integer _Id;
    public GameTypes GameType;
    public int Answered;
    public int AnsweredCorrectly;
    public int Percentage;

    public Score()
    {
    }

    public Score(GameTypes gameType, int answered, int answeredcorrectly, int percentage)
    {
        this.GameType = gameType;
        this.Answered = answered;
        this.AnsweredCorrectly = answeredcorrectly;
        this.Percentage = percentage;
    }

    public Score(Integer _id, GameTypes gameType, int answered, int answeredcorrectly, int percentage)
    {
        this._Id = _id;
        this.GameType = gameType;
        this.Answered = answered;
        this.AnsweredCorrectly = answeredcorrectly;
        this.Percentage = percentage;
    }

    @Override
    public String toString()
    {
        return "Score{" +
                "_Id=" + _Id +
                ", GameType=" + GameType +
                ", Answered=" + Answered +
                ", AnsweredCorrectly=" + AnsweredCorrectly +
                ", Percentage=" + Percentage +
                '}';
    }
}