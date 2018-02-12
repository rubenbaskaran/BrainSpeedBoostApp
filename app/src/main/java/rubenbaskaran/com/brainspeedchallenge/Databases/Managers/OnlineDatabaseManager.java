package rubenbaskaran.com.brainspeedchallenge.Databases.Managers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import rubenbaskaran.com.brainspeedchallenge.Databases.Contracts.DatabaseContract;
import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.Models.Score;

/**
 * Created by Ruben on 17-01-2018.
 */

public class OnlineDatabaseManager
{
    FirebaseDatabase firebaseDatabase;

    public OnlineDatabaseManager()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();

//        SaveNewScoreOnline(new Score(GameTypes.Addition, 10, 0, 0));
        SaveNewScoreOnline(new Score(GameTypes.Addition, 10, 1, 10));
//        SaveNewScoreOnline(new Score(GameTypes.Addition, 10, 2, 20));
//        SaveNewScoreOnline(new Score(GameTypes.Addition, 10, 3, 30));
//        SaveNewScoreOnline(new Score(GameTypes.Addition, 10, 4, 40));
//        SaveNewScoreOnline(new Score(GameTypes.Addition, 10, 5, 50));
//        SaveNewScoreOnline(new Score(GameTypes.Addition, 10, 6, 60));
//        SaveNewScoreOnline(new Score(GameTypes.Addition, 10, 7, 70));
//        SaveNewScoreOnline(new Score(GameTypes.Addition, 10, 8, 80));
//        SaveNewScoreOnline(new Score(GameTypes.Addition, 10, 9, 90));
//        SaveNewScoreOnline(new Score(GameTypes.Addition, 10, 10, 100));
    }

    public boolean SaveNewScoreOnline(final Score score)
    {
        final ArrayList<Score> highscoreList = new ArrayList<>();
        DatabaseReference highscoresGameTypeReference = firebaseDatabase.getReference(score.getGameType().toString());
        Query myQuery = highscoresGameTypeReference.orderByValue();
        myQuery.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.e("Existing item", snapshot.toString());
                    Score score = new Score();

                    score.set_Id((snapshot.child("_Id").getValue(Long.class)).intValue());
                    score.setGameType(Enum.valueOf(GameTypes.class, snapshot.child("gameType").getValue(String.class)));
                    score.setAnswered((snapshot.child("answered").getValue(Long.class)).intValue());
                    score.setAnsweredCorrectly((snapshot.child("answeredCorrectly").getValue(Long.class)).intValue());
                    score.setPercentage((snapshot.child("percentage").getValue(Long.class)).intValue());

                    highscoreList.add(score);
                }

                boolean listIsFull = highscoreList.size() >= 5;
                Log.e("ListIsFull", String.valueOf(listIsFull));
                String scoreKey;

                if (listIsFull)
                {
                    Log.e("SaveNewScore", "List is full!");
                    if (!ValidateHighscore(score, highscoreList))
                    {
                        Log.e("SaveNewScore", "Not a new highscore - Returning!");
                        return;
                    }
                    Log.e("SaveNewScore", "New highscore!");
                    Log.e("SaveNewScore", "Overwriting the lowest score on the top 5!");
                    score.set_Id(5); // TODO: Should be the lowest score instead of just the 5th
                    firebaseDatabase.getReference().child(score.getGameType().toString()).child("5").setValue(score);
                }
                else
                {
                    scoreKey = String.valueOf(highscoreList.size() + 1);
                    Log.e("ScoreKey", scoreKey);
                    score.set_Id(Integer.valueOf(scoreKey));
                    Log.e("SaveNewScore", "List has less than 5 rows. No need to overwrite existing one!");
                    firebaseDatabase.getReference().child(score.getGameType().toString()).child(scoreKey).setValue(score);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        return true;
    }

    public ArrayList<Score> GetOnlineHighscores(GameTypes gameType)
    {
        // TODO: ADD LOADING ANIMATION BEFORE AND AFTER

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

        SQLiteDatabase db = null; // getReadableDatabase();
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
            int levelOneAnsweredCorrectly = cursor.getInt(cursor.getColumnIndexOrThrow(AnsweredCorrectlyColumn));
            int levelOneAnswered = cursor.getInt(cursor.getColumnIndexOrThrow(AnsweredColumn));
            int levelOnePercentage = cursor.getInt(cursor.getColumnIndexOrThrow(PercentageColumn));

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
            Log.e("GetOnlineHighscores", gameType.toString() + " highscores is empty");
        }

        for (Score score : scores)
        {
            Log.e("Item: ", score.toString());
        }
        return scores;
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
}
