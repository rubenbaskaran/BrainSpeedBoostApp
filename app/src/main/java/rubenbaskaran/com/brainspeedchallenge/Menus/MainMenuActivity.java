package rubenbaskaran.com.brainspeedchallenge.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import rubenbaskaran.com.brainspeedchallenge.Databases.Managers.LocalDatabaseManager;
import rubenbaskaran.com.brainspeedchallenge.Databases.Managers.OnlineDatabaseManager;
import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.R;

public class MainMenuActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // TODO: Remove after test
        OnlineDatabaseManager onlineDb = new OnlineDatabaseManager();

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
        Toast.makeText(getApplicationContext(), "Coming in next update!", Toast.LENGTH_SHORT).show();
    }
}
