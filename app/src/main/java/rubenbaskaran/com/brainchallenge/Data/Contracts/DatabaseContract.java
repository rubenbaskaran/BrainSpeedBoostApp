package rubenbaskaran.com.brainchallenge.Data.Contracts;

import android.provider.BaseColumns;

/**
 * Created by Ruben on 18-01-2018.
 */

public class DatabaseContract
{
    public DatabaseContract()
    {
    }

    public static class AdditionHighscore implements BaseColumns
    {
        public static final String TABLE_NAME = "additionhighscore";
        public static final String COLUMN_NAME_ANSWERED_CORRECTLY = "answeredcorrectly";
        public static final String COLUMN_NAME_ANSWERED = "answered";
        public static final String COLUMN_NAME_PERCENTAGE = "percentage";
    }

    public static class SubtractionHighscore implements BaseColumns
    {
        public static final String TABLE_NAME = "subtractionhighscore";
        public static final String COLUMN_NAME_ANSWERED_CORRECTLY = "answeredcorrectly";
        public static final String COLUMN_NAME_ANSWERED = "answered";
        public static final String COLUMN_NAME_PERCENTAGE = "percentage";
    }

    public static class MultiplicationHighscore implements BaseColumns
    {
        public static final String TABLE_NAME = "multiplicationhighscore";
        public static final String COLUMN_NAME_ANSWERED_CORRECTLY = "answeredcorrectly";
        public static final String COLUMN_NAME_ANSWERED = "answered";
        public static final String COLUMN_NAME_PERCENTAGE = "percentage";
    }

    public static class DivisionHighscore implements BaseColumns
    {
        public static final String TABLE_NAME = "divisionhighscore";
        public static final String COLUMN_NAME_ANSWERED_CORRECTLY = "answeredcorrectly";
        public static final String COLUMN_NAME_ANSWERED = "answered";
        public static final String COLUMN_NAME_PERCENTAGE = "percentage";
    }

    public static class ColorHighscore implements BaseColumns
    {
        public static final String TABLE_NAME = "colorhighscore";
        public static final String COLUMN_NAME_ANSWERED_CORRECTLY = "answeredcorrectly";
        public static final String COLUMN_NAME_ANSWERED = "answered";
        public static final String COLUMN_NAME_PERCENTAGE = "percentage";
    }
}
