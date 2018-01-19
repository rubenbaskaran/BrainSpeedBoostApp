package rubenbaskaran.com.brainchallenge.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import rubenbaskaran.com.brainchallenge.Highscore.Score;

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
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_PERCENTAGE + " DOUBLE," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_CORRECTLY_ANSWERED + " INTEGER," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_ANSWERED + " INTEGER," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_PERCENTAGE + " DOUBLE," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_CORRECTLY_ANSWERED + " INTEGER," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_ANSWERED + " INTEGER," +
                    LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_PERCENTAGE + " DOUBLE)";
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

    public void SaveNewScore(Score score)
    {
        ResetDatabase();

        ContentValues values = new ContentValues();
        values.put(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_CORRECTLY_ANSWERED, score.LevelOneQuestionsAnsweredCorrectly);
        values.put(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_ANSWERED, score.LevelOneQuestionsAnswered);
        values.put(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_PERCENTAGE, score.LevelOnePercentage);
        values.put(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_CORRECTLY_ANSWERED, score.LevelTwoQuestionsAnsweredCorrectly);
        values.put(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_ANSWERED, score.LevelTwoQuestionsAnswered);
        values.put(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_PERCENTAGE, score.LevelTwoPercentage);
        values.put(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_CORRECTLY_ANSWERED, score.LevelThreeQuestionsAnsweredCorrectly);
        values.put(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_ANSWERED, score.LevelThreeQuestionsAnswered);
        values.put(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_PERCENTAGE, score.LevelThreePercentage);

        SQLiteDatabase db = getWritableDatabase();
        long returnValue = db.insert(LocalDatabaseContract.ScoresTable.TABLE_NAME, null, values);

        if (returnValue == -1)
        {
            Log.e("Error", "Couldn't save record to local database");
        }
    }

    public ArrayList<Score> GetLocalHighscores()
    {
        String[] projection =
                {
                        LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_CORRECTLY_ANSWERED,
                        LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_ANSWERED,
                        LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_PERCENTAGE,
                        LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_CORRECTLY_ANSWERED,
                        LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_ANSWERED,
                        LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_PERCENTAGE,
                        LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_CORRECTLY_ANSWERED,
                        LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_ANSWERED,
                        LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_PERCENTAGE
                };

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                LocalDatabaseContract.ScoresTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Score> scores = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int levelOneAnsweredCorrectly = cursor.getInt(cursor.getColumnIndexOrThrow(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_CORRECTLY_ANSWERED));
            int levelOneAnswered = cursor.getInt(cursor.getColumnIndexOrThrow(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_ANSWERED));
            double levelOnePercentage = cursor.getInt(cursor.getColumnIndexOrThrow(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELONE_SCORE_PERCENTAGE));
            int levelTwoAnsweredCorrectly = cursor.getInt(cursor.getColumnIndexOrThrow(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_CORRECTLY_ANSWERED));
            int levelTwoAnswered = cursor.getInt(cursor.getColumnIndexOrThrow(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_ANSWERED));
            double levelTwoPercentage = cursor.getInt(cursor.getColumnIndexOrThrow(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTWO_SCORE_PERCENTAGE));
            int levelThreeAnsweredCorrectly = cursor.getInt(cursor.getColumnIndexOrThrow(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_CORRECTLY_ANSWERED));
            int levelThreeAnswered = cursor.getInt(cursor.getColumnIndexOrThrow(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_ANSWERED));
            double levelThreePercentage = cursor.getInt(cursor.getColumnIndexOrThrow(LocalDatabaseContract.ScoresTable.COLUMN_NAME_LEVELTHREE_SCORE_PERCENTAGE));

            scores.add(new Score
                    (
                            levelOneAnswered,
                            levelOneAnsweredCorrectly,
                            levelOnePercentage,
                            levelTwoAnswered,
                            levelTwoAnsweredCorrectly,
                            levelTwoPercentage,
                            levelThreeAnswered,
                            levelThreeAnsweredCorrectly,
                            levelThreePercentage
                    ));
        }
        cursor.close();

        if (scores.isEmpty())
        {
            Score score = new Score
                    (
                            10,
                            10,
                            100,
                            10,
                            10,
                            100,
                            10,
                            10,
                            100
                    );

            scores.add(score);
        }

        return scores;
    }
}
