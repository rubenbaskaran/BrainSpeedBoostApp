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
        ResetDatabase();
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

    public void SaveNewScore(Score score)
    {
        ResetDatabase();

        ContentValues values = new ContentValues();

        switch (score.Table)
        {
            case DatabaseContract.AdditionHighscore.TABLE_NAME:
                values.put(DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.AnsweredCorrectly);
                values.put(DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED, score.Answered);
                values.put(DatabaseContract.AdditionHighscore.COLUMN_NAME_PERCENTAGE, score.Percentage);
                break;
            case DatabaseContract.SubtractionHighscore.TABLE_NAME:
                values.put(DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.AnsweredCorrectly);
                values.put(DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED, score.Answered);
                values.put(DatabaseContract.SubtractionHighscore.COLUMN_NAME_PERCENTAGE, score.Percentage);
                break;
            case DatabaseContract.MultiplicationHighscore.TABLE_NAME:
                values.put(DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.AnsweredCorrectly);
                values.put(DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED, score.Answered);
                values.put(DatabaseContract.MultiplicationHighscore.COLUMN_NAME_PERCENTAGE, score.Percentage);
                break;
            case DatabaseContract.DivisionHighscore.TABLE_NAME:
                values.put(DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.AnsweredCorrectly);
                values.put(DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED, score.Answered);
                values.put(DatabaseContract.DivisionHighscore.COLUMN_NAME_PERCENTAGE, score.Percentage);
                break;
            case DatabaseContract.ColorHighscore.TABLE_NAME:
                values.put(DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.AnsweredCorrectly);
                values.put(DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED, score.Answered);
                values.put(DatabaseContract.ColorHighscore.COLUMN_NAME_PERCENTAGE, score.Percentage);
                break;
        }

        SQLiteDatabase db = getWritableDatabase();
        long returnValue = db.insert(score.Table, null, values);

        if (returnValue == -1)
        {
            Log.e("Error", "Couldn't save record to local database");
        }
    }

    public ArrayList<Score> GetLocalHighscores(GameTypes gameTypes)
    {
        String tableName = null;
        String AnsweredCorrectlyColumn = null;
        String AnsweredColumn = null;
        String PercentageColumn = null;

        switch (gameTypes)
        {
            case Addition:
                tableName = DatabaseContract.AdditionHighscore.TABLE_NAME;
                AnsweredCorrectlyColumn = DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.AdditionHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Subtraction:
                tableName = DatabaseContract.SubtractionHighscore.TABLE_NAME;
                AnsweredCorrectlyColumn = DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.SubtractionHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Multiplication:
                tableName = DatabaseContract.MultiplicationHighscore.TABLE_NAME;
                AnsweredCorrectlyColumn = DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.MultiplicationHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Division:
                tableName = DatabaseContract.DivisionHighscore.TABLE_NAME;
                AnsweredCorrectlyColumn = DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.DivisionHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Color:
                tableName = DatabaseContract.ColorHighscore.TABLE_NAME;
                AnsweredCorrectlyColumn = DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.ColorHighscore.COLUMN_NAME_PERCENTAGE;
                break;
        }

        String[] projection =
                {
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
                null
        );

        ArrayList<Score> scores = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int levelOneAnsweredCorrectly = cursor.getInt(cursor.getColumnIndexOrThrow(AnsweredCorrectlyColumn));
            int levelOneAnswered = cursor.getInt(cursor.getColumnIndexOrThrow(AnsweredColumn));
            double levelOnePercentage = cursor.getInt(cursor.getColumnIndexOrThrow(PercentageColumn));

            scores.add(new Score
                    (
                            tableName,
                            levelOneAnswered,
                            levelOneAnsweredCorrectly,
                            levelOnePercentage
                    ));
        }
        cursor.close();

        if (scores.isEmpty())
        {
            Score mockScore = new Score
                    (
                            "Test",
                            10,
                            10,
                            100
                    );

            scores.add(mockScore);
        }

        return scores;
    }
}
