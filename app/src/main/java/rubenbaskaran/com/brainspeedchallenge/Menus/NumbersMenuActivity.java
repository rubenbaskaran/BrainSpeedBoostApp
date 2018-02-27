package rubenbaskaran.com.brainspeedchallenge.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rubenbaskaran.com.brainspeedchallenge.Databases.Managers.LocalDatabaseManager;
import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.R;

public class NumbersMenuActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_menu);

        LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager(getApplicationContext());
        localDatabaseManager.GetLocalHighscores(GameTypes.Addition);
        localDatabaseManager.GetLocalHighscores(GameTypes.Subtraction);
        localDatabaseManager.GetLocalHighscores(GameTypes.Multiplication);
        localDatabaseManager.GetLocalHighscores(GameTypes.Division);
        localDatabaseManager.GetLocalHighscores(GameTypes.Color);
    }

    public void StartGame(View view)
    {
        String tag = (String) view.getTag();
        Intent i = null;

        switch (tag)
        {
            case "addition":
                i = new Intent(getApplicationContext(), AdditionMenuActivity.class);
                break;
            case "subtraction":
                i = new Intent(getApplicationContext(), SubtractionMenuActivity.class);
                break;
            case "multiplication":
                i = new Intent(getApplicationContext(), MultiplicationMenuActivity.class);
                break;
            case "division":
                i = new Intent(getApplicationContext(), DivisionMenuActivity.class);
                break;
        }

        startActivity(i);
    }
}
