package rubenbaskaran.com.brainchallenge.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rubenbaskaran.com.brainchallenge.Databases.Managers.LocalDatabaseManager;
import rubenbaskaran.com.brainchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainchallenge.R;

public class MainMenuActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager(getApplicationContext());
        localDatabaseManager.GetLocalHighscores(GameTypes.Addition);
        localDatabaseManager.GetLocalHighscores(GameTypes.Subtraction);
        localDatabaseManager.GetLocalHighscores(GameTypes.Multiplication);
        localDatabaseManager.GetLocalHighscores(GameTypes.Division);
        localDatabaseManager.GetLocalHighscores(GameTypes.Color);
    }

    public void NavigateToNumbersMenu(View view)
    {
        Intent i = new Intent(getApplicationContext(), NumbersMenuActivity.class);
        startActivity(i);
    }

    public void NavigateToColors(View view)
    {
    }
}
