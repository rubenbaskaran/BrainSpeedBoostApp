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
        public static final String COLUMN_NAME_LEVELONE_SCORE = "levelonescore";
        public static final String COLUMN_NAME_LEVELTWO_SCORE = "leveltwoscore";
        public static final String COLUMN_NAME_LEVELTHREE_SCORE = "levelthreescore";
    }
}
