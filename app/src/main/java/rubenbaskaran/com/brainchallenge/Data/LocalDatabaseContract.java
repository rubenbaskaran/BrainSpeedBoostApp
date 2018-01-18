package rubenbaskaran.com.brainchallenge.Data;

import android.provider.BaseColumns;

/**
 * Created by Ruben on 18-01-2018.
 */

public class LocalDatabaseContract
{
    public LocalDatabaseContract()
    {
    }

    public static class ScoresTable implements BaseColumns
    {
        public static final String TABLE_NAME = "scores";
        public static final String COLUMN_NAME_LEVELONE_SCORE_CORRECTLY_ANSWERED = "levelonescorecorrectlyanswered";
        public static final String COLUMN_NAME_LEVELONE_SCORE_ANSWERED = "levelonescoreanswered";
        public static final String COLUMN_NAME_LEVELTWO_SCORE_CORRECTLY_ANSWERED = "leveltwoscorecorrectlyanswered";
        public static final String COLUMN_NAME_LEVELTWO_SCORE_ANSWERED = "leveltwoscoreanswered";
        public static final String COLUMN_NAME_LEVELTHREE_SCORE_CORRECTLY_ANSWERED = "levelthreescorecorrectlyanswered";
        public static final String COLUMN_NAME_LEVELTHREE_SCORE_ANSWERED = "levelthreescoreanswered";
    }
}
