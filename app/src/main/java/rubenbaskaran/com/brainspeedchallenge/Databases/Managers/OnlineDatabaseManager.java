package rubenbaskaran.com.brainspeedchallenge.Databases.Managers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.Highscores.GlobalHighscoreFragment;
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
    }

    public void SaveNewScoreOnline(final Score score)
    {
        DatabaseReference gametypeCounterReference = FirebaseDatabase.getInstance().getReference().child(score.getGameType().toString()).child("counter");
        gametypeCounterReference.runTransaction(new Transaction.Handler()
        {
            /* Note: Because doTransaction() is called multiple times, it must be able to handle null data.
               Even if there is existing data in your remote database, it may not be locally cached when the
               transaction function is run, resulting in null for the initial value.
               https://firebase.google.com/docs/database/android/read-and-write#save_data_as_transactions */
            @Override
            public Transaction.Result doTransaction(MutableData mutableData)
            {
                final Long p = mutableData.getValue(Long.class);
                if (p != null)
                {
                    Log.e("doTransaction", "p is " + String.valueOf(p));
                    if (p < 5)
                    {
                        Log.e("SaveNewScoreOnline", "List has less than 5 rows. No need to overwrite existing one!");

                        String scoreKey = String.valueOf(p + 1);
                        Log.e("ScoreKey", scoreKey);
                        score.set_Id(Integer.valueOf(scoreKey));
                        firebaseDatabase.getReference().child(score.getGameType().toString()).child("Scores").child(scoreKey).setValue(score);
                        mutableData.setValue(p + 1);
                        return Transaction.success(mutableData);
                    }
                    else if (p == 5)
                    {
                        Log.e("SaveNewScoreOnline", "List is full! Overwriting existing one if new highscore");

                        final ArrayList<Score> highscoreList = new ArrayList<>();
                        DatabaseReference gametypeScoresReference = firebaseDatabase.getReference(score.getGameType().toString()).child("Scores");
                        Query myQuery = gametypeScoresReference.orderByChild("answeredCorrectly");
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

                                if (!ValidateHighscore(score, highscoreList))
                                {
                                    Log.e("SaveNewScoreOnline", "Not a new highscore - Returning!");
                                    return;
                                }
                                Log.e("SaveNewScoreOnline", "New highscore!");
                                //String username = GetUsernameFromDialog();
                                Log.e("SaveNewScoreOnline", "Overwriting the lowest score on the top 5!");

                                Collections.sort(highscoreList, new Comparator<Score>()
                                {
                                    @Override
                                    public int compare(Score o1, Score o2)
                                    {
                                        if (o1.getPercentage() > o2.getPercentage())
                                            return 1;
                                        if (o1.getPercentage() < o2.getPercentage())
                                            return -1;
                                        return 0;
                                    }
                                });

                                //score.setUsername(username);
                                score.set_Id(highscoreList.get(0).get_Id());
                                firebaseDatabase.getReference().child(score.getGameType().toString()).child("Scores").child(String.valueOf(score.get_Id())).setValue(score);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError)
                            {

                            }
                        });
                    }
                }

                Log.e("doTransaction", "p is null");
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot)
            {
                Log.e("onComplete", "postTransaction:onComplete:" + databaseError);
            }
        });
    }

    public void GetOnlineHighscores(final GlobalHighscoreFragment globalHighscoreFragment)
    {
        final ArrayList<Score> highscoreList = new ArrayList<>();
        DatabaseReference highscoresGameTypeReference = firebaseDatabase.getReference(globalHighscoreFragment.getGameType().toString()).child("Scores");
        Query myQuery = highscoresGameTypeReference.orderByChild("answeredCorrectly");
        myQuery.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.e("Existing item", snapshot.toString());
                    Score score = new Score();

                    // TODO: Get name of user
                    score.set_Id((snapshot.child("_Id").getValue(Long.class)).intValue());
                    score.setGameType(Enum.valueOf(GameTypes.class, snapshot.child("gameType").getValue(String.class)));
                    score.setAnswered((snapshot.child("answered").getValue(Long.class)).intValue());
                    score.setAnsweredCorrectly((snapshot.child("answeredCorrectly").getValue(Long.class)).intValue());
                    score.setPercentage((snapshot.child("percentage").getValue(Long.class)).intValue());

                    highscoreList.add(score);
                }

                Collections.sort(highscoreList, new Comparator<Score>()
                {
                    @Override
                    public int compare(Score o1, Score o2)
                    {
                        if (o1.getPercentage() > o2.getPercentage())
                            return 1;
                        if (o1.getPercentage() < o2.getPercentage())
                            return -1;
                        return 0;
                    }
                });

                globalHighscoreFragment.ShowScore(highscoreList);
                globalHighscoreFragment.HideProgressBar();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
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
