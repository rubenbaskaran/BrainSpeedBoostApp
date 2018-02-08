package rubenbaskaran.com.brainspeedchallenge.Models;

import java.io.Serializable;

import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;

/**
 * Created by Ruben on 17-01-2018.
 */

public class Score implements Serializable
{
    private Integer _Id;
    private GameTypes GameType;
    private int Answered;
    private int AnsweredCorrectly;
    private int Percentage;

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

    public Integer get_Id()
    {
        return _Id;
    }

    public void set_Id(Integer _Id)
    {
        this._Id = _Id;
    }

    public GameTypes getGameType()
    {
        return GameType;
    }

    public void setGameType(GameTypes gameType)
    {
        GameType = gameType;
    }

    public int getAnswered()
    {
        return Answered;
    }

    public void setAnswered(int answered)
    {
        Answered = answered;
    }

    public int getAnsweredCorrectly()
    {
        return AnsweredCorrectly;
    }

    public void setAnsweredCorrectly(int answeredCorrectly)
    {
        AnsweredCorrectly = answeredCorrectly;
    }

    public int getPercentage()
    {
        return Percentage;
    }

    public void setPercentage(int percentage)
    {
        Percentage = percentage;
    }
}