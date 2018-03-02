package rubenbaskaran.com.brainspeedchallenge.Databases.Managers;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import rubenbaskaran.com.brainspeedchallenge.Databases.Contracts.DatabaseContract;
import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.Highscores.UsernameDialog;
import rubenbaskaran.com.brainspeedchallenge.Models.Score;

/**
 * Created by Ruben on 17-01-2018.
 */

public class LocalDatabaseManager extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BrainSpeedChallengeDatabase.db";
    public Context context;

    private static final String SQL_CREATE_ADDITION_TABLE =
            "CREATE TABLE " + DatabaseContract.AdditionHighscore.TABLE_NAME + " (" +
                    DatabaseContract.AdditionHighscore._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.AdditionHighscore.COLUMN_NAME_USERNAME + " TEXT," +
                    DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY + " INTEGER," +
                    DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED + " INTEGER," +
                    DatabaseContract.AdditionHighscore.COLUMN_NAME_PERCENTAGE + " INTEGER)";

    private static final String SQL_CREATE_SUBTRACTION_TABLE =
            "CREATE TABLE " + DatabaseContract.SubtractionHighscore.TABLE_NAME + " (" +
                    DatabaseContract.SubtractionHighscore._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.SubtractionHighscore.COLUMN_NAME_USERNAME + " TEXT," +
                    DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY + " INTEGER," +
                    DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED + " INTEGER," +
                    DatabaseContract.SubtractionHighscore.COLUMN_NAME_PERCENTAGE + " INTEGER)";

    private static final String SQL_CREATE_MULTIPLICATION_TABLE =
            "CREATE TABLE " + DatabaseContract.MultiplicationHighscore.TABLE_NAME + " (" +
                    DatabaseContract.MultiplicationHighscore._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.MultiplicationHighscore.COLUMN_NAME_USERNAME + " TEXT," +
                    DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED_CORRECTLY + " INTEGER," +
                    DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED + " INTEGER," +
                    DatabaseContract.MultiplicationHighscore.COLUMN_NAME_PERCENTAGE + " INTEGER)";

    private static final String SQL_CREATE_DIVISION_TABLE =
            "CREATE TABLE " + DatabaseContract.DivisionHighscore.TABLE_NAME + " (" +
                    DatabaseContract.DivisionHighscore._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.DivisionHighscore.COLUMN_NAME_USERNAME + " TEXT," +
                    DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY + " INTEGER," +
                    DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED + " INTEGER," +
                    DatabaseContract.DivisionHighscore.COLUMN_NAME_PERCENTAGE + " INTEGER)";

    private static final String SQL_CREATE_COLOR_TABLE =
            "CREATE TABLE " + DatabaseContract.ColorHighscore.TABLE_NAME + " (" +
                    DatabaseContract.ColorHighscore._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.ColorHighscore.COLUMN_NAME_USERNAME + " TEXT," +
                    DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED_CORRECTLY + " INTEGER," +
                    DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED + " INTEGER," +
                    DatabaseContract.ColorHighscore.COLUMN_NAME_PERCENTAGE + " INTEGER)";

    private static final String SQL_DELETE_ADDITION_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.AdditionHighscore.TABLE_NAME;
    private static final String SQL_DELETE_SUBTRACTION_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.SubtractionHighscore.TABLE_NAME;
    private static final String SQL_DELETE_MULTIPLICATION_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.MultiplicationHighscore.TABLE_NAME;
    private static final String SQL_DELETE_DIVISION_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.DivisionHighscore.TABLE_NAME;
    private static final String SQL_DELETE_COLOR_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.ColorHighscore.TABLE_NAME;

    public LocalDatabaseManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

    public boolean CheckNewScore(Score score, FragmentManager fragmentManager, AppCompatActivity numbersGameActivity)
    {
        ArrayList<Score> highscoreList = GetLocalHighscores(score.getGameType());
        boolean listIsFull = highscoreList.size() >= 5;

        if (listIsFull)
        {
            Log.e("CheckNewScore", "List is full!");
            if (!ValidateHighscore(score, highscoreList))
            {
                Log.e("CheckNewScore", "Not a new highscore - Returning!");
                return true;
            }
            Log.e("CheckNewScore", "New highscore!");
        }

        UsernameDialog usernameDialog = new UsernameDialog();
        usernameDialog.setCancelable(false);
        usernameDialog.setContext(context);
        usernameDialog.setScore(score);
        usernameDialog.setListIsFull(listIsFull);
        usernameDialog.setHighscoreList(highscoreList);
        usernameDialog.setNumbersGameActivity(numbersGameActivity);
        usernameDialog.show(fragmentManager, "UsernameDialog");
        return false;
    }

    public boolean SaveNewScore(Score score, boolean listIsFull, ArrayList<Score> highscoreList)
    {
        ContentValues values = new ContentValues();
        String table = "";
        String rowColumnToDelete = null;

        switch (score.getGameType())
        {
            case Addition:
                table = DatabaseContract.AdditionHighscore.TABLE_NAME;
                values.put(DatabaseContract.AdditionHighscore.COLUMN_NAME_USERNAME, score.getUsername());
                values.put(DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.getAnsweredCorrectly());
                values.put(DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED, score.getAnswered());
                values.put(DatabaseContract.AdditionHighscore.COLUMN_NAME_PERCENTAGE, score.getPercentage());
                rowColumnToDelete = DatabaseContract.AdditionHighscore._ID;
                break;
            case Subtraction:
                table = DatabaseContract.SubtractionHighscore.TABLE_NAME;
                values.put(DatabaseContract.SubtractionHighscore.COLUMN_NAME_USERNAME, score.getUsername());
                values.put(DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.getAnsweredCorrectly());
                values.put(DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED, score.getAnswered());
                values.put(DatabaseContract.SubtractionHighscore.COLUMN_NAME_PERCENTAGE, score.getPercentage());
                rowColumnToDelete = DatabaseContract.SubtractionHighscore._ID;
                break;
            case Multiplication:
                table = DatabaseContract.MultiplicationHighscore.TABLE_NAME;
                values.put(DatabaseContract.MultiplicationHighscore.COLUMN_NAME_USERNAME, score.getUsername());
                values.put(DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.getAnsweredCorrectly());
                values.put(DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED, score.getAnswered());
                values.put(DatabaseContract.MultiplicationHighscore.COLUMN_NAME_PERCENTAGE, score.getPercentage());
                rowColumnToDelete = DatabaseContract.MultiplicationHighscore._ID;
                break;
            case Division:
                table = DatabaseContract.DivisionHighscore.TABLE_NAME;
                values.put(DatabaseContract.DivisionHighscore.COLUMN_NAME_USERNAME, score.getUsername());
                values.put(DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.getAnsweredCorrectly());
                values.put(DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED, score.getAnswered());
                values.put(DatabaseContract.DivisionHighscore.COLUMN_NAME_PERCENTAGE, score.getPercentage());
                rowColumnToDelete = DatabaseContract.DivisionHighscore._ID;
                break;
            case Color:
                table = DatabaseContract.ColorHighscore.TABLE_NAME;
                values.put(DatabaseContract.ColorHighscore.COLUMN_NAME_USERNAME, score.getUsername());
                values.put(DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED_CORRECTLY, score.getAnsweredCorrectly());
                values.put(DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED, score.getAnswered());
                values.put(DatabaseContract.ColorHighscore.COLUMN_NAME_PERCENTAGE, score.getPercentage());
                rowColumnToDelete = DatabaseContract.ColorHighscore._ID;
                break;
        }

        SQLiteDatabase db = getWritableDatabase();

        if (listIsFull)
        {
            String[] arguments = {String.valueOf(highscoreList.get(highscoreList.size() - 1).get_Id())};
            Log.e("CheckNewScore", "Deleting the lowest score on the top 5!");
            long returnValue = db.delete(table, rowColumnToDelete + " = ?", arguments);

            if (returnValue == -1)
            {
                Log.e("Error", "Couldn't delete record from local database");
            }
        }
        else
        {
            Log.e("CheckNewScore", "List has less than 5 rows. No need to delete existing ones!");
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
            if (newScore.getPercentage() > oldScore.getPercentage())
                return true;
            else if (newScore.getPercentage() == oldScore.getPercentage() && newScore.getAnsweredCorrectly() > oldScore.getAnsweredCorrectly())
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
        String Username = null;
        String AnsweredCorrectlyColumn = null;
        String AnsweredColumn = null;
        String PercentageColumn = null;

        switch (gameType)
        {
            case Addition:
                id = DatabaseContract.AdditionHighscore._ID;
                tableName = DatabaseContract.AdditionHighscore.TABLE_NAME;
                Username = DatabaseContract.AdditionHighscore.COLUMN_NAME_USERNAME;
                AnsweredCorrectlyColumn = DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.AdditionHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.AdditionHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Subtraction:
                id = DatabaseContract.SubtractionHighscore._ID;
                tableName = DatabaseContract.SubtractionHighscore.TABLE_NAME;
                Username = DatabaseContract.SubtractionHighscore.COLUMN_NAME_USERNAME;
                AnsweredCorrectlyColumn = DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.SubtractionHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.SubtractionHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Multiplication:
                id = DatabaseContract.MultiplicationHighscore._ID;
                tableName = DatabaseContract.MultiplicationHighscore.TABLE_NAME;
                Username = DatabaseContract.MultiplicationHighscore.COLUMN_NAME_USERNAME;
                AnsweredCorrectlyColumn = DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.MultiplicationHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.MultiplicationHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Division:
                id = DatabaseContract.DivisionHighscore._ID;
                tableName = DatabaseContract.DivisionHighscore.TABLE_NAME;
                Username = DatabaseContract.DivisionHighscore.COLUMN_NAME_USERNAME;
                AnsweredCorrectlyColumn = DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.DivisionHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.DivisionHighscore.COLUMN_NAME_PERCENTAGE;
                break;
            case Color:
                id = DatabaseContract.ColorHighscore._ID;
                tableName = DatabaseContract.ColorHighscore.TABLE_NAME;
                Username = DatabaseContract.ColorHighscore.COLUMN_NAME_USERNAME;
                AnsweredCorrectlyColumn = DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED_CORRECTLY;
                AnsweredColumn = DatabaseContract.ColorHighscore.COLUMN_NAME_ANSWERED;
                PercentageColumn = DatabaseContract.ColorHighscore.COLUMN_NAME_PERCENTAGE;
                break;
        }

        String[] projection =
                {
                        id,
                        Username,
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
                PercentageColumn + " DESC," + AnsweredCorrectlyColumn + " DESC;"
        );

        ArrayList<Score> scores = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Integer _ID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            String _username = cursor.getString(cursor.getColumnIndexOrThrow(Username));
            int levelOneAnsweredCorrectly = cursor.getInt(cursor.getColumnIndexOrThrow(AnsweredCorrectlyColumn));
            int levelOneAnswered = cursor.getInt(cursor.getColumnIndexOrThrow(AnsweredColumn));
            int levelOnePercentage = cursor.getInt(cursor.getColumnIndexOrThrow(PercentageColumn));

            scores.add(new Score
                    (
                            _ID,
                            gameType,
                            levelOneAnswered,
                            levelOneAnsweredCorrectly,
                            levelOnePercentage,
                            _username
                    ));
        }
        cursor.close();

        if (scores.isEmpty())
        {
            Log.e("GetLocalHighscores", gameType.toString() + " highscores list is empty");
        }

        for (Score score : scores)
        {
            Log.e("Item: ", score.toString());
        }
        return scores;
    }
}
