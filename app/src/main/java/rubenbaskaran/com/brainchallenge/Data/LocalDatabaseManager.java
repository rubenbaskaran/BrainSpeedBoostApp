package rubenbaskaran.com.brainchallenge.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import rubenbaskaran.com.brainchallenge.Highscore.Scores;

/**
 * Created by Ruben on 17-01-2018.
 */

public class LocalDatabaseManager extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BrainSpeedChallengeDatabase.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LocalDatabaseContract.ScoresTable.TABLE_NAME + " (" +
                    LocalDatabaseContract.ScoresTable._ID + " INTEGER PRIMARY KEY," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_CORRECTLY_ANSWERED + " INTEGER," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_ANSWERED + " INTEGER," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_CORRECTLY_ANSWERED + " INTEGER," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_ANSWERED + " INTEGER," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_CORRECTLY_ANSWERED + " INTEGER," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_ANSWERED + " INTEGER)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + LocalDatabaseContract.ScoresTable.TABLE_NAME;

    public LocalDatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void ResetDatabase()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
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
