package rubenbaskaran.com.brainchallenge.Data.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import rubenbaskaran.com.brainchallenge.Data.Contracts.DatabaseContract;
import rubenbaskaran.com.brainchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainchallenge.Models.Score;

/**
 * Created by Ruben on 17-01-2018.
 */

public class LocalDatabaseManager extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BrainSpeedChallengeDatabase.db";

    private static final String SQL_CREATE_ADDITION_TABLE =
            "CREATE TABLE " + DatabaseContract.AdditionHighscore.TABLE_NAME + " (" +
                    DatabaseContract.AdditionHighscore._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY + " INTEGER," +
                    DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED + " INTEGER," +
                    DatabaseContract.AdditionHighscore.COLUMN_NAME_PERCENTAGE + " DOUBLE)";

    private static final String SQL_CREATE_SUBTRACTION_TABLE =
            "CREATE TABLE " + DatabaseContract.SubtractionHighscore.TABLE_NAME + " (" +
                    DatabaseContract.SubtractionHighscore._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY + " INTEGER," +
                    DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED + " INTEGER," +
                    DatabaseContract.SubtractionHighscore.COLUMN_NAME_PERCENTAGE + " DOUBLE)";

    private static final String SQL_CREATE_MULTIPLICATION_TABLE =
            "CREATE TABLE " + DatabaseContract.MultiplicationHighscore.TABLE_NAME + " (" +
                    DatabaseContract.MultiplicationHighscore._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED_CORRECTLY + " INTEGER," +
                    DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED + " INTEGER," +
                    DatabaseContract.MultiplicationHighscore.COLUMN_NAME_PERCENTAGE + " DOUBLE)";

    private static final String SQL_CREATE_DIVISION_TABLE =
            "CREATE TABLE " + DatabaseContract.DivisionHighscore.TABLE_NAME + " (" +
                    DatabaseContract.DivisionHighscore._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY + " INTEGER," +
                    DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED + " INTEGER," +
                    DatabaseContract.DivisionHighscore.COLUMN_NAME_PERCENTAGE + " DOUBLE)";

    private static final String SQL_CREATE_COLOR_TABLE =
            "CREATE TABLE " + DatabaseContract.ColorHighscore.TABLE_NAME + " (" +
                    DatabaseContract.ColorHighscore._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED_CORRECTLY + " INTEGER," +
                    DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED + " INTEGER," +
                    DatabaseContract.ColorHighscore.COLUMN_NAME_PERCENTAGE + " DOUBLE)";

    private static final String SQL_DELETE_ADDITION_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.AdditionHighscore.TABLE_NAME;
    private static final String SQL_DELETE_SUBTRACTION_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.SubtractionHighscore.TABLE_NAME;
    private static final String SQL_DELETE_MULTIPLICATION_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.MultiplicationHighscore.TABLE_NAME;
    private static final String SQL_DELETE_DIVISION_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.DivisionHighscore.TABLE_NAME;
    private static final String SQL_DELETE_COLOR_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.ColorHighscore.TABLE_NAME;

    public LocalDatabaseManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ADDITION_TABLE);
        db.execSQL(SQL_CREATE_SUBTRACTION_TABLE);
        db.execSQL(SQL_CREATE_MULTIPLICATION_TABLE);
        db.execSQL(SQL_CREATE_DIVISION_TABLE);
        db.execSQL(SQL_CREATE_COLOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        DeleteTables(db);
        onCreate(db);
    }

    public void DeleteTables(SQLiteDatabase db)
    {
        db.execSQL(SQL_DELETE_ADDITION_TABLE);
        db.execSQL(SQL_DELETE_SUBTRACTION_TABLE);
        db.execSQL(SQL_DELETE_MULTIPLICATION_TABLE);
        db.execSQL(SQL_DELETE_DIVISION_TABLE);
        db.execSQL(SQL_DELETE_COLOR_TABLE);
    }

    public void ResetDatabase()
    {
        SQLiteDatabase db = getWritableDatabase();
        DeleteTables(db);
        onCreate(db);
    }

    public boolean SaveNewScore(Score score)
    {
        ArrayList<Score> highscoreList = GetLocalHighscores(score.GameType);
        boolean listIsFull = highscoreList.size() >= 5;

        if (listIsFull)
        {
            Log.e("SaveNewScore", "List is full!");
            if (!ValidateHighscore(score, highscoreList))
            {
                Log.e("SaveNewScore", "Not a new highscore - Returning!");
                return false;
            }
            Log.e("SaveNewScore", "New highscore!");
        }

        ContentValues values = new ContentValues();
        String table = "";
        String rowColumnToDelete = null;

        switch (score.GameType)
        {
            case Addition:
                table = DatabaseContract.AdditionHighscore.TABLE_NAME;
                values.put(DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.AnsweredCorrectly);
                values.put(DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED, score.Answered);
                values.put(DatabaseContract.AdditionHighscore.COLUMN_NAME_PERCENTAGE, score.Percentage);
                rowColumnToDelete = DatabaseContract.AdditionHighscore._ID;
                break;
            case Subtraction:
                table = DatabaseContract.SubtractionHighscore.TABLE_NAME;
                values.put(DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.AnsweredCorrectly);
                values.put(DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED, score.Answered);
                values.put(DatabaseContract.SubtractionHighscore.COLUMN_NAME_PERCENTAGE, score.Percentage);
                rowColumnToDelete = DatabaseContract.SubtractionHighscore._ID;
                break;
            case Multiplication:
                table = DatabaseContract.MultiplicationHighscore.TABLE_NAME;
                values.put(DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.AnsweredCorrectly);
                values.put(DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED, score.Answered);
                values.put(DatabaseContract.MultiplicationHighscore.COLUMN_NAME_PERCENTAGE, score.Percentage);
                rowColumnToDelete = DatabaseContract.MultiplicationHighscore._ID;
                break;
            case Division:
                table = DatabaseContract.DivisionHighscore.TABLE_NAME;
                values.put(DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.AnsweredCorrectly);
                values.put(DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED, score.Answered);
                values.put(DatabaseContract.DivisionHighscore.COLUMN_NAME_PERCENTAGE, score.Percentage);
                rowColumnToDelete = DatabaseContract.DivisionHighscore._ID;
                break;
            case Color:
                table = DatabaseContract.ColorHighscore.TABLE_NAME;
                values.put(DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.AnsweredCorrectly);
                values.put(DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED, score.Answered);
                values.put(DatabaseContract.ColorHighscore.COLUMN_NAME_PERCENTAGE, score.Percentage);
                rowColumnToDelete = DatabaseContract.ColorHighscore._ID;
                break;
        }

        SQLiteDatabase db = getWritableDatabase();

        if (listIsFull)
        {
            String[] arguments = {String.valueOf(highscoreList.get(0)._Id)};
            Log.e("SaveNewScore", "Deleting the lowest score on the top 10!");
            long returnValue = db.delete(table, rowColumnToDelete + " = ?", arguments);

            if (returnValue == -1)
            {
                Log.e("Error", "Couldn't delete record from local database");
            }
        }
        else
        {
            Log.e("SaveNewScore", "List has less than 5 rows. No need to delete existing ones!");
        }

        long returnValue = db.insert(table, null, values);

        if (returnValue == -1)
        {
            Log.e("Error", "Couldn't save record to local database");
        }

        return true;
    }

    private boolean ValidateHighscore(Score newScore, ArrayList<Score> oldScores)
    {
        for (Score oldScore : oldScores)
        {
            if (newScore.Percentage > oldScore.Percentage)
                return true;
            else if (newScore.Percentage == oldScore.Percentage && newScore.AnsweredCorrectly > oldScore.AnsweredCorrectly)
            {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Score> GetLocalHighscores(GameTypes gameType)
    {
        String id = null;
        String tableName = null;
        String AnsweredCorrectlyColumn = null;
        String AnsweredColumn = null;
        String PercentageColumn = null;

        switch (gameType)
        {
            case Addition:
                id = DatabaseContract.AdditionHighscore._ID;
                tableName = DatabaseContract.AdditionHighscore.TABLE_NAME;
                AnsweredCorrectlyColumn = DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.AdditionHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Subtraction:
                id = DatabaseContract.SubtractionHighscore._ID;
                tableName = DatabaseContract.SubtractionHighscore.TABLE_NAME;
                AnsweredCorrectlyColumn = DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.SubtractionHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Multiplication:
                id = DatabaseContract.MultiplicationHighscore._ID;
                tableName = DatabaseContract.MultiplicationHighscore.TABLE_NAME;
                AnsweredCorrectlyColumn = DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.MultiplicationHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Division:
                id = DatabaseContract.DivisionHighscore._ID;
                tableName = DatabaseContract.DivisionHighscore.TABLE_NAME;
                AnsweredCorrectlyColumn = DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.DivisionHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Color:
                id = DatabaseContract.ColorHighscore._ID;
                tableName = DatabaseContract.ColorHighscore.TABLE_NAME;
                AnsweredCorrectlyColumn = DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.ColorHighscore.COLUMN_NAME_PERCENTAGE;
                break;
        }

        String[] projection =
                {
                        id,
                        AnsweredCorrectlyColumn,
                        AnsweredColumn,
                        PercentageColumn
                };

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                tableName,
                projection,
                null,
                null,
                null,
                null,
                PercentageColumn + " ASC," + AnsweredCorrectlyColumn + " ASC;"
        );

        ArrayList<Score> scores = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Integer _ID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            int levelOneAnsweredCorrectly = cursor.getInt(cursor.getColumnIndexOrThrow(AnsweredCorrectlyColumn));
            int levelOneAnswered = cursor.getInt(cursor.getColumnIndexOrThrow(AnsweredColumn));
            double levelOnePercentage = cursor.getInt(cursor.getColumnIndexOrThrow(PercentageColumn));

            scores.add(new Score
                    (
                            _ID,
                            gameType,
                            levelOneAnswered,
                            levelOneAnsweredCorrectly,
                            levelOnePercentage
                    ));
        }
        cursor.close();

        if (scores.isEmpty())
        {
            Log.e("GetLocalHighscores", gameType.toString() + " highscores is empty");
        }

        for (Score score : scores)
        {
            Log.e("Item: ", score.toString());
        }
        return scores;
    }
}
